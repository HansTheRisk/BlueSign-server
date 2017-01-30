package main.application.domain.type;

import org.springframework.security.core.GrantedAuthority;

public class Type implements GrantedAuthority {

    private String type;

    public Type() {

    }

    public Type(String type) {
        this.type = type;
    }

    public void setAuthority(String type) {
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return type;
    }
}
