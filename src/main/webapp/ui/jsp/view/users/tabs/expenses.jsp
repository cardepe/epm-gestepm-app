<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col">
        <div class="table-responsive">
            <table id="dTableExpenses" class="table table-striped table-borderer dataTable w-100">
                <thead>
                <tr>
                    <th><spring:message code="id"/></th>
                    <th><spring:message code="description"/></th>
                    <th><spring:message code="project"/></th>
                    <th><spring:message code="status"/></th>
                    <th><spring:message code="date"/></th>
                    <th><spring:message code="amount"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<div id="declineExpenseModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5 id="modalTitle">
                        <spring:message code="user.detail.expenses.decline.title" />
                    </h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="declineExpenseForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="observations" />
                                    <textarea name="observations" class="form-control" rows="6"></textarea>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-sm btn-success" onclick="declineAction()"><spring:message code="decline" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    let dTableExpenses = null;

    $(document).ready(function() {
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
                            { key: 'status', value: ['PENDING'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: ${user.role.id} }
                        ],
                    },
                    {
                        conditions: [
                            { key: 'status', value: ['APPROVED'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: ${user.role.id} }
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
                            { key: 'status', value: ['PENDING'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: ${user.role.id} }
                        ],
                    },
                    {
                        conditions: [
                            { key: 'status', value: ['APPROVED'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: ${user.role.id} }
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
        let filters = [{'createdBy': ${currentUser.id}}]
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
                    return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(data);
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnDefs);
        dTableExpenses = createDataTable('#dTableExpenses', customDataTable, locale);
        customDataTable.setCurrentTable(dTableExpenses);
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

        const data = dTableExpenses.row("#" + personalExpenseSheetId).data();
        const nextStatus = getNextStatus(data.status);

        if (nextStatus) {
            axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
                status: nextStatus
            }).then(() => {
                dTableExpenses.ajax.reload();
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
            dTableExpenses.ajax.reload();
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

</script>