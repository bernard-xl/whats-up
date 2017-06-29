package xl.application.social.whatsup.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import xl.application.social.whatsup.exception.AuthenticationFailedException;

import java.util.Collections;

/**
 * Simple authentication provider that only require username (any username will do),
 * for demonstration purpose.
 */
class SimpleAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        if (username == null || username.isEmpty()) {
            throw new AuthenticationFailedException("username is required");
        }
        
        User user = new User(username, "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
