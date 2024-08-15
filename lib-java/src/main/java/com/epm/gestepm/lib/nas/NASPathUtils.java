package com.epm.gestepm.lib.nas;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class NASPathUtils {

    private static final String SLASH = "/";

    public static Optional<String> getParent(final String path) {

        final Path toPath = Paths.get(path);

        return Optional.ofNullable(toPath.getParent()).map(Path::toString);
    }

    public static Optional<String> getFilename(final String path) {

        final Path toPath = Paths.get(path);

        return Optional.ofNullable(toPath.getFileName()).map(Path::toString);
    }

    public static String concat(final List<String> parts) {

        if (parts == null || parts.isEmpty()) {
            return "";
        }

        final String pathStart = parts.get(0);

        String result = "";

        if (pathStart.startsWith(SLASH)) {
            result = SLASH;
        }

        result = result.concat(parts.stream().map(element -> {

            String trimmed = element.trim();

            if (trimmed.endsWith(SLASH)) {
                trimmed = trimmed.substring(0, trimmed.length() - 1);
            }

            if (trimmed.startsWith(SLASH)) {
                trimmed = trimmed.substring(1);
            }

            return trimmed;

        }).collect(Collectors.joining(SLASH)));

        return result;
    }

    public static List<String> split(final String path) {
        return Arrays.asList(path.split(SLASH));
    }

}
