<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Header-->
<header id="header" class="header">
	<div class="top-left">
		<div class="navbar-header">
			<a class="navbar-brand" href="/"><img src="/ui/static/images/logo.png" alt="Logo" height="40"></a>
			<a id="menuToggle" class="menutoggle"><i class="fa fa-bars"></i></a>
		</div>
	</div>
	<div class="top-right">
		<div class="header-menu">
			<div class="header-left">

				<div class="mr-3 header-left justify-content-center align-self-center">
					<a href="?locale=es">
						<img src="/ui/static/images/ico_cast.gif" alt="es" />
					</a>
				</div>

				<div class="mr-2 header-left justify-content-center align-self-center">
					<a href="?locale=fr">
						<img src="/ui/static/images/ico_fr.png" alt="fr" />
					</a>
				</div>

				<div class="dropdown for-notification">
					<button class="btn btn-secondary dropdown-toggle" type="button" id="pendingExpesnes" <c:if test="${fn:length(expensesPending) > 0}"> data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" </c:if>>
						<i class="fa fa-coins"></i>
						<c:if test="${fn:length(expensesPending) > 0}">
							<span class="count bg-danger">${fn:length(expensesPending)}</span>
						</c:if>
					</button>

					<div class="dropdown-menu" aria-labelledby="pendingExpesnes" x-placement="bottom-start" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(-40px, 56px, 0px); overflow-x: hidden; overflow-y: scroll; height: 300px">
						<c:forEach items="${expensesPending}" var="epDTO">
							<a class="dropdown-item media" href="/${epDTO.expenseType}/${epDTO.id}">
								<p>${epDTO.name}: ${epDTO.expenseSheetsCount}</p>
							</a>
						</c:forEach>
					</div>
				</div>
			</div>

			<div class="d-none d-sm-block ml-3 header-left justify-content-center align-self-center">
				<span class="header-name">${ user.name } ${ user.surnames }</span>
			</div>

			<div class="user-area dropdown float-right">
				<a href="#" class="dropdown-toggle active" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<img class="user-avatar rounded-circle" src="/ui/static/images/profile.png" alt="User Avatar" height="40">
				</a>

				<div class="user-menu dropdown-menu">
					<a class="nav-link" href="/logout"><i class="fa fa-power -off"></i><spring:message code="user.logout" /></a>
				</div>
			</div>

		</div>
	</div>
</header>
<!-- /#header -->