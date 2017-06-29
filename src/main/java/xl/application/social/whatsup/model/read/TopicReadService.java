package xl.application.social.whatsup.model.read;

import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

/**
 * Read side of the topic service
 *
 * Support necessary operations to query topics in various way.
 */
public interface TopicReadService {

    /**
     * Query topics in specified {@link ListingOrder}.
     */
    Page<Topic> list(ListingOrder order, PaginationCursor cursor);

    /**
     * Query topics that are submitted by the same author.
     */
    Page<Topic> list(String author, PaginationCursor cursor);
}
