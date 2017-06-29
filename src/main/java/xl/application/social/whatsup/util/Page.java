package xl.application.social.whatsup.util;

import java.util.Collection;

/**
 *  A sublist of of a list of objects, it also contains the cursors for further navigation.
 */
public class Page<T> {

    private final Collection<T> contents;
    private final String next;
    private final String previous;

    public Page(Collection<T> contents, String next, String previous) {
        this.contents = contents;
        this.next = next;
        this.previous = previous;
    }

    public Collection<T> getContents() {
        return contents;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }
}
