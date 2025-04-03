package com.epm.gestepm.rest.teleworkingsigning;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.creator.TeleworkingSigningCreateDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.deleter.TeleworkingSigningDeleteDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.finder.TeleworkingSigningByIdFinderDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.updater.TeleworkingSigningUpdateDto;
import com.epm.gestepm.modelapi.teleworkingsigning.service.TeleworkingSigningService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.teleworkingsigning.decorators.TeleworkingSigningResponseDecorator;
import com.epm.gestepm.rest.teleworkingsigning.mappers.*;
import com.epm.gestepm.rest.teleworkingsigning.operations.FindTeleworkingSigningV1Operation;
import com.epm.gestepm.rest.teleworkingsigning.operations.ListTeleworkingSigningV1Operation;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningFindRestRequest;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningListRestRequest;
import com.epm.gestepm.rest.teleworkingsigning.response.ResponsesForTeleworkingSigning;
import com.epm.gestepm.rest.teleworkingsigning.response.ResponsesForTeleworkingSigningList;
import com.epm.gestepm.restapi.openapi.api.TeleworkingSigningV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.teleworkingsigning.security.TeleworkingSigningPermission.PRMT_EDIT_TS;
import static com.epm.gestepm.modelapi.teleworkingsigning.security.TeleworkingSigningPermission.PRMT_READ_TS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class TeleworkingSigningController extends BaseController implements TeleworkingSigningV1Api,
        ResponsesForTeleworkingSigning, ResponsesForTeleworkingSigningList {

    private final TeleworkingSigningService personalExpenseSheetService;

    public TeleworkingSigningController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                        final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                        final TeleworkingSigningService personalExpenseSheetService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.personalExpenseSheetService = personalExpenseSheetService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "Get teleworking signing list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListTeleworkingSigningsV1200Response> listTeleworkingSigningsV1(final List<String> meta,
                                                                                              final Boolean links, final Set<String> expand,
                                                                                              final Long offset, final Long limit, final String order,
                                                                                              final String orderBy, final List<Integer> ids, final List<Integer> userIds,
                                                                                              final List<Integer> projectIds, final Boolean current) {
        final TeleworkingSigningListRestRequest req = new TeleworkingSigningListRestRequest(ids, userIds, projectIds, current);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final TeleworkingSigningFilterDto filterDto = getMapper(MapTSToTeleworkingSigningFilterDto.class).from(req);
        final Page<TeleworkingSigningDto> page = this.personalExpenseSheetService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListTeleworkingSigningV1Operation());
        final List<TeleworkingSigning> data = getMapper(MapTSToTeleworkingSigningResponse.class).from(page);

        this.decorate(req, data, TeleworkingSigningResponseDecorator.class);

        return toListTeleworkingSigningsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "Find teleworking signing")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateTeleworkingSigningV1200Response> findTeleworkingSigningByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final TeleworkingSigningFindRestRequest req = new TeleworkingSigningFindRestRequest(id);

        this.setCommon(req, meta, links, expand);

        final TeleworkingSigningByIdFinderDto finderDto = getMapper(MapTSToTeleworkingSigningByIdFinderDto.class).from(req);
        final TeleworkingSigningDto dto = this.personalExpenseSheetService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindTeleworkingSigningV1Operation());
        final TeleworkingSigning data = getMapper(MapTSToTeleworkingSigningResponse.class).from(dto);

        this.decorate(req, data, TeleworkingSigningResponseDecorator.class);

        return toResTeleworkingSigningResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Create teleworking signing")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateTeleworkingSigningV1200Response> createTeleworkingSigningV1(final CreateTeleworkingSigningV1Request reqCreateTeleworkingSigning) {

        final TeleworkingSigningCreateDto createDto = getMapper(MapTSToTeleworkingSigningCreateDto.class).from(reqCreateTeleworkingSigning);

        final TeleworkingSigningDto personalExpenseSheet = this.personalExpenseSheetService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final TeleworkingSigning data = getMapper(MapTSToTeleworkingSigningResponse.class).from(personalExpenseSheet);

        final CreateTeleworkingSigningV1200Response response = new CreateTeleworkingSigningV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Update teleworking signing")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateTeleworkingSigningV1200Response> updateTeleworkingSigningV1(final Integer id, final UpdateTeleworkingSigningV1Request reqUpdateTeleworkingSigning) {

        final TeleworkingSigningUpdateDto updateDto = getMapper(MapTSToTeleworkingSigningUpdateDto.class).from(reqUpdateTeleworkingSigning);
        updateDto.setId(id);

        final TeleworkingSigningDto personalExpenseSheet = this.personalExpenseSheetService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final TeleworkingSigning data = getMapper(MapTSToTeleworkingSigningResponse.class).from(personalExpenseSheet);

        final CreateTeleworkingSigningV1200Response response = new CreateTeleworkingSigningV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Delete teleworking signing")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteTeleworkingSigningV1(final Integer id) {

        final TeleworkingSigningDeleteDto deleteDto = new TeleworkingSigningDeleteDto(id);

        this.personalExpenseSheetService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

}

