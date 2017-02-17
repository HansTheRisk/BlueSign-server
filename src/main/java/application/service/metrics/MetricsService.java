package application.service.metrics;

import application.domain.module.attendance.TotalAverageModuleAttendance;
import application.domain.attendance.Attendance;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.service.attendance.AttendanceService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
import application.util.date.DateUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MetricsService {

    @Autowired
    private ScheduledClassService classService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private DateUtility dateUtility;

    public List<Date> getDatesOfCompletedClasses(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.listDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
        else
            return dateUtility.listDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
    }

    public List<StudentModuleAttendanceCorrelation> getStudentAttendanceList(String moduleCode) {
        List<ScheduledClass> classes = classService.findClassesByModuleCode(moduleCode);
        List<StudentModuleAttendanceCorrelation> correlations = new ArrayList<>();
        final long[] common = {0};

        Set<String> groups =  new HashSet<>(classes.stream()
                .map(scheduledClass -> scheduledClass.getGroup())
                .collect(Collectors.toList()));

        if (groups.contains("NONE")) {
            List<ScheduledClass> noneClasses = classes.stream().filter(scheduledClass -> scheduledClass
                    .getGroup()
                    .equals("NONE")).collect(Collectors.toList());

            noneClasses.forEach(noneTypeClass -> {
                common[0] += calculateClasses(noneTypeClass);
            });
        }

        groups.forEach(group -> {
            if (!group.equals("NONE")) {
                final long[] expectedGroupAttendance = {common[0]};
                List<ScheduledClass> groupClasses = classes.stream().filter(scheduledClass -> scheduledClass
                        .getGroup()
                        .equals(group)).collect(Collectors.toList());
                groupClasses.forEach(groupClass -> expectedGroupAttendance[0] += calculateClasses(groupClass));
                List<StudentModuleAttendanceCorrelation> extracted = studentService.getStudentAttendanceCorrelationForModuleGroup(moduleCode, group);
                extracted.forEach(correlation -> {
                    IndividualCumulativeModuleAttendance att = correlation.getModuleAttendance();
                    att.setModuleCode(moduleCode);
                    att.setTotalCompletedClassesToDate(expectedGroupAttendance[0]);
                    correlation.setModuleAttendance(att);
                });
                correlations.addAll(extracted);
            }
            else {
                studentService.getStudentAttendanceCorrelationForStudentsWithNoGroup(moduleCode).forEach(correlation -> {
                    IndividualCumulativeModuleAttendance att = correlation.getModuleAttendance();
                    att.setModuleCode(moduleCode);
                    att.setTotalCompletedClassesToDate(common[0]);
                    correlation.setModuleAttendance(att);

                    correlations.add(correlation);
                });
            }
        });
        return correlations;
    }

    public List<IndividualCumulativeModuleAttendance> getMobileCumulativeModuleMetricsForStudent(String universityId) {
        List<ScheduledClass> classes = classService.findClassesByStudentUniversityId(universityId);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForStudent(universityId);
        return calculateMobileModuleMetrics(classes, attendance);
    }

    public TotalAverageModuleAttendance getTotalAverageModuleAttendance(String moduleCode) {
        List<ScheduledClass> classes = classService.findClassesByModuleCode(moduleCode);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForModule(moduleCode);
        return calculateAverageModuleAttendanceMetrics(moduleCode, classes, attendance);
    }

    private TotalAverageModuleAttendance calculateAverageModuleAttendanceMetrics(String moduleCode, List<ScheduledClass> classes, List<Attendance> attendance) {
        List<Double> classesAvgPercentages = new ArrayList<Double>();
        final int[] classesToDate = {0};

        classes.forEach(scheduledClass -> {
            double allocated = scheduledClass.getAllocated();
            List<Double> completedClassesAttendancePercentages = new ArrayList<Double>();
            List<Date> completedClasses = calculateClassesDates(scheduledClass);
            classesToDate[0] += completedClasses.size();

            completedClasses.forEach(completedClass -> {
                double records = attendance.stream().filter(record -> (record.getClassUuid().equals(scheduledClass.getUuid())
                                                                    && DateUtils.isSameDay(record.getDate(), completedClass))).count();
                completedClassesAttendancePercentages.add(Double.valueOf(records / allocated));
            });
            classesAvgPercentages.add((completedClassesAttendancePercentages.stream()
                                                                           .mapToDouble(perc -> perc.doubleValue())
                                                                           .sum()) / completedClassesAttendancePercentages.size());
        });

        return new TotalAverageModuleAttendance(moduleCode, classesAvgPercentages.stream().mapToDouble(perc -> perc.doubleValue()).sum(), classes.size(), classesToDate[0]);
    }

    private List<IndividualCumulativeModuleAttendance> calculateMobileModuleMetrics(List<ScheduledClass> classes , List<Attendance> attendance) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        List<IndividualCumulativeModuleAttendance> metrics = new ArrayList<>();
        Set<String> moduleCodes =  new HashSet<>(classes.stream()
                .map(scheduledClass -> scheduledClass.getModuleCode())
                .collect(Collectors.toList()));

        moduleCodes.forEach(moduleCode -> {
            IndividualCumulativeModuleAttendance metric = new IndividualCumulativeModuleAttendance();
            metric.setModuleCode(moduleCode);
            classes.stream().filter(scheduledClass -> scheduledClass.getModuleCode()
                    .equals(moduleCode))
                    .collect(Collectors.toList()).forEach(scheduledClass -> {
                        long total = metric.getTotalCompletedClassesToDate();
                        long toDate = calculateClasses(scheduledClass);
                        metric.setTotalCompletedClassesToDate(total + toDate);
                    });
            metrics.add(metric);
        });

        metrics.forEach(metric -> {
            long count = attendance.stream().filter(att -> att.getModuleCode().equals(metric.getModuleCode())).count();
            metric.setTotalAttended(count);
        });

        return metrics;
    }

    private long calculateClasses(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.countDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
        else
            return dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
    }

    private List<Date> calculateClassesDates(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.listDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
        else
            return dateUtility.listDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
    }

}
