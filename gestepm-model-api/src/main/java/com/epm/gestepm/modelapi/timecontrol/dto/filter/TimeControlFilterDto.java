package com.epm.gestepm.modelapi.timecontrol.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTypeEnumDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class TimeControlFilterDto implements UsableAsCacheKey {

    private Integer userId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<TimeControlTypeEnumDto> types;

    @Override
    public String asCacheKey() {
        final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

        cacheKeyBuilder.addElement("userId", this.userId);
        cacheKeyBuilder.addElement("startDate", this.startDate);
        cacheKeyBuilder.addElement("endDate", this.endDate);
        cacheKeyBuilder.addElement("types", this.types);

        return cacheKeyBuilder.toString();
    }
}
