const usersEndpoint = '/v1/users';

$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
});

function initializeDataTables() {
    let columns = ['id', 'fullName', 'id']
    let endpoint = '/v1/users';
    let actions = [ { action: 'view', url: '/users/{id}', permission: 'read_project' }, { action: 'delete', permission: 'edit_project' }]
    let expand = []
    let filters = [{'leadingProjectId': currentProject.id}]
    let columnDefs = []

    let expensesDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnDefs);
    dTable = createDataTable('#dTable', expensesDataTable, locale);
    expensesDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const params = { roleIds: 4 }; // TODO: change to TEAM_LEADER_ID
    const getCustomName = user => `${user.name} ${user.surnames}`;

    // # CreateForm
    const createForm = document.querySelector('#createForm');

    createSelect2($(createForm.querySelector('[name="userId"]')), usersEndpoint, params, null, getCustomName, 'createForm');
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

        axios.post(endpoint + '/leaders', {
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
    if (confirm(messages.projectLeaders.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete(endpoint + '/leaders/' + id).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
            showNotify(messages.projectLeaders.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
