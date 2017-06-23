package xl.application.social.whatsup.model.feed.cmd;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.model.feed.event.EntryDeleted;
import xl.application.social.whatsup.model.feed.event.EntryPublished;
import xl.application.social.whatsup.model.feed.event.EntryVoted;

import java.util.Objects;

@Component
class SimpleFeedCmdService implements FeedCmdService {

    private final ApplicationEventPublisher eventPublisher;

    private final FeedCmdDao dao;

    public SimpleFeedCmdService(ApplicationEventPublisher eventPublisher, FeedCmdDao dao) {
        this.eventPublisher = eventPublisher;
        this.dao = dao;
    }

    @Override
    public FeedEntry publish(String author, String text) {
        Objects.requireNonNull(author, "author must be non-null");
        Objects.requireNonNull(text, "text must be non-null");

        FeedEntry entry = dao.create(author, text);

        eventPublisher.publishEvent(new EntryPublished(entry));
        return entry;
    }

    @Override
    public void delete(long entryId) {
        dao.delete(entryId).ifPresent(entry -> {
            eventPublisher.publishEvent(new EntryDeleted(entry));
        });
    }

    @Override
    public void upvote(long entryId) {
        dao.find(entryId).ifPresent(entry -> {
            entry.upvote();
            eventPublisher.publishEvent(new EntryVoted(entry));
        });
    }

    @Override
    public void downvote(long entryId) {
        dao.find(entryId).ifPresent(entry -> {
            entry.downvote();
            eventPublisher.publishEvent(new EntryVoted(entry));
        });
    }
}
