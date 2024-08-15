package com.epm.gestepm.lib.testutils;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.applocale.model.dao.AppLocaleDao;
import com.epm.gestepm.lib.applocale.model.service.AppLocaleServiceImpl;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.controller.restcontext.RestContextProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;
import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.locale.provider.DefaultLocaleProvider;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

public class ControllerTest {

    public static final String DEFAULT_LOCALE = "ES";

    protected LocaleProvider mockLocaleProvider() {

        final DefaultLocaleProvider defaultLocaleProvider = new DefaultLocaleProvider();
        defaultLocaleProvider.acquire();
        defaultLocaleProvider.setLocale(DEFAULT_LOCALE);

        return defaultLocaleProvider;
    }

    protected ExecutionRequestProvider mockExecutionRequestProvider() {
        return new MockExecutionRequestProvider();
    }

    protected ExecutionTimeProvider mockExecutionTimeProvider() {
        return new MockExecutionTimeProvider();
    }

    protected RestContextProvider mockRestContextProvider() {
        return new RestContextProvider();
    }

    protected ResponseSuccessfulHelper mockResponseSuccessfulHelper() {
        return new ResponseSuccessfulHelper(this.mockLocaleProvider(), Mockito.mock(MessageSource.class));
    }

    protected AppLocaleService mockAppLocaleService() {
        final AppLocaleDao mockDao = Mockito.mock(AppLocaleDao.class);
        return new AppLocaleServiceImpl(mockDao);
    }

}
