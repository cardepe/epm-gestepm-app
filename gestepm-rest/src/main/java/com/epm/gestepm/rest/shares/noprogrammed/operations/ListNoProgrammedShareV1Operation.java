package com.epm.gestepm.rest.shares.noprogrammed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.NoProgrammedShareV1Api;

public class ListNoProgrammedShareV1Operation extends APIOperation<NoProgrammedShareV1Api, NoProgrammedShareListRestRequest> {

    public ListNoProgrammedShareV1Operation() {
        super("listNoProgrammedShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listNoProgrammedSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit()));
    }

}
