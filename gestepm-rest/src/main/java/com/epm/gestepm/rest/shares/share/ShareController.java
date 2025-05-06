package com.epm.gestepm.rest.shares.share;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;
import com.epm.gestepm.modelapi.shares.share.service.ShareService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.shares.share.decorators.ShareResponseDecorator;
import com.epm.gestepm.rest.shares.share.mappers.MapSToShareFilterDto;
import com.epm.gestepm.rest.shares.share.mappers.MapSToShareResponse;
import com.epm.gestepm.rest.shares.share.operations.ListShareV1Operation;
import com.epm.gestepm.rest.shares.share.request.ShareListRestRequest;
import com.epm.gestepm.rest.shares.share.response.ResponsesForShareList;
import com.epm.gestepm.restapi.openapi.api.ShareV1Api;
import com.epm.gestepm.restapi.openapi.model.Share;
import com.epm.gestepm.restapi.openapi.model.ListSharesV1200Response;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.modelapi.shares.share.security.SharePermission.PRMT_READ_S;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ShareController extends BaseController implements ShareV1Api, ResponsesForShareList {

    private final ShareService shareService;

    public ShareController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                           final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper, ShareService shareService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.shareService = shareService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_S, action = "Get share list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListSharesV1200Response> listSharesV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                                                        final List<Integer> ids, final List<Integer> userIds, final List<Integer> projectIds, final LocalDateTime startDate, final LocalDateTime endDate, final String status, final List<String> types) {

        final ShareListRestRequest req = new ShareListRestRequest(ids, userIds, projectIds, startDate, endDate, status, types);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ShareFilterDto filterDto = getMapper(MapSToShareFilterDto.class).from(req);
        final Page<ShareDto> page = this.shareService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListShareV1Operation());
        final List<Share> data = getMapper(MapSToShareResponse.class).from(page);

        this.decorate(req, data, ShareResponseDecorator.class);

        return toListSharesV1200Response(metadata, data, page.hashCode());
    }
}
