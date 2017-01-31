package application.api.resource;

public class BaseResource<T> {

    public BaseResource(T object) {
        this.object = object;
    }

    protected T object;
}
