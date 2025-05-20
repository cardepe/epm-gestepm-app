package com.epm.gestepm.rest.shares.programmed;

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
import com.epm.gestepm.rest.shares.programmed.operations.FindProgrammedShareBreakV1Operation;
import com.epm.gestepm.rest.shares.programmed.operations.ListProgrammedShareBreakV1Operation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakListRestRequest;
import com.epm.gestepm.rest.shares.programmed.response.ResponsesForProgrammedShareBreak;
import com.epm.gestepm.rest.shares.programmed.response.ResponsesForProgrammedShareBreakList;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakByIdFinderDto;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakFilterDto;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakResponse;
import com.epm.gestepm.rest.shares.share.mappers.MapSBToShareBreakUpdateDto;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareBreakV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.*;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProgrammedShareBreakController extends BaseController implements ProgrammedShareBreakV1Api,
        ResponsesForProgrammedShareBreak, ResponsesForProgrammedShareBreakList {

    private final ShareBreakService shareBreakService;

    public ProgrammedShareBreakController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                          final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                          ShareBreakService shareBreakService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.shareBreakService = shareBreakService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Get programmed share break list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListConstructionShareBreaksV1200Response> listProgrammedShareBreaksV1(final Integer programmedShareId, final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final String status) {

        final ProgrammedShareBreakListRestRequest req = new ProgrammedShareBreakListRestRequest(programmedShareId, ids, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ShareBreakFilterDto filterDto = getMapper(MapSBToShareBreakFilterDto.class).from(req);
        final Page<ShareBreakDto> page = this.shareBreakService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListProgrammedShareBreakV1Operation());
        final List<ShareBreak> data = getMapper(MapSBToShareBreakResponse.class).from(page);

        return toListProgrammedShareBreaksV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Find programmed share break")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> findProgrammedShareBreakByIdV1(final Integer programmedShareId, final Integer breakId, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final ProgrammedShareBreakFindRestRequest req = new ProgrammedShareBreakFindRestRequest(programmedShareId, breakId);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final ShareBreakByIdFinderDto finderDto = getMapper(MapSBToShareBreakByIdFinderDto.class).from(req);
        final ShareBreakDto dto = this.shareBreakService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindProgrammedShareBreakV1Operation());
        final ShareBreak data = getMapper(MapSBToShareBreakResponse.class).from(dto);

        return toResProgrammedShareBreakResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Create programmed share break")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> createProgrammedShareBreakV1(final Integer shareId) {

        final ShareBreakCreateDto createDto = new ShareBreakCreateDto();
        createDto.setProgrammedShareId(shareId);
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
    @RequirePermits(value = PRMT_EDIT_PS, action = "Update programmed share break")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateConstructionShareBreakV1200Response> updateProgrammedShareBreakV1(final Integer shareId, final Integer id, final UpdateConstructionShareBreakV1Request reqUpdateProgrammedShare) {

        final ShareBreakUpdateDto updateDto = getMapper(MapSBToShareBreakUpdateDto.class).from(reqUpdateProgrammedShare);
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
    @RequirePermits(value = PRMT_EDIT_PS, action = "Delete programmed share break")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProgrammedShareBreakV1(final Integer shareId, final Integer id) {

        final ShareBreakDeleteDto deleteDto = new ShareBreakDeleteDto();
        deleteDto.setId(id);

        this.shareBreakService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

