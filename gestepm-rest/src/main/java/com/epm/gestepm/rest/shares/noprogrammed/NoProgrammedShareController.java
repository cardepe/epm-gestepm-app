package com.epm.gestepm.rest.shares.noprogrammed;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.shares.noprogrammed.decorators.NoProgrammedShareResponseDecorator;
import com.epm.gestepm.rest.shares.noprogrammed.mappers.*;
import com.epm.gestepm.rest.shares.noprogrammed.operations.FindNoProgrammedShareV1Operation;
import com.epm.gestepm.rest.shares.noprogrammed.operations.ListNoProgrammedShareV1Operation;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareFindRestRequest;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareListRestRequest;
import com.epm.gestepm.rest.shares.noprogrammed.response.ResponsesForNoProgrammedShare;
import com.epm.gestepm.rest.shares.noprogrammed.response.ResponsesForNoProgrammedShareList;
import com.epm.gestepm.restapi.openapi.api.NoProgrammedShareV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_READ_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class NoProgrammedShareController extends BaseController implements NoProgrammedShareV1Api,
        ResponsesForNoProgrammedShare, ResponsesForNoProgrammedShareList {

    private final NoProgrammedShareService noProgrammedShareService;

    public NoProgrammedShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                             final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                             final NoProgrammedShareService noProgrammedShareService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.noProgrammedShareService = noProgrammedShareService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Get no programmed share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListNoProgrammedSharesV1200Response> listNoProgrammedSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit) {

        final NoProgrammedShareListRestRequest req = new NoProgrammedShareListRestRequest();

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);

        final NoProgrammedShareFilterDto filterDto = getMapper(MapNPSToNoProgrammedShareFilterDto.class).from(req);
        final Page<NoProgrammedShareDto> page = this.noProgrammedShareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListNoProgrammedShareV1Operation());
        final List<NoProgrammedShare> data = getMapper(MapNPSToNoProgrammedShareResponse.class).from(page);

        this.decorate(req, data, NoProgrammedShareResponseDecorator.class);

        return toListNoProgrammedSharesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateNoProgrammedShareV1200Response> findNoProgrammedShareByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final NoProgrammedShareFindRestRequest req = new NoProgrammedShareFindRestRequest(id);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final NoProgrammedShareByIdFinderDto finderDto = getMapper(MapNPSToNoProgrammedShareByIdFinderDto.class).from(req);
        final NoProgrammedShareDto dto = this.noProgrammedShareService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindNoProgrammedShareV1Operation());
        final NoProgrammedShare data = getMapper(MapNPSToNoProgrammedShareResponse.class).from(dto);

        this.decorate(req, data, NoProgrammedShareResponseDecorator.class);

        return toResNoProgrammedShareResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Create no programmed share")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateNoProgrammedShareV1200Response> createNoProgrammedShareV1(final CreateNoProgrammedShareV1Request reqCreateNoProgrammedShare) {

        final NoProgrammedShareCreateDto createDto = getMapper(MapNPSToNoProgrammedShareCreateDto.class).from(reqCreateNoProgrammedShare);

        final NoProgrammedShareDto noProgrammedShareDto = this.noProgrammedShareService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final NoProgrammedShare data = getMapper(MapNPSToNoProgrammedShareResponse.class).from(noProgrammedShareDto);

        final CreateNoProgrammedShareV1200Response response = new CreateNoProgrammedShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Update no programmed share")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateNoProgrammedShareV1200Response> updateNoProgrammedShareV1(final Integer id, final UpdateNoProgrammedShareV1Request reqUpdateNoProgrammedShare) {

        final NoProgrammedShareUpdateDto updateDto = getMapper(MapNPSToNoProgrammedShareUpdateDto.class).from(reqUpdateNoProgrammedShare);
        updateDto.setId(id);

        final NoProgrammedShareDto countryDto = this.noProgrammedShareService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final NoProgrammedShare data = getMapper(MapNPSToNoProgrammedShareResponse.class).from(countryDto);

        final CreateNoProgrammedShareV1200Response response = new CreateNoProgrammedShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Delete no programmed share")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteNoProgrammedShareV1(final Integer id) {

        final NoProgrammedShareDeleteDto deleteDto = new NoProgrammedShareDeleteDto();
        deleteDto.setId(id);

        this.noProgrammedShareService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

