package com.epm.gestepm.rest.shares.programmed;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareFileService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareFileV1Api;
import com.epm.gestepm.restapi.openapi.model.ResSuccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_EDIT_PS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProgrammedShareFileController extends BaseController implements ProgrammedShareFileV1Api {

    private final ProgrammedShareFileService programmedShareFileService;

    public ProgrammedShareFileController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                         final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                         final ProgrammedShareFileService programmedShareFileService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.programmedShareFileService = programmedShareFileService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Delete programmed share file")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProgrammedShareFileV1(final Integer programmedShareId, final Integer id) {

        final ProgrammedShareFileDeleteDto deleteDto = new ProgrammedShareFileDeleteDto();
        deleteDto.setId(id);

        this.programmedShareFileService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

