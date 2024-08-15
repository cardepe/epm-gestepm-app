package com.epm.gestepm.lib.testutils;

import com.epm.gestepm.lib.contextinit.AdditionalYmlPropertiesContextInitializer;
import com.epm.gestepm.lib.jdbc.api.query.SQLBatch;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetch;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementBuilder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:queries-test.properties")
@EnableConfigurationProperties
@ContextConfiguration(initializers = AdditionalYmlPropertiesContextInitializer.class)
@Import(QueriesTestConfiguration.class)
public class QueriesTest {

    private static final String PARAM_REGEX = ":([a-zA-Z_{2}]+)";

    @Autowired
    private SQLStatementBuilder builder;

    protected void assertValid(SQLQuery sqlQuery, Set<String> requiredAttr, Set<String> optionalAttr) {

        final String statement = builder.build(sqlQuery);
        validateParams(statement, requiredAttr, optionalAttr);
    }

    protected void assertValid(SQLQueryFetch sqlQueryFetch, Set<String> requiredAttr, Set<String> optionalAttr) {

        final String statement = builder.build(sqlQueryFetch);
        validateParams(statement, requiredAttr, optionalAttr);
    }

    protected void assertValid(SQLBatch sqlQuery, Set<String> requiredAttr, Set<String> optionalAttr) {

        final String statement = builder.build(sqlQuery);
        validateParams(statement, requiredAttr, optionalAttr);
    }

    private void validateParams(String statement, Set<String> requiredAttr, Set<String> optionalAttr) {

        final Set<String> parameters = new HashSet<>();

        final Pattern paramPattern = Pattern.compile(PARAM_REGEX);
        final Matcher paramMatcher = paramPattern.matcher(statement);

        while (paramMatcher.find()) {
            parameters.add(paramMatcher.group(1));
        }

        assertTrue(parameters.containsAll(requiredAttr));
        assertTrue(parameters.containsAll(optionalAttr));
        assertEquals(parameters.size(), requiredAttr.size() + optionalAttr.size());
    }

}
