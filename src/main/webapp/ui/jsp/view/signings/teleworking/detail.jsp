<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>

    .locationFrame {
        width: 100%;
        height: 450px;
        border: 0;
    }

</style>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/css/bootstrap-select.min.css">

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="signing.teleworking"/></h1>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-2">
                <div class="page-header float-right">
                    <a class="btn btn-default btn-sm"
                       href="${pageContext.request.contextPath}/signings/teleworking"><spring:message
                            code="back"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="card">
        <div class="card-body">
            <div class="title mb-0">
                <spring:message code="info"/>
            </div>

            <form id="editForm" class="needs-validation">
                <div class="row">
                    <div class="col-sm-12 col-md-4">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="project"/>
                                <input type="text" class="form-control mt-1" value="${projectName}" disabled />
                            </label>
                        </div>
                    </div>

                    <div class="col-sm-12 col-md-4">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="start.date"/>
                                <input type="datetime-local" class="form-control mt-1" value="${teleworkingSigning.startedAt}" disabled />
                            </label>
                        </div>
                    </div>

                    <div class="col-sm-12 col-md-4">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="end.date"/>
                                <input type="datetime-local" class="form-control mt-1" value="${teleworkingSigning.closedAt}" disabled />
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12 col-md-6">
                        <label class="col-form-label w-100"><spring:message code="signing.geolocation.start"/>
                            <iframe
                                    class="locationFrame"
                                    loading="lazy"
                                    allowfullscreen
                                    referrerpolicy="no-referrer-when-downgrade"
                                    src="https://www.google.com/maps?q=${teleworkingSigning.startedLocation}&output=embed">
                            </iframe>
                        </label>
                    </div>
                    <div class="col-sm-12 col-md-6">
                        <label class="col-form-label w-100"><spring:message code="signing.geolocation.end"/>
                            <iframe
                                    class="locationFrame"
                                    loading="lazy"
                                    allowfullscreen
                                    referrerpolicy="no-referrer-when-downgrade"
                                    src="https://www.google.com/maps?q=${teleworkingSigning.closedLocation}&output=embed">
                            </iframe>
                        </label>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>