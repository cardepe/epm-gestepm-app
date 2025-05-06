package com.epm.gestepm.rest.shares.work;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareFileService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.restapi.openapi.api.WorkShareFileV1Api;
import com.epm.gestepm.restapi.openapi.model.ResSuccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.shares.work.security.WorkSharePermission.PRMT_EDIT_WS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class WorkShareFileController extends BaseController implements WorkShareFileV1Api {

    private final WorkShareFileService workShareFileService;

    public WorkShareFileController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                   final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                   final WorkShareFileService workShareFileService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.workShareFileService = workShareFileService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_WS, action = "Delete work share file")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteWorkShareFileV1(final Integer workShareId, final Integer id) {

        final WorkShareFileDeleteDto deleteDto = new WorkShareFileDeleteDto();
        deleteDto.setId(id);

        this.workShareFileService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

