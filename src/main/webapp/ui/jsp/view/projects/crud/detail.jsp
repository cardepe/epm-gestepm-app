<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>

    .filter-option-inner-inner {
        color: #495057;
    }

    .form-control[readonly],
    .form-control:disabled {
        background-color: #fff !important;
        cursor: not-allowed;
    }

    #saveProjectBtn,
    #saveCustomerBtn {
        display: none;
    }

</style>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="projects.detail.title"
                                                                   arguments="${ project.name }"/></h1>
                    </div>
                </div>
            </div>
            <div class="col-2">
                <div class="float-right calendarBtns">
                    <a id="returnBtn" href="${pageContext.request.contextPath}/projects"
                       class="btn btn-standard btn-sm">
                        <span class="fc-icon fc-icon-chevron-left"></span> <spring:message code="back"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="card">
        <div class="card-body p-0">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12 col-md-3">
                        <div class="nav flex-column nav-pills p-4" id="v-pills-tab" role="tablist"
                             aria-orientation="vertical">
                            <a class="nav-link" id="v-pills-customer-tab" data-toggle="pill" href="#v-pills-customer"
                               role="tab" aria-controls="v-pills-customer" aria-selected="false"><spring:message
                                    code="project.detail.customer"/></a>
                            <a class="nav-link" id="v-pills-displacements-tab" data-toggle="pill"
                               href="#v-pills-displacements" role="tab" aria-controls="v-pills-displacements"
                               aria-selected="false"><spring:message code="project.detail.displacements"/></a>
                            <a class="nav-link" id="v-pills-equipments-tab" data-toggle="pill"
                               href="#v-pills-equipments" role="tab" aria-controls="v-pills-equipments"
                               aria-selected="false"><spring:message code="project.detail.equipments"/></a>
                            <a class="nav-link" id="v-pills-signings-tab" data-toggle="pill" href="#v-pills-signings"
                               role="tab" aria-controls="v-pills-signings" aria-selected="false"><spring:message
                                    code="project.detail.signings"/></a>
                            <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages"
                               role="tab" aria-controls="v-pills-messages" aria-selected="false"><spring:message
                                    code="project.detail.expends"/></a>
                            <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home"
                               role="tab" aria-controls="v-pills-home" aria-selected="true"><spring:message
                                    code="project.detail.info"/></a>
                            <a class="nav-link" id="v-pills-bosses-tab" data-toggle="pill" href="#v-pills-bosses"
                               role="tab" aria-controls="v-pills-bosses" aria-selected="false"><spring:message
                                    code="project.detail.bosses.pill"/></a>
                            <a class="nav-link" id="v-pills-materials-required-tab" data-toggle="pill"
                               href="#v-pills-materials-required" role="tab" aria-controls="v-pills-materials-required"
                               aria-selected="false"><spring:message code="project.detail.materials.required"/></a>
                            <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile"
                               role="tab" aria-controls="v-pills-profile" aria-selected="false"><spring:message
                                    code="project.detail.users"/></a>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-9 bg-light">
                        <div class="tab-content" id="v-pills-tabContent">

                            <div class="tab-pane fade" id="v-pills-customer" role="tabpanel"
                                 aria-labelledby="v-pills-customer-tab">
                                <form id="customerForm" class="needs-validation">
                                    <div class="row">
                                        <div class="col">
                                            <div class="title mb-4 pb-2">
                                                <spring:message code="project.detail.customer"/>
                                            </div>
                                        </div>

                                        <div class="col text-right">
                                            <button id="saveCustomerBtn" type="button" class="btn btn-accent btn-sm">
                                                <spring:message code="save"/></button>
                                            <button id="editCustomerBtn" type="button" class="btn btn-standard btn-sm">
                                                <spring:message code="edit"/></button>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="customerName" class="col-form-label">
                                                    <spring:message code="project.detail.customer.name"/>
                                                </label>
                                                <input id="customerName" name="customerName" type="text"
                                                       class="form-control" value="${customer.name}" required readonly/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="mainEmail" class="col-form-label">
                                                    <spring:message code="project.detail.customer.email.1"/>
                                                </label>
                                                <input id="mainEmail" name="mainEmail" type="email" class="form-control"
                                                       value="${customer.mainEmail}" readonly/>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="secondaryEmail" class="col-form-label">
                                                    <spring:message code="project.detail.customer.email.2"/>
                                                </label>
                                                <input id="secondaryEmail" name="secondaryEmail" type="email"
                                                       class="form-control" value="${customer.secondaryEmail}"
                                                       readonly/>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="tab-pane fade" id="v-pills-displacements" role="tabpanel"
                                 aria-labelledby="v-pills-displacements-tab">

                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.displacements"/>
                                        </div>
                                    </div>

                                    <div class="col text-right">
                                        <button type="button" class="btn btn-standard btn-sm" data-toggle="modal"
                                                data-target="#addDispModal"><spring:message
                                                code="project.detail.displacements.create"/></button>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableDisplacements"
                                           class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="thDispId"><spring:message code="displacements.table.id"/></th>
                                            <th id="thDispTitle"><spring:message code="displacements.table.title"/></th>
                                            <th id="thDispActivityCenter"><spring:message
                                                    code="displacements.table.activity.center"/></th>
                                            <th id="thDispType"><spring:message
                                                    code="displacements.table.displacement.type"/></th>
                                            <th id="thDispTotalTime"><spring:message
                                                    code="displacements.table.total.time"/></th>
                                            <th id="thDispActions"><spring:message
                                                    code="displacements.table.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="v-pills-equipments" role="tabpanel"
                                 aria-labelledby="v-pills-equipments-tab">

                                <div class="row">
                                    <div class="col-4">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.equipments"/>
                                        </div>
                                    </div>

                                    <div class="col-8 text-right">
                                        <input type="file" id="importFamiliesInput" class="d-none"
                                               onchange="uploadFamiliesFile()"
                                               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                                        <button type="button" class="btn btn-warning btn-sm text-white"
                                                onclick="importFamiliesFile()"><em class="fas fa-file-upload"></em>
                                            <spring:message code="project.detail.equipments.import.excel"/></button>
                                        <a href="/projects/${project.id}/families/export" target="_blank"
                                           class="btn btn-warning btn-sm text-white"><em
                                                class="fas fa-file-download"></em> <spring:message
                                                code="project.detail.equipments.export.excel"/></a>
                                        <button type="button" class="btn btn-standard btn-sm" data-toggle="modal"
                                                data-target="#createEquipmentModal"><spring:message
                                                code="project.detail.equipments.create"/></button>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableEquipments"
                                           class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="thId"><spring:message code="project.detail.equipments.id"/></th>
                                            <th id="thNameES"><spring:message
                                                    code="project.detail.equipments.name.es"/></th>
                                            <th id="thNameFR"><spring:message
                                                    code="project.detail.equipments.name.fr"/></th>
                                            <th id="thBrand"><spring:message
                                                    code="project.detail.equipments.brand"/></th>
                                            <th id="thModel"><spring:message
                                                    code="project.detail.equipments.model"/></th>
                                            <th id="thEnrollment"><spring:message
                                                    code="project.detail.equipments.enrollment"/></th>
                                            <th id="actions"><spring:message
                                                    code="project.detail.equipments.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="v-pills-signings" role="tabpanel"
                                 aria-labelledby="v-pills-signings-tab">

                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.signings"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableSignings"
                                           class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="id"><spring:message code="project.detail.signings.id"/></th>
                                            <th id="username"><spring:message code="project.detail.signings.name"/></th>
                                            <th id="startDate"><spring:message
                                                    code="project.detail.signings.start.date"/></th>
                                            <th id="endDate"><spring:message
                                                    code="project.detail.signings.end.date"/></th>
                                            <th id="shareType"><spring:message
                                                    code="shares.intervention.table.type"/></th>
                                            <th id="actions" class="all"><spring:message
                                                    code="project.detail.signings.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="v-pills-messages" role="tabpanel"
                                 aria-labelledby="v-pills-messages-tab">
                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.expends"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableExpenses" class="table table-striped table-borderer dataTable w-100">
                                        <thead>
                                            <tr>
                                                <th><spring:message code="id"/></th>
                                                <th><spring:message code="description"/></th>
                                                <th><spring:message code="project"/></th>
                                                <th><spring:message code="status"/></th>
                                                <th><spring:message code="date"/></th>
                                                <th><spring:message code="amount"/></th>
                                                <th><spring:message code="actions"/></th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel"
                                 aria-labelledby="v-pills-home-tab">
                                <form id="projectInfoForm" class="needs-validation">
                                    <div class="row">
                                        <div class="col">
                                            <div class="title mb-4 pb-2">
                                                <spring:message code="project.detail.info"/>
                                            </div>
                                        </div>

                                        <div class="col text-right">
                                            <button id="saveProjectBtn" type="button" class="btn btn-accent btn-sm">
                                                <spring:message code="save"/></button>
                                            <button id="editProjectBtn" type="button" class="btn btn-standard btn-sm">
                                                <spring:message code="edit"/></button>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="projectNameInfoInput" class="col-form-label">
                                                    <spring:message code="project.detail.project.name"/>
                                                </label>
                                                <input id="projectNameInfoInput" name="projectName" type="text"
                                                       class="form-control" value="${project.name}" required readonly/>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <label for="activityCenterInfoInput" class="col-form-label"><spring:message
                                                    code="displacements.table.activity.center"/></label>

                                            <select id="activityCenterInfoInput" name="activityCenter"
                                                    class="form-control" required disabled>
                                                <c:forEach items="${activityCenters}" var="activityCenter">
                                                    <option value="${activityCenter.id}"
                                                            <c:if test="${activityCenter.id == project.activityCenter.id}">selected</c:if>>
                                                        <spring:message code="${activityCenter.name}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="responsableInfoInput" class="col-form-label">
                                                    <spring:message code="project.detail.project.responsable"/>
                                                </label>

                                                <!-- 												<select id="responsableInfoInput" name="responsable" class="form-control" required disabled> -->
                                                <%-- 													<c:forEach items="${teamLeaders}" var="teamLeader"> --%>
                                                <%-- 														<option value="${teamLeader.userId}" <c:if test="${teamLeader.userId == project.responsable.id}">selected</c:if>> --%>
                                                <%-- 															<spring:message code="${teamLeader.name} ${teamLeader.surnames}" /> --%>
                                                <!-- 														</option> -->
                                                <%-- 													</c:forEach> --%>
                                                <!-- 												</select> -->

                                                <select id="responsableInfoInput"
                                                        class="form-control input selectpicker"
                                                        data-style="defaultUserSelectPicker" data-live-search="true"
                                                        multiple style="width: 100%" name="responsables" required
                                                        disabled>
                                                    <c:forEach items="${userDTOs}" var="userDTO">
                                                        <option value="${userDTO.userId}"
                                                                <c:if test="${jspUtil.existsInList(responsablesIds, userDTO.userId)}">selected</c:if>>
                                                                ${userDTO.name} ${userDTO.surnames}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="objectiveCostInfoInput" class="col-form-label">
                                                    <spring:message code="project.detail.project.objective.cost"/>
                                                </label>
                                                <input id="objectiveCostInfoInput" name="objectiveCost" type="text"
                                                       class="form-control" value="${project.objectiveCost}" required
                                                       readonly/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="startDateInfoInput" class="col-form-label">
                                                    <spring:message code="project.detail.project.start.date"/>
                                                </label>
                                                <input id="startDateInfoInput" name="startDate" type="date"
                                                       class="form-control"
                                                       value="<fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd" />"
                                                       required readonly/>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="objectiveDateInfoInput" class="col-form-label">
                                                    <spring:message code="project.detail.project.obj.date"/>
                                                </label>
                                                <input id="objectiveDateInfoInput" name="objectiveDate" type="date"
                                                       class="form-control"
                                                       value="<fmt:formatDate value="${project.objectiveDate}" pattern="yyyy-MM-dd" />"
                                                       required readonly/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="stationInfoInput" class="col-form-label"><spring:message
                                                        code="projects.table.station"/></label>
                                                <select id="stationInfoInput" name="station" class="form-control"
                                                        disabled>
                                                    <option value="0"
                                                            <c:if test="${project.station == 0}">selected</c:if>>
                                                        <spring:message code="projects.table.station.none"/>
                                                    </option>
                                                    <option value="1"
                                                            <c:if test="${project.station == 1}">selected</c:if>>
                                                        <spring:message code="projects.table.station.yes"/>
                                                    </option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="forumIdDropdownInfoInput"
                                                       class="col-form-label"><spring:message
                                                        code="projects.table.forum"/></label>
                                                <select id="forumIdDropdownInfoInput" name="forumId"
                                                        class="form-control" disabled>
                                                    <option value=""
                                                            <c:if test="${null == project.forumId}">selected</c:if>>
                                                        <spring:message code="project.detail.forum.empty"/>
                                                    </option>
                                                    <c:forEach items="${forumDTOs}" var="forumDTO">
                                                        <option value="${forumDTO.id}"
                                                                <c:if test="${forumDTO.id == project.forumId}">selected</c:if>>
                                                            <spring:message code="[${forumDTO.id}] ${forumDTO.name}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="tab-pane fade" id="v-pills-bosses" role="tabpanel"
                                 aria-labelledby="v-pills-bosses-tab">
                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.bosses.pill"/>
                                        </div>
                                    </div>

                                    <div class="col text-right">
                                        <button type="button" class="btn btn-standard btn-sm" data-toggle="modal"
                                                data-target="#createBossModal"><spring:message
                                                code="project.detail.bosses.create"/></button>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableBosses" class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="id"><spring:message code="project.detail.members.id"/></th>
                                            <th id="name"><spring:message code="project.detail.members.name"/></th>
                                            <th id="actions" class="all"><spring:message
                                                    code="project.detail.members.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="v-pills-materials-required" role="tabpanel"
                                 aria-labelledby="v-pills-materials-required-tab">

                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.materials.required"/>
                                        </div>
                                    </div>

                                    <div class="col text-right">
                                        <button type="button" class="btn btn-standard btn-sm" data-toggle="modal"
                                                data-target="#createMaterialRequiredModal"><spring:message
                                                code="project.detail.materials.required.create"/></button>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableMaterialsRequired"
                                           class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="thId"><spring:message
                                                    code="project.detail.table.materials.required.id"/></th>
                                            <th id="thNameES"><spring:message
                                                    code="project.detail.table.materials.required.name.es"/></th>
                                            <th id="thNameFR"><spring:message
                                                    code="project.detail.table.materials.required.name.fr"/></th>
                                            <th id="thRequired"><spring:message
                                                    code="project.detail.table.materials.required.required"/></th>
                                            <th id="actions"><spring:message
                                                    code="project.detail.table.materials.required.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>

                            <div class="tab-pane fade" id="v-pills-profile" role="tabpanel"
                                 aria-labelledby="v-pills-profile-tab">

                                <div class="row">
                                    <div class="col">
                                        <div class="title mb-4 pb-2">
                                            <spring:message code="project.detail.users"/>
                                        </div>
                                    </div>

                                    <div class="col text-right">
                                        <button type="button" class="btn btn-standard btn-sm" data-toggle="modal"
                                                data-target="#createModal"><spring:message
                                                code="project.detail.members.create"/></button>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="dTableMembers"
                                           class="table table-striped table-borderer dataTable w-100">
                                        <caption class="d-none">
                                            NO SONAR
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th id="id"><spring:message code="project.detail.members.id"/></th>
                                            <th id="name"><spring:message code="project.detail.members.name"/></th>
                                            <th id="subRole"><spring:message code="project.detail.members.role"/></th>
                                            <th id="actions"><spring:message
                                                    code="project.detail.members.actions"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- MODAL -->
