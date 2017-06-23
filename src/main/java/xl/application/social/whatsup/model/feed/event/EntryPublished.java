package xl.application.social.whatsup.model.feed.event;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public class EntryPublished extends FeedEntryEvent {
    public EntryPublished(FeedEntry entry) {
        super(entry);
    }
}
