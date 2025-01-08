package com.epm.gestepm.rest.displacement.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.rest.activitycenter.decorators.ActivityCenterResponseDecorator;
import com.epm.gestepm.rest.activitycenter.mappers.MapACToActivityCenterResponse;
import com.epm.gestepm.rest.displacement.request.DisplacementFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import com.epm.gestepm.restapi.openapi.model.Displacement;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("displacementResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class DisplacementResponseDecorator extends BaseResponseDataDecorator<Displacement> {

    public static final String D_AC_EXPAND = "activityCenter";

    private final ActivityCenterService activityCenterService;

    public DisplacementResponseDecorator(ApplicationContext applicationContext, ActivityCenterService activityCenterService) {
        super(applicationContext);
        this.activityCenterService = activityCenterService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating displacement response",
            msgOut = "Displacement decorated OK",
            errorMsg = "Error decorating displacement response")
    public void decorate(RestRequest request, Displacement data) {

        if (request.getLinks()) {

            final DisplacementFindRestRequest selfReq = new DisplacementFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(D_AC_EXPAND)) {

            final ActivityCenter activityCenter = data.getActivityCenter();
            final Integer id = activityCenter.getId();

            final ActivityCenterByIdFinderDto finderDto = new ActivityCenterByIdFinderDto(id);
            final ActivityCenterDto activityCenterDto = this.activityCenterService.findOrNotFound(finderDto);

            final ActivityCenter response = getMapper(MapACToActivityCenterResponse.class).from(activityCenterDto);

            data.setActivityCenter(response);

            final RestRequest childReq = request.getChildRequest(D_AC_EXPAND);
            decorateChild(ActivityCenterResponseDecorator.class, childReq, response);
        }
    }
}
