package xl.application.social.whatsup.model.feed.query;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;

public interface FeedQueryDao {

    void insert(FeedEntry entry);

    void update(FeedEntry entry);

    void delete(FeedEntry entry);
}
