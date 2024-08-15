package com.epm.gestepm.lib.jdbc.impl.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLFilterNode {

    private static final String PARAM_REGEX = ":([a-zA-Z_{2}]+)";

    private final boolean isFinal;

    private String value;

    private List<String> parameters;

    public SQLFilterNode(String value) {
        this.isFinal = false;
        this.setValue(value);
    }

    public SQLFilterNode(String value, boolean isFinal) {
        this.isFinal = isFinal;
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {

        this.value = value;
        this.parameters = new ArrayList<>();

        final Pattern paramPattern = Pattern.compile(PARAM_REGEX);
        final Matcher paramMatcher = paramPattern.matcher(value);

        while (paramMatcher.find()) {
            this.parameters.add(paramMatcher.group(1));
        }
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean matchParams(List<String> paramsToMatch) {
        return this.parameters.isEmpty() || (paramsToMatch != null && paramsToMatch.containsAll(this.parameters));
    }

}
