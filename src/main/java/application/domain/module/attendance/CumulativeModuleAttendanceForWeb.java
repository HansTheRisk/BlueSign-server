package application.domain.module.attendance;

/**
 *    This class is for web use (from the lecturer perspective).
 *    It represents the total number of students who should have attended the module's
 *    classes to date against the number of recorded attendances for the module to date
 *    @totalToDate = sum of (for each of the module's classes: ((number of classes to date) * allocated students))
 *    @totalAttended = total of attendance records for the module to date
 *    @classesToDate = number of module's classes completed to date
 */
public class CumulativeModuleAttendanceForWeb extends ModuleAttendance {
    private long classesToDate = 0;

    public CumulativeModuleAttendanceForWeb(String moduleCode, long totalExpectedAttendances, long totalActualAttendances, long classesToDate) {
        super(moduleCode, totalExpectedAttendances, totalActualAttendances);
        this.classesToDate = classesToDate;
    }

    public long getTotalOfExpectedAttendances() {
        return this.totalToDate;
    }

    public void setTotalOfExpectedAttendances(long totalToDate) {
        this.totalToDate = totalToDate;
    }

    public long getTotalOfActualAttendances() {
        return this.totalAttended;
    }

    public void setTotalOfActualAttendances(long totalAttended) {
        this.totalAttended = totalAttended;
    }

    public String getModuleCode() {
        return super.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        super.moduleCode = moduleCode;
    }

    public long getCompletedClassesToDate() {
        return classesToDate;
    }

    public void setClassesToDate(long classesToDate) {
        this.classesToDate = classesToDate;
    }
}
