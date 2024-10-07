package com.epm.gestepm.masterdata.holiday.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementAttributes.*;
import static com.epm.gestepm.masterdata.holiday.dao.constants.HolidayAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class HolidayFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private String name;

    private Integer day;

    private Integer month;

    final List<Integer> countryIds;

    final List<Integer> activityCenterIds;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_H_IDS, this.ids);
        map.putLike(ATTR_H_NAME, this.name);
        map.put(ATTR_H_DAY, this.day);
        map.put(ATTR_H_MONTH, this.month);
        map.putList(ATTR_H_C_IDS, this.countryIds);
        map.putList(ATTR_H_AC_IDS, this.activityCenterIds);

        return map;
    }
}
