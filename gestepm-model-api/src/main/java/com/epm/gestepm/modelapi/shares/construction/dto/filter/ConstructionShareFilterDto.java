package com.epm.gestepm.modelapi.shares.construction.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConstructionShareFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer userId;

  private Integer projectId;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Integer progress;

  private Integer activityCenterId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("userId", this.userId);
    cacheKeyBuilder.addElement("projectId", this.projectId);
    cacheKeyBuilder.addElement("startDate", this.startDate);
    cacheKeyBuilder.addElement("endDate", this.endDate);
    cacheKeyBuilder.addElement("progress", this.progress);
    cacheKeyBuilder.addElement("activityCenterId", this.activityCenterId);

    return cacheKeyBuilder.toString();
  }

}
