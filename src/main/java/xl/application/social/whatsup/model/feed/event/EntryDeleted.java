package xl.application.social.whatsup.model.feed.event;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public class EntryDeleted extends FeedEntryEvent {
    public EntryDeleted(FeedEntry entry) {
        super(entry);
    }
}
