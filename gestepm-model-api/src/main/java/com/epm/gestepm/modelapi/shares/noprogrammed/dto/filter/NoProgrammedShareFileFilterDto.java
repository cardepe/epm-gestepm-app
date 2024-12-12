package com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class NoProgrammedShareFileFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer shareId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("shareId", this.shareId);

    return cacheKeyBuilder.toString();
  }

}
