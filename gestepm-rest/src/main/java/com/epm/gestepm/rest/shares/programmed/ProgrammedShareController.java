package com.epm.gestepm.rest.shares.programmed;

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
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareExportService;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.shares.programmed.decorators.ProgrammedShareResponseDecorator;
import com.epm.gestepm.rest.shares.programmed.mappers.*;
import com.epm.gestepm.rest.shares.programmed.operations.FindProgrammedShareV1Operation;
import com.epm.gestepm.rest.shares.programmed.operations.ListProgrammedShareV1Operation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareFindRestRequest;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareListRestRequest;
import com.epm.gestepm.rest.shares.programmed.response.ResponsesForProgrammedShare;
import com.epm.gestepm.rest.shares.programmed.response.ResponsesForProgrammedShareList;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareV1Api;
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
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_EDIT_PS;
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_READ_PS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProgrammedShareController extends BaseController implements ProgrammedShareV1Api,
        ResponsesForProgrammedShare, ResponsesForProgrammedShareList {

    private final ProgrammedShareService programmedShareService;

    private final ProgrammedShareExportService programmedShareExportService;

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;


    public ProgrammedShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                     final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                     final ProgrammedShareService programmedShareService, ProgrammedShareExportService programmedShareExportService, LocaleProvider localeProvider, MessageSource messageSource) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.programmedShareService = programmedShareService;
        this.programmedShareExportService = programmedShareExportService;
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Get programmed share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListProgrammedSharesV1200Response> listProgrammedSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final List<Integer> userIds, final List<Integer> projectIds, final LocalDateTime startDate, final LocalDateTime endDate, final String status) {

        final ProgrammedShareListRestRequest req = new ProgrammedShareListRestRequest(ids, userIds, projectIds, startDate, endDate, status);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ProgrammedShareFilterDto filterDto = getMapper(MapPSToProgrammedShareFilterDto.class).from(req);
        final Page<ProgrammedShareDto> page = this.programmedShareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListProgrammedShareV1Operation());
        final List<ProgrammedShare> data = getMapper(MapPSToProgrammedShareResponse.class).from(page);

        this.decorate(req, data, ProgrammedShareResponseDecorator.class);

        return toListProgrammedSharesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Find programmed share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateProgrammedShareV1200Response> findProgrammedShareByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand, final String locale) {

        final ProgrammedShareFindRestRequest req = new ProgrammedShareFindRestRequest(id);
        req.setLocale(locale);

        this.setCommon(req, meta, links, expand);

        final ProgrammedShareByIdFinderDto finderDto = getMapper(MapPSToProgrammedShareByIdFinderDto.class).from(req);
        final ProgrammedShareDto dto = this.programmedShareService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindProgrammedShareV1Operation());
        final ProgrammedShare data = getMapper(MapPSToProgrammedShareResponse.class).from(dto);

        this.decorate(req, data, ProgrammedShareResponseDecorator.class);

        return toResProgrammedShareResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Create programmed share")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateProgrammedShareV1200Response> createProgrammedShareV1(final CreateProgrammedShareV1Request reqCreateProgrammedShare) {

        final ProgrammedShareCreateDto createDto = getMapper(MapPSToProgrammedShareCreateDto.class).from(reqCreateProgrammedShare);

        final ProgrammedShareDto programmedShareDto = this.programmedShareService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ProgrammedShare data = getMapper(MapPSToProgrammedShareResponse.class).from(programmedShareDto);

        final CreateProgrammedShareV1200Response response = new CreateProgrammedShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Update programmed share")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateProgrammedShareV1200Response> updateProgrammedShareV1(final Integer id, final UpdateProgrammedShareV1Request reqUpdateProgrammedShare) {

        final ProgrammedShareUpdateDto updateDto = getMapper(MapPSToProgrammedShareUpdateDto.class).from(reqUpdateProgrammedShare);
        updateDto.setId(id);

        final ProgrammedShareDto countryDto = this.programmedShareService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ProgrammedShare data = getMapper(MapPSToProgrammedShareResponse.class).from(countryDto);

        final CreateProgrammedShareV1200Response response = new CreateProgrammedShareV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Delete programmed share")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProgrammedShareV1(final Integer id) {

        final ProgrammedShareDeleteDto deleteDto = new ProgrammedShareDeleteDto();
        deleteDto.setId(id);

        this.programmedShareService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Export programmed share")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<Resource> exportProgrammedShareV1(final Integer shareId) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final java.util.Locale locale = new java.util.Locale(language);

        final ProgrammedShareDto programmedShare = this.programmedShareService.findOrNotFound(new ProgrammedShareByIdFinderDto(shareId));

        final byte[] pdf = this.programmedShareExportService.generate(programmedShare);
        final Resource resource = new ByteArrayResource(pdf);
        final String fileName = this.messageSource.getMessage("shares.programmed.pdf.name",
                new Object[] {
                        shareId.toString(),
                        Utiles.transform(programmedShare.getStartDate(), "dd-MM-yyyy")
                }, locale) + ".pdf";

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}

