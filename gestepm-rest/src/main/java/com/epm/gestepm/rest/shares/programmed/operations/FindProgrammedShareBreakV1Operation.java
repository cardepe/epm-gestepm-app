package com.epm.gestepm.rest.shares.programmed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareBreakV1Api;

public class FindProgrammedShareBreakV1Operation extends APIOperation<ProgrammedShareBreakV1Api, ProgrammedShareBreakFindRestRequest> {

    public FindProgrammedShareBreakV1Operation() {
        super("findProgrammedShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findProgrammedShareBreakByIdV1(req.getProgrammedShareId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
