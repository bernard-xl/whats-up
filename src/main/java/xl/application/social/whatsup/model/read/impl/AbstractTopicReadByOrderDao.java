package xl.application.social.whatsup.model.read.impl;

import xl.application.social.whatsup.exception.ArgumentNotValidException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.read.TopicReadByOrderDao;
import xl.application.social.whatsup.util.Page;
import xl.application.social.whatsup.util.PaginationCursor;

import java.nio.BufferUnderflowException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.stream.Collectors;

abstract class AbstractTopicReadByOrderDao implements TopicReadByOrderDao {

    private final int DEFAULT_COUNT_PER_PAGE = 20;
    private final int MAX_COUNT_PER_PAGE = 64;

    private final TreeMap<OrderedKey, Topic> topics;
    private final HashMap<Long, OrderedKey> indices;
    private final ReadLock readLock;
    private final WriteLock writeLock;

    public AbstractTopicReadByOrderDao() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        topics = new TreeMap<>();
        indices = new HashMap<>();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @Override
    public Page<Topic> list(PaginationCursor cursor) {
        try {
            readLock.lock();

            if (topics.isEmpty()) {
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
                return listBefore(cursor.getBefore().get(), count);
            }
            else if (cursor.getAfter().isPresent()) {
                return listAfter(cursor.getAfter().get(), count);
            }
            else {
                return listDefault(count);
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void insert(Topic topic) {
        try {
            writeLock.lock();
            OrderedKey key = new OrderedKey(topic.getId(), scoreOf(topic));
            topics.put(key, topic);
            indices.put(topic.getId(), key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void update(Topic topic) {
        try {
            writeLock.lock();
            OrderedKey newkey = new OrderedKey(topic.getId(), scoreOf(topic));
            OrderedKey oldKey = indices.get(topic.getId());

            if (newkey.compareTo(oldKey) != 0) {
                topics.remove(oldKey);
                topics.put(newkey, topic);
                indices.put(topic.getId(), newkey);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Topic topic) {
        writeLock.lock();
        try {
            OrderedKey key = indices.remove(topic.getId());
            topics.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    protected abstract double scoreOf(Topic topic);

    private Page<Topic> listDefault(int count) {
        List<Topic> contents = topics
                .values()
                .stream()
                .limit(count)
                .collect(Collectors.toList());
        Topic last = contents.get(contents.size() - 1);

        Page<Topic> result = new Page<>(contents, tryEncodeNext(last), null);
        return result;
    }

    private Page<Topic> listAfter(String encodedkey, int count) {
        try {
            OrderedKey key = OrderedKey.decode(encodedkey);
            List<Topic> contents = topics.subMap(key, false, OrderedKey.MAX, true)
                    .values()
                    .stream()
                    .limit(count)
                    .collect(Collectors.toList());
            Topic first = contents.get(0);
            Topic last = contents.get(contents.size() - 1);

            Page<Topic> result = new Page<>(contents, tryEncodeNext(last), tryEncodePrev(first));
            return result;
        } catch (BufferUnderflowException | IllegalArgumentException e) {
            throw new ArgumentNotValidException("after", encodedkey);
        }
    }

    private Page<Topic> listBefore(String encodedkey, int count) {
        try {
            OrderedKey key = OrderedKey.decode(encodedkey);
            List<Topic> contents = topics.subMap(OrderedKey.MIN, true, key, false)
                    .descendingMap()
                    .entrySet()
                    .stream()
                    .limit(count)
                    .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                    .map(Entry::getValue)
                    .collect(Collectors.toList());
            Topic first = contents.get(0);
            Topic last = contents.get(contents.size() - 1);

            Page<Topic> result = new Page<>(contents, tryEncodeNext(last), tryEncodePrev(first));
            return result;
        } catch (BufferUnderflowException | IllegalArgumentException e) {
            throw new ArgumentNotValidException("before", encodedkey);
        }
    }

    private String tryEncodePrev(Topic topic) {
        if (topics.firstEntry().getValue().equals(topic)) {
            return null;
        }
        return OrderedKey.encode(indices.get(topic.getId()));
    }

    private String tryEncodeNext(Topic topic) {
        if (topics.lastEntry().getValue().equals(topic)) {
            return null;
        }
        return OrderedKey.encode(indices.get(topic.getId()));
    }

}
