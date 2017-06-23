package xl.application.social.whatsup.util;

import java.util.List;

public class Page<Element> {

    private final List<Element> elements;

    private final int totalElements;

    private final int totalPages;

    private final boolean isLast;

    private final boolean isFirst;

    public Page(List<Element> elements, int totalElements, int offset, int count) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.totalPages = (totalElements + count - 1) / count;
        this.isFirst = offset == 0;
        this.isLast = offset + count >= totalElements;
    }

    public List<Element> getElements() {
        return elements;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return isLast;
    }

    public boolean isFirst() {
        return isFirst;
    }
}
