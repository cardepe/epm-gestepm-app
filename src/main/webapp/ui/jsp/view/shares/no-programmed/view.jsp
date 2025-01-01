<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .content {
        height: auto;
    }
</style>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col-12 col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 id="title" class="text-uppercase p-0"></h1>
                        <a id="forumTitle" class="page-sub-title p-0"></a>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-2">
                <div class="page-header float-right">
                    <a id="returnBtn" class="btn btn-default btn-sm"><spring:message code="back"/></a>
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
                <spring:message code="noprogrammedshare" />
            </div>

            <form id="editForm" class="needs-validation">
                <div class="row">
                    <div class="col-12 col-lg-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="family"/>
                                <select name="familyId" class="form-control" required>
                                    <option><spring:message code="family.selection"/></option>
                                    <c:forEach items="${families}" var="family">
                                        <option value="${family.id}">
                                            <c:choose>
                                                <c:when test="${locale == 'es'}">${family.nameES}</c:when>
                                                <c:otherwise>${family.nameFR}</c:otherwise>
                                            </c:choose>
                                        </option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                    </div>

                    <div class="col-12 col-lg-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="subfamily"/>
                                <select name="subFamilyId" class="form-control" required>
                                    <option selected><spring:message code="subfamily.selection"/></option>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 col-lg-6">
                        <div class="d-flex flex-wrap gap-2">
                            <div id="filesFormGroup" class="form-group">
                                <label class="col-form-label w-100"><spring:message code="files"/>
                                    <input type="file" class="form-control" name="files" accept=".jpg, .jpeg, .png" multiple>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 col-md-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="operator.name"/>
                                <input type="text" name="user" class="form-control" readonly/>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12">
                        <div class="form-group mb-0">
                            <spring:message code="description.placeholder" var="placeholder"/>
                            <label class="col-form-label w-100">
                                <spring:message code="description"/>
                                <textarea class="form-control" placeholder="${placeholder}" name="description" required></textarea>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row mt-2 actionable">
                    <div class="col text-right">
                        <button id="finishBtn" type="button" class="btn btn-danger btn-sm">Finalizar</button>
                        <button id="editBtn" type="button" class="btn btn-standard btn-sm movile-full"><spring:message code="save"/></button>
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
                        <spring:message code="inspection"/>
                    </div>
                </div>
                <div class="col text-right">
                    <button id="createInspectionBtn" type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#createModal">
                        <spring:message code="inspection.add"/>
                    </button>
                </div>
            </div>

            <div class="table-responsive">
                <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                    <thead>
                        <tr>
                            <th><spring:message code="id"/></th>
                            <th><spring:message code="start.date"/></th>
                            <th><spring:message code="end.date"/></th>
                            <th><spring:message code="action"/></th>
                            <th><spring:message code="actions"/></th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="noprogrammedshare" /></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="createForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="action" />
                                    <select name="action" class="form-control" required>
                                        <option value="DIAGNOSIS"><spring:message code="inspection.type.diagnosis" /></option>
                                        <option value="INTERVENTION"><spring:message code="inspection.type.intervention" /></option>
                                        <option value="FOLLOWING"><spring:message code="inspection.type.following" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="inspection.second.technical" />
                                    <select name="secondTechnical" class="form-control" required>
                                        <option value="" disabled selected="selected">
                                            <spring:message code="shares.intervention.create.technical.placeholder" />
                                        </option>
                                        <c:forEach items="${usersTeam}" var="technical">
                                            <c:if test="${ user.id != technical.userId }">
                                                <option value="${technical.userId}">
                                                    <spring:message code="${technical.name} ${technical.surnames}" />
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close" />
                        </button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-default btn-sm" onclick="createInspection()">
                            <spring:message code="create" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .file-url {
        display: block;
        font-size: 12px;
        color: blue;
        font-style: italic;
    }
</style>

