package com.epm.gestepm.lib.types;

import java.io.Serializable;
import java.util.Optional;

public class PageCursor implements Serializable {

    private Long total;

    private Long limit;

    private Long offset;

    public PageCursor() {
        this.total = 0L;
    }

    public static PageCursor of(PageCursor pageCursor) {

        final PageCursor newCursor = new PageCursor();
        newCursor.setOffset(pageCursor.getOffset());
        newCursor.setLimit(pageCursor.getLimit());
        newCursor.setTotal(pageCursor.getTotal());

        return newCursor;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public PageCursor firstPage() {

        final PageCursor first = new PageCursor();
        first.setTotal(this.getTotal());
        first.setOffset(0L);
        first.setLimit(this.getLimit());

        return first;
    }

    public Optional<PageCursor> prevPage() {

        if (this.offset == 0) {
            return Optional.empty();
        }

        final PageCursor prev = new PageCursor();
        prev.setTotal(this.getTotal());
        prev.setOffset(Math.max(offset - limit, 0));
        prev.setLimit(this.getLimit());

        return Optional.of(prev);
    }

    public Optional<PageCursor> nextPage() {

        final Long nextOffset = offset + limit;

        if (nextOffset > total) {
            return Optional.empty();
        }

        final PageCursor next = new PageCursor();
        next.setTotal(this.getTotal());
        next.setOffset(nextOffset);
        next.setLimit(this.getLimit());

        return Optional.of(next);
    }

    public PageCursor lastPage() {

        final PageCursor last = new PageCursor();
        last.setTotal(this.getTotal());
        last.setOffset((total / limit) * limit);
        last.setLimit(this.getLimit());

        return last;
    }

}
