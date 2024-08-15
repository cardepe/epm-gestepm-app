package com.epm.gestepm.lib.controller.restcontext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.lib.controller.RestRequest;

public class RestContextProvider {

    private final List<APIOperation<?, ?>> operations;

    public RestContextProvider() {
        this.operations = new ArrayList<>();
    }

    public void register(APIOperation<?, ?> operation) {
        operations.add(operation);
        operations.sort(Comparator.comparing(APIOperation::getPath));
    }

    public Map<String, String> childResourcesOf(APIOperation<?, ?> apiOperation, RestRequest req) {

        final Map<String, String> map = operations.stream()
            .filter(o -> isChild(apiOperation, o))
            .filter(o -> o.defaultLink(req) != null)
            .collect(Collectors.toMap(APIOperation::getOperationId, o -> o.defaultLink(req), (k, v) -> v,
                    LinkedHashMap::new));

        return map.isEmpty() ? null : map;
    }

    private boolean isChild(APIOperation<?, ?> parent, APIOperation<?, ?> target) {

        final String parentPath = parent.getPath();
        String targetPath = target.getPath();

        targetPath = targetPath.replace(parentPath, "");

        return targetPath.matches("^/[a-zA-Z-/]+$");
    }

}