<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.members.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="addMemberForm">
                    <div class="row">
                        <div class="col">
                            <select id="memberDropdown" class="form-control input selectpicker"
                                    data-style="userSelectPicker" data-live-search="true" multiple style="width: 100%"
                                    name="memberId">
                                <c:forEach items="${notMembers}" var="member">
                                    <option value="${member.userId}">
                                            ${member.name} ${member.surnames}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="addMember" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="add"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="createBossModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.bosses.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="addBossForm">
                    <div class="row">
                        <div class="col">
                            <select id="bossDropdown" class="form-control input selectpicker"
                                    data-style="userSelectPicker" data-live-search="true" multiple style="width: 100%"
                                    name="bossId">
                                <c:forEach items="${notBosses}" var="boss">
                                    <option value="${boss.userId}">
                                            ${boss.name} ${boss.surnames}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="addBoss" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="add"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:set var="localeCode" value="${pageContext.response.locale}"/>

<div id="createEquipmentModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.equipments.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="addEquipmentForm">
                    <div class="row">
                        <div class="col">
                            <label for="familyDropdown" class="col-form-label"><spring:message
                                    code="shares.intervention.create.family"/></label>
                            <select id="familyDropdown" class="form-control" name="familyId">
                                <c:forEach items="${notFamilies}" var="family">
                                    <option value="${family.id}">
                                        <c:choose>
                                            <c:when test="${localeCode == 'es'}">
                                                ${family.nameES}
                                            </c:when>
                                            <c:otherwise>
                                                ${family.nameFR}
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="familyNameESInput" class="col-form-label"><spring:message
                                        code="families.admin.create.name.esp"/></label>
                                <input id="familyNameESInput" name="nameES" type="text" class="form-control" required>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="familyNameFRInput" class="col-form-label"><spring:message
                                        code="families.admin.create.name.fra"/></label>
                                <input id="familyNameFRInput" name="nameFR" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="brandInput" class="col-form-label"><spring:message
                                        code="families.admin.create.brand"/></label>
                                <input id="brandInput" name="brand" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="modelInput" class="col-form-label"><spring:message
                                        code="families.admin.create.model"/></label>
                                <input id="modelInput" name="model" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="enrollmentInput" class="col-form-label"><spring:message
                                        code="families.admin.create.enrollment"/></label>
                                <input id="enrollmentInput" name="enrollment" type="text" class="form-control">
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="addEquipment" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="add"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /MODAL -->

