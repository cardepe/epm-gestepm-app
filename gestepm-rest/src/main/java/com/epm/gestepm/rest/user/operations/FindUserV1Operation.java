package com.epm.gestepm.rest.user.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.user.request.UserFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.UserV1Api;

public class FindUserV1Operation extends APIOperation<UserV1Api, UserFindRestRequest> {

    public FindUserV1Operation() {
        super("findUserV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findUserByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
