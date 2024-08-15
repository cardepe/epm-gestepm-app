package com.epm.gestepm.masterdata.api.activitycenter.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class ActivityCenterFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String name;

  private List<Integer> countryIds;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("countryIds", this.countryIds);

    return cacheKeyBuilder.toString();
  }
}
