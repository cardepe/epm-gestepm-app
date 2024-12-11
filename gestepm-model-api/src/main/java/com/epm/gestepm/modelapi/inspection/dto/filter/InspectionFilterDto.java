package com.epm.gestepm.modelapi.inspection.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InspectionFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  @NotNull
  private Integer shareId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("shareId", shareId);

    return cacheKeyBuilder.toString();
  }

}
