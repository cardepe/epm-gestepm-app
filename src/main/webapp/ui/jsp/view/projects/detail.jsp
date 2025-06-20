<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.datatype.jsr310.JavaTimeModule" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    final String currentUser = mapper.writeValueAsString(request.getAttribute("currentUser"));
    final String currentProject = mapper.writeValueAsString(request.getAttribute("currentProject"));
%>

<c:set var="projectTabs" value="${fn:split('expenses,signings,leaders,members,required-materials,families', ',')}" />

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="project.detail"/></h1>
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
            <a href="/projects/${currentProject.id}" class="btn btn-tab ${ tab == 'info' ? 'btn-tab-current' : ''} btn-block mb-2"><spring:message code="info"/></a>
        </div>

        <c:forEach var="tabItem" items="${projectTabs}">
            <div class="col-12 col-sm">
                <a href="/projects/${currentProject.id}/${tabItem}" class="btn btn-tab ${ tab == tabItem ? 'btn-tab-current' : ''} btn-block mb-2"><spring:message code="${tabItem}"/></a>
            </div>
        </c:forEach>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col">
                    <div class="title mb-0 d-flex justify-content-between align-items-center">
                        <spring:message code="${tab}" />
                    </div>
                </div>
                <c:if test="${tab == 'leaders'}">
                    <div class="col text-right">
                        <button type="button" class="btn btn-standard btn-sm mr-1" data-toggle="modal" data-target="#createModal"><spring:message code="add" /></button>
                    </div>
                </c:if>
            </div>

            <jsp:include page="tabs/${tab}.jsp" />
        </div>
    </div>

    <c:if test="${tab == 'info'}">
        <div class="card">
            <div class="card-body">
                <div class="title mb-0 d-flex justify-content-between align-items-center">
                    <spring:message code="project.detail.customer" />
                </div>

                <jsp:include page="tabs/customer.jsp" />
            </div>
        </div>
    </c:if>
</div>

<script>
    const endpoint = '/v1/projects/${currentProject.id}';
    const currentProject = <%= currentProject %>;
    const currentUser = <%= currentUser %>;

    let returnBtn = $('#returnBtn');

    $(document).ready(function() {
        setReturnButtonUrl();
    });

    function setReturnButtonUrl() {
        let lastPageUrl = '/projects';

        if (document.referrer) {
            const lastPagePath = new URL(document.referrer).pathname;

            if (/^\/projects\/\d+(\/[a-z]+)?$/.test(lastPagePath)) {
                lastPageUrl = sessionStorage.getItem('projectsFilter');
            } else {
                const queryParams = document.referrer.split('?')[1];
                lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');
                sessionStorage.setItem('projectsFilter', lastPageUrl);
            }
        }

        returnBtn.attr('href', lastPageUrl);
    }

</script>
