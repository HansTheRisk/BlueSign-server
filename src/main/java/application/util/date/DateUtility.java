package application.util.date;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class provides basic functionality for operating on dates.
 */
@Component
public class DateUtility {

    /**
     * This method counts the number of specific days between
     * two dates.
     * @param startDate
     * @param endDate
     * @param day
     * @return int
     */
    public int countDays(Date startDate, Date endDate, int day) {
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
                if (start.get(Calendar.DAY_OF_WEEK) == day)
                    count++;
                start.add(Calendar.DAY_OF_MONTH, 1);
            }

//            if ((end.get(Calendar.HOUR_OF_DAY)) < (start.get(Calendar.HOUR_OF_DAY)) || (((end.get(Calendar.HOUR_OF_DAY)) == (start.get(Calendar.HOUR_OF_DAY)) && end.get(Calendar.MINUTE) < start.get(Calendar.MINUTE))))
//                count--;
            return count;
        }

    }

    /**
     * This method lists the dates of the specific day
     * occurrences between two dates.
     * @param startDate
     * @param endDate
     * @param day
     * @return List of dates
     */
    public List<Date> listDays(Date startDate, Date endDate, int day) {

        if(startDate.getTime() > endDate.getTime())
            return new ArrayList<>();

        List<Date> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);

        while (start.getTimeInMillis() < end.getTimeInMillis()) {
            if (start.get(Calendar.DAY_OF_WEEK) == day)
                dates.add(start.getTime());
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

//        if ((end.get(Calendar.HOUR_OF_DAY)) < (start.get(Calendar.HOUR_OF_DAY)) || (((end.get(Calendar.HOUR_OF_DAY)) == (start.get(Calendar.HOUR_OF_DAY)) && end.get(Calendar.MINUTE) < start.get(Calendar.MINUTE))))
//            dates.remove(dates.size()-1);

        return dates;
    }
}
