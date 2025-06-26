const usersEndpoint = '/v1/users';

$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
});

function initializeDataTables() {
    let columns = ['id', 'fullName', 'level.name', 'id']
    let actions = [ { action: 'view', url: '/users/{id}', permission: 'read_project' }, { action: 'delete', permission: 'edit_project' }]
    let expand = ['level']
    let filters = [{'memberProjectId': currentProject.id}]
    let columnDefs = []

    let dataTable = new CustomDataTable(columns, usersEndpoint, null, actions, expand, filters, columnDefs);
    dTable = createDataTable('#dTable', dataTable, locale);
    dataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const getCustomName = user => `${user.name} ${user.surnames}`;

    // # CreateForm
    const createForm = document.querySelector('#createForm');

    createSelect2($(createForm.querySelector('[name="userId"]')), usersEndpoint, null, null, getCustomName, 'createForm');
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

        const userId = form.querySelector('[name="userId"]').value;

        axios.post(endpoint + '/members', {
            userId: userId
        }).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => { hideLoading(); createModal.modal('hide'); });
    }
}

function remove(id) {
    if (confirm(messages.projectMembers.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete(endpoint + '/members/' + id).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
            showNotify(messages.projectMembers.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
