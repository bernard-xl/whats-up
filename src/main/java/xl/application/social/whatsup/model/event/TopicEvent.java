package xl.application.social.whatsup.model.event;

import xl.application.social.whatsup.model.entity.Topic;

import java.util.Objects;

public abstract class TopicEvent {

    private Topic topic;

    public TopicEvent(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicEvent)) return false;
        TopicEvent that = (TopicEvent) o;
        return Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic);
    }
}
