package com.epm.gestepm.rest.personalexpense;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseDeleteDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.personalexpense.decorators.PersonalExpenseResponseDecorator;
import com.epm.gestepm.rest.personalexpense.mappers.*;
import com.epm.gestepm.rest.personalexpense.operations.FindPersonalExpenseV1Operation;
import com.epm.gestepm.rest.personalexpense.operations.ListPersonalExpenseV1Operation;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseFindRestRequest;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseListRestRequest;
import com.epm.gestepm.rest.personalexpense.response.ResponsesForPersonalExpense;
import com.epm.gestepm.rest.personalexpense.response.ResponsesForPersonalExpenseList;
import com.epm.gestepm.restapi.openapi.api.PersonalExpenseV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_EDIT_PE;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_READ_PE;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class PersonalExpenseController extends BaseController implements PersonalExpenseV1Api,
        ResponsesForPersonalExpense, ResponsesForPersonalExpenseList {

    private final PersonalExpenseService personalExpenseService;

    public PersonalExpenseController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                     final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                     final PersonalExpenseService personalExpenseService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.personalExpenseService = personalExpenseService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Get personal expense list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListPersonalExpensesV1200Response> listPersonalExpensesV1(final Integer personalExpenseSheetId, final List<String> meta,
                                                                                              final Boolean links, final Set<String> expand,
                                                                                              final Long offset, final Long limit, final String order,
                                                                                              final String orderBy) {
        final PersonalExpenseListRestRequest req = new PersonalExpenseListRestRequest(new ArrayList<>(), personalExpenseSheetId);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final PersonalExpenseFilterDto filterDto = getMapper(MapPEToPersonalExpenseFilterDto.class).from(req);
        final Page<PersonalExpenseDto> page = this.personalExpenseService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListPersonalExpenseV1Operation());
        final List<PersonalExpense> data = getMapper(MapPEToPersonalExpenseResponse.class).from(page);

        this.decorate(req, data, PersonalExpenseResponseDecorator.class);

        return toListPersonalExpensesV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreatePersonalExpenseV1200Response> findPersonalExpenseByIdV1(final Integer personalExpenseSheetId, final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final PersonalExpenseFindRestRequest req = new PersonalExpenseFindRestRequest(id, personalExpenseSheetId);

        this.setCommon(req, meta, links, expand);

        final PersonalExpenseByIdFinderDto finderDto = getMapper(MapPEToPersonalExpenseByIdFinderDto.class).from(req);
        final PersonalExpenseDto dto = this.personalExpenseService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindPersonalExpenseV1Operation());
        final PersonalExpense data = getMapper(MapPEToPersonalExpenseResponse.class).from(dto);

        this.decorate(req, data, PersonalExpenseResponseDecorator.class);

        return toResPersonalExpenseResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PE, action = "Create personal expense")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreatePersonalExpenseV1200Response> createPersonalExpenseV1(final Integer personalExpenseSheetId, final CreatePersonalExpenseV1Request reqCreatePersonalExpense) {

        final PersonalExpenseCreateDto createDto = getMapper(MapPEToPersonalExpenseCreateDto.class).from(reqCreatePersonalExpense);
        createDto.setPersonalExpenseSheetId(personalExpenseSheetId);

        final PersonalExpenseDto personalExpense = this.personalExpenseService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final PersonalExpense data = getMapper(MapPEToPersonalExpenseResponse.class).from(personalExpense);

        final CreatePersonalExpenseV1200Response response = new CreatePersonalExpenseV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PE, action = "Update personal expense")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreatePersonalExpenseV1200Response> updatePersonalExpenseV1(final Integer personalExpenseSheetId, final Integer id, final UpdatePersonalExpenseV1Request reqUpdatePersonalExpense) {

        final PersonalExpenseUpdateDto updateDto = getMapper(MapPEToPersonalExpenseUpdateDto.class).from(reqUpdatePersonalExpense);
        updateDto.setId(id);

        final PersonalExpenseDto personalExpense = this.personalExpenseService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final PersonalExpense data = getMapper(MapPEToPersonalExpenseResponse.class).from(personalExpense);

        final CreatePersonalExpenseV1200Response response = new CreatePersonalExpenseV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PE, action = "Delete personal expense")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deletePersonalExpenseV1(final Integer personalExpenseSheetId, final Integer id) {

        final PersonalExpenseDeleteDto deleteDto = new PersonalExpenseDeleteDto(id);

        this.personalExpenseService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

