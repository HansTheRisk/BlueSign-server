package application.domain.module.attendance;

/**
 *    This class is for the general module perspective.
 *    It represents the total number of students who should have attended the module's
 *    classes to date against the number of recorded attendances for the module to date
 *    @totalToDate = sum of (for each of the module's classes: ((number of classes to date) * allocated students))
 *    @totalAttended = total of attendance records for the module to date
 *    @classesToDate = number of module's classes completed to date
 */
public class TotalCumulativeModuleAttendance extends ModuleAttendance {
    private long classesToDate = 0;

    public TotalCumulativeModuleAttendance(String moduleCode, long totalExpectedAttendances, long totalActualAttendances, long classesToDate) {
        super(moduleCode, totalExpectedAttendances, totalActualAttendances);
        this.classesToDate = classesToDate;
    }

    public long getTotalOfExpectedAttendances() {
        return this.x;
    }

    public void setTotalOfExpectedAttendances(int totalExpectedAttendances) {
        this.x = totalExpectedAttendances;
    }

    public long getTotalOfActualAttendances() {
        return this.y;
    }

    public void setTotalOfActualAttendances(int totalActualAttendances) {
        this.y = totalActualAttendances;
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

    public void setClassesToDate(int classesToDate) {
        this.classesToDate = classesToDate;
    }
}
