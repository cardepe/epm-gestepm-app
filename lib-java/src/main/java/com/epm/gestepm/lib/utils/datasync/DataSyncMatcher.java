package com.epm.gestepm.lib.utils.datasync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DataSyncMatcher<T> {

    private List<T> leftValues;

    private List<T> rightValues;

    public DataSyncMatcher() {
        this.leftValues = new ArrayList<>();
        this.rightValues = new ArrayList<>();
    }

    public DataSyncMatcher<T> left(List<T> leftValues) {
        this.leftValues = Optional.ofNullable(leftValues).orElse(new ArrayList<>());
        return this;
    }

    public DataSyncMatcher<T> right(List<T> rightValues) {
        this.rightValues = Optional.ofNullable(rightValues).orElse(new ArrayList<>());
        return this;
    }

    public <K> DataSyncMatches<K, T> match(final Function<T, K> keyMatcher) {

        final DataSyncMatches<K, T> matches = new DataSyncMatches<>();

        final Map<K, List<T>> leftData = new HashMap<>();
        final Map<K, List<T>> rightData = new HashMap<>();

        leftValues.forEach(l -> addToMap(keyMatcher, l, leftData));
        rightValues.forEach(r -> addToMap(keyMatcher, r, rightData));

        final Set<K> leftKeys = leftData.keySet();
        final Set<K> rightKeys = rightData.keySet();

        final Set<K> keysInBoth = new HashSet<>(leftKeys);
        keysInBoth.retainAll(rightKeys);

        for (K key : keysInBoth) {

            final List<T> left = leftData.get(key);
            final List<T> right = rightData.get(key);

            for (T l : left) {
                for (T r : right) {
                    matches.addMatch(new DataSyncMatch<>(key, l, r));
                }
            }
        }

        final Set<K> keysOnlyLeft = new HashSet<>(leftKeys);
        keysOnlyLeft.removeAll(rightKeys);

        for (K key : keysOnlyLeft) {

            final List<T> left = leftData.get(key);

            for (T l : left) {
                matches.addMatch(new DataSyncMatch<>(key, l, null));
            }
        }

        final Set<K> keysOnlyRight = new HashSet<>(rightKeys);
        keysOnlyRight.removeAll(leftKeys);

        for (K key : keysOnlyRight) {

            final List<T> right = rightData.get(key);

            matches.addMatch(new DataSyncMatch<>(key, null, right.get(0)));
        }

        return matches;

    }

    private <K> void addToMap(final Function<T, K> keyMatcher, T value, Map<K, List<T>> map) {

        final K key = keyMatcher.apply(value);

        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(value);
    }

}
