package com.epm.gestepm.lib.controller;

import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.decorator.ResponseDataDecorator;
import com.epm.gestepm.lib.controller.metadata.*;
import com.epm.gestepm.lib.controller.response.ResSuccess;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.controller.restcontext.RestContextProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.lib.types.PageCursor;
import com.epm.gestepm.lib.utils.ObjectCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class BaseController {

    private final LocaleProvider localeProvider;

    private final ExecutionRequestProvider executionRequestProvider;

    private final ExecutionTimeProvider executionTimeProvider;

    private final RestContextProvider restContextProvider;

    private final ApplicationContext applicationContext;

    private final AppLocaleService appLocaleService;

    private final ResponseSuccessfulHelper responseSuccessfulHelper;

    protected BaseController(LocaleProvider localeProvider, ExecutionRequestProvider executionRequestProvider,
            ExecutionTimeProvider executionTimeProvider, RestContextProvider restContextProvider,
            ApplicationContext applicationContext, AppLocaleService appLocaleService,
            ResponseSuccessfulHelper responseSuccessfulHelper) {

        this.localeProvider = localeProvider;
        this.executionRequestProvider = executionRequestProvider;
        this.executionTimeProvider = executionTimeProvider;
        this.restContextProvider = restContextProvider;
        this.applicationContext = applicationContext;
        this.appLocaleService = appLocaleService;
        this.responseSuccessfulHelper = responseSuccessfulHelper;
    }

    protected void setDefaults(RestRequest req) {
        req.setLocale(localeProvider.getLocale().orElse(null));
    }

    protected void setCommon(RestRequest req, List<String> metaOptions, boolean links, Set<String> expand) {
        req.setExpand(expand);
        req.setLinks(links);
        req.setMeta(metaOptions);
    }

    protected void setPagination(RestRequest req, Long limit, Long offset) {
        req.setLimit(limit);
        req.setOffset(offset);
    }

    protected void setOrder(RestRequest req, String order, String orderBy) {
        req.setOrder(StringUtils.isNoneEmpty(order) ? order.toUpperCase() : null);
        req.setOrderBy(orderBy);
    }

    protected <D extends ResponseDataDecorator<?>> D getDecorator(Class<D> decorator) {
        return applicationContext.getBean(decorator);
    }

    protected <D extends ResponseDataDecorator<T>, T, R extends RestRequest> void decorate(R req, T data,
            Class<D> decorator) {
        getDecorator(decorator).decorate(req, data);
    }

    protected <D extends ResponseDataDecorator<T>, T, R extends RestRequest> void decorate(R req, List<T> data,
            Class<D> decorator) {
        getDecorator(decorator).decorate(req, data);
    }

    protected APIMetadata getDefaultMetadata() {

        final APIMetadata apiMetadata = new APIMetadata();
        apiMetadata.setRequest(getRequestMetadata());

        return apiMetadata;
    }

    protected <R extends RestRequest> APIMetadata getMetadata(R req, APIOperation<?, R> apiOperation) {
        return getBaseMetadata(req, apiOperation);
    }

    protected <R extends RestRequest> APIMetadata getMetadata(R req, Page<?> pageData,
            APIOperation<?, R> apiOperation) {

        APIMetadata metadata = getBaseMetadata(req, apiOperation);
        metadata = metadata != null ? metadata : new APIMetadata();
        metadata.setPagination(getPaginationMetadata(req, pageData, apiOperation));

        return metadata;
    }

    protected <T> ResponseEntity<T> success(Function<ResSuccess, T> mapping) {

        final ResSuccess response = responseSuccessfulHelper.buildSuccessfulResponse();
        final T mapped = mapping.apply(response);

        return ResponseEntity.ok(mapped);
    }

    private <R extends RestRequest> APIMetadata getBaseMetadata(R req, APIOperation<?, R> apiOperation) {

        APIMetadata metadata = null;

        if (req.hasMeta(MetadataFields.META_FIELD_OPERATION)) {
            metadata = new APIMetadata();
            metadata.setOperation(getOperationMetadata(apiOperation));
        }

        if (req.hasMeta(MetadataFields.META_FIELD_REQUEST)) {
            metadata = metadata != null ? metadata : new APIMetadata();
            metadata.setRequest(getRequestMetadata());
        }

        if (apiOperation.hasExpand() && req.hasMeta(MetadataFields.META_FIELD_EXPAND)) {
            metadata = metadata != null ? metadata : new APIMetadata();
            metadata.setExpand(getExpandMetadata(req, apiOperation));
        }

        if (apiOperation.hasLocale() && req.hasMeta(MetadataFields.META_FIELD_LOCALE)) {
            metadata = metadata != null ? metadata : new APIMetadata();
            metadata.setResourceLocale(getResourceLocaleMetadata(req, apiOperation));
        }

        if (req.hasMeta(MetadataFields.META_FIELD_CHILD_RES)) {
            metadata = metadata != null ? metadata : new APIMetadata();
            metadata.setChildResources(restContextProvider.childResourcesOf(apiOperation, req));
        }

        if (apiOperation.hasLinks() && req.hasMeta(MetadataFields.META_FIELD_LINKS)) {
            metadata = metadata != null ? metadata : new APIMetadata();

            final R reqCopy = ObjectCopyUtils.copy(req);
            reqCopy.setLinks(!req.getLinks());

            metadata.setToggleLinks(apiOperation.getLinkFor(reqCopy));
        }

        return metadata;
    }

    private OperationMetadata getOperationMetadata(APIOperation<?, ? extends RestRequest> apiOperation) {

        final OperationMetadata operationMetadata = new OperationMetadata();
        operationMetadata.setApiOperationName(apiOperation.getOperationName());

        return operationMetadata;
    }

    private RequestMetadata getRequestMetadata() {

        final RequestMetadata requestMetadata = new RequestMetadata();
        requestMetadata.setTraceId(executionRequestProvider.getTraceId());
        requestMetadata.setRequestUri(executionRequestProvider.getRequestUri());
        requestMetadata.setRequestTimestamp(executionTimeProvider.getStartExecutionTime());
        requestMetadata.setResponseTimestamp(executionTimeProvider.getElapsedTimeFromNow());
        requestMetadata.setResponseTime(executionTimeProvider.getHumanReadableElapsed());

        return requestMetadata;
    }

    private <R extends RestRequest> ExpandMetadata getExpandMetadata(R req, APIOperation<?, R> apiOperation) {

        final R reqCopy = ObjectCopyUtils.copy(req);
        reqCopy.setExpand(Set.of("_all"));

        final String expandAllLink = apiOperation.tryLinkFor(reqCopy);

        final ExpandMetadata expandMetadata = new ExpandMetadata();
        expandMetadata.setExpandableFields(apiOperation.getExpandableFields());
        expandMetadata.setExpandAll(expandAllLink);

        return expandMetadata;
    }

    private <R extends RestRequest> PaginationMetadata getPaginationMetadata(R req, Page<?> pageData,
            APIOperation<?, R> apiOperation) {

        final PaginationMetadata paginationMetadata = new PaginationMetadata();

        final Long offset = req.getOffset();
        final Long limit = req.getLimit();
        final Long total = pageData.getTotal();

        final Long itemsStart = offset + 1L;
        final Long itemsFinish = Math.min(offset + limit, total);
        final int itemsCount = pageData.size();

        paginationMetadata.setItemsFinish(itemsFinish);
        paginationMetadata.setItemsStart(itemsStart);
        paginationMetadata.setItemsCount(itemsCount);
        paginationMetadata.setItemsTotal(total);

        final PaginationMetadataLinks paginationLinks = new PaginationMetadataLinks();

        final R reqCopy = ObjectCopyUtils.copy(req);

        paginationLinks.setSelf(apiOperation.getLinkFor(reqCopy));

        final PageCursor cursor = pageData.cursor();

        reqCopy.setOffset(cursor.firstPage().getOffset());
        paginationLinks.setFirst(apiOperation.getLinkFor(reqCopy));

        reqCopy.setOffset(cursor.lastPage().getOffset());
        paginationLinks.setLast(apiOperation.getLinkFor(reqCopy));

        if (cursor.nextPage().isPresent()) {
            reqCopy.setOffset(cursor.nextPage().get().getOffset());
            paginationLinks.setNext(apiOperation.getLinkFor(reqCopy));
        }

        if (cursor.prevPage().isPresent()) {
            reqCopy.setOffset(cursor.prevPage().get().getOffset());
            paginationLinks.setNext(apiOperation.getLinkFor(reqCopy));
        }

        paginationMetadata.setLinks(paginationLinks);

        return paginationMetadata;
    }

    private <R extends RestRequest> ResourceLocaleMetadata getResourceLocaleMetadata(R req,
            APIOperation<?, R> apiOperation) {

        final R reqCopy = ObjectCopyUtils.copy(req);

        final AppLocaleDto defaultLocale = this.appLocaleService.findDefaultOrNotFound();
        reqCopy.setLocale(defaultLocale.getAppLocale());

        final ResourceLocaleMetadata resourceLocaleMetadata = new ResourceLocaleMetadata();
        resourceLocaleMetadata.setCurrentResourceLocale(localeProvider.getLocale().orElse("NONE"));
        resourceLocaleMetadata.setResourceInDefaultLocale(apiOperation.getLinkFor(reqCopy));

        return resourceLocaleMetadata;
    }

}
