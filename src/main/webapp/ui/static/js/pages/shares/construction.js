const endpoint = '/v1/shares/construction';

function initializeDataTables() {
    let columns = ['id', 'user.name', 'project.name', 'startDate', 'endDate', 'id']
    let endpoint = '/v1/shares/construction';
    let actions = [ { action: 'view', url: '/shares/construction/{id}', permission: 'read_construction_shares' }, { action: 'delete', permission: 'edit_construction_shares' }]
    let expand = [ 'user,project' ]
    let filters = []; // [{'userIds': userId}]
    let orderable = [[0, 'DESC']]
    let columnsDef = [
        {
            targets: [3, 4],
            render: function (data) {
                return data ? moment(data).format('DD-MM-YYYY HH:mm') : null;
            }
        },
    ]

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnsDef);
    dTable = createDataTable('#dTable', customDataTable, locale);
    customDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const createForm = document.querySelector('#createForm');
    const createSelects = createForm.querySelectorAll('select');

    createSelects.forEach(select => {
        createBasicSelect2($(select));
    });

    const filterForm = document.querySelector('#filterForm');
    const filterSelects = filterForm.querySelectorAll('select');

    filterSelects.forEach(select => {
        createBasicSelect2($(select), 'filterForm');
    });
}

function filter(isReset) {

    let filters = [];

    if (!isReset) {

        let form = document.querySelector('#filterForm');

        let id = form.querySelector('[name="id"]');
        let userId = form.querySelector('[name="userId"]');
        let projectId = form.querySelector('[name="projectId"]');
        let status = form.querySelector('[name="status"]');

        id.value && filters.push({ 'ids': id.value });
        userId.value && filters.push({ 'userIds': userId.value });
        projectId.value && filters.push({ 'projectIds': projectId.value });
        status.value && filters.push({ 'status': status.value });
    } else {
        $('#filterForm')[0].reset();
    }

    customDataTable.setFilters(filters)
    dTable.ajax.reload();
}

function create() {

    showLoading();

    const form = document.querySelector('#createForm');

    const projectId = form.querySelector('[name="projectId"]').value;

    axios.post(endpoint, {
        userId: userId,
        projectId: projectId,
        startDate: new Date().toISOString().slice(0, 19)
    }).then((response) => {
        const construction = response.data.data;
        window.location.replace('/shares/construction/' + construction.id);
    }).catch(error => showNotify(error.response.data.detail, 'danger'))
        .finally(() => hideLoading());
}

function remove(id) {

    if (confirm(messages.shares.displacement.delete.alert.replace('{0}', id))) {

        showLoading();

        axios.delete('/v1/shares/construction/' + id).then(() => {
            dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
            showNotify(messages.shares.displacement.delete.success.replace('{0}', id));
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}