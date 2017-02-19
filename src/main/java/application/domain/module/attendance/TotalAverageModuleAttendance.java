package application.domain.module.attendance;

/**
 * This class represents the total average attendance for a module.
 * x = totaled average attendance percentages of all classes of the module.
 * y = the number of scheduled classes of the module.
 * x/y = the average module attendance percentage.
 */
public class TotalAverageModuleAttendance extends ModuleAttendance<Double> {

    private int classesToDate = 0;

    public TotalAverageModuleAttendance(String moduleCode, double totalAverageClassPercentages, double numOfClasses, int classesToDate) {
        super(moduleCode, totalAverageClassPercentages, numOfClasses);
        this.classesToDate = classesToDate;
    }

    public double getTotaledClassAttendanceAveragePercentages() {
        return this.x;
    }

    public double getNumOfClasses() {
        return this.y;
    }

    public int getClassesToDate() {
        return classesToDate;
    }
}
