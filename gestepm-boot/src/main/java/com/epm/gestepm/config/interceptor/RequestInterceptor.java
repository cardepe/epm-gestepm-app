package com.epm.gestepm.config.interceptor;

import com.epm.gestepm.modelapi.common.scope.ShareListFilterParams;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {

    private ShareListFilterParams shareListFilterParams;

    public RequestInterceptor(ShareListFilterParams shareListFilterParams) {
        this.shareListFilterParams = shareListFilterParams;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        if (modelAndView != null) {

            final String viewName = modelAndView.getViewName();

            if (!viewName.equals("intervention-share") && !viewName.equals("intervention-share-detail")) {
                shareListFilterParams.clear();
            }
        }
    }
}
