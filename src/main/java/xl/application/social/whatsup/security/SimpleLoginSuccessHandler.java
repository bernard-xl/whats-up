package xl.application.social.whatsup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Return HTTP status code 200 (OK) with username, instead of redirection to home page.
 */
class SimpleLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    public SimpleLoginSuccessHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectNode authInfo = mapper.createObjectNode();
        authInfo.put("name", authentication.getName());

        mapper.writeValue(response.getOutputStream(), authInfo);

        clearAuthenticationAttributes(request);
    }
}
