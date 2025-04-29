const endpoint = '/v1/shares/construction';

function initializeDataTables() {
    let columns = ['id', 'project.name', 'startDate', 'endDate', 'id']
    let endpoint = '/v1/shares/construction';
    let actions = [ { action: 'view', url: '/shares/construction/{id}', permission: 'read_construction_shares' }, { action: 'delete', permission: 'edit_construction_shares' }]
    let expand = [ 'project' ]
    let filters = [{'userIds': userId}]
    let orderable = [[0, 'DESC']]
    let columnsDef = [
        {
            targets: [2, 3],
            render: function (data) {
                return moment(data).format('DD-MM-YYYY HH:mm');
            }
        },
    ]

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnsDef);
    dTable = createDataTable('#dTable', customDataTable, locale);
    customDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
    const form = document.querySelector('#createForm');
    const selects = form.querySelectorAll('select');

    selects.forEach(select => {
        createBootstrapSelect2($(select));
    });
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