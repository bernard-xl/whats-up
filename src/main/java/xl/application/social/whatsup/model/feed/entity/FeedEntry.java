package xl.application.social.whatsup.model.feed.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FeedEntry {

    private final long id;

    private final String author;

    private final String text;

    private final Instant publishTime;

    private final AtomicLong upvote;

    private final AtomicLong downvote;

    public FeedEntry(long id, String author, String text, Instant publishTime) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.publishTime = publishTime;
        this.upvote = new AtomicLong(0);
        this.downvote = new AtomicLong(0);
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public long getUpvote() {
        return upvote.get();
    }

    public long getDownvote() {
        return downvote.get();
    }

    public void upvote() {
        upvote.incrementAndGet();
    }

    public void downvote() {
        downvote.incrementAndGet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedEntry feedEntry = (FeedEntry) o;
        return id == feedEntry.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FeedEntry{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

