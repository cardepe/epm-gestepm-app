package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.creator.ShareBreakCreateDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.deleter.ShareBreakDeleteDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.finder.ShareBreakByIdFinderDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.updater.ShareBreakUpdateDto;
import com.epm.gestepm.modelapi.shares.breaks.service.ShareBreakService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.inspection.operations.FindInspectionBreakV1Operation;
import com.epm.gestepm.rest.inspection.operations.ListInspectionBreakV1Operation;
import com.epm.gestepm.rest.inspection.request.InspectionBreakFindRestRequest;
import com.epm.gestepm.rest.inspection.request.InspectionBreakListRestRequest;
import com.epm.gestepm.rest.inspection.response.ResponsesForInspectionBreak;
import com.epm.gestepm.rest.inspection.response.ResponsesForInspectionBreakList;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakByIdFinderDto;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakFilterDto;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakResponse;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakUpdateDto;
import com.epm.gestepm.restapi.openapi.api.InspectionBreakV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class InspectionBreakController extends BaseController implements InspectionBreakV1Api,
        ResponsesForInspectionBreak, ResponsesForInspectionBreakList {

    private final ShareBreakService shareBreakService;

    public InspectionBreakController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                     final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                     ShareBreakService shareBreakService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.shareBreakService = shareBreakService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Get inspection break list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListConstructionShareBreaksV1200Response> listInspectionBreaksV1(final Integer shareId, final Integer inspectionId, final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final String status) {

        final InspectionBreakListRestRequest req = new InspectionBreakListRestRequest(shareId, inspectionId, ids, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ShareBreakFilterDto filterDto = getMapper(MapSBToShareBreakFilterDto.class).from(req);
        final Page<ShareBreakDto> page = this.shareBreakService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListInspectionBreakV1Operation());
        final List<ShareBreak> data = getMapper(MapSBToShareBreakResponse.class).from(page);

        return toListInspectionBreaksV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection break")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> findInspectionBreakByIdV1(final Integer shareId, final Integer inspectionId, final Integer breakId, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final InspectionBreakFindRestRequest req = new InspectionBreakFindRestRequest(shareId, inspectionId, breakId);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final ShareBreakByIdFinderDto finderDto = getMapper(MapSBToShareBreakByIdFinderDto.class).from(req);
        final ShareBreakDto dto = this.shareBreakService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindInspectionBreakV1Operation());
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        return toResInspectionBreakResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Create inspection break")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> createInspectionBreakV1(final Integer shareId, final Integer inspectionId) {

        final ShareBreakCreateDto createDto = new ShareBreakCreateDto();
        createDto.setInspectionId(inspectionId);
        createDto.setStartDate(LocalDateTime.now());

        final ShareBreakDto dto = this.shareBreakService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Update inspection break")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> updateInspectionBreakV1(final Integer shareId, final Integer inspectionId, final Integer id, final UpdateConstructionShareBreakV1Request reqUpdateInspection) {

        final ShareBreakUpdateDto updateDto = getMapper(MapSBToShareBreakUpdateDto.class).from(reqUpdateInspection);
        updateDto.setId(id);

        final ShareBreakDto dto = this.shareBreakService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection break")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteInspectionBreakV1(final Integer shareId, final Integer inspectionId, final Integer id) {

        final ShareBreakDeleteDto deleteDto = new ShareBreakDeleteDto();
        deleteDto.setId(id);

        this.shareBreakService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

