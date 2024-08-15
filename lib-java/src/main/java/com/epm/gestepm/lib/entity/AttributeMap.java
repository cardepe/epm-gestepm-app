package com.epm.gestepm.lib.entity;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttributeMap extends HashMap<String, Object> {

    public void putBooleanAsInt(String attrKey, Boolean value) {

        if (value != null) {
            put(attrKey, value ? 1 : 0);
        }
    }

    public <T extends Enum> void putEnum(String attrKey, T value) {

        final String valueToPut = value != null ? value.name() : null;
        put(attrKey, valueToPut);
    }

    public <T> void putList(String attrKey, List<T> value) {

        final List<T> valueToPut = value != null && !value.isEmpty() ? value : null;
        put(attrKey, valueToPut);
    }

    public <T> void putSet(String attrKey, Set<T> value) {

        final Set<T> valueToPut = value != null && !value.isEmpty() ? value : null;
        put(attrKey, valueToPut);
    }

    public <T extends Enum> void putEnumList(String attrKey, List<T> value) {

        List<String> valueToPut = null;

        if (value != null && !value.isEmpty()) {
            valueToPut = value.stream().map(Enum::name).collect(Collectors.toList());
        }

        put(attrKey, valueToPut);
    }

    public <T extends Enum> void putEnumSet(String attrKey, Set<T> value) {

        List<String> valueToPut = null;

        if (value != null && !value.isEmpty()) {
            valueToPut = value.stream().map(Enum::name).collect(Collectors.toList());
        }

        put(attrKey, valueToPut);
    }

    public void putTimestamp(String attrKey, OffsetDateTime dateTime) {

        final Timestamp valueToPut = dateTime != null ? Timestamp.valueOf(dateTime.toLocalDateTime()) : null;
        put(attrKey, valueToPut);
    }

    public void putFormatted(String attrKey, String value, String format) {

        final String valueToPut = value != null ? String.format(format, value) : null;
        put(attrKey, valueToPut);
    }

    public void putLike(String attrKey, String value) {

        final String valueToPut = value != null ? "%" + value + "%" : null;
        put(attrKey, valueToPut);
    }

    public <T> void putWithTransform(String attrKey, T value, Function<T, T> transformer) {

        if (value != null) {
            put(attrKey, transformer.apply(value));
        }
    }

    public <T> void putListWithTransform(String attrKey, List<T> value, Function<T, T> transformer) {

        if (value != null) {
            final List<T> valueList = value.stream().map(transformer).collect(Collectors.toList());
            put(attrKey, valueList);
        }
    }

}
