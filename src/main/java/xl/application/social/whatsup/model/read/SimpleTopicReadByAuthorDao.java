package xl.application.social.whatsup.model.read;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.exception.ArgumentNotValidException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

@Component
class SimpleTopicReadByAuthorDao implements TopicReadByAuthorDao {

    private final int DEFAULT_COUNT_PER_PAGE = 20;
    private final int MAX_COUNT_PER_PAGE = 64;

    private final ConcurrentHashMap<String, List<Topic>> topics;
    private final ConcurrentHashMap<String, ReentrantReadWriteLock> locks;

    public SimpleTopicReadByAuthorDao() {
        topics = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
    }

    @Override
    public Page<Topic> list(String author, PaginationCursor cursor) {
        ReadLock lock = readLockOf(author);
        try {
            lock.lock();

            List<Topic> list = mustGetListOf(author);
            if (list.isEmpty()) {
                return new Page<>(Collections.emptyList(), null, null);
            }

            int count = cursor.getCount().orElse(DEFAULT_COUNT_PER_PAGE);
            if (count > MAX_COUNT_PER_PAGE) {
                count = MAX_COUNT_PER_PAGE;
            }
            if (count < 1) {
                count = 1;
            }

            if (cursor.getBefore().isPresent()) {
                return listBefore(list, cursor.getBefore().get(), count);
            }
            else {
                return listAfter(list, cursor.getAfter().orElse("0"), count);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void insert(Topic topic) {
        String author = topic.getAuthor();
        WriteLock lock = writeLockOf(author);
        try {
            lock.lock();

            List<Topic> list = mustGetListOf(author);
            list.add(0, topic);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(Topic topic) {
        // do nothing
    }

    @Override
    public void delete(Topic topic) {
        String author = topic.getAuthor();
        WriteLock lock = writeLockOf(author);
        try {
            lock.lock();

            List<Topic> list = topics.get(author);
            if (list != null) {
                list.remove(topic);
            }
        } finally {
            lock.unlock();
        }
    }

    private WriteLock writeLockOf(String author) {
        ReentrantReadWriteLock rw = locks.computeIfAbsent(author, ignored -> new ReentrantReadWriteLock());
        return rw.writeLock();
    }

    private ReadLock readLockOf(String author) {
        ReentrantReadWriteLock rw = locks.computeIfAbsent(author, ignored -> new ReentrantReadWriteLock());
        return rw.readLock();
    }

    private List<Topic> mustGetListOf(String author) {
        return topics.computeIfAbsent(author, ignored -> new ArrayList<>());
    }

    private Page<Topic> listAfter(List<Topic> list, String cursor, int count) {
        try {
            int offset = Integer.parseInt(cursor);
            int fromIndex = Math.max(0, offset);
            int toIndex = Math.min(list.size(), offset + count);

            List<Topic> contents = list.subList(fromIndex, toIndex);
            String next = (toIndex < list.size()) ? Integer.toString(toIndex) : null;
            String prev = (fromIndex > 0) ? Integer.toString(fromIndex) : null;

            return new Page<>(contents, next, prev);
        } catch (NumberFormatException e) {
            throw new ArgumentNotValidException("after", cursor);
        }
    }

    private Page<Topic> listBefore(List<Topic> list, String cursor, int count) {
        try {
            int offset = Integer.parseInt(cursor);
            int fromIndex = Math.max(0, offset - count);
            int toIndex = Math.min(list.size(), offset);

            List<Topic> contents = list.subList(fromIndex, toIndex);
            String next = (toIndex < list.size()) ? Integer.toString(toIndex) : null;
            String prev = (fromIndex > 0) ? Integer.toString(fromIndex) : null;

            return new Page<>(contents, next, prev);
        } catch (NumberFormatException e) {
            throw new ArgumentNotValidException("before", cursor);
        }
    }
}
