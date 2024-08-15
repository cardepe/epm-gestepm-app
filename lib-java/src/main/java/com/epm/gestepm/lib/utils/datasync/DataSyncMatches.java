package com.epm.gestepm.lib.utils.datasync;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataSyncMatches<K, T> {

    private final List<DataSyncMatch<K, T>> matches;

    public DataSyncMatches() {
        this.matches = new ArrayList<>();
    }

    public void addMatch(DataSyncMatch<K, T> match) {
        this.matches.add(match);
    }

    public void addMatches(List<DataSyncMatch<K, T>> matches) {
        this.matches.addAll(matches);
    }

    public Stream<DataSyncMatch<K, T>> stream() {
        return matches.stream();
    }

}
