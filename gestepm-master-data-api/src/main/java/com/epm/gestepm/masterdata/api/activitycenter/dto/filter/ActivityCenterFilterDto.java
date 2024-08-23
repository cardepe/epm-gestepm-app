package com.epm.gestepm.masterdata.api.activitycenter.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityCenterFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String name;

  private List<Integer> countryIds;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("countryIds", this.countryIds);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
