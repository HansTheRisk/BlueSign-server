package unit.metrics;

import main.application.domain.metrics.MobileCumulativeModuleMetrics;
import main.application.domain.scheduledClass.ScheduledClass;
import main.application.util.date.DateUtility;
import org.junit.Test;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MobileCumulativeModuleMetricsTest {

    @Test
    public void test() {

        DateUtility dateUtility = new DateUtility();

        //Simulation of classService.getClassesForStudent();
        List<ScheduledClass> classes = new ArrayList<>();

        Calendar startDateOne = Calendar.getInstance();
        startDateOne.set(2017, 00, 01, 14, 15, 00);

        Calendar startDateTwo = Calendar.getInstance();
        startDateTwo.set(2017, 00, 01, 15, 15, 00);

        Calendar endDateOne = Calendar.getInstance();
        endDateOne.set(2017, 00, 22, 15, 15, 00);

        Calendar endDateTwo = Calendar.getInstance();
        endDateTwo.set(2017, 00, 22, 15, 15, 00);

        Calendar simulatedNow = Calendar.getInstance();
        simulatedNow.set(2017, 00, 16, 17, 00, 00);

        ScheduledClass classOne = new ScheduledClass("COM111", DayOfWeek.MONDAY, startDateOne.getTime(),
                                                    endDateOne.getTime(), new Time(14, 15, 0), new Time(15, 15, 0));

        ScheduledClass classTwo = new ScheduledClass("COM111", DayOfWeek.TUESDAY, startDateTwo.getTime(),
                endDateTwo.getTime(), new Time(15, 15, 0), new Time(16, 15, 0));

        ScheduledClass classThree = new ScheduledClass("COM333", DayOfWeek.WEDNESDAY, startDateOne.getTime(),
                endDateOne.getTime(), new Time(14, 15, 0), new Time(15, 15, 0));

        ScheduledClass classFour = new ScheduledClass("COM333", DayOfWeek.WEDNESDAY, startDateTwo.getTime(),
                endDateTwo.getTime(), new Time(15, 15, 0), new Time(16, 15, 0));

        classes.add(classOne);
        classes.add(classTwo);
        classes.add(classThree);
        classes.add(classFour);

        //Testing of cumulation logic
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
                long toDate = dateUtility.countDays(scheduledClass.getStartDate(), simulatedNow.getTime(), scheduledClass.getDay());
                metric.setTotalToDate(total + toDate);
            });

            metrics.add(metric);

        });
    }

}
