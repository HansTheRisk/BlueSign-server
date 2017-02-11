package application.domain.metrics;

public abstract class AttendanceMetricsAbstract {
    protected long x = 0;
    protected long y = 0;

    public AttendanceMetricsAbstract(){}
    public AttendanceMetricsAbstract(long x, long y) {
        this.x = x;
        this.y = y;
    }
}
