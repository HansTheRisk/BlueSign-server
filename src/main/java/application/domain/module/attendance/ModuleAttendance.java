package application.domain.module.attendance;

import application.domain.metrics.AttendanceMetricsAbstract;

public class ModuleAttendance extends AttendanceMetricsAbstract{
    protected String moduleCode;

    public ModuleAttendance(){}

    public ModuleAttendance(String moduleCode, long x, long y) {
        super(x, y);
        this.moduleCode = moduleCode;
    }
}
