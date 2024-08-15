<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs">
	<div class="alert alert-danger" role="alert">
		<c:choose>
			<c:when test="${fn:startsWith(msgError, 'error.')}">
				<spring:message code="${msgError}" />
			</c:when>
			<c:otherwise>
				${msgError}
			</c:otherwise>
		</c:choose>
	</div>
</div>

<script>
	var $=jQuery.noConflict();
</script>