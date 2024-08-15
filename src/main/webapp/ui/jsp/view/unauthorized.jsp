<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body class="my-unauthorized-page">

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
							<h4 class="card-title">
								<spring:message code="unauthorized.title" />
							</h4>
							<h5 class="card-description">
								<spring:message code="unauthorized.access" />
							</h5>
							<a class="btn btn-danger btn-block" href="javascript:history.back()"><spring:message code="back" /></a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="footer">
			<spring:message code="login.copyright" />
		</div>
	</section>