package application.api.resource.date;

import application.api.resource.BaseResource;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DateResource <T extends Date> extends BaseResource<T> {
    public DateResource(T object) {
        super(object);
    }

    @JsonProperty("date")
    public String getDate() {
        return object.toString();
    }

    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getTime();
    }

}
