package unit.metrics;

import main.application.domain.metrics.MobileCumulativeModuleMetrics;
import main.application.domain.scheduledClass.ScheduledClass;
import main.application.service.metrics.MetricsService;
import main.application.util.date.DateUtility;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ComponentScan(basePackages={"main.application.domain.metrics"})
public class MobileCumulativeModuleMetricsTest {

//    @Mock
//    private MetricsService metricsService;

    @Test
    public void test() {

        DateUtility dateUtility = new DateUtility();

        //Simulation of classService.getClassesForStudent();
        List<ScheduledClass> classes = new ArrayList<>();

        Calendar startDateOne = Calendar.getInstance();
        startDateOne.set(2017, 00, 02, 14, 15, 00);

        Calendar startDateTwo = Calendar.getInstance();
        startDateTwo.set(2017, 00, 03, 14, 15, 00);

        Calendar startDateThree = Calendar.getInstance();
        startDateThree.set(2017, 00, 05, 14, 15, 00);

        Calendar endDateOne = Calendar.getInstance();
        endDateOne.set(2017, 00, 23, 15, 15, 00);

        Calendar endDateTwo = Calendar.getInstance();
        endDateTwo.set(2017, 00, 17, 16, 15, 00);

        Calendar endDateThree = Calendar.getInstance();
        endDateThree.set(2017, 00, 26, 16, 15, 00);

        Calendar simulatedNow = Calendar.getInstance();
        simulatedNow.set(2017, 00, 16, 17, 00, 00);

        ScheduledClass classOne = new ScheduledClass("COM111", startDateOne.getTime(),
                                                    endDateOne.getTime());

        ScheduledClass classTwo = new ScheduledClass("COM111", startDateTwo.getTime(),
                endDateTwo.getTime());

        ScheduledClass classThree = new ScheduledClass("COM333", startDateThree.getTime(),
                endDateThree.getTime());


        classes.add(classOne);
        classes.add(classTwo);
        classes.add(classThree);

//        //Testing of cumulation logic
//        List<MobileCumulativeModuleMetrics> metrics = new ArrayList<>();
//        Set<String> moduleCodes =  new HashSet<>(classes.stream()
//                                            .map(scheduledClass -> scheduledClass.getModuleCode())
//                                            .collect(Collectors.toList()));
//
//        moduleCodes.forEach(moduleCode -> {
//            MobileCumulativeModuleMetrics metric = new MobileCumulativeModuleMetrics();
//            metric.setModuleCode(moduleCode);
//            classes.stream().filter(scheduledClass -> scheduledClass.getModuleCode()
//                                        .equals(moduleCode))
//                                        .collect(Collectors.toList()).forEach(scheduledClass -> {
//
//                long total = metric.getTotalToDate();
//                long toDate = dateUtility.countDays(scheduledClass.getStartDate(), simulatedNow.getTime(), DayOfWeek.of(scheduledClass.getStartDate().getDay()));
//                metric.setTotalToDate(total + toDate);
//            });
//
//            metrics.add(metric);
//        });

    }

}
