package application.domain.metrics;

public class MobileCumulativeModuleMetrics {

    private String moduleCode;
    private long totalToDate = 0;
    private long totalAttended = 0;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public long getTotalToDate() {
        return totalToDate;
    }

    public void setTotalToDate(long totalToDate) {
        this.totalToDate = totalToDate;
    }

    public long getTotalAttended() {
        return totalAttended;
    }

    public void setTotalAttended(long totalAttended) {
        this.totalAttended = totalAttended;
    }
}
