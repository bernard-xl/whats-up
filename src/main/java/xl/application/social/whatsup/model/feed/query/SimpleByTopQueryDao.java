package xl.application.social.whatsup.model.feed.query;


import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;

import java.util.Objects;

@Component
class SimpleByTopQueryDao extends SimpleMutableOrderQueryDao<TopOrder> {

    @Override
    public Order getOrder() {
        return Order.Top;
    }

    @Override
    protected TopOrder orderOf(FeedEntry entry) {
        return new TopOrder(entry);
    }
}

class TopOrder implements Comparable<TopOrder> {

    private final long id;
    private final long upvote;

    public TopOrder(FeedEntry entry) {
        this.id = entry.getId();
        this.upvote = entry.getUpvote();
    }

    @Override
    public int compareTo(TopOrder other) {
        return Long.compare(upvote, other.upvote);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopOrder order = (TopOrder) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
