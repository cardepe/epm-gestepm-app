package com.epm.gestepm.rest.holiday.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.holiday.request.HolidayFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.HolidaysV1Api;

public class FindHolidayV1Operation extends APIOperation<HolidaysV1Api, HolidayFindRestRequest> {

    public FindHolidayV1Operation() {
        super("findHolidayV1");

        this.generateLinksWith((apiClass, req) -> apiClass.findHolidayByIdV1(req.getId(), req.getMeta(),
                req.getLinks(), req.getExpand()));
    }
}
