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
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="station" />
                    <select name="isStation" class="form-control select2 stationSelect" required>
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <option value="true" ${currentProject.isStation == true ? 'selected': ''}><spring:message code="yes" /></option>
                        <option value="false" ${currentProject.isStation == false ? 'selected': ''}><spring:message code="no" /></option>
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
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="signing.teleworking" />
                    <select name="isTeleworking" class="form-control select2" required>
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <option value="true" ${currentProject.isTeleworking == true ? 'selected': ''}><spring:message code="yes" /></option>
                        <option value="false" ${currentProject.isTeleworking == false ? 'selected': ''}><spring:message code="no" /></option>
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
    </div>

    <div class="row">
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
            <button type="button" class="btn btn-standard btn-sm movile-full" onclick="edit()"><spring:message code="save"/></button>
        </div>
    </div>
</form>
