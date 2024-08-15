package com.epm.gestepm.rest.activitycenter.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.activitycenter.request.ActivityCenterFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ActivityCentersV1Api;

public class FindActivityCenterV1Operation extends APIOperation<ActivityCentersV1Api, ActivityCenterFindRestRequest> {

    public FindActivityCenterV1Operation() {
        super("findActivityCenterV1");

        this.generateLinksWith((apiClass, req) -> apiClass.findActivityCenterByIdV1(req.getId(), req.getMeta(),
                req.getLinks(), req.getExpand()));
    }
}
