package com.epm.gestepm.rest.shares.construction;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.shares.construction.decorators.ConstructionShareResponseDecorator;
import com.epm.gestepm.rest.shares.construction.mappers.*;
import com.epm.gestepm.rest.shares.construction.operations.FindConstructionShareV1Operation;
import com.epm.gestepm.rest.shares.construction.operations.ListConstructionShareV1Operation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareFindRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareListRestRequest;
import com.epm.gestepm.rest.shares.construction.response.ResponsesForConstructionShare;
import com.epm.gestepm.rest.shares.construction.response.ResponsesForConstructionShareList;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ConstructionShareController extends BaseController implements ConstructionShareV1Api,
        ResponsesForConstructionShare, ResponsesForConstructionShareList {

    private final ConstructionShareService constructionShareService;

    public ConstructionShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                       final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                       final ConstructionShareService constructionShareService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.constructionShareService = constructionShareService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Get construction share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListConstructionSharesV1200Response> listConstructionSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final List<Integer> userIds, final List<Integer> projectIds, final LocalDateTime startDate, final LocalDateTime endDate, final String status) {

        final ConstructionShareListRestRequest req = new ConstructionShareListRestRequest(ids, userIds, projectIds, startDate, endDate, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ConstructionShareFilterDto filterDto = getMapper(MapCSToConstructionShareFilterDto.class).from(req);
        final Page<ConstructionShareDto> page = this.constructionShareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListConstructionShareV1Operation());
        final List<ConstructionShare> data = getMapper(MapCSToConstructionShareResponse.class).from(page);

        this.decorate(req, data, ConstructionShareResponseDecorator.class);

        return toListConstructionSharesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateConstructionShareV1200Response> findConstructionShareByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final ConstructionShareFindRestRequest req = new ConstructionShareFindRestRequest(id);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final ConstructionShareByIdFinderDto finderDto = getMapper(MapCSToConstructionShareByIdFinderDto.class).from(req);
        final ConstructionShareDto dto = this.constructionShareService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindConstructionShareV1Operation());
        final ConstructionShare data = getMapper(MapCSToConstructionShareResponse.class).from(dto);

        this.decorate(req, data, ConstructionShareResponseDecorator.class);

        return toResConstructionShareResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Create construction share")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateConstructionShareV1200Response> createConstructionShareV1(final CreateConstructionShareV1Request reqCreateConstructionShare) {

        final ConstructionShareCreateDto createDto = getMapper(MapCSToConstructionShareCreateDto.class).from(reqCreateConstructionShare);

        final ConstructionShareDto constructionShareDto = this.constructionShareService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ConstructionShare data = getMapper(MapCSToConstructionShareResponse.class).from(constructionShareDto);

        final CreateConstructionShareV1200Response response = new CreateConstructionShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update construction share")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateConstructionShareV1200Response> updateConstructionShareV1(final Integer id, final UpdateConstructionShareV1Request reqUpdateConstructionShare) {

        final ConstructionShareUpdateDto updateDto = getMapper(MapCSToConstructionShareUpdateDto.class).from(reqUpdateConstructionShare);
        updateDto.setId(id);

        final ConstructionShareDto countryDto = this.constructionShareService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ConstructionShare data = getMapper(MapCSToConstructionShareResponse.class).from(countryDto);

        final CreateConstructionShareV1200Response response = new CreateConstructionShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteConstructionShareV1(final Integer id) {

        final ConstructionShareDeleteDto deleteDto = new ConstructionShareDeleteDto();
        deleteDto.setId(id);

        this.constructionShareService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

