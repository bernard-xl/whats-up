package xl.application.social.whatsup.web;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.write.TopicWriteService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TopicAPIController {

    private final TopicWriteService topics;

    public TopicAPIController(TopicWriteService topics) {
        this.topics = topics;
    }

    @PostMapping("/submit")
    public Topic submit(@Validated @ModelAttribute TopicSubmission submission, Authentication auth) {
        return topics.submit(submission.getTitle(), submission.getLink(), auth.getName());
    }

    @PostMapping("/vote")
    public void vote(@RequestParam("id") long id, @RequestParam("dir") int direction) {
        topics.vote(id, direction);
    }

    @PostMapping("/del")
    public void delete(@RequestParam("id") long id) {
        topics.delete(id);
    }
}
