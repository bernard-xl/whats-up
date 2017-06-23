package xl.application.social.whatsup.model.feed.cmd;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

import java.util.Optional;

public interface FeedCmdDao {

    FeedEntry create(String author, String text);

    Optional<FeedEntry> find(long entryId);

    Optional<FeedEntry> delete(long entryId);
}
