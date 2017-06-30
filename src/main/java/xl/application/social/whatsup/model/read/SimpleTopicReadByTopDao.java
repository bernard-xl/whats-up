package xl.application.social.whatsup.model.read;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;

@Component
class SimpleTopicReadByTopDao extends AbstractTopicReadByOrderDao {

    @Override
    public ListingOrder getOrder() {
        return ListingOrder.Top;
    }

    @Override
    protected double scoreOf(Topic topic) {
        return topic.getUpvote();
    }
}
