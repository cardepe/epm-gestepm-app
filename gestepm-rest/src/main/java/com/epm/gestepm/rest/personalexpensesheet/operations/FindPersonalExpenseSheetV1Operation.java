package com.epm.gestepm.rest.personalexpensesheet.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseSheetV1Api;

public class FindPersonalExpenseSheetV1Operation extends APIOperation<PersonalExpenseSheetV1Api, PersonalExpenseSheetFindRestRequest> {

    public FindPersonalExpenseSheetV1Operation() {
        super("findPersonalExpenseSheetV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findPersonalExpenseSheetByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
