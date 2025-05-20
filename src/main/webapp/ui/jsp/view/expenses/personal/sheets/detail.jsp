<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/css/bootstrap-select.min.css">

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <div id="action-badge" class="badge"></div>
                        <h1 class="text-uppercase pt-1 pb-0"><spring:message code="personal.expenses.sheet"/></h1>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-2">
                <div class="page-header float-right">
                    <a class="btn btn-default btn-sm"
                       href="${pageContext.request.contextPath}/expenses/personal/sheets"><spring:message
                            code="back"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="card">
        <div class="card-body">
            <div class="title mb-0">
                <spring:message code="info"/>
            </div>

            <form id="editForm" class="needs-validation">
                <div class="row">
                    <div class="col">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="description"/>
                                <input name="description" type="text" class="form-control mt-1"/>
                            </label>
                        </div>
                    </div>

                    <div class="col">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="project"/>
                                <select class="form-control input" name="projectId" required>
                                    <option></option>
                                    <c:forEach items="${projects}" var="project">
                                        <option value="${project.id}"><spring:message code="${project.name}"/></option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="start.date"/>
                                <input name="startDate" type="datetime-local" class="form-control mt-1" disabled/>
                            </label>
                        </div>
                    </div>

                    <div class="col">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message
                                    code="shares.displacement.observations"/>
                                <textarea name="observations" type="text" class="form-control mt-1" disabled></textarea>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-right">
                        <button id="sendBtn" type="button" class="btn btn-danger btn-sm movile-full"><spring:message
                                code="send.to.administration"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col">
                    <div class="title mb-0">
                        <spring:message code="personal.expenses"/>
                    </div>
                </div>
                <div class="col text-right">
                    <button id="createBtn" type="button" class="btn btn-default btn-sm" data-toggle="modal"
                            data-target="#personalExpenseModal">
                        <spring:message code="personal.expense.add"/>
                    </button>
                </div>
            </div>

            <div class="table-responsive">
                <table id="dTable" class="table table-striped table-borderer dataTable">
                    <thead>
                    <tr>
                        <th><spring:message code="id"/></th>
                        <th><spring:message code="description"/></th>
                        <th><spring:message code="date"/></th>
                        <th><spring:message code="type"/></th>
                        <th><spring:message code="quantity"/></th>
                        <th><spring:message code="amount"/></th>
                        <th><spring:message code="payment.type"/></th>
                        <th><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="personal-expense-modal.jsp"/>

