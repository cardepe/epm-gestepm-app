package com.epm.gestepm.lib.user.data.condition;

import java.util.List;
import com.epm.gestepm.lib.user.data.UserPermits;

public class UserHasPermit implements UserDataCondition<UserPermits> {

    private final String targetPermit;

    public UserHasPermit(String targetPermit) {
        this.targetPermit = targetPermit;
    }

    @Override
    public boolean test(UserPermits data) {

        boolean passed = false;

        if (data != null) {
            final List<String> permits = data.getValue();
            passed = permits.contains(targetPermit);
        }

        return passed;
    }

}
