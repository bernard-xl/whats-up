package xl.application.social.whatsup.model.feed.cmd;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public interface FeedCmdService {

    FeedEntry publish(String author, String text);

    void delete(long entryId);

    void upvote(long entryId);

    void downvote(long entryId);
}
