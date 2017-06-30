package xl.application.social.whatsup.model.read;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;

@Component
class SimpleTopicReadByControversyDao extends AbstractTopicReadByOrderDao {

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
                ((double)down / up) :
                ((double)up / down);

        return Math.pow(magnitude, balance);
    }
}
