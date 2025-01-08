package com.epm.gestepm.lib.interceptor;

import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.filter.AppLocaleFilterDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.finder.AppLocaleByIdFinderDto;
import com.epm.gestepm.lib.applocale.apimodel.exception.AppLocaleNotSupportedException;
import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.locale.ThreadLocalLocaleProvider;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

public class RequestLocaleInterceptor implements HandlerInterceptor {

    private static final String LOCALE = "locale";

    private static final String LOCALE_ID = "localeId";

    private final ThreadLocalLocaleProvider localeProvider;

    private final LocaleResolver localeResolver;

    private final AppLocaleService appLocaleService;

    public RequestLocaleInterceptor(final ThreadLocalLocaleProvider localeProvider, final LocaleResolver localeResolver,
            final AppLocaleService appLocaleService) {

        this.localeProvider = localeProvider;
        this.localeResolver = localeResolver;
        this.appLocaleService = appLocaleService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        this.localeProvider.acquire();

        String userLang;

        final String queryParamLocale = request.getParameter(LOCALE);
        final String queryParamLocaleId = request.getParameter(LOCALE_ID);

        if (queryParamLocaleId != null) {

            final AppLocaleByIdFinderDto finder = new AppLocaleByIdFinderDto();
            finder.setAppLocaleId(Integer.parseInt(queryParamLocaleId));

            userLang = this.appLocaleService.find(finder)
                .orElseThrow(AppLocaleNotSupportedException::new)
                .getAppLocale();

        } else if (queryParamLocale != null) {

            final AppLocaleFilterDto filter = new AppLocaleFilterDto();
            filter.setLocales(List.of(queryParamLocale));

            final List<AppLocaleDto> list = this.appLocaleService.list(filter);

            if ((list == null) || list.isEmpty()) {
                throw new AppLocaleNotSupportedException();
            }

            userLang = list.get(0).getAppLocale();

        } else {

            final Locale userLocale = this.localeResolver.resolveLocale(request);
            userLang = userLocale.getLanguage();
        }

        if (userLang == null) {

            final AppLocaleFilterDto filter = new AppLocaleFilterDto();
            filter.setDefault(true);

            userLang = this.appLocaleService.list(filter).get(0).getAppLocale();
        }

        this.localeProvider.setLocale(userLang);
        this.localeResolver.setLocale(request, response, new Locale(userLang));

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) {

        this.localeProvider.release();
    }

}
