package xl.application.social.whatsup.model.feed.query;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;

import java.util.Objects;

@Component
class SimpleByControversialQueryDao extends SimpleMutableOrderQueryDao<ControversialOrder> {

    @Override
    public Order getOrder() {
        return Order.Controversial;
    }

    @Override
    protected ControversialOrder orderOf(FeedEntry entry) {
        return new ControversialOrder(entry);
    }
}

class ControversialOrder implements Comparable<ControversialOrder> {

    private final long id;
    private final double score;

    public ControversialOrder(FeedEntry feedEntry) {
        this.id = feedEntry.getId();
        this.score = calculateScore(feedEntry);
    }

    @Override
    public int compareTo(ControversialOrder other) {
        return Double.compare(score, other.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControversialOrder that = (ControversialOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private double calculateScore(FeedEntry entry) {
        long up = entry.getUpvote();
        long down = entry.getDownvote();

        long magnitude = up + down;
        double balance = (up > down)?
                ((double)up / down) :
                ((double)down / up);

        return Math.pow(magnitude, balance);
    }
}
