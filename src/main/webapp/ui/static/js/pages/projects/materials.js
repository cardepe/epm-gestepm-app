$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
});

function initializeDataTables() {
    let columns = ['nameEs', 'nameFr', 'required', 'id']
    let endpoint = '/v1/projects/' + currentProject.id + '/materials';
    let actions = [ { action: 'edit', permission: 'read_project' }, { action: 'delete', permission: 'edit_project' }]
    let expand = []
    let filters = [{'projectIds': currentProject.id}];
    let columnsDef = [
        {
            targets: 2,
            render: function (data) {
                return data ? messages.commons.yes : messages.commons.no ;
            }
        }
    ]

    let dataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnsDef);
    dTable = createDataTable('#dTable', dataTable, locale);
    dataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    // # CreateForm
    const createForm = document.querySelector('#createForm');

    createBasicSelect2($(createForm.querySelector('[name="required"]')), 'createForm');

    // # EditForm
    const editForm = document.querySelector('#editForm');

    createBasicSelect2($(editForm.querySelector('[name="required"]')), 'editForm');
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

        const nameEs = form.querySelector('[name="nameEs"]').value;
        const nameFr = form.querySelector('[name="nameFr"]').value;
        const required = form.querySelector('[name="required"]').value;

        axios.post(endpoint + '/materials', {
            nameEs: nameEs,
            nameFr: nameFr,
            required: required
        }).then(() => {
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

    axios.get(endpoint + '/materials/' + id).then((response) => {
        const projectMaterial = response.data.data;

        editForm.querySelector('[name="nameEs"]').value = projectMaterial.nameEs;
        editForm.querySelector('[name="nameFr"]').value = projectMaterial.nameFr;
        $(editForm.querySelector('[name="required"]')).val(projectMaterial.required ? 'true' : 'false').trigger('change');

        editElement.setAttribute('data-id', id);
        editModal.modal('show');
    });

    saveBtn.click(function() {

        showLoading();

        const nameEs = editForm.querySelector('[name="nameEs"]').value;
        const nameFr = editForm.querySelector('[name="nameFr"]').value;
        const required = editForm.querySelector('[name="required"]').value;

        axios.put(endpoint + '/materials/' + id, {
            nameEs: nameEs,
            nameFr: nameFr,
            required: required
        }).then(() => {
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
    if (confirm(messages.projectMaterials.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete(endpoint + '/materials/' + id).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
            showNotify(messages.projectMaterials.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
