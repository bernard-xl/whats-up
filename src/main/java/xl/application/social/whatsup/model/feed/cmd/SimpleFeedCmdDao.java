package xl.application.social.whatsup.model.feed.cmd;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
class SimpleFeedCmdDao implements FeedCmdDao {

    private final ConcurrentSkipListMap<Long, FeedEntry> entries;
    private final AtomicLong sequence;

    public SimpleFeedCmdDao() {
        entries = new ConcurrentSkipListMap<>();
        sequence = new AtomicLong(0);
    }

    @Override
    public FeedEntry create(String author, String text) {
        long id = sequence.getAndIncrement();
        FeedEntry entry = new FeedEntry(id, author, text, Instant.now());
        entries.put(id, entry);
        return entry;
    }

    @Override
    public Optional<FeedEntry> find(long entryId) {
        FeedEntry entry = entries.get(entryId);
        return Optional.ofNullable(entry);
    }

    @Override
    public Optional<FeedEntry> delete(long entryId) {
        FeedEntry entry = entries.remove(entryId);
        return Optional.ofNullable(entry);
    }
}
