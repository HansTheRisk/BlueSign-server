package application.domain.module.attendance;

public abstract class ModuleAttendance {
    protected String moduleCode;
    protected long totalToDate = 0;
    protected long totalAttended = 0;

    public ModuleAttendance(){};

    public ModuleAttendance(String moduleCode, long totalToDate, long totalAttended) {
        this.moduleCode = moduleCode;
        this.totalToDate = totalToDate;
        this.totalAttended = totalAttended;
    }
}
