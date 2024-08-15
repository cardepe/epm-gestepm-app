package com.epm.gestepm.lib.controller;

import com.epm.gestepm.lib.utils.ObjectCopyUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public abstract class RestRequest implements Serializable {

    private Boolean links;

    private Set<String> expand;

    private List<String> meta;

    private String locale;

    private Long limit;

    private Long offset;

    private String order;

    private String orderBy;

    public void commonValuesFrom(RestRequest req) {
        this.setLinks(req.getLinks());
        this.setLocale(req.getLocale());
        this.setMeta(req.getMeta());
        this.setOrder(req.getOrder());
        this.setOrderBy(req.getOrderBy());
    }

    public boolean hasMeta(String name) {
        return meta != null && (meta.contains(name) || meta.contains("_all"));
    }

    public boolean hasExpand(String field) {
        return expand != null && (expand.contains(field) || expand.contains("_all"));
    }

    public Set<String> getChildExpands(String field) {

        Set<String> childExpands;

        if (expand == null || field == null) {
            childExpands = null;
        } else {
            final String prefix = field.concat(".");
            childExpands = expand.stream()
                .filter(e -> e.startsWith(prefix))
                .map(e -> e.replace(prefix, ""))
                .collect(Collectors.toSet());
        }

        return childExpands;
    }

    public RestRequest getChildRequest(String field) {

        final RestRequest childReq = ObjectCopyUtils.copy(this);
        childReq.setExpand(this.getChildExpands(field));

        return childReq;
    }

}
