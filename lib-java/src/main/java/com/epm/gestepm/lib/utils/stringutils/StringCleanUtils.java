package com.epm.gestepm.lib.utils.stringutils;

public class StringCleanUtils {

    private static final String WHITESPACE_REGEX = "\\s+";

    private static final String SPACE = " ";

    private StringCleanUtils() {
    }

    public static String escapeLineBreaks(final String text) {
        return text.replace("\n", "\\\\n");
    }

    public static String makeSingleLine(final String text) {
        return text.replaceAll(WHITESPACE_REGEX, SPACE).trim();
    }

}
