package xl.application.social.whatsup.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO to describe the error happened.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorReply {

    private final String error;

    private final String message;

    @JsonCreator
    public ErrorReply(@JsonProperty("error") String error, @JsonProperty("message") String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
