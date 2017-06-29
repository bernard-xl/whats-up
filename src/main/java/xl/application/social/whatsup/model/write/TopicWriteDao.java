package xl.application.social.whatsup.model.write;

import xl.application.social.whatsup.model.entity.Topic;

import java.time.Instant;
import java.util.Optional;

/**
 * Data Access Object for topic CRUD.
 */
public interface TopicWriteDao {

    Topic create(String title, String link, String author, Instant submissionTime);

    Optional<Topic> find(long id);

    Optional<Topic> delete(long id);
}
