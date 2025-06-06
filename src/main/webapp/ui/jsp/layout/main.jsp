<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="${locale}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><spring:message code="site.title" /></title>

        <link rel="icon" type="image/png" href="/ui/static/images/favicon.ico">

        <link rel="stylesheet" href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/all.min.css">

        <jsp:include page="../loading/header/${loadingPath}.jsp" />

        <link rel="stylesheet" href="/ui/static/css/elaadmin.css?v=<%= System.currentTimeMillis() %>">
        <link rel="stylesheet" href="/ui/static/css/style.css?v=<%= System.currentTimeMillis() %>">
        <link rel="stylesheet" href="/ui/static/css/pages/${loadingPath}/${type}.css?v=<%= System.currentTimeMillis() %>">

        <script type="text/javascript" src="/webjars/jquery/3.4.1/jquery.min.js"></script>
    </head>

    <body>
        <aside id="left-panel" class="left-panel">
            <tiles:insertAttribute name="sidebar" />
        </aside>

        <div id="right-panel" class="right-panel">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="body" />
        </div>

        <script>
            let locale = '${locale}';
        </script>

        <script type="text/javascript" src="/webjars/matchHeight/dist/jquery.matchHeight-min.js"></script>
        <script type="text/javascript" src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
        <script type="text/javascript" src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/webjars/axios/1.6.8/dist/axios.min.js"></script>
        <script type="text/javascript" src="/webjars/bootstrap-notify/3.1.3/bootstrap-notify.min.js"></script>
        <script type="text/javascript" src="/webjars/momentjs/2.24.0/min/moment.min.js"></script>
        <script type="text/javascript" src="/webjars/momentjs/2.24.0/min/moment-with-locales.min.js"></script>

        <jsp:include page="../loading/footer/${loadingPath}.jsp" />

        <script type="text/javascript" src="/ui/static/js/elaadmin.js?v=<%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="/ui/static/js/script.js?v=<%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="/ui/static/js/pages/${loadingPath}/${type}.js?v=<%= System.currentTimeMillis() %>"></script>
    </body>
</html>