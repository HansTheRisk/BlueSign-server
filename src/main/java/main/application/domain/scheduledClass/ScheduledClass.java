package main.application.domain.scheduledClass;

import main.application.domain.entity.NaturallyIdentifiableEntity;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * This class is a basic resource class for inserting class instances into DB.
 * It is used for inserting class classes into database and extracting them
 * for local and not complicated manipulation.
 */
public class ScheduledClass implements NaturallyIdentifiableEntity {

    private Long id;
    private String uuid;
    private DayOfWeek day;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private String moduleCode;

    public ScheduledClass() {

    }

    public ScheduledClass(String moduleCode, DayOfWeek day, Date startDate, Date endDate, Time startTime, Time endTime) {
        this.moduleCode = moduleCode;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
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
