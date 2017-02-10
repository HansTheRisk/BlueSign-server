package application.domain.scheduledClass.attendance;

import java.util.Date;

public class CompletedClassAttendance {
    String classUuid;
    Date date;
    int allocated = 0;
    int attended = 0;

    public CompletedClassAttendance(){}

    public CompletedClassAttendance(String classUuid, Date date, int allocated, int attended) {
        this.classUuid = classUuid;
        this.date = date;
        this.allocated = allocated;
        this.attended = attended;
    }

    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAllocated() {
        return allocated;
    }

    public void setAllocated(int allocated) {
        this.allocated = allocated;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }
}
