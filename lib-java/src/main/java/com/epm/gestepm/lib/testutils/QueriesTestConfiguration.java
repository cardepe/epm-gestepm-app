package com.epm.gestepm.lib.testutils;

import java.util.HashMap;
import java.util.Map;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementBuilder;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementKeyResolver;
import com.epm.gestepm.lib.jdbc.impl.statement.DefaultSQLStatementBuilder;
import com.epm.gestepm.lib.jdbc.impl.statement.DefaultSQLStatementKeyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public class QueriesTestConfiguration {

    @Bean("keyStatementMap")
    @ConfigurationProperties(prefix = "sql.test")
    public Map<String, String> keyStatementMap() {
        return new HashMap<>();
    }

    @Bean("sqlStatementKeyResolver")
    public SQLStatementKeyResolver sqlStatementKeyResolver(Map<String, String> keyStatementMap) {
        return new DefaultSQLStatementKeyResolver(keyStatementMap);
    }

    @Bean("sqlStatementBuilder")
    public SQLStatementBuilder sqlStatementBuilder(SQLStatementKeyResolver sqlStatementKeyResolver) {
        return new DefaultSQLStatementBuilder(sqlStatementKeyResolver);
    }

}
