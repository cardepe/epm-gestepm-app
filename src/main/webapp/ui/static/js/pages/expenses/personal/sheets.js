function initializeDataTables() {
    let columns = ['id', 'description', 'project.name', 'status', 'startDate', 'id']
    let endpoint = '/v1/expenses/personal/sheets';
    let actions = [
        {
            action: 'file-pdf',
            url: '/v1/expenses/personal/sheets/{id}/export',
            permission: 'edit_personal_expenses_sheet'
        },
        {
            action: 'view',
            url: '/expenses/personal/sheets/{id}',
            permission: 'edit_personal_expenses_sheet'
        },
        {
            action: 'delete',
            permission: 'edit_personal_expenses_sheet'
        }
    ]
    let expand = ['project']
    let filters = [{'userId': userId}]
    let orderable = [[0, 'DESC']]
    let columnDefs = [
        {
            targets: 3,
            render: function (data) {
                return parseStatusToBadge(data);
            }
        },
        {
            targets: 4,
            render: function (data) {
                return moment(data).format('DD-MM-YYYY HH:mm');
            }
        }
    ]

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnDefs);
    createDataTable('#dTable', customDataTable, locale);
}

function initializeSelects() {
    const form = document.querySelector('#createForm');
    const selects = form.querySelectorAll('select');

    selects.forEach(select => {
        $(select).selectpicker({
            liveSearch: true,
            noneSelectedText: messages.select.placeholder
        });
    });
}

function parseStatusToBadge(status) {
    if (status === 'PENDING') {
        return '<div class="badge badge-warning">' + messages.status.pending + '</div>';
    } else if (status === 'APPROVED') {
        return '<span class="badge badge-primary">' + messages.status.approved + '</span>';
    } else if (status === 'PAID') {
        return '<span class="badge badge-success">' + messages.status.paid + '</span>';
    } else if (status === 'REJECTED') {
        return '<span class="badge badge-danger">' + messages.status.rejected + '</span>';
    }
}

function createPersonalExpenseSheet() {
    const form = document.querySelector('#createForm');
    const projectId = form.querySelector('[name="projectId"]').value;
    const description = form.querySelector('[name="description"]').value;

    showLoading();

    axios.post('/v1/expenses/personal/sheets', {
        userId: userId,
        projectId: projectId,
        description: description
    }).then((response) => {
        const id = response.data.data.id;
        window.location.href = '/expenses/personal/sheets/' + id;
    }).catch(error => showNotify(error, 'danger'))
        .finally(() => {
            hideLoading();
            $('#createModal').modal('hide');
        });
}