package application.api.resource.user;

import application.api.resource.BaseResource;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResource extends BaseResource<String>{
    /**
     * The constructor for the class
     *
     * @param object
     */
    public PasswordResource(String object) {
        super(object);
    }

    public PasswordResource() {
        super(null);
    }

    @JsonProperty("password")
    public String getPassword() {
        return object;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        object = password;
    }
}
