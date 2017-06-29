package xl.application.social.whatsup.util;

import java.util.List;

/**
 *  A sublist of of a list of objects, it also contains the cursors for further navigation.
 */
public class Page<T> {

    private final List<T> contents;
    private final String next;
    private final String previous;

    public Page(List<T> contents, String next, String previous) {
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
