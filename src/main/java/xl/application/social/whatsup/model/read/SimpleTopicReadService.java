package xl.application.social.whatsup.model.read;

import org.springframework.stereotype.Service;
import xl.application.social.whatsup.exception.ResourceNotFoundException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.List;

@Service
class SimpleTopicReadService implements TopicReadService {

    private final List<TopicReadByOrderDao> byOrderDao;
    private final TopicReadByAuthorDao byAuthorDao;

    public SimpleTopicReadService(List<TopicReadByOrderDao> byOrderDao, TopicReadByAuthorDao byAuthorDao) {
        this.byOrderDao = byOrderDao;
        this.byAuthorDao = byAuthorDao;
    }

    @Override
    public Page<Topic> list(ListingOrder order, PaginationCursor cursor) {
        for (TopicReadByOrderDao dao : byOrderDao) {
            if (dao.getOrder().equals(order)) {
                return dao.list(cursor);
            }
        }
        throw new ResourceNotFoundException("order", order.name());
    }

    @Override
    public Page<Topic> list(String author, PaginationCursor cursor) {
        return byAuthorDao.list(author, cursor);
    }
}
