package xl.application.social.whatsup.model.feed.event;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public abstract class FeedEntryEvent {

    private FeedEntry entry;

    public FeedEntryEvent(FeedEntry entry) {
        this.entry = entry;
    }

    public FeedEntry getEntry() {
        return entry;
    }
}
