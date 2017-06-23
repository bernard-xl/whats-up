package xl.application.social.whatsup.model.feed.query;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Component
class SimpleByNewQueryDao implements FeedByOrderQueryDao {

    private final ConcurrentSkipListSet<FeedEntry> entries;
    private final AtomicInteger total;

    public SimpleByNewQueryDao() {
        Comparator<FeedEntry> descTimeOrdering = comparing(FeedEntry::getPublishTime).reversed();
        entries = new ConcurrentSkipListSet<>(descTimeOrdering);
        total = new AtomicInteger(0);
    }

    @Override
    public void insert(FeedEntry entry) {
        entries.add(entry);
        total.incrementAndGet();
    }

    @Override
    public void update(FeedEntry entry) {
        // do nothing...
    }

    @Override
    public void delete(FeedEntry entry) {
        entries.remove(entry);
        total.decrementAndGet();
    }

    @Override
    public Page<FeedEntry> list(int offset, int count) {
        List<FeedEntry> result = entries.descendingSet()
                .stream()
                .skip(offset)
                .limit(count)
                .collect(Collectors.toList());

        return new Page<>(result, total.intValue(), offset, count);
    }

    @Override
    public Order getOrder() {
        return Order.New;
    }
}
