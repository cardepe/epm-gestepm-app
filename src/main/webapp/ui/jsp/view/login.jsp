<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%

	final String mail;
	final String password;
	final boolean remember;

	final List<Cookie> cookies = Arrays.asList(request.getCookies());

	final Cookie rememberCookie = cookies.stream()
			.filter(cookie -> "gestepm_remember_login_info".equals(cookie.getName()))
			.findAny()
			.orElse(null);

	if (rememberCookie != null) {

		final String value = new String(Base64.getDecoder().decode(rememberCookie.getValue()));

		mail = value.split(":")[0];
		password = value.split(":")[1];
		remember = true;

	} else {

		mail = "";
		password = "";
		remember = false;
	}

%>

<body class="my-login-page">

	<input id="lang" type="hidden" value="${ lang }" />
	
	<section class="h-100">
		<div class="container h-100">
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper">
					<div class="brand">
						<img src="/ui/static/images/logo-solo.png" alt="logo">
					</div>
					<div class="card fat">
						<div class="card-body">
							<h4 class="card-title"><spring:message code="login.title" /></h4>
							<form method="POST" action="/login/check" class="my-login-validation" novalidate>
								<div class="form-group">
									<label for="email"><spring:message code="login.email" /></label>
									<input id="email" type="text" class="${ errorType == 0 ? 'form-control is-invalid' : 'form-control' }" name="email" value="<%=mail%>" required autofocus>
									<div class="invalid-feedback">
										<spring:message code="login.email.invalid" />
									</div>
								</div>

								<div class="form-group">
									<label for="password"><spring:message code="login.password" /></label>
									<input id="password" type="password" class="${ errorType == 0 ? 'form-control is-invalid' : 'form-control' }" name="password" value="<%=password%>" required data-eye />
								    <div class="invalid-feedback">
								    	<spring:message code="login.password.required" />
							    	</div>
								</div>

								<div class="form-group">
									<div class="custom-checkbox custom-control">
										<input type="checkbox" name="remember" id="remember" class="custom-control-input" <% if (remember) { %>checked<% } %>>
										<label for="remember" class="custom-control-label"><spring:message code="login.remember.me" /></label>
									</div>
								</div>

								<div class="form-group m-0">
									<button type="submit" class="btn btn-primary btn-block">
										<spring:message code="login.button.submit" />
									</button>
								</div>
								
								<c:if test="${errorType > 0}">
									<div class="alert alert-danger mt-2" role="alert">
										<spring:message code="error.login.type.${errorType}" />
									</div>
								</c:if>
							</form>
						</div>
					</div>
					<div class="footer">
						<spring:message code="login.copyright" />
					</div>
				</div>
			</div>
		</div>
	</section>