package xl.application.social.whatsup.model.read;

import xl.application.social.whatsup.model.entity.Topic;

/**
 * Data Access Object for topic query,
 * this interface defines the commonly available operations on all read side dao(s).
 */
public interface TopicReadDao {

    /**
     * Insert a newly submitted topic into data store (to be called by event handler).
     */
    void insert(Topic topic);

    /**
     * Update an existing topic, may affect the rank of the topic in listing (to be called by event handler).
     */
    void update(Topic topic);

    /**
     * Delete the topic from data store (to be called by event handler).
     */
    void delete(Topic topic);
}
