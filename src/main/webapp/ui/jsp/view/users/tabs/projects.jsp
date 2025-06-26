<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col text-right">
    <button type="button" class="btn btn-standard btn-sm" data-toggle="modal" data-target="#createModal"><spring:message code="user.detail.projects.create" /></button>
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

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="user.detail.projects.create" />
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="createForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="project"/>
                                    <select class="form-control select2" data-control="select2" name="projectId" required></select>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
                    </div>
                    <div class="float-right">
                        <button type="submit" class="btn btn-sm btn-success" onclick="addUserToProject()"><spring:message code="add" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
