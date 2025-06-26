$(document).ready(function () {
    initializeDataTables();
});

function initializeDataTables() {
    let columns = ['id', 'description', 'project.name', 'status', 'createdAt', 'amount', 'id']
    let endpoint = '/v1/expenses/personal/sheets';
    let actions = [
        {
            action: 'file-pdf',
            url: '/v1/expenses/personal/sheets/{id}/export',
            permission: 'edit_personal_expenses_sheet'
        },
        {
            action: 'validate',
            permission: 'edit_personal_expenses_sheet',
            conditionGroups: [
                {
                    conditions: [
                        {key: 'status', value: ['PENDING'], operation: '==='},
                        {key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: currentUser.roleId}
                    ],
                },
                {
                    conditions: [
                        {key: 'status', value: ['APPROVED'], operation: '==='},
                        {key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: currentUser.roleId}
                    ]
                }
            ]
        },
        {
            action: 'decline',
            permission: 'edit_personal_expenses_sheet',
            conditionGroups: [
                {
                    conditions: [
                        {key: 'status', value: ['PENDING'], operation: '==='},
                        {key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: currentUser.roleId}
                    ],
                },
                {
                    conditions: [
                        {key: 'status', value: ['APPROVED'], operation: '==='},
                        {key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: currentUser.roleId}
                    ]
                }
            ]
        },
        {
            action: 'view',
            url: '/expenses/personal/sheets/{id}',
            permission: 'edit_personal_expenses_sheet'
        }
    ]
    let expand = ['project']
    let filters = [{'createdBy': currentUser.id}]
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
        },
        {
            targets: 5,
            render: function (data) {
                return new Intl.NumberFormat('es-ES', {style: 'currency', currency: 'EUR'}).format(data);
            }
        }
    ]

    customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnDefs);
    dTable = createDataTable('#dTable', customDataTable, locale);
    customDataTable.setCurrentTable(dTable);
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

function validate(personalExpenseSheetId) {

    showLoading();

    const data = dTable.row("#" + personalExpenseSheetId).data();
    const nextStatus = getNextStatus(data.status);

    if (nextStatus) {
        axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
            status: nextStatus
        }).then(() => {
            dTable.ajax.reload();
            showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}

function decline(personalExpenseSheetId) {
    const modal = $('#declineExpenseModal');
    modal.attr('data-id', personalExpenseSheetId);
    modal.modal('show');
}

function declineAction() {
    showLoading();

    const modal = $('#declineExpenseModal');
    const personalExpenseSheetId = modal.data('id');
    const form = document.querySelector('#declineExpenseForm');
    const obervations = form.querySelector('[name="observations"]')

    axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
        status: 'REJECTED',
        observations: obervations.value
    }).then(() => {
        dTable.ajax.reload();
        showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
    }).catch(error => showNotify(error.response.data.detail, 'danger'))
        .finally(() => {
            hideLoading();
            modal.modal('hide');
        });
}

function getNextStatus(status) {
    if (status === 'PENDING') {
        return 'APPROVED';
    } else if (status === 'APPROVED') {
        return 'PAID';
    }
    return null;
}