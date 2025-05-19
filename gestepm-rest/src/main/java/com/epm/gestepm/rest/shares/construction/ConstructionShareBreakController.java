package com.epm.gestepm.rest.shares.construction;

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
import com.epm.gestepm.rest.shares.construction.operations.FindConstructionShareBreakV1Operation;
import com.epm.gestepm.rest.shares.construction.operations.ListConstructionShareBreakV1Operation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import com.epm.gestepm.rest.shares.construction.response.ResponsesForConstructionShareBreak;
import com.epm.gestepm.rest.shares.construction.response.ResponsesForConstructionShareBreakList;
import com.epm.gestepm.rest.shares.share.mappers.*;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareBreakV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ConstructionShareBreakController extends BaseController implements ConstructionShareBreakV1Api,
        ResponsesForConstructionShareBreak, ResponsesForConstructionShareBreakList {

    private final ShareBreakService shareBreakService;

    public ConstructionShareBreakController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                            final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                            ShareBreakService shareBreakService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.shareBreakService = shareBreakService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Get construction share break list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListConstructionShareBreaksV1200Response> listConstructionShareBreaksV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final List<Integer> constructionShareIds, final String status) {

        final ConstructionShareBreakListRestRequest req = new ConstructionShareBreakListRestRequest(id, ids, constructionShareIds, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ShareBreakFilterDto filterDto = getMapper(MapSBToShareBreakFilterDto.class).from(req);
        final Page<ShareBreakDto> page = this.shareBreakService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListConstructionShareBreakV1Operation());
        final List<ShareBreak> data = getMapper(MapSBToShareBreakResponse.class).from(page);

        return toListConstructionShareBreaksV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share break")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> findConstructionShareBreakByIdV1(final Integer constructionShareId, final Integer breakId, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final ConstructionShareBreakFindRestRequest req = new ConstructionShareBreakFindRestRequest(constructionShareId, breakId);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final ShareBreakByIdFinderDto finderDto = getMapper(MapSBToShareBreakByIdFinderDto.class).from(req);
        final ShareBreakDto dto = this.shareBreakService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindConstructionShareBreakV1Operation());
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        return toResConstructionShareBreakResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Create construction share break")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> createConstructionShareBreakV1(final Integer shareId, final CreateConstructionShareBreakV1Request reqCreateConstructionShare) {

        final ShareBreakCreateDto createDto = getMapper(MapSBToShareBreakCreateDto.class).from(reqCreateConstructionShare);
        createDto.setConstructionShareId(shareId);

        final ShareBreakDto dto = this.shareBreakService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update construction share break")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> updateConstructionShareBreakV1(final Integer shareId, final Integer id, final UpdateConstructionShareBreakV1Request reqUpdateConstructionShare) {

        final ShareBreakUpdateDto updateDto = getMapper(MapSBToShareBreakUpdateDto.class).from(reqUpdateConstructionShare);
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
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share break")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteConstructionShareBreakV1(final Integer shareId, final Integer id) {

        final ShareBreakDeleteDto deleteDto = new ShareBreakDeleteDto();
        deleteDto.setId(id);

        this.shareBreakService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

