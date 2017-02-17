package application.domain.scheduledClass;

import application.domain.entity.NaturallyIdentifiableEntity;

import java.util.Date;

public class ScheduledClass implements NaturallyIdentifiableEntity {

    private Long id;
    private String uuid;
    private Date startDate;
    private Date endDate;
    private String moduleCode;
    private String room;
    private long allocated;
    private String group;

    public ScheduledClass() {

    }

    public ScheduledClass(String moduleCode, Date startDate, Date endDate) {
        this.moduleCode = moduleCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public long getAllocated() {
        return allocated;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAllocated(long allocated) {
        this.allocated = allocated;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
