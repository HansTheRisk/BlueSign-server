package application.domain.scheduledClass.attendance;

import application.domain.metrics.AttendanceMetricsAbstract;

import java.util.Date;

/**
 * This class represents the metrics for a completed class.
 * x = number of allocated students
 * y = number of students who attended
 */
public class CompletedClassAttendance extends AttendanceMetricsAbstract<Integer>{
    private String classUuid;
    private Date date;

    public CompletedClassAttendance(){}

    public CompletedClassAttendance(String classUuid, Date date, int allocated, int attended) {
        super(allocated, attended);
        this.classUuid = classUuid;
        this.date = date;
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

    public long getAllocated() {
        return this.x;
    }

    public void setAllocated(int allocated) {
        this.x = allocated;
    }

    public long getAttended() {
        return this.y;
    }

    public void setAttended(int attended) {
        this.y = attended;
    }
}
