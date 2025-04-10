package com.epm.gestepm.rest.shares.displacement;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.creator.DisplacementShareCreateDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.deleter.DisplacementShareDeleteDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.filter.DisplacementShareFilterDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;
import com.epm.gestepm.modelapi.shares.displacement.service.DisplacementShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.shares.displacement.decorators.DisplacementShareResponseDecorator;
import com.epm.gestepm.rest.shares.displacement.mappers.*;
import com.epm.gestepm.rest.shares.displacement.operations.FindDisplacementShareV1Operation;
import com.epm.gestepm.rest.shares.displacement.operations.ListDisplacementShareV1Operation;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareFindRestRequest;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareListRestRequest;
import com.epm.gestepm.rest.shares.displacement.response.ResponsesForDisplacementShare;
import com.epm.gestepm.rest.shares.displacement.response.ResponsesForDisplacementShareList;
import com.epm.gestepm.restapi.openapi.api.DisplacementShareV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.displacement.security.DisplacementSharePermission.PRMT_EDIT_DS;
import static com.epm.gestepm.modelapi.shares.displacement.security.DisplacementSharePermission.PRMT_READ_DS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class DisplacementShareController extends BaseController implements DisplacementShareV1Api,
        ResponsesForDisplacementShare, ResponsesForDisplacementShareList {

    private final DisplacementShareService displacementShareService;

    public DisplacementShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                       final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                       final DisplacementShareService displacementShareService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.displacementShareService = displacementShareService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_DS, action = "Get displacement share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListDisplacementSharesV1200Response> listDisplacementSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final List<Integer> ids,
                                                                                        final List<Integer> userIds, final List<Integer> projectIds, final LocalDateTime startDate, final LocalDateTime endDate) {

        final DisplacementShareListRestRequest req = new DisplacementShareListRestRequest(ids, userIds, projectIds, startDate, endDate);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);

        final DisplacementShareFilterDto filterDto = getMapper(MapDSToDisplacementShareFilterDto.class).from(req);
        final Page<DisplacementShareDto> page = this.displacementShareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListDisplacementShareV1Operation());
        final List<DisplacementShare> data = getMapper(MapDSToDisplacementShareResponse.class).from(page);

        this.decorate(req, data, DisplacementShareResponseDecorator.class);

        return toListDisplacementSharesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_DS, action = "Find displacement share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateDisplacementShareV1200Response> findDisplacementShareByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final DisplacementShareFindRestRequest req = new DisplacementShareFindRestRequest(id);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final DisplacementShareByIdFinderDto finderDto = getMapper(MapDSToDisplacementShareByIdFinderDto.class).from(req);
        final DisplacementShareDto dto = this.displacementShareService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindDisplacementShareV1Operation());
        final DisplacementShare data = getMapper(MapDSToDisplacementShareResponse.class).from(dto);

        this.decorate(req, data, DisplacementShareResponseDecorator.class);

        return toResDisplacementShareResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_DS, action = "Create displacement share")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateDisplacementShareV1200Response> createDisplacementShareV1(final CreateDisplacementShareV1Request reqCreateDisplacementShare) {

        final DisplacementShareCreateDto createDto = getMapper(MapDSToDisplacementShareCreateDto.class).from(reqCreateDisplacementShare);

        final DisplacementShareDto displacementShareDto = this.displacementShareService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final DisplacementShare data = getMapper(MapDSToDisplacementShareResponse.class).from(displacementShareDto);

        final CreateDisplacementShareV1200Response response = new CreateDisplacementShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_DS, action = "Update displacement share")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateDisplacementShareV1200Response> updateDisplacementShareV1(final Integer id, final UpdateDisplacementShareV1Request reqUpdateDisplacementShare) {

        final DisplacementShareUpdateDto updateDto = getMapper(MapDSToDisplacementShareUpdateDto.class).from(reqUpdateDisplacementShare);
        updateDto.setId(id);

        final DisplacementShareDto countryDto = this.displacementShareService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final DisplacementShare data = getMapper(MapDSToDisplacementShareResponse.class).from(countryDto);

        final CreateDisplacementShareV1200Response response = new CreateDisplacementShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_DS, action = "Delete displacement share")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteDisplacementShareV1(final Integer id) {

        final DisplacementShareDeleteDto deleteDto = new DisplacementShareDeleteDto();
        deleteDto.setId(id);

        this.displacementShareService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

