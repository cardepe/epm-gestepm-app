package com.epm.gestepm.lib.jdbc.impl.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;
import com.epm.gestepm.lib.utils.stringutils.StringCleanUtils;

public class SQLFilterParser {

    private static final String CLOSE_AND = "AND]";

    private static final String CLOSE_OR = "OR]";

    private static final String OPEN_AND = "[AND";

    private static final String OPEN_OR = "[OR";

    private SQLFilterParser() {
    }

    public static String parse(final String filter, final List<String> paramsToKeep) {

        final Stack<SQLFilterNode> expressionStack = new Stack<>();
        final Stack<String> operatorStack = new Stack<>();
        final Stack<SQLFilterNode> clauseStack = new Stack<>();

        final String filterToParse = StringCleanUtils.makeSingleLine(filter);
        final String[] split = filterToParse.split("--");

        Arrays.stream(split)
            .map(String::trim)
            .filter(v -> !v.isBlank())
            .map(SQLFilterNode::new)
            .forEach(expressionStack::push);

        while (!expressionStack.isEmpty()) {

            final SQLFilterNode current = expressionStack.pop();

            if (current.getValue().equals(CLOSE_AND) || current.getValue().equals(CLOSE_OR)) {

                operatorStack.push(current.getValue().replace("]", ""));

            } else if (current.getValue().equals(OPEN_AND) || current.getValue().equals(OPEN_OR)) {

                List<String> clauseParts = new ArrayList<>();

                while (!clauseStack.isEmpty()) {

                    final SQLFilterNode node = clauseStack.pop();
                    final String value = node.getValue();
                    final boolean isFinal = node.isFinal();
                    final boolean matchParams = node.matchParams(paramsToKeep);

                    if (matchParams || isFinal) {
                        clauseParts.add(String.format(" %s ", value));
                    }
                }

                final String operator = operatorStack.pop();

                if (!clauseParts.isEmpty()) {

                    final StringJoiner joiner = new StringJoiner(operator, "(", ")");
                    clauseParts.forEach(joiner::add);

                    final SQLFilterNode finalNode = new SQLFilterNode(joiner.toString(), true);

                    expressionStack.push(finalNode);
                }

            } else {
                clauseStack.push(current);
            }
        }

        if (clauseStack.isEmpty()) {
            return "";
        } else {
            final SQLFilterNode node = clauseStack.pop();
            return node.getValue();
        }
    }

}
