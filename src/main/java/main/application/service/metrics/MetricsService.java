package main.application.service.metrics;

import main.application.domain.metrics.MobileCumulativeModuleMetrics;
import main.application.domain.scheduledClass.ScheduledClass;
import main.application.service.scheduledClass.ScheduledClassService;
import main.application.util.date.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MetricsService {

    @Autowired
    private ScheduledClassService classService;
    @Autowired
    private DateUtility dateUtility;

    public List<MobileCumulativeModuleMetrics> getMobileCumulativeModuleMetrics(String universityId) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        List<ScheduledClass> classes = classService.findClassesByStudentUniversityId(universityId);
        //TODO: get students attendance!

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
                long toDate = dateUtility.countDays(scheduledClass.getStartDate(), now.getTime(), scheduledClass.getDay());
                metric.setTotalToDate(total + toDate);
            });
            metrics.add(metric);
        });

        return metrics;
    }

}
