<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/ui/static/css/pe-icon-7-stroke.min.css" rel="stylesheet" />

<style>
    .content {
        height: auto;
    }

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
                        <h1 class="text-uppercase"><spring:message code="personal.expenses.sheets" /></h1>
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
                                <label for="filterNameInput"><spring:message code="activity.centers.table.name" /></label>
                                <input type="text" class="form-control" id="filterNameInput" placeholder="<spring:message code="activity.centers.table.name" />">
                            </div>
                            <div class="form-group">
                                <label for="filterCountryInput" class="col-form-label"><spring:message code="activity.centers.table.country" /></label>
                                <select id="filterCountryInput" name="countryId" class="form-control">
                                    <option value="" selected="selected">
                                        <spring:message code="activity.centers.country.placeholder" />
                                    </option>
                                    <c:forEach items="${countries}" var="country">
                                        <option value="${country.id}">
                                            <spring:message code="${country.name}" />
                                        </option>
                                    </c:forEach>
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
    <div class="row">
        <div class="col-xs-12 col">
            <div class="card">
                <div class="card-body">
                    <div class="stat-widget-five">
                        <div class="stat-icon dib flat-color-3">
                            <i class="pe-7s-clock"></i>
                        </div>
                        <div class="stat-content">
                            <div class="text-left dib">
                                <div class="stat-text"><span class="count">100</span>€</div>
                                <div class="stat-heading">pendientes en 4 hojas</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col">
            <div class="card">
                <div class="card-body">
                    <div class="stat-widget-five">
                        <div class="stat-icon dib flat-color-1">
                            <i class="pe-7s-check"></i>
                        </div>
                        <div class="stat-content">
                            <div class="text-left dib">
                                <div class="stat-text"><span class="count">50</span>€</div>
                                <div class="stat-heading">aprobados en 1 hoja</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col">
            <div class="card">
                <div class="card-body">
                    <div class="stat-widget-five">
                        <div class="stat-icon dib flat-color-5">
                            <i class="pe-7s-cash"></i>
                        </div>
                        <div class="stat-content">
                            <div class="text-left dib">
                                <div class="stat-text"><span class="count">75</span>€</div>
                                <div class="stat-heading">pagados en 7 hojas</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col">
            <div class="card">
                <div class="card-body">
                    <div class="stat-widget-five">
                        <div class="stat-icon dib flat-color-4">
                            <i class="pe-7s-close-circle"></i>
                        </div>
                        <div class="stat-content">
                            <div class="text-left dib">
                                <div class="stat-text"><span class="count">15</span>€</div>
                                <div class="stat-heading">denegados en 1 hoja</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col">
            <div class="card">
                <div class="card-body">
                    <div class="stat-widget-five">
                        <div class="stat-icon dib flat-color-6">
                            <i class="pe-7s-cash"></i>
                        </div>
                        <div class="stat-content">
                            <div class="text-left dib">
                                <div class="stat-text"><span class="count">150</span>€</div>
                                <div class="stat-heading">pendientes de cobrar</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row h-100">
        <div class="col h-100">
            <div class="card">
                <div class="card-body">
                    <div class="title mb-0">
                        <spring:message code="personal.expenses.sheets" />
                    </div>

                    <div class="table-responsive">
                        <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                            <thead>
                                <tr>
                                    <th><spring:message code="id" /></th>
                                    <th><spring:message code="description" /></th>
                                    <th><spring:message code="project" /></th>
                                    <th><spring:message code="status" /></th>
                                    <th><spring:message code="date" /></th>
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

<script>

    let locale = '${locale}';
    let $ = jQuery.noConflict();

    $(document).ready(async function () {

        let columns = ['id', 'description', 'project.name', 'status', 'startDate', 'id']
        let endpoint = '/v1/expenses/personal/sheets';
        let actions = [
            { action: 'file-pdf', url: '/v1/expenses/personal/sheets/{id}/export', permission: 'edit_personal_expenses_sheet'},
            { action: 'edit', permission: 'edit_personal_expenses_sheet' },
            { action: 'delete', permission: 'edit_personal_expenses_sheet' }]
        let expand = [ 'country' ]
        let filters = []

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters);
        createDataTable('#dTable', customDataTable, locale);
    });

</script>