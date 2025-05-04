package com.epm.gestepm.rest.shares.programmed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareV1Api;

public class FindProgrammedShareV1Operation extends APIOperation<ProgrammedShareV1Api, ProgrammedShareFindRestRequest> {

    public FindProgrammedShareV1Operation() {
        super("findProgrammedShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findProgrammedShareByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
