package com.epm.gestepm.lib.jdbc.impl.keyholder;

import org.springframework.jdbc.support.GeneratedKeyHolder;

public class DefaultSQLKeyHolderGenerator implements SQLKeyHolderGenerator {

    @Override
    public GeneratedKeyHolder get() {
        return new GeneratedKeyHolder();
    }

}
