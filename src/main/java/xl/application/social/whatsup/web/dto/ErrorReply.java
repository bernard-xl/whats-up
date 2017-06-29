package xl.application.social.whatsup.web.dto;

/**
 * DTO to describe the error happened.
 */
public class ErrorReply {

    private final String error;

    private final String message;

    public ErrorReply(String error, String message) {
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
