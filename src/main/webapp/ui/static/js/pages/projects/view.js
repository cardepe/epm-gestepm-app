const view = '/projects'
const endpoint = '/v1/projects';
const activityCenterEndpoint = '/v1/activity-centers'
const usersEndpoint = '/v1/users';

$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
    filterByParams();
});

function initializeDataTables() {
    let columns = ['id', 'name', 'activityCenter.name', 'startDate', 'endDate', 'id']
    let actions = [
        {
            action: 'view',
            url: view + '/{id}',
            permission: 'read_projects'
        },
        {
            action: 'delete',
            permission: 'edit_projects'
        }
    ]
    let expand = ['activityCenter']
    let filters = [{ 'role': true }]
    let columnsDef = []

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnsDef);
    dTable = createDataTable('#dTable', customDataTable, locale);
    customDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const params = { roleIds: 4 }; // TODO: change to TEAM_LEADER_ID
    const getCustomName = user => `${user.name} ${user.surnames}`;

    // # CreateForm
    const createForm = document.querySelector('#createForm');

    createBasicSelect2($(createForm.querySelector('[name="isStation"]')), 'createForm');
    createSelect2($(createForm.querySelector('[name="activityCenterId"]')), activityCenterEndpoint, null, null, null, 'createForm');
    createBasicSelect2($(createForm.querySelector('[name="isTeleworking"]')), 'createForm');
    createBasicSelect2($(createForm.querySelector('[name="forumId"]')), 'createForm');
    createSelect2($(createForm.querySelector('[name="responsibleId"]')), usersEndpoint, params, null, getCustomName, 'createForm');

    // # FilterForm
    const filterForm = document.querySelector('#filterForm');

    createBasicSelect2($( filterForm.querySelector('[name="isStation"]')), 'filterForm');
    createSelect2($(filterForm.querySelector('[name="activityCenterId"]')), activityCenterEndpoint, null, null, null, 'filterForm');
    createBasicSelect2($(filterForm.querySelector('[name="isTeleworking"]')), 'filterForm');
    createSelect2($(filterForm.querySelector('[name="responsibleId"]')), usersEndpoint, params, null, getCustomName, 'filterForm');
    createBasicSelect2($(filterForm.querySelector('[name="state"]')), 'filterForm');
}

function filterByParams() {

    const queryParams = new URLSearchParams(window.location.search);

    let filters = [];

    let ids = queryParams.get('ids');
    let nameContains = queryParams.get('nameContains');
    let isStation = queryParams.get('isStation');
    let activityCenterIds = queryParams.get('activityCenterIds');
    let isTeleworking = queryParams.get('isTeleworking');
    let responsibleIds = queryParams.get('responsibleIds');
    let state = queryParams.get('state');
    let pageNumber = queryParams.get('pageNumber');

    const filterForm = document.querySelector('#filterForm');

    filterForm.querySelector('[name="id"]').value = ids;
    filterForm.querySelector('[name="nameContains"]').value = nameContains;
    filterForm.querySelector('[name="isStation"]').value = isStation;
    filterForm.querySelector('[name="activityCenterId"]').value = activityCenterIds;
    filterForm.querySelector('[name="isTeleworking"]').value = isTeleworking;
    filterForm.querySelector('[name="state"]').value = state;
    filterForm.querySelector('[name="responsibleId"]').value = responsibleIds;

    if (ids) { filters.push({ 'ids': ids }); }
    if (nameContains) { filters.push({ 'nameContains': nameContains }); }
    if (isStation) { filters.push({ 'isStation': isStation }); }
    if (activityCenterIds) { filters.push({ 'activityCenterIds': activityCenterIds }); }
    if (isTeleworking) { filters.push({ 'isTeleworking': isTeleworking }); }
    if (responsibleIds) { filters.push({ 'responsibleIds': responsibleIds }); }
    if (state) { filters.push({ 'state': state }); }
    if (pageNumber) { filters.push({ 'pageNumber': pageNumber }); }

    filters.push({ 'role': true}); // TODO: a revisar

    customDataTable.setFilters(filters);
}

function filter(isReset) {

    let filters = [];

    if (!isReset) {

        let form = document.querySelector('#filterForm');

        let id = form.querySelector('[name="id"]');
        let nameContains = form.querySelector('[name="nameContains"]');
        let isStation = form.querySelector('[name="isStation"]');
        let activityCenterId = form.querySelector('[name="activityCenterId"]');
        let isTeleworking = form.querySelector('[name="isTeleworking"]');
        let state = form.querySelector('[name="state"]');
        let responsibleId = form.querySelector('[name="responsibleId"]');

        id.value && filters.push({'ids': id.value});
        nameContains.value && filters.push({'nameContains': nameContains.value});
        isStation.value && filters.push({'isStation': isStation.value});
        activityCenterId.value && filters.push({'activityCenterIds': activityCenterId.value});
        isTeleworking.value && filters.push({'isTeleworking': isTeleworking.value});
        responsibleId.value && filters.push({'responsibleIds': responsibleId.value});
        state.value && filters.push({'state': state.value});
    } else {
        resetForm('#filterForm');
    }

    customDataTable.setFilters(filters);
    dTable.order([[0]]).ajax.reload();
}

function create() {

    const createFromJQ = $('#createForm');

    if (!isValidForm('#createForm')) {
        createFromJQ.addClass('was-validated');
    } else {

        showLoading();
        createFromJQ.removeClass('was-validated');

        const form = document.querySelector('#createForm');

        const name = form.querySelector('[name="name"]').value;
        const isStation = form.querySelector('[name="isStation"]').value;
        const activityCenterId = form.querySelector('[name="activityCenterId"]').value;
        const objectiveCost = form.querySelector('[name="objectiveCost"]').value;
        const startDate = form.querySelector('[name="startDate"]').value;
        const endDate = form.querySelector('[name="endDate"]').value;
        const forumId = form.querySelector('[name="forumId"]').value;
        const isTeleworking = form.querySelector('[name="isTeleworking"]').value;
        const selectResponsible = form.querySelector('[name="responsibleIds"]');
        const responsibleIds = Array.from(selectResponsible.selectedOptions).map(option => option.value);

        axios.post(endpoint, {
            name: name,
            isStation: isStation,
            activityCenterId: activityCenterId,
            objectiveCost: objectiveCost,
            startDate: startDate,
            endDate: endDate,
            forumId: forumId ? forumId : null,
            isTeleworking: isTeleworking,
            responsibleIds: responsibleIds
        }).then((response) => {
            const project = response.data.data;
            window.location.replace(view + '/' + project.id);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}

function remove(id) {

    if (confirm(messages.projects.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete(endpoint + '/' + id).then(() => {
            dTable.ajax.reload(function () {
                dTable.page(dTable.page()).draw(false);
            }, false);
            showNotify(messages.projects.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
