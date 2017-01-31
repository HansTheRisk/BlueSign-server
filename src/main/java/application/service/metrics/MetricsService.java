package application.service.metrics;

import application.domain.attendance.Attendance;
import application.domain.metrics.MobileCumulativeModuleMetrics;
import application.domain.scheduledClass.ScheduledClass;
import application.service.attendance.AttendanceService;
import application.service.scheduledClass.ScheduledClassService;
import application.util.date.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
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

    public List<MobileCumulativeModuleMetrics> getMobileCumulativeModuleMetrics(String universityId) {
        List<ScheduledClass> classes = classService.findClassesByStudentUniversityId(universityId);
        List<Attendance> attendance = attendanceService.getAttendanceForStudent(universityId);
        return calculateMobileModuleMetrics(classes, attendance);
    }

    private List<MobileCumulativeModuleMetrics> calculateMobileModuleMetrics(List<ScheduledClass> classes , List<Attendance> attendance) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        List<MobileCumulativeModuleMetrics> metrics = new ArrayList<>();
        Set<String> moduleCodes =  new HashSet<>(classes.stream()
                .map(scheduledClass -> scheduledClass.getModuleCode())
                .collect(Collectors.toList()));

        moduleCodes.forEach(moduleCode -> {
            MobileCumulativeModuleMetrics metric = new MobileCumulativeModuleMetrics();
            metric.setModuleCode(moduleCode);
            classes.stream().filter(scheduledClass -> scheduledClass.getModuleCode()
                    .equals(moduleCode))
                    .collect(Collectors.toList()).forEach(scheduledClass -> {
                        long total = metric.getTotalToDate();
                        long toDate;

                        if(now.getTimeInMillis() > scheduledClass.getEndDate().getTime())
                            toDate = dateUtility.countDays(scheduledClass.getStartDate(), scheduledClass.getEndDate(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
                        else
                            toDate = dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
                        metric.setTotalToDate(total + toDate);
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
