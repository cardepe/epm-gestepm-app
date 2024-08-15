package com.epm.gestepm.lib.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.epm.gestepm.lib.user.data.UserData;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(UserProviderOverrides.class)
public @interface UserProviderOverride {

    Class<? extends UserData<?>> userDataClass();

    String value();

}
