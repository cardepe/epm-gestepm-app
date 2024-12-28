package com.epm.gestepm.modelapi.inspection.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InspectionFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer shareId;

  private Integer projectId;

  private OffsetDateTime startDate;

  private OffsetDateTime endDate;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("shareId", shareId);
    cacheKeyBuilder.addElement("projectId", this.projectId);
    cacheKeyBuilder.addElement("startDate", this.startDate);
    cacheKeyBuilder.addElement("endDate", this.endDate);

    return cacheKeyBuilder.toString();
  }

}
