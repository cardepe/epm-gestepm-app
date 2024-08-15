package com.epm.gestepm.lib.controller.metadata;

public class PaginationMetadata {

    private Integer itemsCount;

    private Long itemsStart;

    private Long itemsFinish;

    private Long itemsTotal;

    private PaginationMetadataLinks links;

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public Long getItemsStart() {
        return itemsStart;
    }

    public void setItemsStart(Long itemsStart) {
        this.itemsStart = itemsStart;
    }

    public Long getItemsFinish() {
        return itemsFinish;
    }

    public void setItemsFinish(Long itemsFinish) {
        this.itemsFinish = itemsFinish;
    }

    public Long getItemsTotal() {
        return itemsTotal;
    }

    public void setItemsTotal(Long itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    public PaginationMetadataLinks getLinks() {
        return links;
    }

    public void setLinks(PaginationMetadataLinks links) {
        this.links = links;
    }

}
