package xl.application.social.whatsup.model.feed.query;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;

import java.time.Instant;
import java.util.Objects;

@Component
class SimpleByHotQueryDao extends SimpleMutableOrderQueryDao<HotOrder> {

    @Override
    public Order getOrder() {
        return Order.Hot;
    }

    @Override
    protected HotOrder orderOf(FeedEntry entry) {
        return new HotOrder(entry);
    }
}

class HotOrder implements Comparable<HotOrder> {

    private final long id;
    private final double score;
    private final Instant timestamp;

    public HotOrder(FeedEntry entry) {
        id = entry.getId();
        score = calculateScore(entry);
        timestamp = Instant.now();
    }

    @Override
    public int compareTo(HotOrder other) {
        int cmp = Double.compare(other.score, score);
        return (cmp == 0)? -timestamp.compareTo(other.timestamp) : cmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotOrder hotOrder = (HotOrder) o;
        return id == hotOrder.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private double calculateScore(FeedEntry entry) {
        long vote = entry.getUpvote() - entry.getDownvote();
        long sign = Long.compare(vote, 0);
        long existence = entry.getPublishTime().getEpochSecond() - 1134028003;

        double order = Math.log10(Math.max(vote, 1));

        return sign * order + existence / 45000;
    }
}