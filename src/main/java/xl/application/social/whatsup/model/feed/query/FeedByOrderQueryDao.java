package xl.application.social.whatsup.model.feed.query;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

public interface FeedByOrderQueryDao extends FeedQueryDao {

    Page<FeedEntry> list(int offset, int count);

    Order getOrder();
}
