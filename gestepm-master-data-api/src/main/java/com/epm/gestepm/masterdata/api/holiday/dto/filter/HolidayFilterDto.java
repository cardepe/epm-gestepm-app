package com.epm.gestepm.masterdata.api.holiday.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HolidayFilterDto extends OrderableDto implements UsableAsCacheKey {

    private List<Integer> ids;

    private String name;

    private Integer day;

    private Integer month;

    private List<Integer> countryIds;

    private List<Integer> activityCenterIds;

    @Override
    public String asCacheKey() {

        final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

        cacheKeyBuilder.addElement("ids", this.ids);
        cacheKeyBuilder.addElement("name", this.name);
        cacheKeyBuilder.addElement("day", this.day);
        cacheKeyBuilder.addElement("month", this.month);
        cacheKeyBuilder.addElement("countryIds", this.countryIds);
        cacheKeyBuilder.addElement("activityCenterIds", this.activityCenterIds);
        cacheKeyBuilder.addElement("orderable", super.toString());

        return cacheKeyBuilder.toString();
    }
}
