package application.domain.allocation;

/**
 * The Allocation domain class represents the allocation
 * of student to a scheduled class.
 */
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
