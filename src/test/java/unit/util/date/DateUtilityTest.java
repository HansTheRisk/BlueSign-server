package unit.util.date;

import application.util.date.DateUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtilityTest {

    @Test
    public void countDaysTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 12, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 12, 00);

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), Calendar.TUESDAY);
        Assert.assertEquals(3, days);

    }

    @Test
    public void countDaysEndTimeSmallerTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 12, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 11, 00);

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), Calendar.SUNDAY);
        Assert.assertEquals(3, days);

    }

    @Test
    public void countDaysEndTimeGreaterTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 12, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 15, 00);

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), Calendar.SUNDAY);
        Assert.assertEquals(4, days);

    }

    @Test
    public void countDaysEndDateSmallerTest() {

        DateUtility utility = new DateUtility();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 01, 12, 00);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 22, 11, 00);

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), Calendar.TUESDAY);
        Assert.assertEquals(0, days);

    }

    @Test
    public void countDaysDifferentDaysTest() {

        DateUtility utility = new DateUtility();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 02, 10, 00);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 11, 00);

        long days = utility.countDays(startDate.getTime(), endDate.getTime(), Calendar.SUNDAY);
        Assert.assertEquals(1, days);

    }

    @Test
    public void listDaysDifferentDaysTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 10, 00, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 9, 00, 00);

        List<Date> dates = new ArrayList<>();
        int dayNum = 01;
        for(int i = 0; i<3; i++) {
            Calendar date = Calendar.getInstance();
            date.set(2017, 0, dayNum, 00, 00, 00);
            dates.add(date.getTime());
            dayNum += 7;
        }
        List<Date> days = utility.listDays(startDate.getTime(), endDate.getTime(), Calendar.SUNDAY);
        days.forEach(dayResult -> {
            Assert.assertTrue(DateUtils.isSameDay(dayResult, dates.get(days.indexOf(dayResult))));
        });
    }

    @Test
    public void listDaysTest() {

        DateUtility utility = new DateUtility();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 00, 01, 00, 00, 00);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 00, 22, 00, 00, 00);

        List<Date> dates = new ArrayList<>();
        int dayNum = 03;
        for(int i = 0; i<3; i++) {
            Calendar date = Calendar.getInstance();
            date.set(2017, 0, dayNum, 00, 00, 00);
            dates.add(date.getTime());
            dayNum += 7;
        }
        List<Date> days = utility.listDays(startDate.getTime(), endDate.getTime(), Calendar.TUESDAY);
        days.forEach(dayResult -> {
            Assert.assertTrue(DateUtils.isSameDay(dayResult, dates.get(days.indexOf(dayResult))));
        });
    }
}
