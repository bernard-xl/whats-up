package xl.application.social.whatsup.model.read.impl;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.read.ListingOrder;

@Component
class SimpleTopicReadByControversialDao extends AbstractTopicReadByOrderDao {

    @Override
    public ListingOrder getOrder() {
        return ListingOrder.Controversial;
    }

    @Override
    protected double scoreOf(Topic topic) {
        long up = topic.getUpvote();
        long down = topic.getDownvote();

        if (up == 0 || down == 0) {
            return 0.00;
        }

        long magnitude = up + down;
        double balance = (up > down)?
                ((double)up / down) :
                ((double)down / up);

        return Math.pow(magnitude, balance);
    }
}
