package com.epm.gestepm.rest.signings;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.signings.dto.SigningExportDto;
import com.epm.gestepm.modelapi.signings.service.SigningExportService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.restapi.openapi.api.SigningV1Api;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class SigningExportController extends BaseController implements SigningV1Api {

    private final SigningExportService signingExportService;

    public SigningExportController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                        final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                        final SigningExportService signingExportService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.signingExportService = signingExportService;
    }

    @Override
    // @RequirePermits(value = PRMT_READ_SI, action = "Export signings")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<Resource> exportSigningsV1(final LocalDateTime startDate, final LocalDateTime endDate, final Integer userId) {

        final SigningExportDto signingExportDto = new SigningExportDto();
        signingExportDto.setStartDate(startDate);
        signingExportDto.setEndDate(endDate);
        signingExportDto.setUserId(userId);

        final byte[] excel = this.signingExportService.generate(signingExportDto);
        final Resource resource = new ByteArrayResource(excel);
        final String fileName = this.signingExportService.buildFileName(signingExportDto);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);


    }
}
