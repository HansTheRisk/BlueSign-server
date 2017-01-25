package main.application.domain.allocation;

public class Allocation {

    private String studentUniversityId;
    private String classUuid;

    public Allocation(String studentUniversityId, String classUuid) {
        this.studentUniversityId = studentUniversityId;
        this.classUuid = classUuid;
    }

    public String getStudentUniversityId() {
        return studentUniversityId;
    }

    public void setStudentUniversityId(String studentUniversityId) {
        this.studentUniversityId = studentUniversityId;
    }

    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
    }
}
