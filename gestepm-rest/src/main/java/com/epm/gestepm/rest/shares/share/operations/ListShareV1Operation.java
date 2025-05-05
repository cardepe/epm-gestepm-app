package com.epm.gestepm.rest.shares.share.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.share.request.ShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ShareV1Api;

public class ListShareV1Operation extends APIOperation<ShareV1Api, ShareListRestRequest> {

    public ListShareV1Operation() {
        super("listShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getOrder(), req.getOrderBy(), req.getIds(), req.getUserIds(), req.getProjectIds(), req.getStartDate(), req.getEndDate(), req.getStatus(), req.getTypes()));
    }

}
