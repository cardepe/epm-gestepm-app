package com.epm.gestepm.rest.timecontrol.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.timecontrol.request.TimeControlListRestRequest;
import com.epm.gestepm.restapi.openapi.api.TimeControlV1Api;

public class ListTimeControlV1Operation extends APIOperation<TimeControlV1Api, TimeControlListRestRequest> {

    public ListTimeControlV1Operation() {
        super("listTimeControlV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listTimeControlsV1(req.getMeta(),
                req.getLinks(), req.getExpand(), req.getUserId(), req.getStartDate(), req.getEndDate(), req.getTypes()));
    }

}
