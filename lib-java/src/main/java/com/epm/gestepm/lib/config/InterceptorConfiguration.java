package com.epm.gestepm.lib.config;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.executiontrace.provider.DefaultExecutionTimeProvider;
import com.epm.gestepm.lib.interceptor.ExecutionTimeInterceptor;
import com.epm.gestepm.lib.interceptor.RequestLocaleInterceptor;
import com.epm.gestepm.lib.interceptor.TraceIdInterceptor;
import com.epm.gestepm.lib.interceptor.UserProviderInterceptor;
import com.epm.gestepm.lib.locale.ThreadLocalLocaleProvider;
import com.epm.gestepm.lib.user.ThreadLocalUserProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final DefaultExecutionTimeProvider executionTimeProvider;

    private final ThreadLocalUserProvider userProvider;

    private final ThreadLocalLocaleProvider localeProvider;

    private final LocaleResolver localeResolver;

    private final AppLocaleService appLocaleService;

    public InterceptorConfiguration(final DefaultExecutionTimeProvider executionTimeProvider,
                                    final ThreadLocalUserProvider userProvider, final ThreadLocalLocaleProvider localeProvider,
                                    final LocaleResolver localeResolver,
                                    final AppLocaleService appLocaleService) {
        this.executionTimeProvider = executionTimeProvider;
        this.userProvider = userProvider;
        this.localeProvider = localeProvider;
        this.localeResolver = localeResolver;
        this.appLocaleService = appLocaleService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        final ExecutionTimeInterceptor executionTimeInterceptor;
        executionTimeInterceptor = new ExecutionTimeInterceptor(this.executionTimeProvider);

        registry.addInterceptor(executionTimeInterceptor);

        final UserProviderInterceptor userProviderInterceptor;
        userProviderInterceptor = new UserProviderInterceptor(this.userProvider);

        registry.addInterceptor(userProviderInterceptor);

        final RequestLocaleInterceptor requestLocaleInterceptor = new RequestLocaleInterceptor(this.localeProvider,
                this.localeResolver,
                this.appLocaleService);

        registry.addInterceptor(requestLocaleInterceptor);

        registry.addInterceptor(new TraceIdInterceptor());
    }

}
