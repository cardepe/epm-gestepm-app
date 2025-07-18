<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="editForm" class="needs-validation">
    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="name"/>
                    <input type="text" name="name" class="form-control" value="${currentUser.name}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="surnames"/>
                    <input type="text" name="surnames" class="form-control" value="${currentUser.surnames}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="activity.center"/>
                    <select class="form-control input" name="activityCenterId" required>
                        <option></option>
                        <c:forEach items="${activityCenters}" var="activityCenter">
                            <option value="${activityCenter.id}"
                                    <c:if test="${activityCenter.id == currentUser.activityCenterId}">selected</c:if>>
                                <spring:message code="${activityCenter.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="email"/>
                    <input type="email" name="email" class="form-control" value="${currentUser.email}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="password"/>
                    <input type="password" name="password" class="form-control" value="random-password" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="users.table.signingid"/>
                    <input type="number" name="signingId" class="form-control" value="${currentUser.signingId}" />
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="role"/>
                    <select class="form-control input" name="roleId" required>
                        <option></option>
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.id}"
                                    <c:if test="${role.id == currentUser.roleId}">selected</c:if>>
                                <spring:message code="${role.roleName}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100 bootstrap-select"><spring:message code="level"/>
                    <select class="form-control input" name="levelId" required>
                        <option></option>
                        <c:forEach items="${levels}" var="level">
                            <option value="${level.id}"
                                    <c:if test="${level.id == currentUser.levelId}">selected</c:if>>
                                <spring:message code="${level.rol}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="w-100 bootstrap-select">
                    <spring:message code="status" />
                    <select name="state" class="form-control select2">
                        <option value=""><spring:message code="select.placeholder" /></option>
                        <option value="0" ${currentUser.state == 0 ? 'selected': ''}><spring:message code="disabled" /></option>
                        <option value="1" ${currentUser.state == 1 ? 'selected': ''}><spring:message code="enabled" /></option>
                    </select>
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="working.hours"/>
                    <input type="number" name="workingHours" class="form-control" value="${currentUser.workingHours}" />
                </label>
            </div>
        </div>
    </div>

    <div class="row mt-2 actionable">
        <div class="col text-right">
            <button type="button" class="btn btn-standard btn-sm movile-full" onclick="create()"><spring:message code="save"/></button>
        </div>
    </div>
</form>
