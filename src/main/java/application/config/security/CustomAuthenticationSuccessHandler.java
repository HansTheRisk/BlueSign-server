package application.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is responsible for managing user redirection
 * for the web application.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * This method registers the redirection strategy.
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, determineTargetUrl(authentication));
        clearAuthenticationAttributes(request);
    }

    /**
     * This method returns matches URL to the user type.
     * @param authentication
     * @return String
     */
    protected String determineTargetUrl(Authentication authentication) {
        final String[] endpoint = {""};
        authentication.getAuthorities().forEach(auth -> {
            if (auth.getAuthority().equals("ROLE_ADMIN"))
                endpoint[0] = "/admin";
            else if (auth.getAuthority().equals("ROLE_LECTURER"))
                endpoint[0] = "/lecturer";
        });
        return endpoint[0];
    }

    /**
     * This method safely clears the authentication attributes.
     * @param request
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    /**
     * A setter for the redirection strategy.
     * @param redirectStrategy
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    /**
     * A getter for the redirection strategy.
     * @return
     */
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
