package com.epm.gestepm.lib.jdbc.api.query;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SQLInsert<K> extends SQLQuery {

    private Consumer<K> keyConsumer;

    public SQLInsert() {
        super();
    }

    @Override
    public SQLInsert<K> useQuery(final String queryKey) {
        super.useQuery(queryKey);
        return this;
    }

    @Override
    public SQLInsert<K> useFilter(final String filterKey) {
        super.useFilter(filterKey);
        return this;
    }

    @Override
    public SQLInsert<K> withParams(final Map<String, Object> params) {
        super.withParams(params);
        return this;
    }

    public SQLInsert<K> onGeneratedKey(final Consumer<K> keyConsumer) {
        this.keyConsumer = keyConsumer;
        return this;
    }

    public Consumer<K> getKeyConsumer() {
        return this.keyConsumer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLInsert)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final SQLInsert<?> sqlInsert = (SQLInsert<?>) o;
        return Objects.equals(this.getKeyConsumer(), sqlInsert.getKeyConsumer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getKeyConsumer());
    }

    @Override
    public String toString() {
        return "SQLInsert [keyConsumer=" + this.keyConsumer + "]";
    }

}