<div id="editEquipmentModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.equipments.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="editEquipmentForm">
                    <div class="row">
                        <div class="col">
                            <label for="familyEditDropdown" class="col-form-label"><spring:message
                                    code="shares.intervention.create.family"/></label>
                            <select id="familyEditDropdown" class="form-control" name="familyId">
                                <c:forEach items="${notFamilies}" var="family">
                                    <option value="${family.id}">
                                        <c:choose>
                                            <c:when test="${localeCode == 'es'}">
                                                ${family.nameES}
                                            </c:when>
                                            <c:otherwise>
                                                ${family.nameFR}
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="familyNameESEditInput" class="col-form-label"><spring:message
                                        code="families.admin.create.name.esp"/></label>
                                <input id="familyNameESEditInput" name="nameES" type="text" class="form-control"
                                       required>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="familyNameFREditInput" class="col-form-label"><spring:message
                                        code="families.admin.create.name.fra"/></label>
                                <input id="familyNameFREditInput" name="nameFR" type="text" class="form-control"
                                       required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="brandEditInput" class="col-form-label"><spring:message
                                        code="families.admin.create.brand"/></label>
                                <input id="brandEditInput" name="brand" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="modelEditInput" class="col-form-label"><spring:message
                                        code="families.admin.create.model"/></label>
                                <input id="modelEditInput" name="model" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="enrollmentEditInput" class="col-form-label"><spring:message
                                        code="families.admin.create.enrollment"/></label>
                                <input id="enrollmentEditInput" name="enrollment" type="text" class="form-control">
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="editEquipment" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="edit"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /MODAL -->

<!-- MATERIAL REQUIRED MODAL -->
<div id="createMaterialRequiredModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.materials.required.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="addMaterialRequiredForm">

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="materialRequiredNameESInput" class="col-form-label"><spring:message
                                        code="project.detail.table.materials.required.name.es"/></label>
                                <input id="materialRequiredNameESInput" name="nameES" type="text" class="form-control"
                                       required>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="materialRequiredNameFRInput" class="col-form-label"><spring:message
                                        code="project.detail.table.materials.required.name.fr"/></label>
                                <input id="materialRequiredNameFRInput" name="nameFR" type="text" class="form-control"
                                       required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <div class="form-check form-check-inline">
                                    <input type="checkbox" class="form-check-input" id="materialRequiredCheckbox"
                                           name="required" style="width: 20px; height: 20px">
                                    <label class="form-check-label" for="materialRequiredCheckbox"><spring:message
                                            code="project.detail.table.materials.required.required"/></label>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="addMaterialRequired" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="add"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /MODAL -->

<!-- EDIT MATERIAL REQUIRED MODAL -->
<div id="editMaterialRequiredModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="project.detail.materials.required.create"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="editMaterialRequiredForm">

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="materialRequiredNameESEditInput" class="col-form-label"><spring:message
                                        code="project.detail.table.materials.required.name.es"/></label>
                                <input id="materialRequiredNameESEditInput" name="nameES" type="text"
                                       class="form-control" required>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="materialRequiredNameFREditInput" class="col-form-label"><spring:message
                                        code="project.detail.table.materials.required.name.fr"/></label>
                                <input id="materialRequiredNameFREditInput" name="nameFR" type="text"
                                       class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <div class="form-check form-check-inline">
                                    <input type="checkbox" class="form-check-input" id="materialRequiredEditCheckbox"
                                           name="required" style="width: 20px; height: 20px">
                                    <label class="form-check-label" for="materialRequiredEditCheckbox"><spring:message
                                            code="project.detail.table.materials.required.required"/></label>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button id="editMaterialRequired" type="submit" class="btn btn-sm btn-success"><spring:message
                                code="add"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /MODAL -->

