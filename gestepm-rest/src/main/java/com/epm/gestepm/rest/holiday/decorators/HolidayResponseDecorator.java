package com.epm.gestepm.rest.holiday.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.masterdata.api.country.dto.finder.CountryByIdFinderDto;
import com.epm.gestepm.masterdata.api.country.service.CountryService;
import com.epm.gestepm.rest.activitycenter.decorators.ActivityCenterResponseDecorator;
import com.epm.gestepm.rest.activitycenter.mappers.MapACToActivityCenterResponse;
import com.epm.gestepm.rest.country.mappers.MapCToCountryResponse;
import com.epm.gestepm.rest.holiday.request.HolidayFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import com.epm.gestepm.restapi.openapi.model.Country;
import com.epm.gestepm.restapi.openapi.model.Holiday;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("holidayResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class HolidayResponseDecorator extends BaseResponseDataDecorator<Holiday> {

    public static final String H_C_EXPAND = "country";

    public static final String H_AC_EXPAND = "activityCenter";

    private final ActivityCenterService activityCenterService;

    private final CountryService countryService;

    public HolidayResponseDecorator(ApplicationContext applicationContext, ActivityCenterService activityCenterService, CountryService countryService) {
        super(applicationContext);
        this.activityCenterService = activityCenterService;
        this.countryService = countryService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating holiday response",
            msgOut = "Holiday decorated OK",
            errorMsg = "Error decorating holiday response")
    public void decorate(RestRequest request, Holiday data) {

        if (request.getLinks()) {

            final HolidayFindRestRequest selfReq = new HolidayFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(H_C_EXPAND)) {

            final Country country = data.getCountry();
            final Integer id = country.getId();

            final CountryByIdFinderDto finderDto = new CountryByIdFinderDto(id);
            final CountryDto countryDto = this.countryService.findOrNotFound(finderDto);

            final Country response = getMapper(MapCToCountryResponse.class).from(countryDto);

            data.setCountry(response);
        }

        if (request.hasExpand(H_AC_EXPAND)) {

            final ActivityCenter activityCenter = data.getActivityCenter();
            final Integer id = activityCenter.getId();

            final ActivityCenterByIdFinderDto finderDto = new ActivityCenterByIdFinderDto(id);
            final ActivityCenterDto activityCenterDto = this.activityCenterService.findOrNotFound(finderDto);

            final ActivityCenter response = getMapper(MapACToActivityCenterResponse.class).from(activityCenterDto);

            data.setActivityCenter(response);

            final RestRequest childReq = request.getChildRequest(H_AC_EXPAND);
            decorateChild(ActivityCenterResponseDecorator.class, childReq, response);
        }
    }
}
