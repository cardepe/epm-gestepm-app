package com.epm.gestepm.masterdata.api.displacement.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementTypeDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DisplacementFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  final List<Integer> activityCenterIds;

  private String name;

  private DisplacementTypeDto type;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("activityCenterIds", this.activityCenterIds);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("type", this.type);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
