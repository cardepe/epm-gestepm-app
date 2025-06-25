<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

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

<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/static/js/plugins/signature_pad/signature_pad.umd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/static/js/select2/select2-utils.js?v=<%= System.currentTimeMillis() %>"></script>


<script>

    const projectsView = '/projects'
    const projectsEndpoint = '/v1/projects';

    $(document).ready(function() {
        initializeDataTables();
        initializeSelects();
    });

    function initializeDataTables() {
        let columns = ['id', 'name', 'activityCenter.name', 'startDate', 'endDate', 'id']
        let actions = [
            {
                action: 'view',
                url: projectsView + '/{id}',
                permission: 'read_projects'
            },
            {
                action: 'delete',
                permission: 'edit_projects'
            }
        ]
        let expand = ['activityCenter']
        let filters = [{ 'memberIds': ${currentUser.id} }];
        let columnsDef = []

        customDataTable = new CustomDataTable(columns, projectsEndpoint, null, actions, expand, filters, columnsDef);
        dTable = createDataTable('#dTable', customDataTable, locale);
        customDataTable.setCurrentTable(dTable);
    }

    function initializeSelects() {

        // # CreateForm
        const createForm = document.querySelector('#createForm');

        createSelect2($(createForm.querySelector('[name="projectId"]')), projectsEndpoint, null, null, null, 'createForm');
    }

    function addUserToProject() {
        const createModal = $('#createModal');
        const createFromJQ = $('#createForm');

        if (!isValidForm('#createForm')) {
            createFromJQ.addClass('was-validated');
        } else {

            showLoading();
            createFromJQ.removeClass('was-validated');

            const form = document.querySelector('#createForm');

            const projectId = form.querySelector('[name="projectId"]').value;

            axios.post('/v1/projects/' + projectId + '/members', {
                userId: ${currentUser.id}
            }).then(() => dTable.ajax.reload())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => { hideLoading(); createModal.modal('hide'); });
        }
    }

    function remove(projectId) {
        const ok = confirm("${jspUtil.parseTagToText('project.delete.alert')}");

        if (ok) {
            showLoading();

            axios.delete('/v1/projects/' + projectId + '/members/${currentUser.id}', { })
                .then(() => dTable.ajax.reload())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

</script>