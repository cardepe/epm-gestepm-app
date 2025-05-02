package com.epm.gestepm.rest.shares.construction;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareFileService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareFileV1Api;
import com.epm.gestepm.restapi.openapi.model.ResSuccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ConstructionShareFileController extends BaseController implements ConstructionShareFileV1Api {

    private final ConstructionShareFileService constructionShareFileService;

    public ConstructionShareFileController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                           final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                           final ConstructionShareFileService constructionShareFileService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.constructionShareFileService = constructionShareFileService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share file")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteConstructionShareFileV1(final Integer constructionShareId, final Integer id) {

        final ConstructionShareFileDeleteDto deleteDto = new ConstructionShareFileDeleteDto();
        deleteDto.setId(id);

        this.constructionShareFileService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

