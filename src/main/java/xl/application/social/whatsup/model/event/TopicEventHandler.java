package xl.application.social.whatsup.model.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xl.application.social.whatsup.model.read.TopicReadDao;

import java.util.List;

@Component
class TopicEventHandler {

    private List<TopicReadDao> readDao;

    public TopicEventHandler(List<TopicReadDao> readDao) {
        this.readDao = readDao;
    }

    @EventListener
    public void handleSubmitted(TopicSubmitted e) {
        for (TopicReadDao dao : readDao) {
            dao.insert(e.getTopic());
        }
    }

    @EventListener
    public void handleVoted(TopicVoted e) {
        for (TopicReadDao dao : readDao) {
            dao.update(e.getTopic());
        }
    }

    @EventListener
    public void handleDeleted(TopicDeleted e) {
        for (TopicReadDao dao : readDao) {
            dao.delete(e.getTopic());
        }
    }
}
