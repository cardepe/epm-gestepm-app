package com.epm.gestepm.masterdata.api.displacement.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class DisplacementFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer activityCenterId;

  private String name;

  private Integer type;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("activityCenterId", this.activityCenterId);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("type", this.type);

    return cacheKeyBuilder.toString();
  }
}
