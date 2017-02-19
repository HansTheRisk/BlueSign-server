package application.domain.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents the role a user can have.
 */
public class Role implements GrantedAuthority {

    private String role;

    public Role() {

    }

    public Role(String role) {
        this.role = role;
    }

    public void setAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

}
