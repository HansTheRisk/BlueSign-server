package application.domain.user;

import org.springframework.security.core.GrantedAuthority;

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
