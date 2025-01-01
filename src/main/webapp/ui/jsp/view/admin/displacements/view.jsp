<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #filterForm {
        top: 100% !important;
        left: auto !important;
        right: 0;
        min-width: 20rem;
    }
</style>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="displacements.title" /></h1>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="page-header float-right">
                    <div class="dropdown d-inline-block">
                        <button type="button" class="btn btn-outline-secondary btn-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <spring:message code="filters" />
                        </button>

                        <form id="filterForm" class="dropdown-menu dropdown-menu-right filter-dropdown p-4">
                            <div class="form-group">
                                <label for="filterIdInput">Id</label>
                                <input type="text" class="form-control" id="filterIdInput" placeholder="Id">
                            </div>
                            <div class="form-group">
                                <label for="filterNameInput"><spring:message code="displacements.table.title" /></label>
                                <input type="text" class="form-control" id="filterNameInput" placeholder="<spring:message code="displacements.table.title" />">
                            </div>
                            <div class="form-group">
                                <label for="filterActivityCenterInput" class="col-form-label"><spring:message code="displacements.table.activity.center" /></label>
                                <select id="filterActivityCenterInput" name="activityCenterId" class="form-control">
                                    <option value="" selected="selected">
                                        <spring:message code="displacements.activity.center.placeholder" />
                                    </option>
                                    <c:forEach items="${activityCenters}" var="activityCenter">
                                        <option value="${activityCenter.id}">
                                            <spring:message code="${activityCenter.name}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="filterTypeInput" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label>
                                <select id="filterTypeInput" name="type" class="form-control">
                                    <option value="" selected="selected">
                                        <spring:message code="displacements.table.displacement.type" />
                                    </option>
                                    <option value="VEHICLE"><spring:message code="displacements.type.0" /></option>
                                    <option value="PUBLIC_TRANSPORT"><spring:message code="displacements.type.1" /></option>
                                </select>
                            </div>
                            <div class="d-flex justify-content-between">
                                <button id="resetFilterBtn" type="button" onclick="filter(true)" class="btn btn-outline-secondary btn-sm"><spring:message code="reset" /></button>
                                <button id="submitFilterBtn" type="button" onclick="filter()" class="btn btn-default"><spring:message code="filter" /></button>
                            </div>
                        </form>
                    </div>
                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#createModal">
                        <spring:message code="create" />
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
                    <div class="title mb-0">
                        <spring:message code="displacements.title" />
                    </div>

                    <div class="table-responsive">
                        <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                            <thead>
                                <tr>
                                    <th><spring:message code="displacements.table.id" /></th>
                                    <th><spring:message code="displacements.table.title" /></th>
                                    <th><spring:message code="displacements.table.activity.center" /></th>
                                    <th><spring:message code="displacements.table.displacement.type" /></th>
                                    <th><spring:message code="displacements.table.total.time" /></th>
                                    <th><spring:message code="displacements.table.actions" /></th>
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
                    <h5><spring:message code="displacements.create.btn" /></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="createForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="createName" class="col-form-label"><spring:message code="displacements.table.title" /></label>
                                <input id="createName" name="name" type="text" class="form-control" required />
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="createActivityCenter" class="col-form-label"><spring:message code="displacements.table.activity.center" /></label>
                                <select id="createActivityCenter" name="activityCenterId" class="form-control" required>
                                    <option disabled value="" selected="selected">
                                        <spring:message code="displacements.activity.center.placeholder" />
                                    </option>
                                    <c:forEach items="${activityCenters}" var="activityCenter">
                                        <option value="${activityCenter.id}">
                                            <spring:message code="${activityCenter.name}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="createType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label>
                                <select id="createType" name="type" class="form-control">
                                    <option value="" selected="selected">
                                        <spring:message code="displacements.table.displacement.type" />
                                    </option>
                                    <option value="VEHICLE"><spring:message code="displacements.type.0" /></option>
                                    <option value="PUBLIC_TRANSPORT"><spring:message code="displacements.type.1" /></option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="createTotalTime" class="col-form-label"><spring:message code="displacements.table.total.time" /></label>
                                <input type="time" class="form-control" id="createTotalTime" name="totalTime" required>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close" />
                        </button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-default btn-sm" onclick="create()">
                            <spring:message code="create" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="displacements.create.btn" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="editForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="editName" class="col-form-label"><spring:message code="displacements.table.title" /></label>
                                <input id="editName" name="name" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="editActivityCenter" class="col-form-label"><spring:message code="displacements.table.activity.center" /></label>
                                <select id="editActivityCenter" name="activityCenterId" class="form-control" required>
                                    <c:forEach items="${activityCenters}" var="activityCenter">
                                        <option value="${activityCenter.id}">
                                            <spring:message code="${activityCenter.name}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="editType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label>
                                <select id="editType" name="type" class="form-control">
                                    <option value="" selected="selected">
                                        <spring:message code="displacements.table.displacement.type" />
                                    </option>
                                    <option value="VEHICLE"><spring:message code="displacements.type.0" /></option>
                                    <option value="PUBLIC_TRANSPORT"><spring:message code="displacements.type.1" /></option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="editTotalTime" class="col-form-label"><spring:message code="displacements.table.total.time" /></label>
                                <input type="time" class="form-control" id="editTotalTime" name="totalTime" required>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close" />
                        </button>
                    </div>
                    <div class="float-right">
                        <button id="saveBtn" type="button" class="btn btn-default btn-sm">
                            <spring:message code="save" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    let locale = '${locale}';

    $(document).ready(function() {

        let columns = ['id', 'name', 'activityCenter.name', 'type', 'totalTime', 'id']
        let endpoint = '/v1/displacements';
        let actions = [ { action: 'edit', permission: 'edit_displacements' }, { action: 'delete', permission: 'edit_displacements' }]
        let expand = [ 'activityCenter' ]
        let filters = []
        let orderable = [[0, 'ASC']]
        let columnsDef = [
            {
                targets: 3,
                render: function(type) {
                    return formatType(type);
                }
            },
            {
                targets: 4,
                render: function(totalTime) {
                    return minutesToTime(totalTime);
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnsDef);
        createDataTable('#dTable', customDataTable, locale);
    });

</script>