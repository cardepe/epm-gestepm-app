package com.epm.gestepm.modelapi.scopes;

import com.epm.gestepm.modelapi.common.scope.ShareListFilterParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class ShareListScope {

    @Bean
    @SessionScope
    public ShareListFilterParams rsShareListFilterParams() {
        return new ShareListFilterParams();
    }

}
