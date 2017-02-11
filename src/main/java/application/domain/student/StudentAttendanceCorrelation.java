package application.domain.student;

import application.domain.attendance.Attendance;

public class StudentAttendanceCorrelation {
    private Student student;
    private Attendance attendance;

    public StudentAttendanceCorrelation(Student student, Attendance attendance) {
        this.student = student;
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
