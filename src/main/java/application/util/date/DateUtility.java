package application.util.date;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DateUtility {

    public int countDays(Date startDate, Date endDate, DayOfWeek day) {
        int count = 0;

        if(startDate.getTime() > endDate.getTime()) {
            return count;
        }
        else {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(startDate);
            end.setTime(endDate);

            while (start.getTimeInMillis() < end.getTimeInMillis()) {
                if (start.get(Calendar.DAY_OF_WEEK) == convertDayOfWeekToCalendarDay(day))
                    count++;
                start.add(Calendar.DAY_OF_MONTH, 1);
            }

            if ((end.getTime().getHours() + end.getTime().getMinutes()) < (start.getTime().getHours() + start.getTime().getMinutes()))
                count--;
            return count;
        }

    }

    public List<Date> listDays(Date startDate, Date endDate, DayOfWeek day) {

        List<Date> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);

        while (start.getTimeInMillis() < end.getTimeInMillis()) {
            if (start.get(Calendar.DAY_OF_WEEK) == convertDayOfWeekToCalendarDay(day))
                dates.add(start.getTime());
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        if ((end.getTime().getHours() + end.getTime().getMinutes()) < (start.getTime().getHours() + start.getTime().getMinutes()))
            dates.remove(dates.size()-1);

        return dates;
    }

    private int convertDayOfWeekToCalendarDay(DayOfWeek day) {
        int response = -1;
        switch (day) {
            case MONDAY:
                response =  2;
                break;
            case TUESDAY:
                response = 3;
                break;
            case WEDNESDAY:
                response = 4;
                break;
            case THURSDAY:
                response = 5;
                break;
            case FRIDAY:
                response = 6;
                break;
            case SATURDAY:
                response = 7;
                break;
            case SUNDAY:
                response = 1;
                break;
        }
        return response;
    }

}
