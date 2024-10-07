package com.epm.gestepm.masterdata.holiday.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.masterdata.holiday.dao.constants.HolidayAttributes.ATTR_H_ID;

@Data
public class HolidayByIdFinder implements CollectableAttributes {

    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_H_ID, this.id);

        return map;
    }
}
