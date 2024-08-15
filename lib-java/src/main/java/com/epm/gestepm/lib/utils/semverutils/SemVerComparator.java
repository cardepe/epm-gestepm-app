package com.epm.gestepm.lib.utils.semverutils;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Comparador de tags de versión.
 *
 * <p>
 * En caso de comparar dos números, la comparación será numérica. En caso contrario, se usa
 * comparación alfanumérica.
 *
 * <p>
 * Por defecto ordena de Menor a Mayor (0.0.0 -> 1.0.0 -> 1.0.1 -> 2.0.0)
 */
public class SemVerComparator implements Comparator<String> {

    private static final String PATTERN_ORDER = "((\\d+)|([a-zA-Z]+))";

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(final String o1Name, final String o2Name) {

        Integer resultCompare = null;

        final Pattern pattern = Pattern.compile(PATTERN_ORDER);
        final Matcher o1TokenMatcher = pattern.matcher(o1Name);
        final Matcher o2TokenMatcher = pattern.matcher(o2Name);

        while (o1TokenMatcher.find() && o2TokenMatcher.find() && (resultCompare == null || resultCompare == 0)) {

            resultCompare = compareMatchName(o1TokenMatcher, o2TokenMatcher);
        }

        if (resultCompare == null || resultCompare == 0) {
            resultCompare = o1Name.compareTo(o2Name);
        }

        return resultCompare;

    }

    private Integer compareMatchName(final Matcher o1TokenMatcher, final Matcher o2TokenMatcher) {

        int resultCompare;

        final String o1Token = o1TokenMatcher.groupCount() > 0 ? o1TokenMatcher.group(1) : "";
        final String o2Token = o2TokenMatcher.groupCount() > 0 ? o2TokenMatcher.group(1) : "";

        if (o1Token.matches("\\d+") && o2Token.matches("\\d+")) {

            final Long o1TokenNumber = Long.parseLong(o1Token);
            final Long o2TokenNumber = Long.parseLong(o2Token);

            resultCompare = o1TokenNumber.compareTo(o2TokenNumber);

        } else {
            resultCompare = o1Token.compareTo(o2Token);
        }

        return resultCompare;
    }

}
