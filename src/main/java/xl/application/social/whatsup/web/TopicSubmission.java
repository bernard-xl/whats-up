package xl.application.social.whatsup.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO to represent a submission
 */
public class TopicSubmission {

    private static final String URL_PATTERN = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

    @NotNull
    @Size(min = 5, max = 255)
    private String title;

    @Pattern(regexp = URL_PATTERN, message = "invalid HTTP URL")
    private String link;

    private TopicSubmission() {
        // for Spring MVC to instantiate this class
    }

    public TopicSubmission(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
