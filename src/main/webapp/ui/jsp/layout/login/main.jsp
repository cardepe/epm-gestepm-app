<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="site.title" /></title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="/ui/static/images/favicon.ico">
    
    <!-- CSS Libs -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/all.min.css">
	
	<!-- Custom styles for this template -->
	<link href="/ui/static/css/pages/login.css" rel="stylesheet">
</head>

<body>
	<tiles:insertAttribute name="body" />
	
	<!-- JS Libs -->
    <script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	
	<script src="/ui/static/js/pages/login.js?v=<%= System.currentTimeMillis() %>"></script>
</body>

</html>