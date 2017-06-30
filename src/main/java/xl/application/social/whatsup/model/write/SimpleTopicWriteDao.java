package xl.application.social.whatsup.model.write;

import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.entity.Topic;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
class SimpleTopicWriteDao implements TopicWriteDao {

    private final ConcurrentHashMap<Long, Topic> topics;
    private final AtomicLong sequence;

    public SimpleTopicWriteDao() {
        topics = new ConcurrentHashMap<>();
        sequence = new AtomicLong(0);
    }

    @Override
    public Topic create(String title, String link, String author, Instant submissionTime) {
        long id = sequence.getAndIncrement();
        Topic topic = new Topic(id, title, link, author, submissionTime);
        topics.put(id, topic);
        return topic;
    }

    @Override
    public Optional<Topic> find(long id) {
        Topic found = topics.get(id);
        return Optional.ofNullable(found);
    }

    @Override
    public Optional<Topic> delete(long id) {
        Topic deleted = topics.remove(id);
        return Optional.ofNullable(deleted);
    }
}
