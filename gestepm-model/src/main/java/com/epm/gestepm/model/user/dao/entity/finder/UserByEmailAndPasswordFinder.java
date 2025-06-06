package com.epm.gestepm.model.user.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.user.dao.constants.UserAttributes.*;

@Data
public class UserByEmailAndPasswordFinder implements CollectableAttributes {

  @NotNull
  private String email;

  @NotNull
  private String password;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_U_EMAIL, this.email);
    map.put(ATTR_U_PASSWORD, this.password);

    return map;
  }

}
