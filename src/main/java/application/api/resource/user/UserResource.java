package application.api.resource.user;

import application.api.resource.NaturallyIdentifiableResource;
import application.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource extends NaturallyIdentifiableResource<User>{

    public UserResource() {
        super(new User());
    }

    /**
     * Constructor for the class.
     *
     * @param object
     */
    public UserResource(User object) {
        super(object);
    }

    /**
     * This method returns the user uuid.
     * @return String
     */
    @JsonProperty("uuid")
    public String getUuid() {
        return object.getUuid();
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        object.setUuid(uuid);
    }

    /**
     * This method returns the username.
     * @return String
     */
    @JsonProperty("username")
    public String getUsername() {
        return object.getUsername();
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        object.setUsername(username);
    }

    /**
     * This method returns the user's name.
     * @return String
     */
    @JsonProperty("name")
    public String getName() {
        return object.getName();
    }

    @JsonProperty("name")
    public void setName(String name) {
        object.setName(name);
    }

    /**
     * This method returns the user's surname.
     * @return String
     */
    @JsonProperty("surname")
    public String getSurname() {
        return object.getSurname();
    }

    @JsonProperty("surname")
    public void setSurname(String surname) {
        object.setSurname(surname);
    }

    /**
     * This method returns the user's role.
     * @return String
     */
    @JsonProperty("role")
    public String getRole() {
        return object.getRole();
    }

    @JsonProperty("role")
    public void setRole(String role) {
        object.setRole(role);
    }

    /**
     * This method returns the user's email.
     * @return String
     */
    @JsonProperty("email")
    public String getEmail() {
        return object.getEmail();
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        object.setEmail(email);
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        object.setPassword(password);
    }

}
