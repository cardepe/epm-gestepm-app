package com.epm.gestepm.lib.entity;

import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import lombok.Data;

@Data
public class Orderable {

    private SQLOrderByType order;

    private String orderBy;

}
