package com.epm.gestepm.lib.controller.metadata;

import java.util.List;

public class ExpandMetadata {

    private List<String> expandableFields;

    private String expandAll;

    public List<String> getExpandableFields() {
        return expandableFields;
    }

    public void setExpandableFields(List<String> expandableFields) {
        this.expandableFields = expandableFields;
    }

    public String getExpandAll() {
        return expandAll;
    }

    public void setExpandAll(String expandAll) {
        this.expandAll = expandAll;
    }

}
