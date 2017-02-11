package application.service.metrics;

import application.domain.attendance.Attendance;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.module.attendance.TotalCumulativeModuleAttendance;
import application.domain.scheduledClass.ScheduledClass;
import application.service.attendance.AttendanceService;
import application.service.scheduledClass.ScheduledClassService;
import application.util.date.DateUtility;
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

    //TODO: CHange this to accept a student object instead
    public List<IndividualCumulativeModuleAttendance> getMobileCumulativeModuleMetricsForStudent(String universityId) {
        List<ScheduledClass> classes = classService.findClassesByStudentUniversityId(universityId);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForStudent(universityId);
        return calculateMobileModuleMetrics(classes, attendance);
    }

    public TotalCumulativeModuleAttendance getCumulativeModuleAttendanceMetrics(String moduleCode) {
        List<ScheduledClass> classes = classService.findClassesByModuleCode(moduleCode);
        List<Attendance> attendance = attendanceService.getAttendanceRecordsForModule(moduleCode);
        return calculateCumulativeModuleAttendanceMetrics(moduleCode, classes, attendance);
    }

    private TotalCumulativeModuleAttendance calculateCumulativeModuleAttendanceMetrics(String moduleCode, List<ScheduledClass> classes, List<Attendance> attendance) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        final long[] totalExpectedAttendances = {0};
        final long[] totalClassesToDate = {0};
        int attended = attendance.size();

        classes.forEach(scheduledClass -> {
            long numOfCompleted = 0;
            if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
                numOfCompleted = dateUtility.countDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
            else
                numOfCompleted = dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
            totalExpectedAttendances[0] += scheduledClass.getAllocated() * numOfCompleted;
            totalClassesToDate[0] += numOfCompleted;
        });
        return new TotalCumulativeModuleAttendance(moduleCode, totalExpectedAttendances[0], attended, totalClassesToDate[0]);
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
                        long toDate;

                        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
                            toDate = dateUtility.countDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
                        else
                            toDate = dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
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

}
