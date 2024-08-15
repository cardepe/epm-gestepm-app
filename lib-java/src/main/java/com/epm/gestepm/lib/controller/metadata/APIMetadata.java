package com.epm.gestepm.lib.controller.metadata;

import java.util.Map;

public class APIMetadata {

    private OperationMetadata operation;

    private RequestMetadata request;

    private ExpandMetadata expand;

    private PaginationMetadata pagination;

    private ResourceLocaleMetadata resourceLocale;

    private String toggleLinks;

    private Map<String, String> childResources;

    public OperationMetadata getOperation() {
        return operation;
    }

    public void setOperation(OperationMetadata operation) {
        this.operation = operation;
    }

    public RequestMetadata getRequest() {
        return request;
    }

    public void setRequest(RequestMetadata request) {
        this.request = request;
    }

    public ExpandMetadata getExpand() {
        return expand;
    }

    public void setExpand(ExpandMetadata expand) {
        this.expand = expand;
    }

    public PaginationMetadata getPagination() {
        return pagination;
    }

    public void setPagination(PaginationMetadata pagination) {
        this.pagination = pagination;
    }

    public ResourceLocaleMetadata getResourceLocale() {
        return resourceLocale;
    }

    public void setResourceLocale(ResourceLocaleMetadata resourceLocale) {
        this.resourceLocale = resourceLocale;
    }

    public String getToggleLinks() {
        return toggleLinks;
    }

    public void setToggleLinks(String toggleLinks) {
        this.toggleLinks = toggleLinks;
    }

    public Map<String, String> getChildResources() {
        return childResources;
    }

    public void setChildResources(Map<String, String> childResources) {
        this.childResources = childResources;
    }

}
