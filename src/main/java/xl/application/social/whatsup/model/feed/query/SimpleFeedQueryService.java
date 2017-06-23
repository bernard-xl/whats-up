package xl.application.social.whatsup.model.feed.query;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

import java.util.List;

@Component
class SimpleFeedQueryService implements FeedQueryService {

    private final FeedByAuthorQueryDao authorDao;
    private final List<FeedByOrderQueryDao> orderedDao;

    public SimpleFeedQueryService(FeedByAuthorQueryDao authorDao, List<FeedByOrderQueryDao> orderedDao) {
        this.authorDao = authorDao;
        this.orderedDao = orderedDao;
    }

    @Override
    public Page<FeedEntry> listByOrder(Order order, int offset, int count) {
        return queryByOrder(order).list(offset, count);
    }

    @Override
    public Page<FeedEntry> listByAuthor(String author, int offset, int count) {
        return authorDao.list(author, offset, count);
    }

    private FeedByOrderQueryDao queryByOrder(Order order) {
        for (FeedByOrderQueryDao dao : orderedDao) {
            if (dao.getOrder() == order) {
                return dao;
            }
        }
        throw new IllegalStateException("No dao found for order " + order.name());
    }
}
