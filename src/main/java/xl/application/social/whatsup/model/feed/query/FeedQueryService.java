package xl.application.social.whatsup.model.feed.query;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

public interface FeedQueryService {

    Page<FeedEntry> listByOrder(Order order, int offset, int count);

    Page<FeedEntry> listByAuthor(String author, int offset, int count);
}
