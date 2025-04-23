package com.epm.gestepm.rest.timecontrol;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.timecontrol.decorators.TimeControlResponseDecorator;
import com.epm.gestepm.rest.timecontrol.mappers.*;
import com.epm.gestepm.rest.timecontrol.operations.ListTimeControlV1Operation;
import com.epm.gestepm.rest.timecontrol.request.TimeControlListRestRequest;
import com.epm.gestepm.rest.timecontrol.response.ResponsesForTimeControlList;
import com.epm.gestepm.restapi.openapi.api.TimeControlV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.timecontrol.security.TimeControlPermission.PRMT_READ_TC;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class TimeControlController extends BaseController implements TimeControlV1Api, ResponsesForTimeControlList {

    private final TimeControlService timeControlService;

    public TimeControlController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                 final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                 final TimeControlService timeControlService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.timeControlService = timeControlService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_TC, action = "Get time control list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListTimeControlsV1200Response> listTimeControlsV1(final List<String> meta, final Boolean links,
                                                                            final Set<String> expand, final Integer userId,
                                                                            final LocalDateTime startDate, final LocalDateTime endDate,
                                                                            final List<String> types) {
        final TimeControlListRestRequest req = new TimeControlListRestRequest(userId, startDate, endDate, types);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);

        final TimeControlFilterDto filterDto = getMapper(MapTCToTimeControlFilterDto.class).from(req);
        final List<TimeControlDto> list = this.timeControlService.list(filterDto);

        final APIMetadata metadata = this.getMetadata(req, new ListTimeControlV1Operation());
        final List<TimeControl> data = getMapper(MapTCToTimeControlResponse.class).from(list);

        this.decorate(req, data, TimeControlResponseDecorator.class);

        return toListTimeControlsV1200Response(metadata, data, list.hashCode());
    }
}