<!-- VIEW CONST MODAL -->
<div id="viewConstModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="viewConstForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.view.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.construction.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" readonly>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.construction.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" readonly>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.construction.description"/></label>
                                <textarea id="observations" name="observations" class="form-control" rows="6"
                                          readonly></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW CONST MODAL -->

<!-- UPDATE CONST MODAL -->
<div id="updateConstModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateConstForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.update.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.construction.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.construction.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.construction.description"/></label>
                                <textarea id="observations" name="observations" class="form-control"
                                          rows="6"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="updateConstBtn" type="button" class="btn btn-sm btn-success"><spring:message
                                    code="save"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /UPDATE CONST MODAL -->

<!-- VIEW PROG MODAL -->
<div id="viewProgModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="viewProgForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.view.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.construction.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" readonly>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.construction.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" readonly>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.construction.description"/></label>
                                <textarea id="observations" name="observations" class="form-control" rows="6"
                                          readonly></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW PROG MODAL -->

<!-- UPDATE PROG MODAL -->
<div id="updateProgModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateProgForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.update.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.construction.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.construction.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.construction.description"/></label>
                                <textarea id="observations" name="observations" class="form-control"
                                          rows="6"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="updateProgBtn" type="button" class="btn btn-sm btn-success"><spring:message
                                    code="save"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW PROG MODAL -->

<!-- VIEW DISP MODAL -->
<div id="viewDispModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="viewDispForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.view.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="activityCenter" class="col-form-label"><spring:message
                                        code="shares.displacement.table.activity.center"/></label>
                                <select id="activityCenter" name="activityCenter" class="form-control" required readonly
                                        disabled>
                                    <option disabled selected="selected">
                                        <spring:message code="shares.displacement.table.activity.center"/>
                                    </option>
                                    <c:forEach items="${teamLeaders}" var="teamLeader">
                                        <option value="${teamLeader.userId}">
                                            <spring:message code="${teamLeader.name} ${teamLeader.surnames}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="displacementDate" class="col-form-label"><spring:message
                                        code="shares.displacement.table.total.time"/></label>
                                <input id="displacementDate" name="displacementDate" type="datetime-local"
                                       class="form-control" required readonly>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="manualHours" class="col-form-label"><spring:message
                                    code="shares.displacement.total.time"/></label>
                            <input type="time" class="form-control" id="manualHours" name="manualHours" readonly>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="roundTrip" class="col-form-label"><spring:message
                                    code="shares.displacement.round.trip"/></label>
                            <input type="checkbox" class="form-control" id="roundTrip" name="roundTrip"
                                   style="width: 20px" readonly disabled>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.displacement.observations"/></label>
                                <textarea id="observations" name="observations" class="form-control" rows="6"
                                          readonly></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW MODAL -->

<!-- UPDATE DISP MODAL -->
<div id="updateDispModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateDispForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.update.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="activityCenter" class="col-form-label"><spring:message
                                        code="shares.displacement.table.activity.center"/></label>
                                <select id="activityCenter" name="activityCenter" class="form-control" required>
                                    <option disabled selected="selected">
                                        <spring:message code="shares.displacement.table.activity.center"/>
                                    </option>
                                    <c:forEach items="${teamLeaders}" var="teamLeader">
                                        <option value="${teamLeader.userId}">
                                            <spring:message code="${teamLeader.name} ${teamLeader.surnames}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="displacementDate" class="col-form-label"><spring:message
                                        code="shares.displacement.table.total.time"/></label>
                                <input id="displacementDate" name="displacementDate" type="datetime-local"
                                       class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="manualHours" class="col-form-label"><spring:message
                                    code="shares.displacement.total.time"/></label>
                            <input type="time" class="form-control" id="manualHours" name="manualHours">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="roundTrip" class="col-form-label"><spring:message
                                    code="shares.displacement.round.trip"/></label>
                            <input type="checkbox" class="form-control" id="roundTrip" name="roundTrip"
                                   style="width: 20px">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.displacement.observations"/></label>
                                <textarea id="observations" name="observations" class="form-control"
                                          rows="6"></textarea>
                            </div>
                        </div>
                    </div>

                    <input id="manualDisplacement" name="manualDisplacement" type="hidden" class="form-control"/>
                    <input type="hidden" class="form-control" id="projectId" name="projectId">
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="updateDispBtn" type="button" class="btn btn-sm btn-success"><spring:message
                                    code="save"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /CREATE MODAL -->

<!-- VIEW WORK MODAL -->
<div id="viewWorkModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="viewWorkForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.work.view.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.work.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" readonly>
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.work.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" readonly>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.work.description"/></label>
                                <textarea id="observations" name="observations" class="form-control" rows="6"
                                          readonly></textarea>
                            </div>
                        </div>
                    </div>

                    <div id="images" class="row justify-content-center"></div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW WORK MODAL -->

<!-- UPDATE WORK MODAL -->
<div id="updateWorkModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateWorkForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.work.update.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.work.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.work.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="observations" class="col-form-label"><spring:message
                                        code="shares.work.description"/></label>
                                <textarea id="observations" name="observations" class="form-control"
                                          rows="6"></textarea>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" class="form-control" id="projectId" name="projectId">
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="updateWorkBtn" type="button" class="btn btn-sm btn-success"
                                    data-dismiss="modal"><spring:message code="update"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW WORK MODAL -->

<!-- UPDATE INTERVENTION MODAL -->
<div id="updateIntModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateIntForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="shares.displacement.update.btn"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6">
                            <label for="startDate" class="col-form-label"><spring:message
                                    code="shares.work.start.date"/></label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <label for="endDate" class="col-form-label"><spring:message
                                    code="shares.work.end.date"/></label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate">
                        </div>
                    </div>

                    <input type="hidden" class="form-control" id="projectId" name="projectId">
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="updateIntBtn" type="button" class="btn btn-sm btn-success" data-dismiss="modal">
                                <spring:message code="update"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /VIEW WORK MODAL -->

<!-- ADD DISP MODAL -->
<div id="addDispModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addDispForm" class="needs-validation">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="project.detail.displacements.modal.title"/>
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <select id="displacementDropdown" class="form-control input selectpicker"
                                    data-style="userSelectPicker" data-live-search="true" multiple style="width: 100%"
                                    name="displacementIds">
                                <c:forEach items="${notDisplacements}" var="displacement">
                                    <option value="${displacement.id}">
                                            ${displacement.title}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                    code="close"/></button>
                        </div>
                        <div class="float-right">
                            <button id="addDispBtn" type="button" class="btn btn-sm btn-success" data-dismiss="modal">
                                <spring:message code="save"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /ADD DISP MODAL -->

