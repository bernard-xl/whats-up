package xl.application.social.whatsup.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.model.feed.query.FeedQueryService;
import xl.application.social.whatsup.model.feed.query.Order;
import xl.application.social.whatsup.util.Page;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private static final int PAGE_SIZE = 20;

    private final FeedQueryService feeds;

    public FeedController(FeedQueryService feeds) {
        this.feeds = feeds;
    }

    @GetMapping("/self")
    public Page<FeedEntry> listSelf(@RequestParam("page") Optional<Integer> page) {
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        return listByAuthor(self, page);
    }

    @GetMapping("/{author}")
    public Page<FeedEntry> listAuthor(@PathVariable("author") String author, @RequestParam("page") Optional<Integer> page) {
        return listByAuthor(author, page);
    }

    @GetMapping({"", "/hot"})
    public Page<FeedEntry> listHot(@RequestParam("page") Optional<Integer> page) {
        return listByOrder(Order.Hot, page);
    }

    @GetMapping("/top")
    public Page<FeedEntry> listTop(@RequestParam("page") Optional<Integer> page) {
        return listByOrder(Order.Top, page);
    }

    @GetMapping("/new")
    public Page<FeedEntry> listNew(@RequestParam("page") Optional<Integer> page) {
        return listByOrder(Order.New, page);
    }

    @GetMapping("/controversial")
    public Page<FeedEntry> listControversial(@RequestParam("page") Optional<Integer> page) {
        return listByOrder(Order.Controversial, page);
    }

    private Page<FeedEntry> listByAuthor(String author, Optional<Integer> page) {
        int offset = (page.orElse(1) - 1) * PAGE_SIZE;
        if (offset < 0) {
            throw new IllegalArgumentException("page must be positive");
        }

        Page<FeedEntry> list = feeds.listByAuthor(author, offset, PAGE_SIZE);
        return list;
    }

    private Page<FeedEntry> listByOrder(Order order, Optional<Integer> page) {
        int offset = (page.orElse(1) - 1) * PAGE_SIZE;
        if (offset < 0) {
            throw new IllegalArgumentException("page must be positive");
        }

        Page<FeedEntry> list = feeds.listByOrder(order, offset, PAGE_SIZE);
        return list;
    }
}
