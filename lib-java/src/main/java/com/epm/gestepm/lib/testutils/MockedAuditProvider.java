package com.epm.gestepm.lib.testutils;

import java.time.LocalDateTime;
import com.epm.gestepm.lib.audit.AuditProvider;

public class MockedAuditProvider implements AuditProvider {

    @Override
    public Integer login() {
        return 1;
    }

    @Override
    public LocalDateTime time() {
        return LocalDateTime.parse("2007-12-03T10:15:30+01:00");
    }

}