<!-- DECLINE EXPENSE MODAL -->
<div id="declineExpenseModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="user.detail.expenses.decline.title"/>
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="declineExpenseForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label"><spring:message code="user.detail.expenses.decline.observations" />
                                    <textarea name="observations" class="form-control" rows="6"></textarea>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message
                                code="close"/></button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-sm btn-success" onclick="declineAction()"><spring:message
                                code="decline"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {

        /* Select 2 */
        $('#forumIdDropdown').select2({
            allowClear: true,
            dropdownCssClass: 'selectStyle',
            containerCssClass: 'selectControlStyle'
        });

        $('#familyDropdown').select2({
            allowClear: true,
            dropdownCssClass: 'selectStyle',
            containerCssClass: 'selectControlStyle'
        });

        $('#familyEditDropdown').select2({
            allowClear: true,
            dropdownCssClass: 'selectStyle',
            containerCssClass: 'selectControlStyle'
        });

        $('#responsableInfoInput').selectpicker();
        /* End Select 2 */

        /* Datatables */
        var dTableMembers = $('#dTableMembers').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/members/dt",
            "rowId": "us_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[1, "asc"]],
            "columns": [
                {"data": "us_id"},
                {"data": "us_fullName"},
                {"data": "us_role"},
                {"data": null}
            ],
            "columnDefs": [
                {"targets": [0], "visible": false},
                {"className": "text-center", "targets": "_all"},
                {"defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function (settings, json) {
                parseActionButtons();
            }
        });

        var dTableBosses = $('#dTableBosses').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/bosses/dt",
            "rowId": "us_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[1, "asc"]],
            "columns": [
                {"data": "us_id"},
                {"data": "us_fullName"},
                {"data": null}
            ],
            "columnDefs": [
                {"targets": [0], "visible": false},
                {"className": "text-center", "targets": "_all"},
                {"defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function (settings, json) {
                parseBossesActionButtons();
            }
        });

        initializePersonalExpensesDataTables();

        var dTableSignings = $('#dTableSignings').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/signings/dt",
            "rowId": "st_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "columns": [
                {"data": "st_id"},
                {"data": "st_username"},
                {"data": "st_startDate"},
                {"data": "st_endDate"},
                {"data": "st_shareType"},
                {"data": null}
            ],
            "columnDefs": [
                {"className": "text-center", "orderable": false, "targets": "_all"},
                {
                    "render": function (data) {

                        if (!data) {
                            return '-';
                        }

                        return moment(data).format('DD/MM/YYYY HH:mm');
                    },
                    "targets": [2, 3]
                },
                {
                    "render": function (data, type, row) {
                        return parseShareType(data);
                    },
                    "targets": 4
                },
                {"defaultContent": "${tableShareActionButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function () {
                parseSigningsActionButtons();
            }
        });

        var dTableEquipments = $('#dTableEquipments').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/families/dt",
            "rowId": "fa_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[1, "asc"]],
            "columns": [
                {"data": "fa_id"},
                {"data": "fa_nameES"},
                {"data": "fa_nameFR"},
                {"data": "fa_brand", "defaultContent": "-"},
                {"data": "fa_model", "defaultContent": "-"},
                {"data": "fa_enrollment", "defaultContent": "-"},
                {"data": null}
            ],
            "columnDefs": [
                {"targets": [0], "visible": false},
                {"className": "text-center", "targets": "_all"},
                {"defaultContent": "${tableModifyButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function (settings, json) {
                parseEquipmentsActionButtons();
            }
        });

        var dTableDisplacements = $('#dTableDisplacements').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/displacements/dt",
            "rowId": "di_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[1, "asc"]],
            "columns": [
                {"data": "di_id"},
                {"data": "di_title"},
                {"data": "di_activityCenter"},
                {"data": "di_displacementType"},
                {"data": "di_totalTime"},
                {"data": null}
            ],
            "columnDefs": [
                {"targets": [0], "visible": false},
                {"className": "text-center", "targets": "_all"},
                {"defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function (settings, json) {
                parseDisplacementsActionButtons();
            }
        });

        var dTableMaterialsRequired = $('#dTableMaterialsRequired').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/projects/${project.id}/materials-required/dt",
            "rowId": "mr_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[1, "asc"]],
            "columns": [
                {"data": "mr_id"},
                {"data": "mr_nameES"},
                {"data": "mr_nameFR"},
                {"data": "mr_required"},
                {"data": null}
            ],
            "columnDefs": [
                {"targets": [0], "visible": false},
                {"className": "text-center", "targets": "_all"},
                {"defaultContent": "${tableModifyButtons}", "orderable": false, "targets": -1}
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function (settings, json) {
                parseMaterialsRequiredActionButtons();
            }
        });

        /* End Datatables */

        function parseShareType(data) {
            if (data === 'cs') {
                return '<span class="badge badge-success"> ${jspUtil.parseTagToText('cs')} </span>';
            } else if (data === 'ds') {
                return '<span class="badge badge-warning"> ${jspUtil.parseTagToText('ds')} </span>';
            } else if (data === 'ips') {
                return '<div class="badge badge-secondary"> ${jspUtil.parseTagToText('ips')} </div>';
            } else if (data === 'is') {
                return '<span class="badge badge-primary"> ${jspUtil.parseTagToText('is')} </span>';
            } else if (data === 'ws') {
                return '<span class="badge badge-info"> ${jspUtil.parseTagToText('ws')} </span>';
            }

            return '';
        }

        /* Select 2 */

        $('#memberDropdown').selectpicker();
        $('#bossDropdown').selectpicker();
        $('#displacementDropdown').selectpicker();

        /* End Select 2 */

        $('#addMember').click(function () {

            showLoading();

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/members/create",
                data: $('#addMemberForm').serialize(),
                success: function (msg) {
                    dTableMembers.ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#createModal').modal('hide');
        });

        $('#addBoss').click(function () {

            showLoading();

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/bosses/create",
                data: $('#addBossForm').serialize(),
                success: function (msg) {
                    dTableMembers.ajax.reload();
                    dTableBosses.ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#createBossModal').modal('hide');
        });

        $('#addEquipment').click(function () {

            showLoading();

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/families/create",
                data: $('#addEquipmentForm').serialize(),
                success: function (msg) {
                    dTableEquipments.ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#createEquipmentModal').modal('hide');
        });

        $('#addMaterialRequired').click(function () {

            if (validateMaterialRequiredForm()) {
                $('#addMaterialRequiredForm').addClass('was-validated');
            } else {

                showLoading();

                $('#addMaterialRequiredForm').removeClass('was-validated');

                if ($('#materialRequiredCheckbox').prop('checked')) {
                    $('#materialRequiredCheckbox').val(1);
                } else {
                    $('#materialRequiredCheckbox').val(0);
                }

                $.ajax({
                    type: "POST",
                    url: "/projects/${project.id}/materials-required/create",
                    data: $('#addMaterialRequiredForm').serialize(),
                    success: function (msg) {
                        dTableMaterialsRequired.ajax.reload();
                        hideLoading();
                        showNotify(msg, 'success');
                    },
                    error: function (e) {
                        hideLoading();
                        showNotify(e.responseText, 'danger');
                    }
                });

                $('#createMaterialRequiredModal').modal('hide');
            }
        });

        $('#editProjectBtn').click(function () {

            $('#saveProjectBtn').toggle();

            var editingMode = $('#saveProjectBtn').css('display') != 'none';

            $('#projectNameInfoInput').attr('readonly', !editingMode);
            $('#activityCenterInfoInput').attr('disabled', !editingMode);
            $('#responsableInfoInput').attr('disabled', !editingMode);
            $('#objectiveCostInfoInput').attr('readonly', !editingMode);
            $('#startDateInfoInput').attr('readonly', !editingMode);
            $('#objectiveDateInfoInput').attr('readonly', !editingMode);
            $('#stationInfoInput').attr('disabled', !editingMode);
            $('#forumIdDropdownInfoInput').attr('disabled', !editingMode);

            $('#responsableInfoInput').selectpicker('refresh');
        });

        $('#editCustomerBtn').click(function () {

            $('#saveCustomerBtn').toggle();

            var editingMode = $('#saveCustomerBtn').css('display') != 'none';

            $('#customerName').attr('readonly', !editingMode);
            $('#mainEmail').attr('readonly', !editingMode);
            $('#secondaryEmail').attr('readonly', !editingMode);
        });

        $('#saveProjectBtn').click(function () {

            if (validateForm()) {
                $('#projectInfoForm').addClass('was-validated');
            } else {
                showLoading();

                $('#projectInfoForm').removeClass('was-validated');

                $.ajax({
                    type: "POST",
                    url: "/projects/${project.id}/update",
                    data: $('#projectInfoForm').serialize(),
                    success: function (msg) {
                        $('#editProjectBtn').click();
                        hideLoading();
                        showNotify(msg, 'success');
                    },
                    error: function (e) {
                        hideLoading();
                        showNotify(e.responseText, 'danger');
                    }
                });
            }
        });

        $('#saveCustomerBtn').click(function () {

            if (validateCustomerForm()) {
                $('#customerForm').addClass('was-validated');
            } else {
                showLoading();

                $('#customerForm').removeClass('was-validated');

                $.ajax({
                    type: "POST",
                    url: "/projects/${project.id}/customer/update",
                    data: $('#customerForm').serialize(),
                    success: function (msg) {
                        $('#editCustomerBtn').click();
                        hideLoading();
                        showNotify(msg, 'success');
                    },
                    error: function (e) {
                        hideLoading();
                        showNotify(e.responseText, 'danger');
                    }
                });
            }
        });

        $('#addDispBtn').click(function () {

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/displacements/create",
                data: $('#addDispForm').serialize(),
                success: function (msg) {
                    dTableDisplacements.ajax.reload();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#addDispModal').modal('hide');
        });

        /* Clear Modal */
        $('#createMaterialRequiredModal').on('hidden.bs.modal', function (e) {
            $(this)
                .find("input,textarea,select")
                .val('')
                .end()
                .find("input[type=checkbox], input[type=radio]")
                .prop("checked", "")
                .end();
        });
    });

    function validateForm() {
        var projectName = document.getElementById('projectNameInfoInput');
        var activityCenter = document.getElementById('activityCenterInfoInput');
        var responsable = document.getElementById('responsableInfoInput');
        var objectiveCost = document.getElementById('objectiveCostInfoInput');
        var startDate = document.getElementById('startDateInfoInput');
        var objectiveDate = document.getElementById('objectiveDateInfoInput');

        return (!projectName.value.length || !activityCenter.value.length || !responsable.value.length || !objectiveCost.value.length || !startDate.value.length || !objectiveDate.value.length);
    }

    function validateCustomerForm() {
        var customerName = document.getElementById('customerName');
        var mainEmail = document.getElementById('mainEmail');
        var secondEmail = document.getElementById('secondaryEmail');

        if (mainEmail.value.length || secondEmail.value.length) {
            return (!customerName.value.length);
        }

        return false;
    }

    function validateMaterialRequiredForm() {
        var nameES = document.getElementById('materialRequiredNameESInput');
        var nameFR = document.getElementById('materialRequiredNameFRInput');

        return (!nameES.value.length || !nameFR.value.length);
    }

    function parseActionButtons() {

        var tableRows = $('#dTableMembers tbody tr');

        tableRows.each(function () {

            var memberId = $(this).attr('id');
            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) {
                    $(this).attr('onclick', 'deleteMember(' + memberId + ')');
                }
            });
        });
    }

    function parseBossesActionButtons() {

        var tableRows = $('#dTableBosses tbody tr');

        tableRows.each(function () {

            var memberId = $(this).attr('id');
            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) {
                    $(this).attr('onclick', 'deleteBoss(' + memberId + ')');
                }
            });
        });
    }

    function parseEquipmentsActionButtons() {

        var tableRows = $('#dTableEquipments tbody tr');

        tableRows.each(function () {

            var equipmentId = $(this).attr('id');
            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) {
                    $(this).attr('onclick', 'editEquipment(' + equipmentId + ')');
                } else if (index == 1) {
                    $(this).attr('onclick', 'deleteEquipment(' + equipmentId + ')');
                }
            });
        });
    }

    function parseDisplacementsActionButtons() {

        var tableRows = $('#dTableDisplacements tbody tr');

        tableRows.each(function () {

            var displacementId = $(this).attr('id');
            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) {
                    $(this).attr('onclick', 'deleteDisplacement(' + displacementId + ')');
                }
            });
        });
    }

    function parseMaterialsRequiredActionButtons() {

        var tableRows = $('#dTableMaterialsRequired tbody tr');
        var table = $('#dTableMaterialsRequired').DataTable();

        tableRows.each(function () {

            var materialRequiredId = $(this).attr('id');

            var data = table.row($(this)).data();
            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) {
                    $(this).attr("onclick", "editMaterialRequired(" + materialRequiredId + ",'" + data.mr_nameES + "','" + data.mr_nameFR + "','" + data.mr_required + "')");
                } else if (index == 1) {
                    $(this).attr("onclick", "deleteMaterialRequired(" + materialRequiredId + ")");
                }
            });
        });
    }

    function parseSigningsActionButtons() {

        var tableRows = $('#dTableSignings tbody tr');
        var table = $('#dTableSignings').DataTable();

        tableRows.each(function () {

            var id = $(this).attr('id');

            if (!id) {
                return;
            }

            var data = table.row($(this)).data();
            var action = data.st_action;
            var shareType = data.st_shareType;

            var lastColumn = $(this).children().last();
            var emList = lastColumn.children();

            emList.each(function (index) {

                if (index == 0) { // pdf

                    if (!data.st_endDate) {
                        return $(this).remove();
                    }

                    if (shareType === 'cs') {
                        $(this).wrap('<a href="/shares/intervention/construction/' + id + '/pdf" target="_blank"></a>');
                    } else if (shareType === 'is') {

                        // es 'Seguimiento'
                        if (action == 3) {
                            return $(this).remove();
                        }

                        $(this).wrap('<a href="/v1/shares/no-programmed/0/inspections/' + id + '/export" target="_blank"></a>');

                    } else if (shareType === 'ips') {
                        $(this).wrap('<a href="/shares/intervention/programmed/' + id + '/pdf" target="_blank"></a>');
                    } else if (shareType === 'ws') {
                        $(this).wrap('<a href="/shares/work/' + id + '/pdf" target="_blank"></a>');
                    } else {
                        return $(this).remove();
                    }

                } else if (index == 1) {

                    if (shareType == 'is') {
                        $(this).wrap('<a href="/v1/shares/no-programmed/0/inspections/' + id + '/export-materials" target="_blank"></a>');
                    } else {
                        $(this).remove();
                    }

                } else if (index == 2) { // view
                    if (shareType !== 'is') {
                        return $(this).attr('onclick', shareType + 'ViewShare(' + id + ')');
                    }

                    $(this).remove();
                } else if (index == 3) {
                    $(this).attr('onclick', shareType + 'EditShare(' + id + ')');
                } else if (index == 4) {
                    $(this).attr('onclick', 'deleteSigning(' + id + ', \'' + shareType + '\')');
                }
            });
        });
    }

    function getCsShare(id) {
        return $.ajax({
            url: '/shares/intervention/construction/' + id,
            type: 'GET'
        });
    }

    function getDsShare(id) {
        return $.ajax({
            url: '/shares/displacement/' + id,
            type: 'GET'
        });
    }

    function getIpsShare(id) {
        return $.ajax({
            url: '/shares/intervention/programmed/' + id,
            type: 'GET'
        });
    }

    function getIsShare(id) {
        return $.ajax({
            url: '/shares/no-programmed/' + id,
            type: 'GET'
        });
    }

    function getWsShare(id) {
        return $.ajax({
            url: '/shares/work/' + id,
            type: 'GET'
        });
    }

    function getFiles(id) {
        return $.ajax({
            url: '/shares/work/files/' + id,
            type: 'GET'
        });
    }

    function initForm(share, form) {
        Object.keys(share).forEach(function (key) {
            if (form.elements[key]) {
                if (form.elements[key].type === 'checkbox') {
                    form.elements[key].checked = share[key];
                } else {
                    form.elements[key].value = share[key];
                }
            }
        });
    }

    async function csViewShare(id) {
        var share = await getCsShare(id);
        var form = document.forms['viewConstForm'];
        initForm(share, form);
        $('#viewConstModal').modal('show');
    }

    async function dsViewShare(id) {
        let share = await getDsShare(id);
        let activityCenterId = share.activityCenter.id;
        let callbackFunction = function (html) {
            $('#viewDispForm #displacement').html(html);
            let form = document.forms['viewDispForm'];
            initForm(share, form);
            $('#viewDispModal').modal('show');
        }

        loadDisplacements(activityCenterId, callbackFunction);
    }

    async function ipsViewShare(id) {
        var share = await getIpsShare(id);
        var form = document.forms['viewProgForm'];
        initForm(share, form);
        $('#viewProgModal').modal('show');
    }

    async function wsViewShare(id) {
        var share = await getWsShare(id);
        var form = document.forms['viewWorkForm'];
        var shareFiles = await getFiles(id);
        var exampleId = 1;
        $('#images').empty();
        shareFiles.forEach(function (shareFile) {
            var div = document.createElement('div');
            var a = document.createElement('a');
            var image = document.createElement('img');
            var url = 'data:image/' + shareFile.ext + ';base64,' + shareFile.content;
            image.src = url;
            image.className = "rounded img-thumbnail";
            a.href = url;
            a.dataset.lightbox = "example-" + exampleId++;
            a.append(image);
            div.className = "col-3 mb-2";
            div.append(a);
            $('#images').append(div);
        })
        initForm(share, form);
        $('#viewWorkModal').modal('show');
    }

    async function csEditShare(id) {
        var share = await getCsShare(id);
        var form = document.forms['updateConstForm'];
        initForm(share, form);
        $('#updateConstModal').modal('show');

        $('#updateConstBtn').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "PUT",
                url: "/shares/construction/" + id,
                data: $('#updateConstForm').serialize(),
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#updateConstModal').modal('hide');
        });
    }

    async function wsEditShare(id) {
        var share = await getWsShare(id);
        var form = document.forms['updateWorkForm'];
        initForm(share, form);
        $('#updateWorkModal').modal('show');

        $('#updateWorkBtn').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "PUT",
                url: "/shares/work/" + id,
                data: $('#updateWorkForm').serialize(),
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#updateWorkModal').modal('hide');
        });
    }

    async function dsEditShare(id) {
        let share = await getDsShare(id);
        let activityCenterId = share.activityCenter.id;
        let callbackFunction = function (html) {
            $('#updateDispForm #displacement').html(html);
            let form = document.forms['updateDispForm'];
            initForm(share, form);
            $('#updateDispModal').modal('show');
        }

        loadDisplacements(activityCenterId, callbackFunction);

        $('#updateDispBtn').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "PUT",
                url: "/shares/displacement/" + id,
                data: $('#updateDispForm').serialize(),
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#updateDispModal').modal('hide');
        });
    }

    async function ipsEditShare(id) {
        var share = await getIpsShare(id);
        var form = document.forms['updateProgForm'];
        initForm(share, form);
        $('#updateProgModal').modal('show');

        $('#updateProgBtn').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "PUT",
                url: "/shares/programmed/" + id,
                data: $('#updateProgForm').serialize(),
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#updateProgModal').modal('hide');
        });
    }

    async function isEditShare(id) {
        var share = await getIsShare(id);
        var form = document.forms['updateIntForm'];
        initForm(share, form);
        $('#updateIntModal').modal('show');

        $('#updateIntBtn').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "PUT",
                url: "/shares/no-programmed/" + id,
                data: $('#updateIntForm').serialize(),
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#updateIntModal').modal('hide');
        });
    }

    function deleteMember(memberId) {

        showLoading();

        $.ajax({
            type: "DELETE",
            url: "/projects/${project.id}/members/delete/" + memberId,
            success: function (msg) {
                $('#dTableMembers').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

    function deleteBoss(bossId) {

        showLoading();

        $.ajax({
            type: "DELETE",
            url: "/projects/${project.id}/bosses/delete/" + bossId,
            success: function (msg) {
                $('#dTableMembers').DataTable().ajax.reload();
                $('#dTableBosses').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

    function editEquipment(equipmentId) {

        var equipment;

        $.ajax({
            type: "GET",
            url: "/admin/families/" + equipmentId + "/info",
            async: false,
            success: function (msg) {
                equipment = msg;
            }
        });

        $('#editEquipmentModal').find('#familyEditDropdown').val(equipment.familyId).trigger('change');
        $('#editEquipmentModal').find('#familyNameESEditInput').val(equipment.nameES);
        $('#editEquipmentModal').find('#familyNameFREditInput').val(equipment.nameFR);
        $('#editEquipmentModal').find('#brandEditInput').val(equipment.brand);
        $('#editEquipmentModal').find('#modelEditInput').val(equipment.model);
        $('#editEquipmentModal').find('#enrollmentEditInput').val(equipment.enrollment);

        $('#editEquipmentModal').modal('show');

        $('#editEquipment').unbind('click').click(function () {

            showLoading();

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/families/edit/" + equipmentId,
                data: $('#editEquipmentForm').serialize(),
                success: function (msg) {
                    $('#dTableEquipments').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#editEquipmentModal').modal('hide');
        });
    }

    function deleteEquipment(equipmentId) {

        showLoading();

        $.ajax({
            type: "DELETE",
            url: "/projects/${project.id}/families/delete/" + equipmentId,
            success: function (msg) {
                $('#dTableEquipments').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

    function deleteDisplacement(displacementId) {

        showLoading();

        $.ajax({
            type: "DELETE",
            url: "/projects/${project.id}/displacements/delete/" + displacementId,
            success: function (msg) {
                $('#dTableDisplacements').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

    function deleteSigning(signingId, shareType) {

        var ok = confirm("${jspUtil.parseTagToText('share.default.delete.alert')}");

        if (ok) {

            showLoading();

            var deleteUri = '';

            if (shareType === 'cs') {
                deleteUri = '/shares/intervention/construction/delete/' + signingId;
            } else if (shareType === 'ds') {
                deleteUri = '/shares/displacement/' + signingId;
            } else if (shareType === 'ips') {
                deleteUri = '/shares/intervention/programmed/delete/' + signingId;
            } else if (shareType === 'is') {
                deleteUri = '/shares/no-programmed/' + signingId;
            } else if (shareType === 'ws') {
                deleteUri = '/shares/work/' + signingId;
            }

            $.ajax({
                type: "DELETE",
                url: deleteUri,
                success: function (msg) {
                    $('#dTableSignings').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });
        }
    }

    function editMaterialRequired(id, nameES, nameFR, required) {

        $('#editMaterialRequiredModal').find('#materialRequiredNameESEditInput').val(nameES);
        $('#editMaterialRequiredModal').find('#materialRequiredNameFREditInput').val(nameFR);
        $('#editMaterialRequiredModal').find('#materialRequiredEditCheckbox').prop('checked', required == 1 ? true : false);

        $('#editMaterialRequiredModal').modal('show');

        $('#editMaterialRequired').unbind('click').click(function () {

            showLoading();

            if ($('#materialRequiredEditCheckbox').prop('checked')) {
                $('#materialRequiredEditCheckbox').val(1);
            } else {
                $('#materialRequiredEditCheckbox').val(0);
            }

            $.ajax({
                type: "POST",
                url: "/projects/${project.id}/materials-required/edit/" + id,
                data: $('#editMaterialRequiredForm').serialize(),
                success: function (msg) {
                    $('#dTableMaterialsRequired').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function (e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });

            $('#editMaterialRequiredModal').modal('hide');
        });
    }

    function deleteMaterialRequired(materialRequiredId) {

        showLoading();

        $.ajax({
            type: "DELETE",
            url: "/projects/${project.id}/materials-required/delete/" + materialRequiredId,
            success: function (msg) {
                $('#dTableMaterialsRequired').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

    function importFamiliesFile() {
        document.getElementById("importFamiliesInput").click();
    }

    function uploadFamiliesFile() {

        showLoading();

        var excel = document.getElementById("importFamiliesInput").files[0];

        var fd = new FormData();
        fd.append('file', excel);

        $.ajax({
            url: '/projects/${project.id}/families/import',
            type: 'POST',
            data: fd,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (msg) {
                $('#dTableEquipments').DataTable().ajax.reload();
                hideLoading();
                showNotify(msg, 'success');
            },
            error: function (e) {
                hideLoading();
                showNotify(e.responseText, 'danger');
            }
        });
    }

</script>

<script>
    let locale = '${locale}';
    let dTableExpenses = null;

    document.addEventListener("DOMContentLoaded", function () {
        setReturnButtonUrl();
    });

    function setReturnButtonUrl() {
        if (document.referrer) {

            const lastPagePath = new URL(document.referrer).pathname;
            const queryParams = document.referrer.split('?')[1];
            const lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');

            if (lastPagePath === '/projects') {
                $('#returnBtn').attr('href', lastPageUrl);
            }
        }
    }

    function initializePersonalExpensesDataTables() {
        let columns = ['id', 'description', 'project.name', 'status', 'startDate', 'amount', 'id']
        let endpoint = '/v1/expenses/personal/sheets';
        let actions = [
            {
                action: 'file-pdf',
                url: '/v1/expenses/personal/sheets/{id}/export',
                permission: 'edit_personal_expenses_sheet'
            },
            {
                action: 'validate',
                permission: 'edit_personal_expenses_sheet',
                condition: {
                    key: 'status',
                    value: ['PENDING', 'APPROVED'],
                    operation: '==='
                }
            },
            {
                action: 'decline',
                permission: 'edit_personal_expenses_sheet',
                condition: {
                    key: 'status',
                    value: ['PENDING', 'APPROVED'],
                    operation: '==='
                }
            },
            {
                action: 'view',
                url: '/expenses/personal/sheets/{id}',
                permission: 'edit_personal_expenses_sheet'
            }
        ]
        let expand = ['project']
        let filters = [{'projectId': ${project.id}}]
        let orderable = [[0, 'DESC']]
        let columnDefs = [
            {
                targets: 3,
                render: function (data) {
                    return parseStatusToBadge(data);
                }
            },
            {
                targets: 4,
                render: function (data) {
                    return moment(data).format('DD-MM-YYYY HH:mm');
                }
            },
            {
                targets: 5,
                render: function (data) {
                    return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(data);
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnDefs);
        dTableExpenses = createDataTable('#dTableExpenses', customDataTable, locale);
        customDataTable.setCurrentTable(dTableExpenses);
    }

    function parseStatusToBadge(status) {
        if (status === 'PENDING') {
            return '<div class="badge badge-warning">' + messages.status.pending + '</div>';
        } else if (status === 'APPROVED') {
            return '<span class="badge badge-primary">' + messages.status.approved + '</span>';
        } else if (status === 'PAID') {
            return '<span class="badge badge-success">' + messages.status.paid + '</span>';
        } else if (status === 'REJECTED') {
            return '<span class="badge badge-danger">' + messages.status.rejected + '</span>';
        }
    }

    function validate(personalExpenseSheetId) {

        showLoading();

        const data = dTableExpenses.row("#" + personalExpenseSheetId).data();
        const nextStatus = getNextStatus(data.status);

        if (nextStatus) {
            axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
                status: nextStatus
            }).then(() => {
                dTableExpenses.ajax.reload();
                showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
            }).catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

    function decline(personalExpenseSheetId) {
        const modal = $('#declineExpenseModal');
        modal.attr('data-id', personalExpenseSheetId);
        modal.modal('show');
    }

    function declineAction() {
        showLoading();

        const modal = $('#declineExpenseModal');
        const personalExpenseSheetId = modal.data('id');
        const form = document.querySelector('#declineExpenseForm');
        const obervations = form.querySelector('[name="observations"]')

        axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
            status: 'REJECTED',
            observations: obervations.value
        }).then(() => {
            dTableExpenses.ajax.reload();
            showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => {
                hideLoading();
                modal.modal('hide');
            });
    }

    function getNextStatus(status) {
        if (status === 'PENDING') {
            return 'APPROVED';
        } else if (status === 'APPROVED') {
            return 'PAID';
        }
        return null;
    }

</script>
