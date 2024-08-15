package com.epm.gestepm.lib.nas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.epm.gestepm.lib.nas.exception.InvalidNASPathException;
import javax.validation.constraints.NotNull;

public interface NASDatasource {

    String getWorkingDir();

    default String pathWithWorkingDir(String path) {
        return NASPathUtils.concat(List.of(getWorkingDir(), path));
    }

    default byte[] read(final @NotNull String path) {

        final String folderPath = NASPathUtils.getParent(path).orElse("/");
        final String filename = NASPathUtils.getFilename(path).orElseThrow(() -> new InvalidNASPathException(path));

        return read(folderPath, filename);
    }

    byte[] read(final @NotNull String folderPath, final @NotNull String filename);

    default void create(final @NotNull String path, final @NotNull byte[] content) {

        final String folderPath = NASPathUtils.getParent(path).orElse("/");
        final String filename = NASPathUtils.getFilename(path).orElseThrow(() -> new InvalidNASPathException(path));

        create(folderPath, filename, content);
    }

    void create(final @NotNull String folderPath, final @NotNull String filename, final @NotNull byte[] content);

    default void create(final @NotNull String path, final @NotNull byte[] content, boolean createDirs) {

        final String folderPath = NASPathUtils.getParent(path).orElse("/");
        final String filename = NASPathUtils.getFilename(path).orElseThrow(() -> new InvalidNASPathException(path));

        create(folderPath, filename, content, createDirs);
    }

    void create(final @NotNull String folderPath, final @NotNull String filename, final @NotNull byte[] content,
            boolean createDirs);

    default void update(final String path, final byte[] content) {

        final String folderPath = NASPathUtils.getParent(path).orElse("/");
        final String filename = NASPathUtils.getFilename(path).orElseThrow(() -> new InvalidNASPathException(path));

        update(folderPath, filename, content);
    }

    void update(final @NotNull String folderPath, final @NotNull String filename, final @NotNull byte[] content);

    default void upsert(final @NotNull String path, final @NotNull byte[] content) {

        if (exists(path)) {
            update(path, content);
        } else {
            create(path, content);
        }
    }

    default void upsert(final @NotNull String folderPath, final @NotNull String filename,
            final @NotNull byte[] content) {

        final String target = NASPathUtils.concat(List.of(folderPath, filename));

        if (exists(target)) {
            update(target, content);
        } else {
            create(target, content);
        }
    }

    default void upsert(final @NotNull String path, final @NotNull byte[] content, boolean createDirs) {

        if (exists(path)) {
            update(path, content);
        } else {
            create(path, content, createDirs);
        }
    }

    default void upsert(final @NotNull String folderPath, final @NotNull String filename, final @NotNull byte[] content,
            boolean createDirs) {

        final String target = NASPathUtils.concat(List.of(folderPath, filename));

        if (exists(target)) {
            update(target, content);
        } else {
            create(target, content, createDirs);
        }
    }

    default void delete(final @NotNull String path) {

        final String folderPath = NASPathUtils.getParent(path).orElse("/");
        final String filename = NASPathUtils.getFilename(path).orElseThrow(() -> new InvalidNASPathException(path));

        delete(folderPath, filename);
    }

    void delete(final @NotNull String folderPath, final @NotNull String filename);

    default boolean exists(final @NotNull String path) {

        final String workingPath = pathWithWorkingDir(path);
        final Path asPath = Paths.get(workingPath);

        return Files.exists(asPath);
    }

    default boolean isDir(final @NotNull String path) {

        if (exists(path)) {

            final String workingPath = pathWithWorkingDir(path);
            final Path asPath = Paths.get(workingPath);

            return Files.isDirectory(asPath);
        }

        return false;
    }

    default boolean isFile(final @NotNull String path) {

        if (exists(path)) {

            final String workingPath = pathWithWorkingDir(path);
            final Path asPath = Paths.get(workingPath);

            return Files.isRegularFile(asPath);
        }

        return false;
    }

}
