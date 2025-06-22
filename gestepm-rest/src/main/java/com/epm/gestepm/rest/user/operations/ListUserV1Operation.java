package com.epm.gestepm.rest.user.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.user.request.UserListRestRequest;
import com.epm.gestepm.restapi.openapi.api.UserV1Api;

public class ListUserV1Operation extends APIOperation<UserV1Api, UserListRestRequest> {

    public ListUserV1Operation() {
        super("listUserV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listUsersV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getOrder(), req.getOrderBy(), req.getIds(), req.getNameContains(), req.getEmail(), req.getPassword(), req.getActivityCenterIds(), req.getState(), req.getSigningIds(),
                        req.getRoleIds(), req.getLevelIds(), req.getLeadingProjectId(), req.getMemberProjectId()));
    }

}
