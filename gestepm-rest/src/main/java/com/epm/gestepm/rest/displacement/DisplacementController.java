package com.epm.gestepm.rest.displacement;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementDto;
import com.epm.gestepm.masterdata.api.displacement.dto.creator.DisplacementCreateDto;
import com.epm.gestepm.masterdata.api.displacement.dto.deleter.DisplacementDeleteDto;
import com.epm.gestepm.masterdata.api.displacement.dto.filter.DisplacementFilterDto;
import com.epm.gestepm.masterdata.api.displacement.dto.finder.DisplacementByIdFinderDto;
import com.epm.gestepm.masterdata.api.displacement.dto.updater.DisplacementUpdateDto;
import com.epm.gestepm.masterdata.api.displacement.service.DisplacementService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.displacement.decorators.DisplacementResponseDecorator;
import com.epm.gestepm.rest.displacement.mappers.*;
import com.epm.gestepm.rest.displacement.operations.FindDisplacementV1Operation;
import com.epm.gestepm.rest.displacement.operations.ListDisplacementV1Operation;
import com.epm.gestepm.rest.displacement.request.DisplacementFindRestRequest;
import com.epm.gestepm.rest.displacement.request.DisplacementListRestRequest;
import com.epm.gestepm.rest.displacement.response.ResponsesForDisplacement;
import com.epm.gestepm.rest.displacement.response.ResponsesForDisplacementList;
import com.epm.gestepm.restapi.openapi.api.DisplacementV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class DisplacementController extends BaseController implements DisplacementV1Api, ResponsesForDisplacement, ResponsesForDisplacementList {

    private final DisplacementService activityCenterService;

    public DisplacementController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                  final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                  final DisplacementService activityCenterService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.activityCenterService = activityCenterService;
    }

    @Override
    // @RequirePermits(value = PRMT_READ_D, action = "Get displacement list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ResDisplacementList> listDisplacementsV1(final List<String> meta, final Boolean links, Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy, final List<Integer> ids, final List<Integer> activityCenterIds, final String name, final DisplacementType type) {

        final DisplacementListRestRequest req = new DisplacementListRestRequest(ids, activityCenterIds, name, type);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final DisplacementFilterDto filterDto = getMapper(MapDRToDisplacementFilterDto.class).from(req);
        final Page<DisplacementDto> page = this.activityCenterService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListDisplacementV1Operation());
        final List<Displacement> data = getMapper(MapDToDisplacementResponse.class).from(page);

        this.decorate(req, data, DisplacementResponseDecorator.class);

        return toResDisplacementListResponse(metadata, data, page.hashCode());
    }

    @Override
    // @RequirePermits(value = PRMT_READ_D, action = "Find displacement")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ResDisplacement> findDisplacementByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final DisplacementFindRestRequest req = new DisplacementFindRestRequest(id);

        this.setCommon(req, meta, links, null);

        final DisplacementByIdFinderDto finderDto = getMapper(MapDRToDByIdFinderDto.class).from(req);
        final DisplacementDto dto = this.activityCenterService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindDisplacementV1Operation());
        final Displacement data = getMapper(MapDToDisplacementResponse.class).from(dto);

        this.decorate(req, data, DisplacementResponseDecorator.class);

        return toResDisplacementResponse(metadata, data, dto.hashCode());
    }

    @Override
    // @RequirePermits(value = PRMT_EDIT_D, action = "Create displacement")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<ResDisplacement> createDisplacementV1(final ReqCreateDisplacement reqCreateDisplacement) {

        final DisplacementCreateDto createDto = getMapper(MapDToDisplacementCreateDto.class).from(reqCreateDisplacement);

        final DisplacementDto activityCenterDto = this.activityCenterService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Displacement data = getMapper(MapDToDisplacementResponse.class).from(activityCenterDto);

        final ResDisplacement response = new ResDisplacement();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    // @RequirePermits(value = PRMT_EDIT_D, action = "Update displacement")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<ResDisplacement> updateDisplacementV1(final Integer id, final ReqUpdateDisplacement reqUpdateDisplacement) {

        final DisplacementUpdateDto updateDto = getMapper(MapDToDisplacementUpdateDto.class).from(reqUpdateDisplacement);
        updateDto.setId(id);

        final DisplacementDto activityCenterDto = this.activityCenterService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Displacement data = getMapper(MapDToDisplacementResponse.class).from(activityCenterDto);

        final ResDisplacement response = new ResDisplacement();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    // @RequirePermits(value = PRMT_EDIT_D, action = "Delete displacement")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteDisplacementV1(final Integer id) {

        final DisplacementDeleteDto deleteDto = new DisplacementDeleteDto();
        deleteDto.setId(id);

        this.activityCenterService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}
