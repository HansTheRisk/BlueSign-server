package application.config.security;

import application.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web Security Config class for the web application.
 * Determines what security level applies to what API controllers.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * Configures security for the API endpoints.
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/student/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/lecturer/**").hasRole("LECTURER")
                .and()
                .formLogin().loginPage("/login").successHandler(authenticationSuccessHandler);
    }

}
