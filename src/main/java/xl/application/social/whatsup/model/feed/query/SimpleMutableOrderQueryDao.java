package xl.application.social.whatsup.model.feed.query;

import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

abstract class SimpleMutableOrderQueryDao<Ordering extends Comparable<Ordering>>
        implements FeedByOrderQueryDao {

    private final TreeMap<Ordering, FeedEntry> entries;
    private final HashMap<Long, Ordering> indices;
    private final Lock writeLock;
    private final Lock readLock;

    public SimpleMutableOrderQueryDao() {
        ReadWriteLock rw = new ReentrantReadWriteLock();
        entries = new TreeMap<>();
        indices = new HashMap<>();
        writeLock = rw.writeLock();
        readLock = rw.readLock();
    }

    @Override
    public void insert(FeedEntry entry) {
        try {
            writeLock.lock();
            Ordering order = orderOf(entry);
            entries.put(order, entry);
            indices.put(entry.getId(), order);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void update(FeedEntry entry) {
        try {
            writeLock.lock();

            Ordering oldOrder = indices.remove(entry.getId());
            entries.remove(oldOrder);

            Ordering newOrder = orderOf(entry);
            entries.put(newOrder, entry);
            indices.put(entry.getId(), newOrder);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(FeedEntry entry) {
        try {
            writeLock.lock();
            Ordering order = indices.remove(entry.getId());
            entries.remove(order);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Page<FeedEntry> list(int offset, int count) {
        try {
            readLock.lock();
            List<FeedEntry> result = entries.values()
                    .stream()
                    .skip(offset)
                    .limit(count)
                    .collect(Collectors.toList());
            return new Page<>(result, entries.size(), offset, count);
        } finally {
            readLock.lock();
        }
    }

    protected abstract Ordering orderOf(FeedEntry entry);
}
