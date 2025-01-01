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
                                <input name="startDate" type="datetime-local" class="form-control mt-1" disabled />
                            </label>
                        </div>
                    </div>

                    <div class="col">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="shares.displacement.observations"/>
                                <textarea name="observations" type="text" class="form-control mt-1" disabled></textarea>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-right">
                        <button id="editBtn" type="button" class="btn btn-standard btn-sm movile-full"><spring:message
                                code="save"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="title mb-0">
                <spring:message code="personal.expenses"/>
            </div>

            <div class="table-responsive">
                <table id="dTable" class="table table-striped table-borderer dataTable">
                    <thead>
                    <tr>
                        <th><spring:message code="id"/></th>
                        <th><spring:message code="description"/></th>
                        <th><spring:message code="type"/></th>
                        <th><spring:message code="start.date"/></th>
                        <th><spring:message code="amount"/></th>
                        <th><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    let locale = '${locale}';
    let personalExpenseSheet;

    async function getPersonalExpenseShare() {
        await axios.get('/v1' + window.location.pathname).then((response) => {
            personalExpenseSheet = response.data.data;
        }).catch(error => showNotify(error, 'danger'));
    }

    function init() {
        loadHeader();
        initializeSelects();
        // initializeDataTables();
    }

    function loadHeader() {
        const actionBadge = document.querySelector('#action-badge');

        if (personalExpenseSheet.status === 'PENDING') {
            actionBadge.classList.add('badge-info');
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
                noneSelectedText: messages.select.placeholder
            });
        });
    }

    function initializeDataTables() {
        let columns = ['id', 'description', 'priceType', 'startDate', 'amount', 'id']
        let endpoint = '/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses';
        let actions = [
            {
                action: 'edit',
                permission: 'edit_personal_expense'
            },
            {
                action: 'delete',
                permission: 'edit_personal_expense'
            }
        ]
        let expand = []
        let filters = []
        let orderable = [[0, 'DESC']]
        let columnDefs = [
            /*{
                targets: 3,
                render: function (data) {
                    return parseStatusToBadge(data);
                }
            },*/
            {
                targets: 3,
                render: function (data) {
                    return moment(data).format('DD-MM-YYYY HH:mm');
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnDefs);
        createDataTable('#dTable', customDataTable, locale);
    }

    function update() {
        const editForm = document.querySelector('#editForm');
        editForm.querySelector('[name="description"]').value = personalExpenseSheet.description;

        const project = editForm.querySelector('[name="projectId"]')
        project.value = personalExpenseSheet.project.id;
        $(project).selectpicker('refresh');

        editForm.querySelector('[name="startDate"]').value = personalExpenseSheet.startDate;

        const observations = editForm.querySelector('[name="observations"]');
        if (observations.value) {
            observations.value = personalExpenseSheet.observations;
        }

        if (personalExpenseSheet.status !== 'PENDING') {
            editForm.querySelector('#editBtn').remove();
            disableForm('#editForm');
        }
    }

    function save() {
        const editBtn = $('#editBtn');
        const editForm = document.querySelector('#editForm');

        editBtn.click(async () => {
            showLoading();

            const description = editForm.querySelector('[name="description"]');
            const projectId = editForm.querySelector('[name="projectId"]');

            axios.patch('/v1' + window.location.pathname, {
                description: description ? description.value : null,
                projectId: projectId ? projectId.value : null
            }).then((response) => {
                personalExpenseSheet = response.data.data;
                showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheet.id))
            }).catch(error => showNotify(error, 'danger'))
                .finally(() => hideLoading());
        })
    }

    $(document).ready(async function () {
        await getPersonalExpenseShare();
        init();
        update();
        save();
    });

</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
