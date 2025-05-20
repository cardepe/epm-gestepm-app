<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <div id="action-badge" class="badge"></div>
                        <h1 id="title" class="text-uppercase pt-1 pb-0"></h1>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-2">
                <div class="page-header float-right">
                    <a id="backButton" class="btn btn-default btn-sm"><spring:message code="back"/></a>
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
                <spring:message code="inspection" />
            </div>

            <jsp:include page="inspection-form.jsp" />
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col">
                    <div class="title mb-0">
                        <spring:message code="breaks"/>
                    </div>
                </div>
                <div class="col text-right">
                    <c:if test="${inspection.endDate == null}">
                        <c:if test="${empty currentShareBreak}">
                            <button type="button" class="btn btn-default btn-sm" onclick="startBreak()">
                                <spring:message code="breaks.start"/>
                            </button>
                        </c:if>

                        <c:if test="${not empty currentShareBreak}">
                            <button type="button" class="btn btn-danger btn-sm" onclick="finishBreak()">
                                <spring:message code="breaks.end"/>
                            </button>
                        </c:if>
                    </c:if>
                </div>
            </div>

            <div class="table-responsive">
                <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                    <thead>
                    <tr>
                        <th><spring:message code="start.date"/></th>
                        <th><spring:message code="end.date"/></th>
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
    let share;
    let inspection;
    const isShareFinished = ${ inspection.endDate != null };

    async function getInspection() {
        await axios.get('/v1' + window.location.pathname, { params: { _expand: 'files,firstTechnical,secondTechnical' }}).then((response) => {
            inspection = response.data.data;
        }).catch(error => showNotify(error.response.data.detail, 'danger'));
    }

    async function getShare(id) {
        await axios.get('/v1/shares/no-programmed/' + id).then((response) => {
            share = response.data.data;
        }).catch(error => showNotify(error.response.data.detail, 'danger'));
    }

    function init() {
        loadHeader();
        loadSignatures();
    }

    function loadHeader() {
        const actionBadge = document.querySelector('#action-badge');
        const pageTitle = document.querySelector('#title');
        const backButton = document.querySelector('#backButton');

        if (inspection.action === 'INTERVENTION') {
            actionBadge.classList.add('badge-warning');
            actionBadge.textContent = messages.inspections.intervention;
        } else if (inspection.action === 'DIAGNOSIS') {
            actionBadge.classList.add('badge-success');
            actionBadge.textContent = messages.inspections.diagnosis;
        } else if (inspection.action === 'FOLLOWING') {
            actionBadge.classList.add('badge-info');
            actionBadge.textContent = messages.inspections.following;
        }

        pageTitle.textContent = messages.inspections.title.replace('{0}', inspection.id);
        backButton.href = '/shares/no-programmed/' + inspection.share.id;
    }

    function loadDataTables() {
        let columns = ['startDate', 'endDate', 'id']
        let endpoint = '/v1/shares/no-programmed/${inspection.shareId}/inspections/${inspection.id}/breaks';
        let actions = []
        if (!isShareFinished) {
            actions.push(
                {
                    action: 'delete',
                    permission: 'edit_inspections',
                    conditionGroups: [
                        {
                            conditions: [
                                { key: 'endDate', value: [ undefined ], operation: '!==' }
                            ]
                        }
                    ]
                }
            );
        }
        let expand = []
        let columnDefs = [
            {
                targets: [0, 1],
                render: function (data) {
                    return data ? parseDate(data, 'DD-MM-YYYY HH:mm') : '-';
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, null, columnDefs);
        dTable = createDataTable('#dTable', customDataTable, locale);
        customDataTable.setCurrentTable(dTable);
    }

    function startBreak() {
        showLoading();

        axios.post('/v1/shares/no-programmed/${inspection.shareId}/inspections/${inspection.id}/breaks', { })
            .then(() => location.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }

    function finishBreak() {
        showLoading();

        axios.put('/v1/shares/no-programmed/${inspection.shareId}/inspections/${inspection.id}/breaks/${currentShareBreak.id}', { })
            .then(() => location.reload())
            .catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }

    function remove(id) {
        const alertMessage = messages.breaks.delete.alert;
        if (confirm(alertMessage)) {

            showLoading();

            axios.delete('/v1/shares/no-programmed/${inspection.shareId}/inspections/${inspection.id}/breaks/' + id).then(() => {
                dTable.ajax.reload();
                const successMessage = messages.breaks.delete.success;
                showNotify(successMessage);
            }).catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

    $(document).ready(async function () {
        await getInspection();
        await getShare(inspection.share.id);
        init();
        update();
        save();
        loadDataTables();
    });

</script>