package com.epm.gestepm.masterdata.api.holiday.dto.finder;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HolidayByIdFinderDto implements UsableAsCacheKey {

  @NotNull
  private Integer id;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("id", this.id);

    return cacheKeyBuilder.toString();
  }

}
