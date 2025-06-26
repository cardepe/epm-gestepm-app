<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="editForm" class="needs-validation">
    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="name"/>
                    <input type="text" name="name" class="form-control" value="${currentProject.name}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="type" />
                    <select name="type" class="form-control select2">
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <option value="NORMAL" ${currentProject.type == 'NORMAL' ? 'selected': ''}><spring:message code="normal" /></option>
                        <option value="STATION" ${currentProject.type == 'STATION' ? 'selected': ''}><spring:message code="station" /></option>
                        <option value="OFFICE" ${currentProject.type == 'OFFICE' ? 'selected': ''}><spring:message code="office" /></option>
                        <option value="TELEWORKING" ${currentProject.type == 'TELEWORKING' ? 'selected': ''}><spring:message code="teleworking" /></option>
                    </select>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="activity.center"/>
                    <select class="form-control select2" data-control="select2" name="activityCenterId" required></select>
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="project.detail.project.objective.cost"/>
                    <input type="number" name="objectiveCost" class="form-control" value="${currentProject.objectiveCost}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="start.date" />
                    <input name="startDate" type="date" class="form-control" value="${currentProject.startDate}" required>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="end.date" />
                    <input name="endDate" type="date" class="form-control" value="${currentProject.endDate}" required>
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="projects.table.forum"/>
                    <select name="forumId" class="form-control select2">
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <c:forEach items="${forums}" var="forum">
                            <option value="${forum.id}" ${currentProject.forumId == forum.id ? 'selected': ''}><spring:message code="[${forum.id}] ${forum.name}" /></option>
                        </c:forEach>
                    </select>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="responsible"/>
                    <select class="form-control select2" data-control="select2" name="responsibleIds" multiple required></select>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="status" />
                    <select name="state" class="form-control select2" required>
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <option value="1" ${currentProject.state == 1 ? 'selected': ''}><spring:message code="enabled" /></option>
                        <option value="0" ${currentProject.state == 0 ? 'selected': ''}><spring:message code="disabled" /></option>
                    </select>
                </label>
            </div>
        </div>
    </div>

    <div class="row mt-2 actionable">
        <div class="col text-right">
            <button type="button" class="btn btn-outline-warning btn-sm text-warning" onclick="duplicate()">
                <spring:message code="duplicate"/>
            </button>
            <button type="button" class="btn btn-warning btn-sm text-white" data-toggle="modal" data-target="#exportModal">
                <spring:message code="export"/>
            </button>
            <button type="button" class="btn btn-standard btn-sm movile-full" onclick="edit()"><spring:message code="save"/></button>
        </div>
    </div>
</form>

<div class="modal fade" id="exportModal" tabindex="-1" role="dialog" aria-labelledby="exportLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form type="GET" action="/v1/projects/${currentProject.id}/export" target="_blank">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="export" />
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="year" />
                                    <select name="year" class="form-control form-control-sm">
                                        <option disabled value="" selected="selected">
                                            <spring:message code="time.control.year.placeholder" />
                                        </option>
                                        <c:forEach items="${years}" var="year">
                                            <option value="${year}">
                                                <spring:message code="${year}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
                        </div>
                        <div class="float-right">
                            <button type="submit" class="btn btn-sm btn-success"><spring:message code="export" /></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>