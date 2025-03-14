<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.Objects" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%

    String mail = "";
    String password = "";
    boolean remember = false;

    final String language = request.getLocale().getLanguage();

    if (!Objects.isNull(request.getCookies())) {
        final List<Cookie> cookies = Arrays.asList(request.getCookies());

        final Cookie rememberCookie = cookies.stream()
                .filter(cookie -> "gestepm_remember_login_info".equals(cookie.getName()))
                .findAny()
                .orElse(null);

        if (rememberCookie != null) {
            final String value = new String(Base64.getDecoder().decode(rememberCookie.getValue()));

            if (StringUtils.isNoneEmpty(value)) {
                final String[] parts = value.split(":");

                if (parts.length == 2) {
                    mail = parts[0];
                    password = parts[1];
                    remember = true;
                }
            }
        }
    }

%>

<body class="my-login-page">

<style>
    .app-version {
        font-size: 11px;
        font-weight: bold;
    }
</style>

<input id="lang" type="hidden" value="<%=language%>"/>

<section class="h-100">
    <div class="container h-100">
        <div class="row justify-content-md-center h-100">
            <div class="card-wrapper">
                <div class="brand">
                    <img src="/ui/static/images/logo-solo.png" alt="logo">
                </div>
                <div class="card fat">
                    <div class="card-body">
                        <h4 class="card-title"><spring:message code="login.title"/></h4>
                        <form method="POST" action="/login/check" class="my-login-validation" novalidate>
                            <div class="form-group">
                                <label for="email"><spring:message code="login.email"/></label>
                                <input id="email" type="text"
                                       class="${ errorType == 0 ? 'form-control is-invalid' : 'form-control' }"
                                       name="email" value="<%=mail%>" required autofocus>
                                <div class="invalid-feedback">
                                    <spring:message code="login.email.invalid"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password"><spring:message code="login.password"/></label>
                                <input id="password" type="password"
                                       class="${ errorType == 0 ? 'form-control is-invalid' : 'form-control' }"
                                       name="password" value="<%=password%>" required data-eye/>
                                <div class="invalid-feedback">
                                    <spring:message code="login.password.required"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="custom-checkbox custom-control">
                                    <input type="checkbox" name="remember" id="remember" class="custom-control-input"
                                           <% if (remember) { %>checked<% } %>>
                                    <label for="remember" class="custom-control-label"><spring:message
                                            code="login.remember.me"/></label>
                                </div>
                            </div>

                            <div class="form-group m-0">
                                <button type="submit" class="btn btn-primary btn-block">
                                    <spring:message code="login.button.submit"/>
                                </button>
                            </div>

                            <c:if test="${errorType > 0}">
                                <div class="alert alert-danger mt-2" role="alert">
                                    <spring:message code="error.login.type.${errorType}"/>
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
                <div class="footer">
                    <p class="app-version">v.${appVersion}</p>
                    <span><spring:message code="login.copyright"/></span>
                </div>
            </div>
        </div>
    </div>
</section>