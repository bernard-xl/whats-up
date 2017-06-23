package xl.application.social.whatsup.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xl.application.social.whatsup.model.feed.cmd.FeedCmdService;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;

@RestController
@RequestMapping("/entry")
public class EntryController {

    private final FeedCmdService feeds;

    public EntryController(FeedCmdService feeds) {
        this.feeds = feeds;
    }

    @PostMapping
    public FeedEntry publish(@RequestParam("text") String text, Authentication auth) {
        FeedEntry entry = feeds.publish(auth.getName(), text);
        return entry;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        feeds.delete(id);
    }

    @PostMapping("/{id}/upvote")
    public void upvote(@PathVariable("id") long id) {
        feeds.upvote(id);
    }

    @PostMapping("/{id}/downvote")
    public void downvote(@PathVariable("id") long id) {
        feeds.downvote(id);
    }
}
