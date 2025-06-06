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
                            <select id="projectDropdown" class="form-control input selectpicker" data-style="userSelectPicker" data-live-search="true" multiple style="width: 100%" name="projectId">
                                <c:forEach items="${notBelongProjects}" var="notBelongProject">
                                    <option value="${notBelongProject.id}">
                                        <spring:message code="${notBelongProject.projectName}" />
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

<script>

    $(document).ready(function() {
        initializeDataTables();
    });

    function initializeDataTables() {
        dTable = $('#dTable').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "pageLength": 7,
            "ajax": "/users/${currentUser.id}/projects/dt",
            "rowId": "pr_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[0, "desc"]],
            "columns": [
                { "data": "pr_id" },
                { "data": "pr_name" },
                { "data": "pr_startDate" },
                { "data": "pr_endDate" },
                { "data": null }
            ],
            "columnDefs": [
                { "className": "text-center", "targets": "_all" },
                {
                    "render": function(data) {
                        return moment(data).format('DD/MM/YYYY');
                    },
                    "targets": [2, 3]
                },
                { "defaultContent": "<em class=\"far fa-trash-alt\"></em>", "orderable": false, "targets": -1 }
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function() {
                parseProjectActionButtons();
            }
        });
    }

    function parseProjectActionButtons() {

        let tableRows = $('#dTable tbody tr');

        tableRows.each(function() {

            let projectId = $(this).attr('id');
            let lastColumn = $(this).children().last();
            let emList = lastColumn.children();

            emList.each(function(index) {

                if (index === 0) {
                    $(this).attr('onclick', 'deleteProject(' + projectId + ')');
                }
            });
        });
    }

    function addUserToProject() {
        const createModal = $('#createModal');
        const createFromJQ = $('#createForm');

        if (!isValidForm('#createForm')) {
            createFromJQ.addClass('was-validated');
        } else {

            showLoading();
            createFromJQ.removeClass('was-validated');

            axios.post('/users/${currentUser.id}/projects/create', createFromJQ.serialize())
                .then(() => dTable.ajax.reload())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => { hideLoading(); createModal.modal('hide'); });
        }
    }

    function deleteProject(projectId) {
        const ok = confirm("${jspUtil.parseTagToText('project.delete.alert')}");

        if (ok) {
            showLoading();

            axios.delete('/users/${currentUser.id}/projects/delete/' + projectId, { })
                .then(() => dTable.ajax.reload())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

</script>