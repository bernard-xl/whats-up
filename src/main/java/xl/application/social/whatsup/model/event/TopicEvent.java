package xl.application.social.whatsup.model.event;

import xl.application.social.whatsup.model.entity.Topic;

public abstract class TopicEvent {

    private Topic topic;

    public TopicEvent(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
