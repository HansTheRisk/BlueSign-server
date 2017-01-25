package main.application.api.resource.signIn;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.BaseResource;

public class SignInResource extends BaseResource<Integer> {

    public SignInResource() {
        super(null);
    }

    public SignInResource(int object) {
        super(object);
    }

    @JsonProperty("code")
    public int getCode() {
        return object;
    }

    @JsonProperty("code")
    public void setCode(int code) {
        this.object = code;
    }
}
