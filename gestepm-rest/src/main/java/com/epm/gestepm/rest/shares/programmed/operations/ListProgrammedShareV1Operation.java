package com.epm.gestepm.rest.shares.programmed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareV1Api;

public class ListProgrammedShareV1Operation extends APIOperation<ProgrammedShareV1Api, ProgrammedShareListRestRequest> {

    public ListProgrammedShareV1Operation() {
        super("listProgrammedShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listProgrammedSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getOrder(), req.getOrderBy(), req.getIds(), req.getUserIds(), req.getProjectIds(), req.getStartDate(), req.getEndDate(), req.getStatus()));
    }

}
