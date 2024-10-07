package com.epm.gestepm.rest.holiday.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.holiday.request.HolidayListRestRequest;
import com.epm.gestepm.restapi.openapi.api.HolidaysV1Api;

public class ListHolidayV1Operation extends APIOperation<HolidaysV1Api, HolidayListRestRequest> {

    public ListHolidayV1Operation() {
        super("listHolidayV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listHolidaysV1(req.getMeta(), req.getLinks(),
                req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(),
                req.getName(), req.getDay(), req.getMonth(), req.getCountryIds(), req.getActivityCenterIds()));
    }

}
