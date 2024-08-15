package com.epm.gestepm.masterdata.api.country.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class CountryFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String name;

  private List<String> tags;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("tags", this.tags);

    return cacheKeyBuilder.toString();
  }

}
