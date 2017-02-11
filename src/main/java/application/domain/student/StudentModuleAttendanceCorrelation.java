package application.domain.student;

import application.domain.module.attendance.ModuleAttendance;

public class StudentModuleAttendanceCorrelation {

    private Student student;
    private ModuleAttendance moduleAttendance;

    public StudentModuleAttendanceCorrelation(Student student, ModuleAttendance moduleAttendance) {
        this.student = student;
        this.moduleAttendance = moduleAttendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ModuleAttendance getModuleAttendance() {
        return moduleAttendance;
    }

    public void setModuleAttendance(ModuleAttendance moduleAttendance) {
        this.moduleAttendance = moduleAttendance;
    }
}
