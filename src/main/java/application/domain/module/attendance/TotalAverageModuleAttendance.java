package application.domain.module.attendance;

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
