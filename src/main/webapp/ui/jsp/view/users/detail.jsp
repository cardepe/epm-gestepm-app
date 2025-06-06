<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="userTabs" value="${fn:split('signings,expenses,projects,holidays', ',')}" />

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/css/bootstrap-select.min.css">

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="user.detail"/></h1>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-2">
                <div class="page-header float-right">
                    <a id="returnBtn" class="btn btn-default btn-sm"><spring:message code="back"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="row">
        <div class="col-12 col-sm">
            <a href="/users/${currentUser.id}" class="btn btn-tab ${ tab == 'info' ? 'btn-tab-current' : ''} btn-block mb-2"><spring:message code="info"/></a>
        </div>

        <c:forEach var="tabItem" items="${userTabs}">
            <div class="col-12 col-sm">
                <a href="/users/${currentUser.id}/${tabItem}" class="btn btn-tab ${ tab == tabItem ? 'btn-tab-current' : ''} btn-block mb-2"><spring:message code="${tabItem}"/></a>
            </div>
        </c:forEach>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="title mb-0 d-flex justify-content-between align-items-center">
                <spring:message code="${tab}" />
            </div>

            <jsp:include page="tabs/${tab}.jsp" />
        </div>
    </div>

    <c:if test="${tab == 'info'}">
        <div class="card">
            <div class="card-body">
                <div class="title mb-0 d-flex justify-content-between align-items-center">
                    <spring:message code="forum.config" />
                </div>

                <jsp:include page="tabs/forum.jsp" />
            </div>
        </div>
    </c:if>
</div>

<script>

    let locale = '${locale}';
    let returnBtn = $('#returnBtn');

    const endpoint = '/v1/users/${currentUser.id}';

    $(document).ready(function() {
        setReturnButtonUrl();
    });

    function setReturnButtonUrl() {
        let lastPageUrl = '/users';

        if (document.referrer) {
            const lastPagePath = new URL(document.referrer).pathname;

            if (/^\/users\/\d+(\/[a-z]+)?$/.test(lastPagePath)) {
                lastPageUrl = sessionStorage.getItem('usersFilter');
            } else {
                const queryParams = document.referrer.split('?')[1];
                lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');
                sessionStorage.setItem('usersFilter', lastPageUrl);
            }
        }

        returnBtn.attr('href', lastPageUrl);
    }

</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/static/js/select2/select2-utils.js?v=<%= System.currentTimeMillis() %>"></script>
