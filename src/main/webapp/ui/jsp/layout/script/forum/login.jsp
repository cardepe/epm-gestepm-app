<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
</head>

<body>

   	<form id="submitForm" method="POST" action="${forumUrl}/ucp.php?mode=login">
   		<input type="hidden" name="username" value="${username}" />
   		<input type="hidden" name="password" value="${password}" />
   		<input type="hidden" name="login" value="Identificarse" />
   		<input type="hidden" name="redirect" value="./index.php?" />
   		<input type="hidden" name="creation_time" value="${creationTime}" />
   		<input type="hidden" name="form_token" value="${formToken}" />
   	</form>

	<!-- JS Libs -->
    <script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
    
    <script>
		$(document).ready(function() {
			$('#submitForm').submit();
		});
    </script>
</body>

</html>