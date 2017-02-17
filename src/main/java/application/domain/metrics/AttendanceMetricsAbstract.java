package application.domain.metrics;

public abstract class AttendanceMetricsAbstract<T extends Number> {
    protected T x;
    protected T y;

    public AttendanceMetricsAbstract(){}
    public AttendanceMetricsAbstract(T x, T y) {
        this.x = x;
        this.y = y;
    }
}
