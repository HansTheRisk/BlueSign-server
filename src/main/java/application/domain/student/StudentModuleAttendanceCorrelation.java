package application.domain.student;

import application.domain.module.attendance.IndividualCumulativeModuleAttendance;

public class StudentModuleAttendanceCorrelation {

    private Student student;
    private IndividualCumulativeModuleAttendance moduleAttendance;

    public StudentModuleAttendanceCorrelation(Student student, IndividualCumulativeModuleAttendance moduleAttendance) {
        this.student = student;
        this.moduleAttendance = moduleAttendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public IndividualCumulativeModuleAttendance getModuleAttendance() {
        return moduleAttendance;
    }

    public void setModuleAttendance(IndividualCumulativeModuleAttendance moduleAttendance) {
        this.moduleAttendance = moduleAttendance;
    }
}
