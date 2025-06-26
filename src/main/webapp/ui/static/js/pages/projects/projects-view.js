const endpoint = '/v1/projects';
const activityCenterEndpoint = '/v1/activity-centers'
const usersEndpoint = '/v1/users';

$(document).ready(function() {
    initializeDataTables();
    initializeSelects();
    filterByParams();
});

function initializeDataTables() {
    let columns = ['id', 'name', 'activityCenter.name', 'id']
    let actions = [
        {
            action: 'view',
            permission: 'view_projects'
        }
    ]
    let expand = ['activityCenter']

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand);
    dTable = createDataTable('#dTable', customDataTable, locale);
    customDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const params = { roleIds: 4 }; // TODO: change to TEAM_LEADER_ID
    const getCustomName = user => `${user.name} ${user.surnames}`;

    // # FilterForm
    const filterForm = document.querySelector('#filterForm');

    createBasicSelect2($( filterForm.querySelector('[name="isStation"]')), 'filterForm');
    createSelect2($(filterForm.querySelector('[name="activityCenterId"]')), activityCenterEndpoint, null, null, null, 'filterForm');
    createBasicSelect2($(filterForm.querySelector('[name="isTeleworking"]')), 'filterForm');
    createSelect2($(filterForm.querySelector('[name="responsibleId"]')), usersEndpoint, params, null, getCustomName, 'filterForm');
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
    let pageNumber = queryParams.get('pageNumber');

    const filterForm = document.querySelector('#filterForm');

    filterForm.querySelector('[name="id"]').value = ids;
    filterForm.querySelector('[name="nameContains"]').value = nameContains;
    filterForm.querySelector('[name="isStation"]').value = isStation;
    filterForm.querySelector('[name="activityCenterId"]').value = activityCenterIds;
    filterForm.querySelector('[name="isTeleworking"]').value = isTeleworking;
    filterForm.querySelector('[name="responsibleId"]').value = responsibleIds;

    if (ids) { filters.push({ 'ids': ids }); }
    if (nameContains) { filters.push({ 'nameContains': nameContains }); }
    if (isStation) { filters.push({ 'isStation': isStation }); }
    if (activityCenterIds) { filters.push({ 'activityCenterIds': activityCenterIds }); }
    if (isTeleworking) { filters.push({ 'isTeleworking': isTeleworking }); }
    if (responsibleIds) { filters.push({ 'responsibleIds': responsibleIds }); }
    if (pageNumber) { filters.push({ 'pageNumber': pageNumber }); }

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
        let responsibleId = form.querySelector('[name="responsibleId"]');

        id.value && filters.push({'ids': id.value});
        nameContains.value && filters.push({'nameContains': nameContains.value});
        isStation.value && filters.push({'isStation': isStation.value});
        activityCenterId.value && filters.push({'activityCenterIds': activityCenterId.value});
        isTeleworking.value && filters.push({'isTeleworking': isTeleworking.value});
        responsibleId.value && filters.push({'responsibleIds': responsibleId.value});
    } else {
        resetForm('#filterForm');
    }

    customDataTable.setFilters(filters);
    dTable.order([[0]]).ajax.reload();
}

async function view(id) {

    const project = await getProjectById(id);
    const responsible = project.responsible;

    const htmlCode = responsible
        .map(r => `- ${r.name} ${r.surnames} (${r.email})`)
        .join('<br>');

    $('.modal-body').html(htmlCode);

    $('#viewModal').modal('show');
}

async function getProjectById(id) {
    const response = await axios.get('/v1/projects/' + id, { params: { _expand: 'responsible' } });
    console.log(response);
    return response.data.data;
}