package xl.application.social.whatsup.model.read.impl;

import xl.application.social.whatsup.model.entity.Topic;

import java.time.Instant;

class TestUtils {

    private TestUtils() {
        // static class, not intended to be instantiated
    }

    public static Topic createTopic(long id, int upvote, int downvote) {
        Topic topic = new Topic(id, "Testing " + id, null, "JUnit", Instant.now());
        for (int i = 0; i < upvote; i++) {
            topic.doUpvote();
        }
        for (int i = 0; i < downvote; i++) {
            topic.doDownvote();
        }
        return topic;
    }
}
