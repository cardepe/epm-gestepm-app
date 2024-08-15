package com.epm.gestepm.rest.common;

import com.epm.gestepm.lib.controller.restcontext.RestContextProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;
import com.epm.gestepm.lib.locale.LocaleProvider;
import org.springframework.stereotype.Component;

@Component("commonProviders")
public class CommonProviders {

  private final LocaleProvider localeProvider;

  private final ExecutionRequestProvider executionRequestProvider;

  private final ExecutionTimeProvider executionTimeProvider;

  private final RestContextProvider restContextProvider;

  public CommonProviders(LocaleProvider localeProvider,
      ExecutionRequestProvider executionRequestProvider,
      ExecutionTimeProvider executionTimeProvider,
      RestContextProvider restContextProvider) {

    this.localeProvider = localeProvider;
    this.executionRequestProvider = executionRequestProvider;
    this.executionTimeProvider = executionTimeProvider;
    this.restContextProvider = restContextProvider;
  }

  public LocaleProvider localeProvider() {
    return localeProvider;
  }

  public ExecutionRequestProvider executionRequestProvider() {
    return executionRequestProvider;
  }

  public ExecutionTimeProvider executionTimeProvider() {
    return executionTimeProvider;
  }

  public RestContextProvider restContextProvider() {
    return restContextProvider;
  }

}
