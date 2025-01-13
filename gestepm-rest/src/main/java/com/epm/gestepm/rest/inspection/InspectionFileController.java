package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionFileDeleteDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.restapi.openapi.api.InspectionFileV1Api;
import com.epm.gestepm.restapi.openapi.model.ResSuccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class InspectionFileController extends BaseController implements InspectionFileV1Api {

    private final InspectionFileService inspectionFileService;

    public InspectionFileController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                    final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                    final InspectionFileService inspectionFileService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.inspectionFileService = inspectionFileService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection file")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteInspectionFileV1(final Integer shareId, final Integer inspectionId, final Integer id) {

        final InspectionFileDeleteDto deleteDto = new InspectionFileDeleteDto();
        deleteDto.setId(id);

        this.inspectionFileService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