<script>

    let locale = '${locale}';

    let lastPageUrl;
    let returnBtn = $('#returnBtn');

    let share;
    let nextAction = '${nextAction}';
    let currentMode;

    async function init() {
        const editForm = document.querySelector('#editForm');
        const shareId = getShareId();

        await getShare(shareId);

        const familyElement = editForm.querySelector('[name="familyId"]');

        familyElement.onchange = () => {
            updateSubFamilySelect();
        }

        updateModalAction(nextAction);
    }

    function getShareId() {
        const currentUrl = window.location.href;
        const segments = currentUrl.split('/');

        return segments[segments.length - 1];
    }

    async function getShare(id) {
        await axios.get('/v1/shares/no-programmed/' + id, {
            params: {
                _expand: 'family,subFamily,user,project,files',
                locale: locale
            }
        }).then((response) => {
            share = response.data.data;
        }).catch(error => showNotify(error, 'danger'));
    }

    function edit(id) {
        const editBtn = $('#editBtn');
        const editForm = document.querySelector('#editForm');

        editBtn.click(async () => {
            if (!validateForm('#editForm')) {
                return;
            }

            showLoading();

            const files = editForm.querySelector('[name="files"]').files;
            let filesData = [];

            for (let i = 0; i < files.length; i++) {
                const file = files[i];

                filesData.push({
                    name: file.name,
                    content: await toBase64(file)
                });
            }

            axios.patch('/v1/shares/no-programmed/' + id, {
                familyId: editForm.querySelector('[name="familyId"]').value,
                subFamilyId: editForm.querySelector('[name="subFamilyId"]').value,
                description: editForm.querySelector('[name="description"]').value,
                state: 'INITIALIZED',
                files: filesData
            }).then((response) => {
                share = response.data.data;
                share.files = filesData;
                setWorkingMode();
                showNotify(messages.shares.noprogrammed.update.success)
            }).catch(error => showNotify(error, 'danger'))
                .finally(() => hideLoading());
        })
    }

    function validateForm(formId) {
        let isValid = true;
        const $form = $(formId);

        $form.find('[required]').each(function () {
            const $field = $(this);
            const value = $field.val().trim();

            if (!value) {
                isValid = false;
                $field.addClass('is-invalid');
            } else {
                $field.removeClass('is-invalid');
            }
        });

        return isValid;
    }

    function close(id) {
        const finishBtn = $('#finishBtn');

        finishBtn.click(async () => {
            showLoading();

            axios.patch('/v1/shares/no-programmed/' + id, {
                state: 'CLOSED'
            }).then((response) => {
                share = response.data.data;
                setCompletedMode();
                showNotify(messages.shares.noprogrammed.update.success)
            }).catch(error => showNotify(error, 'danger'))
                .finally(() => hideLoading());
        });
    }

    function update(share) {
        const form = document.querySelector('#editForm');

        document.querySelector('#title').textContent = messages.shares.noprogrammed.title.replace('{0}', share.id) + ' - ' + share.project.name;

        if (share.family) {
            form.querySelector('[name="familyId"]').value = share.family.id;
        }

        if (share.subFamily) {
            const subFamily = form.querySelector('[name="subFamilyId"]');

            const newOption = document.createElement('option');
            newOption.value = share.subFamily.id;
            newOption.textContent = share.subFamily.name;

            subFamily.appendChild(newOption);
            subFamily.value = newOption.value;
        }

        if (share.user) {
            form.querySelector('[name="user"]').value = share.user.name + ' ' + share.user.surnames;
        }

        if (share.description) {
            form.querySelector('[name="description"]').value = share.description;
        }

        if (share.forumTitle) {
            const forumTitle = document.querySelector('#forumTitle');
            forumTitle.textContent = share.forumTitle;
            forumTitle.href = '${forumUrl}';
            forumTitle.target = '_blank';
        }

        if (share.state === 'NEW') {
            setInitialMode();
        } else if (share.state === 'INITIALIZED' || share.state === 'IN_PROGRESS') {
            setWorkingMode();
        } else {
            setCompletedMode();
        }
    }

    function updateSubFamilySelect() {
        // FIXME: to change for new API v2
        const form = document.querySelector('#editForm');
        const familyId = form.querySelector('[name="familyId"]').value;

        if (familyId) {
            axios.get('/admin/families/' + familyId + '/subfamilies')
                .then((response) => {
                    const subFamily = form.querySelector('[name="subFamilyId"]');
                    subFamily.innerHTML = '';

                    appendElementToList({name: '<spring:message code="family.selection" />'}, subFamily);

                    response.data.forEach((element) => {
                        appendElementToList(element, subFamily);
                    }).catch(error => showNotify(error, 'danger'));
                })
        }
    }

    function setInitialMode() {
        document.querySelector('#finishBtn').classList.add('d-none');
        document.querySelector('#createInspectionBtn').classList.add('d-none');

        currentMode = 'INITIAL';
    }

    function setWorkingMode() {
        document.querySelector('#editBtn').remove();
        document.querySelector('#finishBtn').classList.remove('d-none');
        document.querySelector('#createInspectionBtn').classList.remove('d-none');

        disableForm('#editForm');
        showFiles();

        currentMode = 'WORKING';
    }

    function setCompletedMode() {
        if (currentMode !== 'WORKING') {
            document.querySelector('#editBtn').remove();
        }

        document.querySelector('#finishBtn').remove();
        document.querySelector('#createInspectionBtn').remove();

        if (currentMode !== 'WORKING') {
            disableForm('#editForm');
            showFiles();
        }

        currentMode = 'COMPLETED';
    }

    function toBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result.split(",")[1]);
            reader.onerror = (error) => reject(error);
            reader.readAsDataURL(file);
        });
    }

    function appendElementToList(element, list) {
        const newOption = document.createElement('option');
        newOption.value = element.id;
        newOption.textContent = element.name;

        list.appendChild(newOption);
    }

    function loadDataTables() {
        let columns = ['id', 'startDate', 'endDate', 'action', 'id']
        let endpoint = '/v1/shares/no-programmed/' + share.id + '/inspections';
        let actions = [
            { action: 'edit', type: 'redirect', url: '/shares/no-programmed/' + share.id + '/inspections/{id}', permission: 'edit_no_programmed_shares' },
            { action: 'file-pdf', url: '/v1/shares/no-programmed/' + share.id + '/inspections/{id}/export' },
            { action: 'delete', permission: 'edit_no_programmed_shares' }
        ]
        let expand = []
        let filters = []
        let orderable = [[0, 'DESC']]
        let columnDefs = [
            {
                targets: [1, 2],
                render: function (data) {
                    return data ? parseDate(data, 'DD-MM-YYYY HH:mm') : '-';
                }
            },
            {
                targets: 3,
                render: function (data) {
                    return parseTypeToBadge(data);
                }
            }
        ]

        customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnDefs);
        createDataTable('#dTable', customDataTable, locale);
    }

    function parseTypeToBadge(data) {
        if (data === 'INTERVENTION') {
            return '<div class="badge badge-warning">' + messages.inspections.intervention + '</div>';
        } else if (data === 'DIAGNOSIS') {
            return '<span class="badge badge-success">' + messages.inspections.diagnosis + '</span>';
        } else if (data === 'FOLLOWING') {
            return '<span class="badge badge-info">' + messages.inspections.following + '</span>';
        }
    }

    function showFiles() {
        const form = document.querySelector('#editForm');
        const filesFormGroup = form.querySelector('#filesFormGroup');

        const linksContainer = document.createElement('div');

        if (share.files.length > 0) {
            const downloadBase64File = (fileName, base64Content) => {
                const binaryData = atob(base64Content);
                const byteNumbers = new Uint8Array(binaryData.length);

                for (let i = 0; i < binaryData.length; i++) {
                    byteNumbers[i] = binaryData.charCodeAt(i);
                }

                const link = document.createElement('a');
                const blob = new Blob([byteNumbers], {type: 'application/octet-stream'});

                link.href = URL.createObjectURL(blob);
                link.download = fileName;
                link.textContent = fileName;
                link.classList.add('btn', 'btn-outline-primary', 'btn-sm', 'mr-1');
                link.target = '_blank';

                return link;
            };

            share.files.forEach(file => {
                const link = downloadBase64File(file.name, file.content);
                linksContainer.appendChild(link);
            });

            filesFormGroup.appendChild(linksContainer);
        } else {
            const span = document.createElement('span');
            span.textContent = messages.shares.noprogrammed.files.empty;
            span.classList.add('font-size-12', 'font-italic');

            linksContainer.appendChild(span);
            filesFormGroup.appendChild(linksContainer);
        }
    }

    function updateModalAction(action) {
        const modal = document.querySelector('#createModal');
        modal.querySelector('[name="action"]').value = action;
    }

    function createInspection() {
        const form = document.querySelector('#createForm');
        const secondTechnical = form.querySelector('[name="secondTechnical"]');
        showLoading();

        axios.post('/v1/shares/no-programmed/' + share.id + '/inspections', {
            firstTechnicalId: ${user.id},
            secondTechnicalId: secondTechnical ? secondTechnical.value : null,
            action: form.querySelector('[name="action"]').value,
        }).then((response) => {
            const inspection = response.data.data;
            nextAction = getNextAction(inspection.action);
            updateModalAction(nextAction);
            dTable.ajax.reload();
            showNotify(messages.inspections.create.success.replace('{0}', inspection.id));
        }).catch(error => showNotify(error, 'danger'))
            .finally(() => {
                hideLoading();
                $('#createModal').modal('hide');
            });
    }

    function getNextAction(currentAction) {
        if (currentAction === 'DIAGNOSIS') {
            return 'INTERVENTION'
        } else if (currentAction === 'INTERVENTION') {
            return 'FOLLOWING'
        } else if (currentAction === 'FOLLOWING') {
            return 'DIAGNOSIS'
        }
    }

    function remove(inspectionId) {
        const alertMessage = messages.inspections.delete.alert.replace('{0}', inspectionId);
        if (confirm(alertMessage)) {

            showLoading();

            axios.delete('/v1/shares/no-programmed/' + share.id + '/inspections/' + inspectionId).then(() => {
                dTable.ajax.reload();
                const successMessage = messages.inspections.delete.success.replace('{0}', inspectionId);
                showNotify(successMessage);
            }).catch(error => showNotify(error, 'danger'))
                .finally(() => hideLoading());
        }
    }

    function setReturnButtonUrl() {
        if (document.referrer) {
            const lastPagePath = new URL(document.referrer).pathname;

            if (lastPagePath === '/shares/intervention') {
                const queryParams = document.referrer.split('?')[1];
                lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');
                sessionStorage.setItem('sharesFilter', lastPageUrl);
            } else if (lastPagePath.startsWith('/shares/no-programmed/')) {
                lastPageUrl = sessionStorage.getItem('sharesFilter');
            } else {
                lastPageUrl = '/shares/intervention';
            }

            returnBtn.attr('href', lastPageUrl);
        }
    }

    $(document).ready(async function () {
        await init();
        update(share);
        edit(share.id);
        close(share.id);
        loadDataTables();
        setReturnButtonUrl();
    });

</script>