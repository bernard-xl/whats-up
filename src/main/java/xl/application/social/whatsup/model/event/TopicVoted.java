package xl.application.social.whatsup.model.event;

import xl.application.social.whatsup.model.entity.Topic;

public class TopicVoted extends TopicEvent {
    
    public TopicVoted(Topic topic) {
        super(topic);
    }
}
