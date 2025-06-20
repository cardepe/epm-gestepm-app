<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="projects"/></h1>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="page-header float-right">
                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#createModal">
                        <spring:message code="create"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="row h-100">
        <div class="col h-100">
            <div class="card">
                <div class="card-body">
                    <div class="title mb-0 d-flex justify-content-between align-items-center">
                        <div>
                            <spring:message code="projects" />
                        </div>
                        <div class="dropdown">
                            <button type="button" class="btn btn-outline-secondary btn-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <spring:message code="filters" />
                            </button>

                            <form id="filterForm" class="dropdown-menu dropdown-menu-right filter-dropdown p-4">
                                <div class="form-group">
                                    <label class="w-100">
                                        <spring:message code="id" />
                                        <input type="number" name="id" class="form-control" placeholder="Id">
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100">
                                        <spring:message code="name" />
                                        <input type="text" name="nameContains" class="form-control" placeholder="<spring:message code="name" />">
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100 bootstrap-select">
                                        <spring:message code="station" />
                                        <select name="isStation" class="form-control select2">
                                            <option value=""><spring:message code="select.placeholder" /></option>
                                            <option value="1"><spring:message code="yes" /></option>
                                            <option value="0"><spring:message code="no" /></option>
                                        </select>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100 bootstrap-select">
                                        <spring:message code="activity.center" />
                                        <select name="activityCenterId" class="form-control select2">
                                            <option value=""><spring:message code="select.placeholder" /></option>
                                            <c:forEach items="${activityCenters}" var="activityCenter">
                                                <option value="${activityCenter.id}"><spring:message code="${activityCenter.name}" /></option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100 bootstrap-select">
                                        <spring:message code="signing.teleworking" />
                                        <select name="isTeleworking" class="form-control select2">
                                            <option value=""><spring:message code="select.placeholder" /></option>
                                            <option value="1"><spring:message code="yes" /></option>
                                            <option value="0"><spring:message code="no" /></option>
                                        </select>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100 bootstrap-select">
                                        <spring:message code="responsible" />
                                        <select class="form-control select2" data-control="select2"
                                                name="responsibleId"></select>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="w-100 bootstrap-select">
                                        <spring:message code="status" />
                                        <select name="state" class="form-control select2">
                                            <option value=""><spring:message code="select.placeholder" /></option>
                                            <option value="0"><spring:message code="disabled" /></option>
                                            <option value="1"><spring:message code="enabled" /></option>
                                        </select>
                                    </label>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <button type="button" onclick="filter(true)" class="btn btn-outline-secondary btn-sm"><spring:message code="reset" /></button>
                                    <button type="button" onclick="filter()" class="btn btn-default"><spring:message code="filter" /></button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                            <thead>
                            <tr>
                                <th><spring:message code="id" /></th>
                                <th><spring:message code="name" /></th>
                                <th><spring:message code="activity.center" /></th>
                                <th><spring:message code="start.date" /></th>
                                <th><spring:message code="end.date" /></th>
                                <th><spring:message code="actions" /></th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="projects.create.btn"/></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="createForm">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="name"/>
                                    <input type="text" name="name" class="form-control" required/>
                                </label>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="station" />
                                    <select name="isStation" class="form-control select2 stationSelect" required>
                                        <option value=""><spring:message code="select.placeholder" /></option>
                                        <option value="true"><spring:message code="yes" /></option>
                                        <option value="false"><spring:message code="no" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="activity.center"/>
                                    <select class="form-control select2" data-control="select2" name="activityCenterId" required></select>
                                </label>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="project.detail.project.objective.cost"/>
                                    <input type="number" name="objectiveCost" class="form-control" required/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="start.date" />
                                    <input name="startDate" type="date" class="form-control" required>
                                </label>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="end.date" />
                                    <input name="endDate" type="date" class="form-control" required>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="projects.table.forum"/>
                                    <select name="forumId" class="form-control select2">
                                        <option value=""><spring:message code="select.placeholder" /></option>
                                        <c:forEach items="${forums}" var="forum">
                                            <option value="${forum.id}"><spring:message code="[${forum.id}] ${forum.name}" /></option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="signing.teleworking" />
                                    <select name="isTeleworking" class="form-control select2" required>
                                        <option value=""><spring:message code="select.placeholder" /></option>
                                        <option value="true"><spring:message code="yes" /></option>
                                        <option value="false"><spring:message code="no" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="responsible"/>
                                    <select class="form-control select2" data-control="select2" name="responsibleIds" multiple required></select>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close"/>
                        </button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-default btn-sm" onclick="create()">
                            <spring:message code="create"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
