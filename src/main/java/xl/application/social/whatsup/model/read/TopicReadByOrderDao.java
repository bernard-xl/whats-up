package xl.application.social.whatsup.model.read;

import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

/**
 * Data Access Object for topic query by {@link ListingOrder}.
 */
public interface TopicReadByOrderDao extends TopicReadDao {

    /**
     * Listing part of the topics with {@link PaginationCursor}.
     */
    Page<Topic> list(PaginationCursor cursor);

    /**
     * {@link ListingOrder} supported by this DAO.
     */
    ListingOrder getOrder();
}
