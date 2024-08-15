package com.epm.gestepm.rest.activitycenter.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.activitycenter.request.ActivityCenterListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ActivityCentersV1Api;

public class ListActivityCenterV1Operation extends APIOperation<ActivityCentersV1Api, ActivityCenterListRestRequest> {

    public ListActivityCenterV1Operation() {
        super("listActivityCenterV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listActivityCentersV1(req.getMeta(), req.getLinks(),
                req.getExpand(), req.getOffset(), req.getLimit(), req.getIds(), req.getName(), req.getCountryIds()));
    }

}
