package xl.application.social.whatsup.util;

import java.util.Optional;

/**
 * Cursor to navigate part of a huge list of objects.
 */
public class PaginationCursor {

    private String after;
    private String before;
    private Integer count;

    private PaginationCursor() {
        // for Spring MVC to instantiate this class
    }

    public PaginationCursor(String after, String before, Integer count) {
        this.after = after;
        this.before = before;
        this.count = count;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Optional<String> getAfter() {
        return Optional.ofNullable(after);
    }

    public Optional<String> getBefore() {
        return Optional.ofNullable(before);
    }

    public Optional<Integer> getCount() {
        return Optional.ofNullable(count);
    }
}
