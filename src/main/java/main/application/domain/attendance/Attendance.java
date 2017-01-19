package main.application.domain.attendance;

import java.util.Date;

public class Attendance {
    private String studentUniversityId;
    private String classUuid;
    private Date date;
    private String moduleCode;

    public Attendance() {

    }

    public Attendance(String studentUniversityId, String classUuid, Date date) {
        this.studentUniversityId = studentUniversityId;
        this.classUuid = classUuid;
        this.date = date;
    }

    public Attendance(String studentUniversityId, String classUuid, Date date, String moduleCode) {
        this.studentUniversityId = studentUniversityId;
        this.classUuid = classUuid;
        this.date = date;
        this.moduleCode = moduleCode;
    }

    public String getStudentUuid() {
        return studentUniversityId;
    }

    public void setStudentUuid(String studentUniversityId) {
        this.studentUniversityId = studentUniversityId;
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

    public String getStudentUniversityId() {
        return studentUniversityId;
    }

    public void setStudentUniversityId(String studentUniversityId) {
        this.studentUniversityId = studentUniversityId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}
