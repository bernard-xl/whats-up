package xl.application.social.whatsup.model.feed.query;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

public interface FeedByAuthorQueryDao extends FeedQueryDao {

    Page<FeedEntry> list(String author, int offset, int count);
}
