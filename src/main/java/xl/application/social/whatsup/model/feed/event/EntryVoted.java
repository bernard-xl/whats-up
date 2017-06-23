package xl.application.social.whatsup.model.feed.event;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public class EntryVoted extends FeedEntryEvent {
    public EntryVoted(FeedEntry entry) {
        super(entry);
    }
}
