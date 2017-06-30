package xl.application.social.whatsup.model.entity;

import java.time.Instant;

/**
 * Utility class to help creation of {@link Topic} in tests.
 */
public class Topics {

    private Topics() {
        // static class, not intended to be instantiated.
    }

    public static Topic get(long id, String title) {
        return new Topic(id, title, null, "nobody", Instant.now());
    }

    public static Topic get(long id, String title, String author) {
        return new Topic(id, title, null, author, Instant.now());
    }

    public static Topic get(long id, String title, long upvote, long downvote) {
        return new Topic(id, title, null, "nobody", Instant.now(), upvote, downvote);
    }
}
