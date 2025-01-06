package com.epm.gestepm.rest.personalexpense.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseListRestRequest;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseV1Api;

public class ListPersonalExpenseV1Operation extends APIOperation<PersonalExpenseV1Api, PersonalExpenseListRestRequest> {

    public ListPersonalExpenseV1Operation() {
        super("listPersonalExpenseV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listPersonalExpensesV1(req.getPersonalExpenseSheetId(), req.getMeta(),
                req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy()));
    }

}
