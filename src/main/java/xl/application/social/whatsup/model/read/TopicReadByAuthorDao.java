package xl.application.social.whatsup.model.read;

import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

/**
 * Data Access Object for topic query by author name.
 */
public interface TopicReadByAuthorDao extends TopicReadDao {

    /**
     * Listing part of the topics submitted by the same author with {@link PaginationCursor}.
     */
    Page<Topic> list(String author, PaginationCursor cursor);
}
