package application.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This generic class provides the
 * foundations for all the resource classes.
 *
 * A resource class is used to form the JSON
 * responses of the API.
 * @param <T>
 */
public class BaseResource<T> {

    /**
     * The constructor for the class
     * @param object
     */
    public BaseResource(T object) {
        this.object = object;
    }

    @JsonIgnore
    public T getObject() {
        return object;
    }

    protected T object;
}
