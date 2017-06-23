package xl.application.social.whatsup.model.feed.query;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.feed.entity.FeedEntry;
import xl.application.social.whatsup.util.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
class SimpleByAuthorQueryDao implements FeedByAuthorQueryDao {

    private ConcurrentHashMap<String, List<FeedEntry>> entries;
    private ConcurrentHashMap<String, ReadWriteLock> locks;

    public SimpleByAuthorQueryDao() {
        entries = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(FeedEntry entry) {
        String author = entry.getAuthor();
        Lock lock = writeLockOf(author);
        try {
            lock.lock();

            List<FeedEntry> list = mustGetListOf(author);
            list.add(0, entry);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(FeedEntry entry) {
        // do nothing...
    }

    @Override
    public void delete(FeedEntry entry) {
        String author = entry.getAuthor();
        Lock lock = writeLockOf(author);
        try {
            lock.lock();

            List<FeedEntry> list = entries.get(author);
            if (list != null) {
                list.remove(entry);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Page<FeedEntry> list(String author, int offset, int count) {
        Objects.requireNonNull(author, "author must be non-null");
        if (offset < 0) {
            throw new IllegalArgumentException("offset must be >= 0");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be > 0");
        }

        Lock lock = readLockOf(author);
        try {
            lock.lock();
            List<FeedEntry> list = entries.get(author);

            if (list != null) {
                int fromIndex = Math.min(offset, list.size());
                int toIndex = Math.min(offset + count, list.size());
                List<FeedEntry> result = list.subList(fromIndex, toIndex);

                return new Page<>(result, list.size(), offset, count);
            }
            return new Page<>(Collections.emptyList(), 0, offset, count);

        } finally {
            lock.unlock();
        }
    }

    private Lock writeLockOf(String author) {
        ReadWriteLock rw = locks.computeIfAbsent(author, ignored -> new ReentrantReadWriteLock());
        return rw.writeLock();
    }

    private Lock readLockOf(String author) {
        ReadWriteLock rw = locks.computeIfAbsent(author, ignored -> new ReentrantReadWriteLock());
        return rw.readLock();
    }

    private List<FeedEntry> mustGetListOf(String author) {
        return entries.computeIfAbsent(author, ignored -> new ArrayList<>());
    }
}
