package xl.application.social.whatsup.model.read.impl;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.read.ListingOrder;

@Component
class SimpleTopicReadByNewDao extends AbstractTopicReadByOrderDao {

    @Override
    public ListingOrder getOrder() {
        return ListingOrder.New;
    }

    @Override
    protected double scoreOf(Topic topic) {
        return topic.getSubmissionTime().toEpochMilli();
    }
}
