package xl.application.social.whatsup.model.feed.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.query.FeedQueryDao;

import java.util.List;

@Component
class FeedEntryEventHandler {

    private final List<FeedQueryDao> allDao;

    public FeedEntryEventHandler(List<FeedQueryDao> allDao) {
        this.allDao = allDao;
    }

    @EventListener
    public void handleEntryPublished(EntryPublished e) {
        for (FeedQueryDao dao : allDao) {
            dao.insert(e.getEntry());
        }
    }

    @EventListener
    public void handleEntryVoted(EntryVoted e) {
        for (FeedQueryDao dao : allDao) {
            dao.update(e.getEntry());
        }
    }

    @EventListener
    public void handleEntryDeleted(EntryDeleted e) {
        for (FeedQueryDao dao : allDao) {
            dao.delete(e.getEntry());
        }
    }
}
