package xl.application.social.whatsup.web.security;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class SimpleUser extends User {
    public SimpleUser(String username) {
        super(username, "", Collections.emptyList());
    }
}
