package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.inspection.decorators.InspectionResponseDecorator;
import com.epm.gestepm.rest.inspection.mappers.*;
import com.epm.gestepm.rest.inspection.operations.FindInspectionV1Operation;
import com.epm.gestepm.rest.inspection.operations.ListInspectionV1Operation;
import com.epm.gestepm.rest.inspection.request.InspectionFindRestRequest;
import com.epm.gestepm.rest.inspection.request.InspectionListRestRequest;
import com.epm.gestepm.rest.inspection.response.ResponsesForInspection;
import com.epm.gestepm.rest.inspection.response.ResponsesForInspectionList;
import com.epm.gestepm.restapi.openapi.api.InspectionV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class InspectionController extends BaseController implements InspectionV1Api,
        ResponsesForInspection, ResponsesForInspectionList {

    private final InspectionService inspectionService;

    public InspectionController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                final InspectionService inspectionService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.inspectionService = inspectionService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Get inspection list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListInspectionsV1200Response> listInspectionsV1(final Integer shareId, final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit) {

        final InspectionListRestRequest req = new InspectionListRestRequest(shareId, new ArrayList<>());

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);

        final InspectionFilterDto filterDto = getMapper(MapIToInspectionFilterDto.class).from(req);
        final Page<InspectionDto> page = this.inspectionService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListInspectionV1Operation());
        final List<Inspection> data = getMapper(MapIToInspectionResponse.class).from(page);

        return toListInspectionsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateInspectionV1200Response> findInspectionByIdV1(final Integer shareId, final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final InspectionFindRestRequest req = new InspectionFindRestRequest(id, shareId);

        this.setCommon(req, meta, links, expand);

        final InspectionByIdFinderDto finderDto = getMapper(MapIToInspectionByIdFinderDto.class).from(req);
        final InspectionDto dto = this.inspectionService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindInspectionV1Operation());
        final Inspection data = getMapper(MapIToInspectionResponse.class).from(dto);

        this.decorate(req, data, InspectionResponseDecorator.class);

        return toResInspectionResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Create inspection")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateInspectionV1200Response> createInspectionV1(final Integer shareId, final CreateInspectionV1Request reqCreateInspection) {

        final InspectionCreateDto createDto = getMapper(MapIToInspectionCreateDto.class).from(reqCreateInspection);
        createDto.setShareId(shareId);

        final InspectionDto countryDto = this.inspectionService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Inspection data = getMapper(MapIToInspectionResponse.class).from(countryDto);

        final CreateInspectionV1200Response response = new CreateInspectionV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Update inspection")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateInspectionV1200Response> updateInspectionV1(final Integer shareId, final Integer inspectionId, final UpdateInspectionV1Request reqUpdateInspection) {

        final InspectionUpdateDto updateDto = getMapper(MapIToInspectionUpdateDto.class).from(reqUpdateInspection);
        updateDto.setId(inspectionId);
        updateDto.setShareId(shareId);

        final InspectionDto countryDto = this.inspectionService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Inspection data = getMapper(MapIToInspectionResponse.class).from(countryDto);

        final CreateInspectionV1200Response response = new CreateInspectionV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteInspectionV1(final Integer shareId, final Integer inspectionId) {

        final InspectionDeleteDto deleteDto = new InspectionDeleteDto(inspectionId, shareId);

        this.inspectionService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

