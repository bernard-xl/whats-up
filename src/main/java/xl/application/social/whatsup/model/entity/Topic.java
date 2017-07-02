package xl.application.social.whatsup.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Entity to represent topic
 */
public class Topic {

    private final long id;
    private final String title;
    private final String link;
    private final String author;
    private final Instant submissionTime;
    private final AtomicLong upvote;
    private final AtomicLong downvote;

    public Topic(long id, String title, String link, String author, Instant submissionTime) {
        this(id, title, link, author, submissionTime, 0, 0);
    }

    @JsonCreator
    public Topic(
            @JsonProperty("id") long id,
            @JsonProperty("title") String title,
            @JsonProperty("link") String link,
            @JsonProperty("author") String author,
            @JsonProperty("submissionTime") Instant submissionTime,
            @JsonProperty("upvote") long upvote,
            @JsonProperty("downvote") long downvote) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.author = author;
        this.submissionTime = submissionTime;
        this.upvote = new AtomicLong(upvote);
        this.downvote = new AtomicLong(downvote);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getSubmissionTime() {
        return submissionTime;
    }

    public long getUpvote() {
        return upvote.get();
    }

    public long getDownvote() {
        return downvote.get();
    }

    public long doUpvote() {
        return upvote.incrementAndGet();
    }

    public long doUpvote(long times) {
        return upvote.addAndGet(times);
    }

    public long doDownvote() {
        return downvote.incrementAndGet();
    }

    public long doDownvote(long times) {
        return downvote.addAndGet(times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id == topic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
