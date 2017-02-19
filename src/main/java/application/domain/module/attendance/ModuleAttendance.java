package application.domain.module.attendance;

import application.domain.metrics.AttendanceMetricsAbstract;

/**
 * This class extends the AttendanceMetricsAbstract to
 * add the additional detail in form of the module code.
 * @param <T>
 */
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
