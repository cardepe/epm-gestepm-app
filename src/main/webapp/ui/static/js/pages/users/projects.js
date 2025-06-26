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
    let filters = [{ 'memberIds': currentUser.id }];
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
            userId: currentUser.id
    }).then(() => dTable.ajax.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => { hideLoading(); createModal.modal('hide'); });
    }
}

function remove(projectId) {
    if (confirm(messages.projects.delete.alert.replace('{0}', projectId))) {
        showLoading();

        axios.delete('/v1/projects/' + projectId + '/members/' + currentUser.id, { })
            .then(() => dTable.ajax.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}