package xl.application.social.whatsup.web.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO to represent a submission
 */
public class TopicSubmission {

    @NotNull
    @Size(min = 5, max = 255)
    private String title;

    @Pattern(regexp = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\\\?([^#]*))?(#(.*))?")
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
