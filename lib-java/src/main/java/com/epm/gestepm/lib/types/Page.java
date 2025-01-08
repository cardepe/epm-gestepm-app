package com.epm.gestepm.lib.types;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNullElse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
public class Page<T> implements Iterable<T>, Serializable {

    private final PageCursor cursor;

    private List<T> content;

    public Page() {
        this.cursor = new PageCursor();
        this.content = new ArrayList<>();
    }

    public Page(PageCursor cursor, List<T> content) {
        this.cursor = PageCursor.of(cursor);
        this.content = content;
    }

    public Long getTotal() {
        return cursor.getTotal();
    }

    public void setTotal(Long total) {
        this.cursor.setTotal(total);
    }

    public Long getLimit() {
        return cursor.getLimit();
    }

    public void setLimit(Long limit) {
        this.cursor.setLimit(limit);
    }

    public Long getOffset() {
        return cursor.getOffset();
    }

    public void setOffset(Long offset) {
        this.cursor.setOffset(offset);
    }

    public PageCursor cursor() {
        return this.cursor;
    }

    public Optional<T> get(int index) {

        final List<T> list = requireNonNullElse(this.content, new ArrayList<T>());

        if (list.size() > index) {
            return Optional.of(list.get(index));
        }

        return Optional.empty();
    }

    public void add(T value) {
        this.content.add(value);
    }

    public void addAll(Collection<T> collection) {
        this.content.addAll(collection);
    }

    public boolean isEmpty() {
        return requireNonNullElse(this.content, new ArrayList<T>()).isEmpty();
    }

    public int size() {
        return this.content.size();
    }

    public Page<T> setEmpty() {

        setContent(new ArrayList<>());
        setTotal(0L);
        setOffset(0L);
        setLimit(20L);

        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return requireNonNullElse(this.content, new ArrayList<T>()).iterator();
    }
}
