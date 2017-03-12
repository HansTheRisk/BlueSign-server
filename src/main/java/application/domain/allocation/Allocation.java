package application.domain.allocation;

import java.util.Date;

/**
 * The Allocation domain class represents the allocation
 * of student to a scheduled class.
 */
public class Allocation {

    private String studentUniversityId;
    private String classUuid;
    private Date start;
    private Date end;

    public Allocation(String studentUniversityId, String classUuid) {
        this.studentUniversityId = studentUniversityId;
        this.classUuid = classUuid;
    }

    public Allocation(String studentUniversityId, String classUuid, Date start, Date end) {
        this.studentUniversityId = studentUniversityId;
        this.classUuid = classUuid;
        this.start = start;
        this.end = end;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
