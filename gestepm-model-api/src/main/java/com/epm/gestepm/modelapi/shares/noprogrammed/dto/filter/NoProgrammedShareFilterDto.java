package com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class NoProgrammedShareFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);

    return cacheKeyBuilder.toString();
  }

}
