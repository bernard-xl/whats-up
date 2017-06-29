package xl.application.social.whatsup.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.read.ListingOrder;
import xl.application.social.whatsup.model.read.TopicReadService;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

@RestController
public class TopicListingController {

    private final TopicReadService topics;

    public TopicListingController(TopicReadService topics) {
        this.topics = topics;
    }

    @GetMapping("/hot")
    public Page<Topic> listHot(@ModelAttribute PaginationCursor cursor) {
        return topics.list(ListingOrder.Hot, cursor);
    }

    @GetMapping("/top")
    public Page<Topic> listTop(@ModelAttribute PaginationCursor cursor) {
        return topics.list(ListingOrder.Top, cursor);
    }

    @GetMapping("/new")
    public Page<Topic> listNew(@ModelAttribute PaginationCursor cursor) {
        return topics.list(ListingOrder.New, cursor);
    }

    @GetMapping("/controversial")
    public Page<Topic> listControversial(@ModelAttribute PaginationCursor cursor) {
        return topics.list(ListingOrder.Controversial, cursor);
    }

    @GetMapping("/user/{author}")
    public Page<Topic> listHot(@PathVariable("author") String author, @ModelAttribute PaginationCursor cursor) {
        return topics.list(author, cursor);
    }
}
