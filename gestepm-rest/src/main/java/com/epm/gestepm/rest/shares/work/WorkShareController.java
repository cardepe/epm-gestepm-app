package com.epm.gestepm.rest.shares.work;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareCreateDto;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareDeleteDto;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareExportService;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.shares.work.decorators.WorkShareResponseDecorator;
import com.epm.gestepm.rest.shares.work.mappers.*;
import com.epm.gestepm.rest.shares.work.operations.FindWorkShareV1Operation;
import com.epm.gestepm.rest.shares.work.operations.ListWorkShareV1Operation;
import com.epm.gestepm.rest.shares.work.request.WorkShareFindRestRequest;
import com.epm.gestepm.rest.shares.work.request.WorkShareListRestRequest;
import com.epm.gestepm.rest.shares.work.response.ResponsesForWorkShare;
import com.epm.gestepm.rest.shares.work.response.ResponsesForWorkShareList;
import com.epm.gestepm.restapi.openapi.api.WorkShareV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static com.epm.gestepm.modelapi.shares.work.security.WorkSharePermission.PRMT_EDIT_WS;
import static com.epm.gestepm.modelapi.shares.work.security.WorkSharePermission.PRMT_READ_WS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class WorkShareController extends BaseController implements WorkShareV1Api,
        ResponsesForWorkShare, ResponsesForWorkShareList {

    private final WorkShareService workShareService;

    private final WorkShareExportService workShareExportService;

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;


    public WorkShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                               final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                               final WorkShareService workShareService, WorkShareExportService workShareExportService, LocaleProvider localeProvider, MessageSource messageSource) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.workShareService = workShareService;
        this.workShareExportService = workShareExportService;
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    @RequirePermits(value = PRMT_READ_WS, action = "Get work share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListWorkSharesV1200Response> listWorkSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final List<Integer> userIds, final List<Integer> projectIds, final LocalDateTime startDate, final LocalDateTime endDate, final String status) {

        final WorkShareListRestRequest req = new WorkShareListRestRequest(ids, userIds, projectIds, startDate, endDate, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final WorkShareFilterDto filterDto = getMapper(MapWSToWorkShareFilterDto.class).from(req);
        final Page<WorkShareDto> page = this.workShareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListWorkShareV1Operation());
        final List<WorkShare> data = getMapper(MapWSToWorkShareResponse.class).from(page);

        this.decorate(req, data, WorkShareResponseDecorator.class);

        return toListWorkSharesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_WS, action = "Find work share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateWorkShareV1200Response> findWorkShareByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final WorkShareFindRestRequest req = new WorkShareFindRestRequest(id);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final WorkShareByIdFinderDto finderDto = getMapper(MapWSToWorkShareByIdFinderDto.class).from(req);
        final WorkShareDto dto = this.workShareService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindWorkShareV1Operation());
        final WorkShare data = getMapper(MapWSToWorkShareResponse.class).from(dto);

        this.decorate(req, data, WorkShareResponseDecorator.class);

        return toResWorkShareResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_WS, action = "Create work share")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateWorkShareV1200Response> createWorkShareV1(final CreateWorkShareV1Request reqCreateWorkShare) {

        final WorkShareCreateDto createDto = getMapper(MapWSToWorkShareCreateDto.class).from(reqCreateWorkShare);

        final WorkShareDto workShareDto = this.workShareService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final WorkShare data = getMapper(MapWSToWorkShareResponse.class).from(workShareDto);

        final CreateWorkShareV1200Response response = new CreateWorkShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_WS, action = "Update work share")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateWorkShareV1200Response> updateWorkShareV1(final Integer id, final UpdateWorkShareV1Request reqUpdateWorkShare) {

        final WorkShareUpdateDto updateDto = getMapper(MapWSToWorkShareUpdateDto.class).from(reqUpdateWorkShare);
        updateDto.setId(id);

        final WorkShareDto countryDto = this.workShareService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final WorkShare data = getMapper(MapWSToWorkShareResponse.class).from(countryDto);

        final CreateWorkShareV1200Response response = new CreateWorkShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_WS, action = "Delete work share")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteWorkShareV1(final Integer id) {

        final WorkShareDeleteDto deleteDto = new WorkShareDeleteDto();
        deleteDto.setId(id);

        this.workShareService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Export work share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<Resource> exportWorkShareV1(final Integer shareId) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final java.util.Locale locale = new java.util.Locale(language);

        final WorkShareDto workShare = this.workShareService.findOrNotFound(new WorkShareByIdFinderDto(shareId));

        final byte[] pdf = this.workShareExportService.generate(workShare);
        final Resource resource = new ByteArrayResource(pdf);
        final String fileName = messageSource.getMessage("shares.work.pdf.name",
                new Object[] {
                        shareId.toString(),
                        Utiles.transform(workShare.getStartDate(), "dd-MM-yyyy")
                }, locale) + ".pdf";

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}

