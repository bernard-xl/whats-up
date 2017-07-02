package xl.application.social.whatsup.model.write;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xl.application.social.whatsup.exception.ResourceNotFoundException;
import xl.application.social.whatsup.model.entity.Topic;
import xl.application.social.whatsup.model.event.TopicDeleted;
import xl.application.social.whatsup.model.event.TopicSubmitted;
import xl.application.social.whatsup.model.event.TopicVoted;

import java.time.Instant;

@Service
class SimpleTopicWriteService implements TopicWriteService {

    private final ApplicationEventPublisher publisher;
    private final TopicWriteDao dao;

    public SimpleTopicWriteService(ApplicationEventPublisher publisher, TopicWriteDao dao) {
        this.publisher = publisher;
        this.dao = dao;
    }

    @Override
    public Topic submit(String title, String link, String author) {
        Topic topic = dao.create(title, link, author, Instant.now());
        publisher.publishEvent(new TopicSubmitted(topic));
        return topic;
    }

    @Override
    public void delete(long id) {
        Topic topic = dao.delete(id).orElseThrow(() -> new ResourceNotFoundException("topic", id));
        publisher.publishEvent(new TopicDeleted(topic));
    }

    @Override
    public void vote(long id, int direction) {
        vote(id, direction, 1);
    }

    @Override
    public void vote(long id, int direction, int times) {
        Topic topic = dao.find(id).orElseThrow(() -> new ResourceNotFoundException("topic", id));
        if (direction > 0) {
            topic.doUpvote(times);
        } else if (direction < 0) {
            topic.doDownvote(times);
        }
        publisher.publishEvent(new TopicVoted(topic));
    }
}
