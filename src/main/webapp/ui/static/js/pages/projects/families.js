$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
    initializeActions();
});

function initializeDataTables() {
    dTable = $('#dTable').DataTable({
        "lengthChange": false,
        "searching": false,
        "responsive": true,
        "processing": true,
        "serverSide": true,
        "pageLength": 10,
        "ajax": "/v1/projects/" + currentProject.id + "/families/dt",
        "rowId": "fa_id",
        "language": {
            "url": "/ui/static/lang/datatables/" + locale + ".json"
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
            {"defaultContent": "<em class=\"fas fa-edit\"></em><em class=\"far fa-trash-alt\"></em>", "orderable": false, "targets": -1}
        ],
        "dom": "<'top'i>rt<'bottom'p><'clear'>",
        "drawCallback": function () {
            parseEquipmentsActionButtons();
        }
    });
}

function initializeSelects() {
    // # CreateForm
    const createForm = document.querySelector('#createForm');

    createBasicSelect2($(createForm.querySelector('[name="familyId"]')), 'createForm');

    // # EditForm
    const editForm = document.querySelector('#editForm');

    createBasicSelect2($(editForm.querySelector('[name="required"]')), 'editForm');
}

function initializeActions() {
    const uploadBtn = document.getElementById('uploadBtn');
    const fileInput = document.getElementById('fileInput');

    uploadBtn.addEventListener('click', () => fileInput.click());
    fileInput.addEventListener('change', () => uploadFile(fileInput.files[0]));
}

function create() {

    const createModal = $('#createModal');
    const createFromJQ = $('#createForm');

    if (!isValidForm('#createForm')) {
        createFromJQ.addClass('was-validated');
    } else {

        showLoading();
        createFromJQ.removeClass('was-validated');

        const form = document.querySelector('#createForm');

        /*
        const familyId = form.querySelector('[name="familyId"]').value;
        const nameES = form.querySelector('[name="nameES"]').value;
        const nameFR = form.querySelector('[name="nameFR"]').value;
        const brand = form.querySelector('[name="brand"]').value;
        const model = form.querySelector('[name="model"]').value;
        const enrollment = form.querySelector('[name="enrollment"]').value;
        */

        axios.post(endpoint + '/families', createFromJQ.serialize()).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => { hideLoading(); createModal.modal('hide'); });
    }
}

function edit(id) {

    const editModal = $('#editModal');
    const saveBtn = $('#saveBtn');
    const editElement = document.getElementById('editModal');
    const editForm = editElement.querySelector('#editForm');
    const editFormJQ = $('#editForm');

    axios.get(endpoint + '/families/' + id).then((response) => {
        // const projectMaterial = response.data.data;
        const projectFamily = response.data;

        $(editForm.querySelector('[name="familyId"]')).val(projectFamily.familyId ? 'true' : 'false').trigger('change');
        editForm.querySelector('[name="nameES"]').value = projectFamily.nameES;
        editForm.querySelector('[name="nameFR"]').value = projectFamily.nameFR;
        editForm.querySelector('[name="brand"]').value = projectFamily.brand;
        editForm.querySelector('[name="model"]').value = projectFamily.model !== undefined ? projectFamily.model : '';
        editForm.querySelector('[name="enrollment"]').value = projectFamily.enrollment !== undefined ? projectFamily.enrollment : '';

        editElement.setAttribute('data-id', id);
        editModal.modal('show');
    });

    saveBtn.click(function() {

        showLoading();

        /*
        const familyId = editForm.querySelector('[name="familyId"]').value;
        const nameES = editForm.querySelector('[name="nameES"]').value;
        const nameFR = editForm.querySelector('[name="nameFR"]').value;
        const brand = editForm.querySelector('[name="brand"]').value;
        const model = editForm.querySelector('[name="model"]').value;
        const enrollment = editForm.querySelector('[name="enrollment"]').value;
        */

        axios.put(endpoint + '/families/' + id, editFormJQ.serialize()).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => {
                hideLoading();
                editModal.modal('hide');
            });
    });
}

function remove(id) {
    if (confirm(messages.families.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete(endpoint + '/families/' + id).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
            showNotify(messages.families.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}

function uploadFile(file) {
    showLoading();

    const formData = new FormData();
    formData.append('file', file);

    axios.post(endpoint + '/families/import', formData, { headers: { 'Content-Type': 'multipart/form-data' }})
        .then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
        .finally(() => hideLoading());
}

/* TODO: TO REMOVE */
function parseEquipmentsActionButtons() {

    const tableRows = $('#dTable tbody tr');

    tableRows.each(function () {

        const equipmentId = $(this).attr('id');
        const lastColumn = $(this).children().last();
        const emList = lastColumn.children();

        emList.each(function (index) {

            if (index === 0) {
                $(this).attr('onclick', 'edit(' + equipmentId + ')');
            } else if (index === 1) {
                $(this).attr('onclick', 'remove(' + equipmentId + ')');
            }
        });
    });
}
