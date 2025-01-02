package com.epm.gestepm.rest.personalexpense.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseV1Api;

public class FindPersonalExpenseV1Operation extends APIOperation<PersonalExpenseV1Api, PersonalExpenseFindRestRequest> {

    public FindPersonalExpenseV1Operation() {
        super("findPersonalExpenseV1");

        this.generateLinksWith((apiClass, req) -> apiClass.findPersonalExpenseByIdV1(
                        req.getPersonalExpenseSheetId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
