package application.domain.module.attendance;

import application.domain.metrics.AttendanceMetricsAbstract;

public class ModuleAttendance<T extends Number> extends AttendanceMetricsAbstract<T>{
    protected String moduleCode;

    public ModuleAttendance(){}

    public ModuleAttendance(String moduleCode, T x, T y) {
        super(x, y);
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }
}
