package com.epm.gestepm.masterdata.holiday.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.masterdata.holiday.dao.constants.HolidayAttributes.*;

@Data
public class HolidayUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer day;

    @NotNull
    private Integer month;

    @NotNull
    private Integer countryId;

    private Integer activityCenterId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_H_ID, this.id);
        map.put(ATTR_H_NAME, this.name);
        map.put(ATTR_H_DAY, this.day);
        map.put(ATTR_H_MONTH, this.month);
        map.put(ATTR_H_CO_ID, this.countryId);
        map.put(ATTR_H_AC_ID, this.activityCenterId);

        return map;
    }

}
