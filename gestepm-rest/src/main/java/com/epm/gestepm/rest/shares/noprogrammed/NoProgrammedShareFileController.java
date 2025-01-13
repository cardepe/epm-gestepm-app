package com.epm.gestepm.rest.shares.noprogrammed;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareFileService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.restapi.openapi.api.NoProgrammedShareFileV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class NoProgrammedShareFileController extends BaseController implements NoProgrammedShareFileV1Api {

    private final NoProgrammedShareFileService noProgrammedShareFileService;

    public NoProgrammedShareFileController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                           final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                           final NoProgrammedShareFileService noProgrammedShareFileService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.noProgrammedShareFileService = noProgrammedShareFileService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Delete no programmed share file")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteNoProgrammedShareFileV1(final Integer shareId, final Integer id) {

        final NoProgrammedShareFileDeleteDto deleteDto = new NoProgrammedShareFileDeleteDto();
        deleteDto.setId(id);

        this.noProgrammedShareFileService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

