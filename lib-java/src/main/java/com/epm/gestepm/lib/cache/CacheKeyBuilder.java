package com.epm.gestepm.lib.cache;

import static java.util.stream.Collectors.joining;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class CacheKeyBuilder {

    private final List<String> elements;

    public CacheKeyBuilder() {
        elements = new ArrayList<>();
    }

    public <T> void addElement(T value) {

        String valueRep = value != null ? replaceInvalidCharacters(value.toString()) : "[null]";

        elements.add(valueRep);
    }

    public <T> void addElement(String key, T value) {

        String valueRep = "[null]";

        if (value != null) {

            valueRep = "[".concat(value.toString()).concat("]");
            valueRep = replaceInvalidCharacters(valueRep);
        }

        elements.add(key.concat(valueRep));
    }

    public <T> void addElement(String key, List<T> value) {

        String valueRep = "[null]";

        if (value != null) {

            valueRep = value.stream().map(String::valueOf).collect(joining(",", "[", "]"));
            valueRep = replaceInvalidCharacters(valueRep);
        }

        elements.add(key.concat(valueRep));
    }

    private String replaceInvalidCharacters(String value) {

        final String[] invalidCharsArray = new String[] { "\\" };
        final String[] validCharsArray = new String[] { "-" };

        return StringUtils.replaceEach(value, invalidCharsArray, validCharsArray);
    }

    @Override
    public String toString() {
        return String.join("-", elements);
    }

}
