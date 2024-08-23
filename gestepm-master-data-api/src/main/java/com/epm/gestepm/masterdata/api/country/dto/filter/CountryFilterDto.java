package com.epm.gestepm.masterdata.api.country.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CountryFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String name;

  private List<String> tags;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("name", this.name);
    cacheKeyBuilder.addElement("tags", this.tags);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
