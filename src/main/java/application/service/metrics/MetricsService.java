package application.service.metrics;

import application.domain.allocation.Allocation;
import application.domain.module.attendance.TotalAverageModuleAttendance;
import application.domain.attendance.Attendance;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.service.allocation.AllocationService;
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

/**
 * Service that calculates all the metrics / statistics for
 * a student, module or class attendance.
 */
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
    @Autowired
    private AllocationService allocationService;

    /**
     * Returns dates of completed classes' dates of a scheduled class.
     * @param scheduledClass
     * @return List of dates
     */
    public List<Date> getDatesOfCompletedClasses(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTime(scheduledClass.getStartDate());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.listDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), dayOfWeek);
        else
            return dateUtility.listDays(scheduledClass.getStartDate(), now.getTime(), dayOfWeek);
    }

    /**
     * This method returns a list of StudentModuleAttendanceCorrelation.
     * It identifies all the module groups for a given module and then
     * performs attendance calculations for each group respectively.
     *
     * Custom group attendance = e.g.
     * (groupA attendance records) / (groupA expected attendance + generic group (NONE) expected attendance)
     * @param moduleCode
     * @return List of StudentModuleAttendanceCorrelation
     */
    public List<StudentModuleAttendanceCorrelation> getStudentAttendanceList(String moduleCode) {
        List<ScheduledClass> classes = classService.getClassesByModuleCode(moduleCode);
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

    /**
     * This method creates individual attendance metrics
     * for single student. For mobile use only.
     * @param universityId
     * @return List of IndividualCumulativeModuleAttendance
     */
    public List<IndividualCumulativeModuleAttendance> getMobileCumulativeModuleMetricsForStudent(String universityId) {
        List<ScheduledClass> classes = classService.getClassesByStudentUniversityId(universityId);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForStudent(universityId);
        return calculateMobileModuleMetrics(classes, attendance);
    }

    /**
     * This method gets the average attendance for a module.
     * @param moduleCode
     * @return TotalAverageModuleAttendance
     */
    public TotalAverageModuleAttendance getTotalAverageModuleAttendance(String moduleCode) {
        List<ScheduledClass> classes = classService.getClassesByModuleCode(moduleCode);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForModule(moduleCode);
        List<Allocation> allocations = allocationService.getModulesClassesAllocations(moduleCode);
        return calculateAverageModuleAttendanceMetrics(moduleCode, classes, attendance, allocations);
    }

    /**
     * Private method that performs the calculations for the
     * getTotalAverageModuleAttendance() method.
     *
     * It calculates the average attendance for each completed class
     * of the scheduled classes of the given module. Then it totals the
     * result and returns it with the number indicating the number of
     * scheduled classes of the module for further calculation.
     *
     * TotalAvgPercentageOfAllClasses / number of scheduled classes of the module.
     *
     * @param moduleCode
     * @param classes
     * @param attendance
     * @return TotalAverageModuleAttendance
     */
    private TotalAverageModuleAttendance calculateAverageModuleAttendanceMetrics(String moduleCode, List<ScheduledClass> classes, List<Attendance> attendance, List<Allocation> allocations) {
        List<Double> classesAvgPercentages = new ArrayList<Double>();
        final int[] numOfClasses = {0};
        final int[] classesToDate = {0};

        classes.forEach(scheduledClass -> {
            List<Double> completedClassesAttendancePercentages = new ArrayList<Double>();
            List<Date> completedClasses = calculateClassesDates(scheduledClass);
            classesToDate[0] += completedClasses.size();

            if(completedClasses.size() != 0)
                numOfClasses[0]++;

            completedClasses.forEach(completedClass -> {

                Date startDate = addTimeToDate(completedClass,
                                               scheduledClass.getStartDate().getHours(),
                                               scheduledClass.getStartDate().getMinutes(),
                                               scheduledClass.getStartDate().getSeconds());

                Date endDate = addTimeToDate(completedClass,
                                             scheduledClass.getEndDate().getHours(),
                                             scheduledClass.getEndDate().getMinutes(),
                                             scheduledClass.getEndDate().getSeconds());

                double allocated = allocations.stream().filter(allocation -> {
                    boolean sameClass = allocation.getClassUuid().equals(scheduledClass.getUuid());

                    if(sameClass && ((allocation.getStart().getTime() < endDate.getTime()) && allocation.getEnd() == null))
                        return true;
                    else if(sameClass && ((allocation.getStart().getTime() < endDate.getTime()) && allocation.getEnd().getTime() > startDate.getTime()))
                        return true;
                    else
                        return false;

                }).map(allocation -> allocation.getStudentUniversityId()).collect(Collectors.toSet()).size();

                double records = attendance.stream().filter(record -> (record.getClassUuid().equals(scheduledClass.getUuid())
                                                                    && DateUtils.isSameDay(record.getDate(), completedClass))).count();
                completedClassesAttendancePercentages.add(Double.valueOf(allocated!=0 ? records/allocated : 0));
            });
            if(!completedClassesAttendancePercentages.isEmpty())
                classesAvgPercentages.add((completedClassesAttendancePercentages.stream()
                                                                           .mapToDouble(perc -> perc.doubleValue())
                                                                           .sum()) / completedClassesAttendancePercentages.size());
        });

        return new TotalAverageModuleAttendance(moduleCode, classesAvgPercentages.stream().mapToDouble(perc -> perc.doubleValue()).sum(), numOfClasses[0], classesToDate[0]);
    }

    private Date addTimeToDate(Date date, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * This private method supports the
     * getMobileCumulativeModuleMetricsForStudent method.
     * It performs the attendance calculations for
     * every class that the student is allocated for and then
     * groups the results by module code getting the individual
     * student attendance results for each module.
     * @param classes
     * @param attendance
     * @return List of IndividualCumulativeModuleAttendance
     */
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

    /**
     * This method calculates the number of completed
     * classes between two dates for a given scheduled
     * class.
     * @param scheduledClass
     * @return long
     */
    private long calculateClasses(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTime(scheduledClass.getStartDate());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.countDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), dayOfWeek);
        else
            return dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), dayOfWeek);
    }

    /**
     * This method calculated the completed classes
     * for the given scheduled class and returns their
     * dates.
     * @param scheduledClass
     * @return List of Dates
     */
    private List<Date> calculateClassesDates(ScheduledClass scheduledClass) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTime(scheduledClass.getStartDate());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
            return dateUtility.listDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), dayOfWeek);
        else
            return dateUtility.listDays(scheduledClass.getStartDate(), now.getTime(), dayOfWeek);
    }

}
