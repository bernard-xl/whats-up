package xl.application.social.whatsup.model.read.impl;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.read.ListingOrder;

@Component
class SimpleTopicReadByHotDao extends AbstractTopicReadByOrderDao {

    @Override
    public ListingOrder getOrder() {
        return ListingOrder.Hot;
    }

    @Override
    protected double scoreOf(Topic topic) {
        long vote = topic.getUpvote() - topic.getDownvote();
        long sign = Long.compare(vote, 0);
        long existence = topic.getSubmissionTime().getEpochSecond() - 1134028003;

        double order = Math.log10(Math.max(vote, 1));

        return sign * order + existence / 45000;
    }
}
