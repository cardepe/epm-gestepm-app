<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<div class="table-responsive">
    <table id="dTable" class="table table-striped table-borderer dataTable w-100">
        <thead>
        <tr>
            <th><spring:message code="id" /></th>
            <th><spring:message code="date" /></th>
            <th><spring:message code="status" /></th>
            <th><spring:message code="actions" /></th>
        </tr>
        </thead>
    </table>
</div>

<div id="declineModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="user.detail.holidays.decline.title" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="declineForm">
                    <input type="hidden" id="holidayId" />

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="user.detail.holidays.decline.observations" />
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
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-sm btn-success" onclick="declineHoliday()"><spring:message code="decline" /></button>
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
            "ajax": "/users/${currentUser.id}/holidays/dt",
            "rowId": "uh_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[0, "desc"]],
            "columns": [
                { "data": "uh_id" },
                { "data": "uh_date" },
                { "data": "uh_status" },
                { "data": null }
            ],
            "columnDefs": [
                { "targets": [0], "visible": false },
                { "className": "text-center", "targets": "_all" },
                {
                    "render": function ( data ) {
                        return moment(data).format('DD/MM/YYYY');
                    },
                    "targets": 1
                },
                {
                    "className": "text-right",
                    "render": function ( data ) {
                        return parseStatus(data);
                    },
                    "targets": 2
                },
                { "defaultContent": "<em class=\"fas fa-thumbs-up\"></em><em class=\"fas fa-thumbs-down\"></em><em class=\"far fa-trash-alt\"></em>", "orderable": false, "targets": -1 }
            ],
            "dom": "<'top'i>rt<'bottom'p><'clear'>",
            "drawCallback": function() {
                parseActionButtons();
            }
        });
    }

    function parseActionButtons() {
        let tableRows = $('#dTable tbody tr');

        tableRows.each(function() {

            let holidayId = $(this).attr('id');
            let status = jQuery($(this).children().get(1));
            let lastColumn = $(this).children().last();
            let emList = lastColumn.children();

            emList.each(function(index) {

                if (index === 0) {
                    $(this).attr('onclick', 'validateHoliday(' + holidayId + ')');

                    if (!canValidate(status)) {
                        $(this).remove();
                    }
                } else if (index === 1) {
                    $(this).attr('onclick', 'openModal(' + holidayId + ')');

                    if (!canValidate(status)) {
                        $(this).remove();
                    }
                } else if (index === 2) {
                    $(this).attr('onclick', 'deleteHoliday(' + holidayId + ')');

                    if (canValidate(status)) {
                        $(this).remove();
                    }
                }
            });
        });
    }

    function canValidate(status) {
        const roleId = ${user.role.id};
        const rejected = status.children().hasClass('badge-danger');
        const pending = status.children().hasClass('badge-secondary');
        const approved = status.children().hasClass('badge-primary');

        if (approved || rejected) {
            return false;
        } else if (pending && roleId >= ${jspUtil.getRolId('ROLE_RRHH')}) {
            return true;
        }

        return false;
    }

    function validateHoliday(holidayId) {
        showLoading();

        axios.post('/holidays/validate/' + holidayId, { })
            .then(() => dTable.ajax.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }

    function declineHoliday() {
        showLoading();

        const declineModal = $('#declineModal');
        const holidayId = $('#holidayId').val();

        axios.post('/holidays/decline/' + holidayId, $('#declineForm').serialize())
            .then(() => dTable.ajax.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => { hideLoading(); declineModal.modal('hide'); });
    }

    function openModal(holidayId) {
        $('#holidayId').val(holidayId);
        $('#declineModal').modal('show');
    }

    function deleteHoliday(holidayId) {
        const ok = confirm("${jspUtil.parseTagToText('holidays.admin.delete.alert')}");

        if (ok) {
            showLoading();

            axios.delete('/holidays/delete/' + holidayId, { })
                .then(() => dTable.ajax.reload())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

</script>