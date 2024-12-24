package com.epm.gestepm.modelapi.inspection.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class InspectionFileFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer inspectionId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("inspectionId", this.inspectionId);

    return cacheKeyBuilder.toString();
  }

}
