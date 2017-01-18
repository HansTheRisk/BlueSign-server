package unit.util.date;

import main.application.util.date.DateUtility;
import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtilityTest {

    @Test
    public void countDaysTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 00, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 00, 00);
        DayOfWeek day = DayOfWeek.TUESDAY;

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), day);
        Assert.assertEquals(3, days);

    }

//    @Test
//    public void listDaysTest() {
//
//        DateUtility utility = new DateUtility();
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2017, 00, 01, 00, 00, 00);
//
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2017, 00, 22, 00, 00, 00);
//        DayOfWeek day = DayOfWeek.TUESDAY;
//
//        List<Date> dates = new ArrayList<>();
//        int dayNum = 03;
//        for(int i = 0; i<3; i++) {
//            Calendar date = Calendar.getInstance();
//            date.set(2017, 0, dayNum, 00, 00, 00);
//            dates.add(date.getTime());
//            dayNum += 7;
//        }
//        List<Date> days = utility.listDays(startDate.getTime(), endDate.getTime(), day);
//        Assert.assertArrayEquals(dates.toArray(), days.toArray());
//
//    }

}
