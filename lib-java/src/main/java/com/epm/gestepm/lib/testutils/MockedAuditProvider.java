package com.epm.gestepm.lib.testutils;

import java.time.OffsetDateTime;
import com.epm.gestepm.lib.audit.AuditProvider;

public class MockedAuditProvider implements AuditProvider {

    @Override
    public String login() {
        return "mockeduser";
    }

    @Override
    public OffsetDateTime time() {
        return OffsetDateTime.parse("2007-12-03T10:15:30+01:00");
    }

}
