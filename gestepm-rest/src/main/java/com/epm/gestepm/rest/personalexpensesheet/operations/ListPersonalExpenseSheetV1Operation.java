package com.epm.gestepm.rest.personalexpensesheet.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetListRestRequest;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseSheetV1Api;

public class ListPersonalExpenseSheetV1Operation extends APIOperation<PersonalExpenseSheetV1Api, PersonalExpenseSheetListRestRequest> {

    public ListPersonalExpenseSheetV1Operation() {
        super("listPersonalExpenseSheetV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listPersonalExpenseSheetsV1(req.getMeta(),
                req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(), req.getProjectId()));
    }

}
