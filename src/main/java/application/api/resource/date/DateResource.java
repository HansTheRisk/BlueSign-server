package application.api.resource.date;

import application.api.resource.BaseResource;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class used to represent dates as JSON resources
 * @param <T>
 */
public class DateResource <T extends Date> extends BaseResource<T> {
    public DateResource(T object) {
        super(object);
    }

    /**
     * Returns date as String.
     * @return String
     */
    @JsonProperty("date")
    public String getDate() {
        return object.toString();
    }

    /**
     * Returns date as timestamp.
     * @return long
     */
    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getTime();
    }

}
