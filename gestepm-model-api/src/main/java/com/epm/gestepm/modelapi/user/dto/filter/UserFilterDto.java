package com.epm.gestepm.modelapi.user.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String nameContains;

  private String email;

  private String password;

  private List<Integer> activityCenterIds;

  private Integer state;

  private List<Integer> signingIds;

  private List<Integer> roleIds;

  private List<Integer> levelIds;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("email", this.email);
    cacheKeyBuilder.addElement("password", this.password);
    cacheKeyBuilder.addElement("activityCenterIds", this.activityCenterIds);
    cacheKeyBuilder.addElement("state", this.state);
    cacheKeyBuilder.addElement("signingIds", this.signingIds);
    cacheKeyBuilder.addElement("roleIds", this.roleIds);
    cacheKeyBuilder.addElement("levelIds", this.levelIds);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
