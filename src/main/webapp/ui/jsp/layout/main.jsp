<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="site.title" /></title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/ui/static/images/favicon.ico">
    
    <!-- CSS Bootstrap -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css">
    
    <!-- CSS FontAwesome -->
	<link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/all.min.css">
	
	<!-- CSS Calendar -->
	<link rel="stylesheet" href="/webjars/fullcalendar/4.3.1/core/main.min.css">
	<c:if test="${pageName eq '/signing/personal' || fn:startsWith(pageName,'/users')}">
		<link rel="stylesheet" href="/webjars/fullcalendar/4.3.1/daygrid/main.min.css">
		<link rel="stylesheet" href="/webjars/fullcalendar/4.3.1/timegrid/main.min.css">
	</c:if>
	
	<!-- CSS Year Calendar -->
	<link rel="stylesheet" href="/ui/static/css/plugins/js-year-calendar/js-year-calendar.min.css">
	
	<c:if test="${pageName eq '/shares/intervention'}">
		<!-- CSS BS Stepper -->
		<link rel="stylesheet" href="/ui/static/css/plugins/bs-stepper/bs-stepper.css">
	</c:if>

	<c:if test="${fn:startsWith(pageName, '/signing') || fn:startsWith(pageName, '/shares') || fn:startsWith(pageName, '/expenses') || fn:startsWith(pageName, '/users') || fn:startsWith(pageName,'/projects') || fn:startsWith(pageName,'/holidays/admin')}">
		<!-- CSS Select 2 -->
		<link rel="stylesheet" href="/webjars/select2/4.0.12/css/select2.min.css">
	</c:if>
	
	<c:if test="${fn:startsWith(pageName,'/expenses') || fn:startsWith(pageName, '/signing') || fn:startsWith(pageName, '/shares') || fn:startsWith(pageName,'/projects') || fn:startsWith(pageName,'/users') || fn:startsWith(pageName,'/roles') || fn:startsWith(pageName,'/admin') }">
		<!-- CSS Datatables -->
		<link rel="stylesheet" href="/webjars/datatables/1.10.20/css/dataTables.bootstrap4.min.css">
		<link rel="stylesheet" href="/ui/static/css/plugins/responsive/responsive.bootstrap4.min.css">
	</c:if>
	
	<c:if test="${fn:startsWith(pageName,'/projects/') || fn:startsWith(pageName,'/users/') || fn:startsWith(pageName, '/admin/families')}">
		<!-- CSS Bootstrap Select -->
		<link rel="stylesheet" href="/webjars/bootstrap-select/1.13.17/css/bootstrap-select.min.css">
	</c:if>
	
	<c:if test="${fn:startsWith(pageName,'/projects/') || pageName eq '/shares/intervention' || pageName eq '/shares/work'}">
		<!-- CSS Lightbox -->
		<link rel="stylesheet" href="/webjars/lightbox2/2.11.1/dist/css/lightbox.min.css">
	</c:if>
	
	<!-- CSS PageName -->
	<link rel="stylesheet" href="/ui/static/css/pages${pageName}.css">
	
	<!-- Custom styles for this template -->
	<link rel="stylesheet" href="/ui/static/css/elaadmin.css">
	<link rel="stylesheet" href="/ui/static/css/table.css">
	<link rel="stylesheet" href="/ui/static/css/widget.css">
    <link rel="stylesheet" href="/ui/static/css/style.css">
    
    <!-- JS Libs -->
    <script type="text/javascript" src="/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/matchHeight/dist/jquery.matchHeight-min.js"></script>
    <script type="text/javascript" src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
	<script type="text/javascript" src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/webjars/axios/1.6.8/dist/axios.min.js"></script>
	
	<c:if test="${pageName eq '/signing/personal' || fn:startsWith(pageName,'/users')}">
		<!-- JS Calendar -->
		<script type="text/javascript" src="/webjars/fullcalendar/4.3.1/core/main.min.js"></script>
		<script type="text/javascript" src="/webjars/fullcalendar/4.3.1/core/locales/es.js"></script>
		<script type="text/javascript" src="/webjars/fullcalendar/4.3.1/daygrid/main.min.js"></script>
		<script type="text/javascript" src="/webjars/fullcalendar/4.3.1/timegrid/main.min.js"></script>
	</c:if>
	
	<!-- JS Year Calendar -->
	<script type="text/javascript" src="/ui/static/js/plugins/js-year-calendar/js-year-calendar.min.js"></script>
	<script type="text/javascript" src="/ui/static/js/plugins/js-year-calendar/js-year-calendar.${locale}.js"></script>

	<c:if test="${ fn:startsWith(pageName, '/signing') || fn:startsWith(pageName, '/users/') }">

		<!-- Location -->
		<script type="text/javascript" src="/ui/static/js/location.js"></script>

	</c:if>
	
	<c:if test="${pageName eq '/shares/intervention' || fn:startsWith(pageName, '/shares/intervention/no-programmed/detail/') || pageName eq '/signing'}">
	
		<!-- JS BS Stepper -->
		<script type="text/javascript" src="/ui/static/js/plugins/bs-stepper/bs-stepper.min.js"></script>
		
		<!-- Signature pad -->
		<script type="text/javascript" src="/ui/static/js/plugins/signature_pad/signature_pad.umd.js"></script>
	</c:if>
		
	<c:if test="${fn:startsWith(pageName, '/signing') || fn:startsWith(pageName, '/shares') || fn:startsWith(pageName, '/expenses') || fn:startsWith(pageName, '/users') || fn:startsWith(pageName,'/projects') || fn:startsWith(pageName,'/holidays/admin')}">
		<!-- JS Select2 -->
		<script type="text/javascript" src="/webjars/select2/4.0.12/js/select2.full.min.js"></script>
	</c:if>
	
	<c:if test="${fn:startsWith(pageName, '/expenses') || fn:startsWith(pageName, '/signing') || fn:startsWith(pageName, '/shares') || fn:startsWith(pageName,'/projects') || fn:startsWith(pageName,'/users') || fn:startsWith(pageName,'/roles') || fn:startsWith(pageName,'/admin') || fn:startsWith(pageName,'/countries') || fn:startsWith(pageName,'/activity-centers') }">
		<!-- JS Datatables -->
		<script type="text/javascript" src="/webjars/datatables/1.10.20/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="/webjars/datatables/1.10.20/js/dataTables.bootstrap4.min.js"></script>
		<script type="text/javascript" src="/webjars/datatables-responsive/2.2.3/js/dataTables.responsive.js"></script>
		<script type="text/javascript" src="/webjars/datatables-responsive/2.2.3/js/responsive.bootstrap4.js"></script>
		<script type="text/javascript" src="/ui/static/js/datatables/datatables-utils.js"></script>
	</c:if>
		
	<c:if test="${fn:startsWith(pageName,'/projects/') || fn:startsWith(pageName,'/users/') || fn:startsWith(pageName, '/admin/families')}">
		<!-- JS Bootstrap Select -->
		<script type="text/javascript" src="/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
	</c:if>
	
	<c:if test="${fn:startsWith(pageName,'/projects/') || pageName eq '/shares/intervention' || pageName eq '/shares/work'}">
		<!-- JS Lightbox -->
		<script type="text/javascript" src="/webjars/lightbox2/2.11.1/dist/js/lightbox.min.js"></script>
	</c:if>
	
	<!-- JS Bootstrap notify -->
	<script type="text/javascript" src="/webjars/bootstrap-notify/3.1.3/bootstrap-notify.min.js"></script>
	
	<!-- JS Moment -->
	<script type="text/javascript" src="/webjars/momentjs/2.24.0/min/moment.min.js"></script>
	<script type="text/javascript" src="/webjars/momentjs/2.24.0/min/moment-with-locales.min.js"></script>
		
	<!-- JS Custom -->
	<script type="text/javascript" src="/ui/static/js/elaadmin.js"></script>
	<script type="text/javascript" src="/ui/static/js/script.js"></script>
	
	<!-- JS PageName -->
	<script type="text/javascript" src="/ui/static/js/pages${pageName}.js"></script>
</head>

<body>
	<tiles:insertAttribute name="sidebar" />
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
</body>

<tiles:insertAttribute name="footer" />

<div class="loading style-2"><div class="loading-wheel"></div></div>

</html>