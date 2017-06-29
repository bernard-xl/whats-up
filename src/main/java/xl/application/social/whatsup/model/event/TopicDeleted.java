package xl.application.social.whatsup.model.event;

import xl.application.social.whatsup.model.entity.Topic;

public class TopicDeleted extends TopicEvent {
    
    public TopicDeleted(Topic topic) {
        super(topic);
    }
}
