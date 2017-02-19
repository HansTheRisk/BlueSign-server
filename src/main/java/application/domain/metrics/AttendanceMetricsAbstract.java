package application.domain.metrics;

/**
 * An abstract class that is used for performing
 * attendance calculations for modules and classes
 * on individual and collective level.
 *
 * The x and y values can be set to any data type.
 * @param <T>
 */
public abstract class AttendanceMetricsAbstract<T extends Number> {
    protected T x;
    protected T y;

    public AttendanceMetricsAbstract(){}
    public AttendanceMetricsAbstract(T x, T y) {
        this.x = x;
        this.y = y;
    }
}