<script>
    let locale = '${locale}';
    let personalExpenseSheet;

    async function getPersonalExpenseShare() {
        await axios.get('/v1' + window.location.pathname).then((response) => {
            personalExpenseSheet = response.data.data;
        }).catch(error => showNotify(error.response.data.detail, 'danger'));
    }

    function init() {
        loadHeader();
        initializeSelects();
        initializeDataTables();

        if (personalExpenseSheet.status) {
            document.querySelector('#createBtn').remove();
        }
    }

    function loadHeader() {
        const actionBadge = document.querySelector('#action-badge');

        if (personalExpenseSheet.status === 'PENDING') {
            actionBadge.classList.add('badge-warning');
            actionBadge.textContent = messages.status.pending;
        } else if (personalExpenseSheet.status === 'APPROVED') {
            actionBadge.classList.add('badge-primary');
            actionBadge.textContent = messages.status.approved;
        } else if (personalExpenseSheet.status === 'PAID') {
            actionBadge.classList.add('badge-success');
            actionBadge.textContent = messages.status.paid;
        } else if (personalExpenseSheet.status === 'REJECTED') {
            actionBadge.classList.add('badge-danger');
            actionBadge.textContent = messages.status.rejected;
        }
    }

    function initializeSelects() {
        const form = document.querySelector('#editForm');
        const selects = form.querySelectorAll('select');

        selects.forEach(select => {
            $(select).selectpicker({
                liveSearch: true,
                noneSelectedText: messages.select2.placeholder
            });
        });
    }

    function initializeDataTables() {
        let columns = ['id', 'description', 'startDate', 'priceType', 'quantity', 'amount', 'paymentType', 'id']
        let endpoint = '/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses';
        let actions = !personalExpenseSheet.status ? [
            {
                action: 'edit',
                permission: 'edit_personal_expense'
            },
            {
                action: 'delete',
                permission: 'edit_personal_expense'
            }
        ] : []
        let expand = []
        let filters = [{'personalExpenseSheetId': personalExpenseSheet.id}]
        let columnDefs = [
            {
                targets: 2,
                render: function (data) {
                    return moment(data).format('DD-MM-YYYY HH:mm');
                }
            },
            {
                targets: 3,
                render: function (data) {
                    return parseTypeToBadge(data);
                }
            },
            {
                targets: 5,
                render: function (data) {
                    return data + '€';
                }
            },
            {
                targets: 6,
                render: function (data) {
                    return parsePaymentTypeToBadge(data);
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnDefs);
        dTable = createDataTable('#dTable', customDataTable, locale);
        customDataTable.setCurrentTable(dTable);
    }

    function update() {
        const editForm = document.querySelector('#editForm');
        editForm.querySelector('[name="description"]').value = personalExpenseSheet.description;

        const project = editForm.querySelector('[name="projectId"]')
        project.value = personalExpenseSheet.project.id;
        $(project).selectpicker('refresh');

        editForm.querySelector('[name="startDate"]').value = personalExpenseSheet.createdAt;

        const observations = editForm.querySelector('[name="observations"]');
        if (observations.value) {
            observations.value = personalExpenseSheet.observations;
        }

        if (personalExpenseSheet.status) {
            editForm.querySelector('#sendBtn').remove();
            disableForm('#editForm');
        }
    }

    function send() {
        const sendBtn = $('#sendBtn');
        const editForm = document.querySelector('#editForm');

        sendBtn.click(async () => {
            if (personalExpenseSheet.status) {
                showNotify(messages.personalExpenseSheet.invalidStatus, 'info');
                return;
            }

            showLoading();

            const description = editForm.querySelector('[name="description"]');
            const projectId = editForm.querySelector('[name="projectId"]');

            axios.patch('/v1' + window.location.pathname, {
                description: description ? description.value : null,
                projectId: projectId ? projectId.value : null,
                status: 'PENDING'
            }).then((response) => {
                location.reload();
            }).catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        });
    }

    function remove(id) {
        const alertMessage = messages.personalExpense.delete.alert.replace('{0}', id);
        if (confirm(alertMessage)) {

            showLoading();

            axios.delete('/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses/' + id).then(() => {
                dTable.ajax.reload();
                const successMessage = messages.personalExpense.delete.success.replace('{0}', id);
                showNotify(successMessage);
            }).catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

    function parseTypeToBadge(priceType) {
        if (priceType === 'FLY') {
            return '<div class="badge badge-primary">' + messages.priceType.fly + '</div>';
        } else if (priceType === 'FOOD') {
            return '<div class="badge badge-warning">' + messages.priceType.food + '</div>';
        } else if (priceType === 'GAS') {
            return '<div class="badge badge-muted">' + messages.priceType.gas + '</div>';
        } else if (priceType === 'GASOIL') {
            return '<div class="badge badge-secondary">' + messages.priceType.gasoil + '</div>';
        } else if (priceType === 'GASOLINE') {
            return '<div class="badge badge-secondary">' + messages.priceType.gasoline + '</div>';
        } else if (priceType === 'HOTEL') {
            return '<div class="badge badge-success">' + messages.priceType.hotel + '</div>';
        } else if (priceType === 'KMS') {
            return '<div class="badge badge-info">' + messages.priceType.kms + '</div>';
        } else if (priceType === 'OTHER') {
            return '<div class="badge badge-danger">' + messages.priceType.other + '</div>';
        } else if (priceType === 'PARKING') {
            return '<div class="badge badge-dark">' + messages.priceType.parking + '</div>';
        }
    }

    function parsePaymentTypeToBadge(paymentType) {
        if (paymentType === 'EPM_TARGET') {
            return '<div class="badge badge-success">' + messages.paymentType.epmTarget + '</div>';
        } else if (paymentType === 'OTHER') {
            return '<div class="badge badge-danger">' + messages.paymentType.other + '</div>';
        }
    }

    $(document).ready(async function () {
        await getPersonalExpenseShare();
        init();
        update();
        send();
    });

</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
