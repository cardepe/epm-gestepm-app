package com.epm.gestepm.lib.user.data;

import java.util.ArrayList;
import java.util.List;

public class UserPermits extends UserData<List<String>> {

    public UserPermits(List<String> value) {
        super(value != null ? value : new ArrayList<>());
    }

}
