package xl.application.social.whatsup.model.write;

import xl.application.social.whatsup.model.entity.Topic;

/**
 * Write side of topic service,
 * support CRUD of topic and responsible for event publish.
 */
public interface TopicWriteService {

    Topic submit(String title, String link, String author);

    void delete(long id);

    void vote(long id, int direction);

    void vote(long id, int direction, int times);
}
