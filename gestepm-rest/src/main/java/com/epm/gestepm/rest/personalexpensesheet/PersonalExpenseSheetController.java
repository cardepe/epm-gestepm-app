package com.epm.gestepm.rest.personalexpensesheet;

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
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter.PersonalExpenseSheetDeleteDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.service.PersonalExpenseSheetExportService;
import com.epm.gestepm.modelapi.personalexpensesheet.service.PersonalExpenseSheetService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.personalexpensesheet.decorators.PersonalExpenseSheetResponseDecorator;
import com.epm.gestepm.rest.personalexpensesheet.mappers.*;
import com.epm.gestepm.rest.personalexpensesheet.operations.FindPersonalExpenseSheetV1Operation;
import com.epm.gestepm.rest.personalexpensesheet.operations.ListPersonalExpenseSheetV1Operation;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetFindRestRequest;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetListRestRequest;
import com.epm.gestepm.rest.personalexpensesheet.response.ResponsesForPersonalExpenseSheet;
import com.epm.gestepm.rest.personalexpensesheet.response.ResponsesForPersonalExpenseSheetList;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseSheetV1Api;
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
import static com.epm.gestepm.modelapi.personalexpensesheet.security.PersonalExpenseSheetPermission.PRMT_EDIT_PES;
import static com.epm.gestepm.modelapi.personalexpensesheet.security.PersonalExpenseSheetPermission.PRMT_READ_PES;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class PersonalExpenseSheetController extends BaseController implements PersonalExpenseSheetV1Api,
        ResponsesForPersonalExpenseSheet, ResponsesForPersonalExpenseSheetList {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final PersonalExpenseSheetService personalExpenseSheetService;

    private final PersonalExpenseSheetExportService personalExpenseSheetExportService;

    public PersonalExpenseSheetController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                          final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper, LocaleProvider localeProvider, MessageSource messageSource,
                                          final PersonalExpenseSheetService personalExpenseSheetService, PersonalExpenseSheetExportService personalExpenseSheetExportService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
        this.personalExpenseSheetService = personalExpenseSheetService;
        this.personalExpenseSheetExportService = personalExpenseSheetExportService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PES, action = "Get personal expense sheet list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListPersonalExpenseSheetsV1200Response> listPersonalExpenseSheetsV1(final List<String> meta,
                                                                                              final Boolean links, final Set<String> expand,
                                                                                              final Long offset, final Long limit, final String order,
                                                                                              final String orderBy, final List<Integer> ids, final Integer projectId,
                                                                                              final Integer createdBy, final String description, final LocalDateTime startDate,
                                                                                              final String status, final String observations) {
        final PersonalExpenseSheetListRestRequest req = new PersonalExpenseSheetListRestRequest(ids, projectId, createdBy, description, startDate, status, observations);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final PersonalExpenseSheetFilterDto filterDto = getMapper(MapPESToPersonalExpenseSheetFilterDto.class).from(req);
        final Page<PersonalExpenseSheetDto> page = this.personalExpenseSheetService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListPersonalExpenseSheetV1Operation());
        final List<PersonalExpenseSheet> data = getMapper(MapPESToPersonalExpenseSheetResponse.class).from(page);

        this.decorate(req, data, PersonalExpenseSheetResponseDecorator.class);

        return toListPersonalExpenseSheetsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PES, action = "Find personal expense sheet")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreatePersonalExpenseSheetV1200Response> findPersonalExpenseSheetByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final PersonalExpenseSheetFindRestRequest req = new PersonalExpenseSheetFindRestRequest(id);

        this.setCommon(req, meta, links, expand);

        final PersonalExpenseSheetByIdFinderDto finderDto = getMapper(MapPESToPersonalExpenseSheetByIdFinderDto.class).from(req);
        final PersonalExpenseSheetDto dto = this.personalExpenseSheetService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindPersonalExpenseSheetV1Operation());
        final PersonalExpenseSheet data = getMapper(MapPESToPersonalExpenseSheetResponse.class).from(dto);

        this.decorate(req, data, PersonalExpenseSheetResponseDecorator.class);

        return toResPersonalExpenseSheetResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PES, action = "Create personal expense sheet")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreatePersonalExpenseSheetV1200Response> createPersonalExpenseSheetV1(final CreatePersonalExpenseSheetV1Request reqCreatePersonalExpenseSheet) {

        final PersonalExpenseSheetCreateDto createDto = getMapper(MapPESToPersonalExpenseSheetCreateDto.class).from(reqCreatePersonalExpenseSheet);

        final PersonalExpenseSheetDto personalExpenseSheet = this.personalExpenseSheetService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final PersonalExpenseSheet data = getMapper(MapPESToPersonalExpenseSheetResponse.class).from(personalExpenseSheet);

        final CreatePersonalExpenseSheetV1200Response response = new CreatePersonalExpenseSheetV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PES, action = "Update personal expense sheet")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreatePersonalExpenseSheetV1200Response> updatePersonalExpenseSheetV1(final Integer id, final UpdatePersonalExpenseSheetV1Request reqUpdatePersonalExpenseSheet) {

        final PersonalExpenseSheetUpdateDto updateDto = getMapper(MapPESToPersonalExpenseSheetUpdateDto.class).from(reqUpdatePersonalExpenseSheet);
        updateDto.setId(id);

        final PersonalExpenseSheetDto personalExpenseSheet = this.personalExpenseSheetService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final PersonalExpenseSheet data = getMapper(MapPESToPersonalExpenseSheetResponse.class).from(personalExpenseSheet);

        final CreatePersonalExpenseSheetV1200Response response = new CreatePersonalExpenseSheetV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PES, action = "Delete personal expense sheet")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deletePersonalExpenseSheetV1(final Integer id) {

        final PersonalExpenseSheetDeleteDto deleteDto = new PersonalExpenseSheetDeleteDto(id);

        this.personalExpenseSheetService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PES, action = "Export personal expense sheet")
    @LogExecution(operation = OP_READ)
    public  ResponseEntity<Resource> exportPersonalExpenseSheetV1(final Integer personalExpenseSheetId) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final java.util.Locale locale = new java.util.Locale(language);

        final PersonalExpenseSheetDto personalExpenseSheet = this.personalExpenseSheetService.findOrNotFound(new PersonalExpenseSheetByIdFinderDto(personalExpenseSheetId));
        final byte[] pdf = this.personalExpenseSheetExportService.generate(personalExpenseSheet);

        final String dateFormat = "dd-MM-yyyy";
        final Resource resource = new ByteArrayResource(pdf);
        final String fileName = messageSource.getMessage("expense.pdf.name",
                new Object[] {
                        personalExpenseSheet.getId(),
                        Utiles.transform(personalExpenseSheet.getCreatedAt(), dateFormat)
                }, locale) + ".pdf";

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}

