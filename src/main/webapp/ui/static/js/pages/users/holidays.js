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
        "ajax": "/users/" + currentUser.id + "/holidays/dt",
        "rowId": "uh_id",
        "language": {
            "url": "/ui/static/lang/datatables/" + locale + ".json"
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
    const roleId = currentUser.roleId;
    const rejected = status.children().hasClass('badge-danger');
    const pending = status.children().hasClass('badge-secondary');
    const approved = status.children().hasClass('badge-primary');

    if (approved || rejected) {
        return false;
    } else if (pending && roleId >= 6) {
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
    if (confirm(messages.holidays.delete.alert.replace('{0}', holidayId))) {
        showLoading();

        axios.delete('/holidays/delete/' + holidayId, { })
            .then(() => dTable.ajax.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}