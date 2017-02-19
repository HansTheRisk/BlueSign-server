package application.api.resource.signIn;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;

/**
 * This class is used for creating JSON requests for signing into the attendance system.
 */
public class SignInResource extends BaseResource<Integer> {

    public SignInResource() {
        super(null);
    }

    public SignInResource(int object) {
        super(object);
    }

    /**
     * Returns the sign in code.
     * @return int
     */
    @JsonProperty("code")
    public int getCode() {
        return object;
    }

    /**
     * Sets the sign in code value.
     * @param code
     */
    @JsonProperty("code")
    public void setCode(int code) {
        this.object = code;
    }
}
