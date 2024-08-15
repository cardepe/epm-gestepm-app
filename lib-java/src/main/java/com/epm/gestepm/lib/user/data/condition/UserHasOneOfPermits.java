package com.epm.gestepm.lib.user.data.condition;

import java.util.List;
import com.epm.gestepm.lib.user.data.UserPermits;

public class UserHasOneOfPermits implements UserDataCondition<UserPermits> {

    private final List<String> targetPermits;

    public UserHasOneOfPermits(List<String> targetPermits) {
        this.targetPermits = targetPermits;
    }

    @Override
    public boolean test(UserPermits data) {

        boolean passed = false;

        if (data != null) {
            final List<String> permits = data.getValue();
            passed = permits.stream().anyMatch(targetPermits::contains);
        }

        return passed;
    }

}
