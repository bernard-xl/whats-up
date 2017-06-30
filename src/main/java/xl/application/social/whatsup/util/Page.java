package xl.application.social.whatsup.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *  A sublist of of a list of objects, it also contains the cursors for further navigation.
 */
public class Page<T> {

    private final List<T> contents;
    private final String next;
    private final String previous;

    @JsonCreator
    public Page(
            @JsonProperty("contents") List<T> contents,
            @JsonProperty("next") String next,
            @JsonProperty("previous") String previous) {
        this.contents = contents;
        this.next = next;
        this.previous = previous;
    }

    public List<T> getContents() {
        return contents;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }
}
