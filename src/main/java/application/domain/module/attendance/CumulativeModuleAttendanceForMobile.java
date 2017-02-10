package application.domain.module.attendance;
/**
 *    This class is for mobile use (individual student perspective).
 *    It represents the number of times the student attended the allocated to him module's classes
 *    against the number of times the classes have been run i.e.
 *    @totalToDate = number of times the allocated to student modules' classes have been run to date
 *    @totalAttended = number of times the student attended the allocated module's class to date
 */
public class CumulativeModuleAttendanceForMobile extends ModuleAttendance{

    public CumulativeModuleAttendanceForMobile() {
        super();
    }

    public CumulativeModuleAttendanceForMobile(String moduleCode, long totalCompletedToDate, long totalAttended) {
        super(moduleCode, totalCompletedToDate, totalAttended);
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public long getTotalCompletedClassesToDate() {
        return totalToDate;
    }

    public void setTotalCompletedClassesToDate(long totalToDate) {
        this.totalToDate = totalToDate;
    }

    public long getTotalAttended() {
        return totalAttended;
    }

    public void setTotalAttended(long totalAttended) {
        this.totalAttended = totalAttended;
    }
}
