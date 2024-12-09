<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col col-lg-10">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 id="title" class="text-uppercase p-0"></h1>
                        <span id="forumTitle" class="page-sub-title"></span>
                    </div>
                </div>
            </div>
            <div class="col col-lg-2">
                <div class="page-header float-right">
                    <button type="button" class="btn btn-default btn-sm">
                        <spring:message code="back" />
                    </button>
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
                Incidencia
            </div>

            <form id="editForm" class="needs-validation">
                <div class="row">
                    <div class="col col-lg-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="shares.intervention.create.family" />
                                <select name="familyId" class="form-control" required>
                                    <option><spring:message code="shares.intervention.create.family.placeholder" /></option>
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

                    <div class="col col-lg-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="shares.intervention.create.sub.family" />
                                <select name="subFamilyId" class="form-control" required>
                                    <option selected><spring:message code="shares.intervention.create.sub.family.placeholder" /></option>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col col-lg-6">
                        <div class="form-group">
                            <label class="col-form-label w-100"><spring:message code="shares.intervention.create.intervention.files" />
                                <input type="file" class="form-control" name="files" accept=".jpg, .jpeg, .png" multiple>
                            </label>
                        </div>
                    </div>

                    <div class="col col-md-6">
                        <div class="form-group">
                            <label class="col-form-label w-100">
                                <spring:message code="shares.intervention.create.operator.name" />
                                <input type="text" name="user" class="form-control" readonly />
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="form-group mb-0">
                            <spring:message code="shares.intervention.create.fault.desc.placeholder" var="placeholder" />
                            <label class="col-form-label w-100">
                                <spring:message code="shares.intervention.create.fault.desc" />
                                <textarea class="form-control" placeholder="${placeholder}" name="description" required></textarea>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row mt-2 actionable">
                    <div class="col text-right">
                        <button id="editBtn" type="button" class="btn btn-standard btn-sm movile-full"><spring:message code="save" /></button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="title mb-0">
                Acciones
            </div>

            <div class="table-responsive">
                <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                    <thead>
                        <tr>
                            <th><spring:message code="shares.intervention.table.id" /></th>
                            <th><spring:message code="shares.intervention.create.fault.start.hour" /></th>
                            <th><spring:message code="shares.intervention.create.fault.end.hour" /></th>
                            <th><spring:message code="shares.intervention.table.action" /></th>
                            <th><spring:message code="shares.displacement.table.actions" /></th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script>

    let locale = '${locale}';
    let $ = jQuery.noConflict();

    let share;

    async function init() {
        const editForm = document.querySelector('#editForm');
        const shareId = getShareId();

        await getShare(shareId);

        const familyElement = editForm.querySelector('[name="familyId"]');

        familyElement.onchange = () => {
            updateSubFamilySelect();
        }
    }

    function getShareId() {
        const currentUrl = window.location.href;
        const segments = currentUrl.split('/');

        return segments[segments.length - 1];
    }

    async function getShare(id) {
        await axios.get('/v1/shares/no-programmed/' + id, { params: { _expand: 'family,subFamily,user,project', locale: locale }}).then((response) => {
            share = response.data.data;
        }).catch(error => showNotify(error, 'danger'));
    }

    function edit(id) {
        const editBtn = $('#editBtn');
        const editForm = document.querySelector('#editForm');

        editBtn.click(async () => {
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
            }).then(() => showNotify(messages.shares.noprogrammed.update.success))
                .catch(error => showNotify(error, 'danger'))
                .finally(() => hideLoading());
        })
    }

    function update(share) {
        const form = document.querySelector('#editForm');

        document.querySelector('#title').textContent = messages.shares.noprogrammed.title.replace('{0}', share.id)  + ' - ' + share.project.name;

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
            document.querySelector('#forumTitle').textContent = share.forumTitle;
        }

        if (['INITIALIZED', 'IN_PROGRESS', 'CLOSED'].includes(share.state)) {
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

                    appendElementToList({ name: '<spring:message code="shares.intervention.create.sub.family.placeholder" />' }, subFamily);

                    response.data.forEach((element) => {
                        appendElementToList(element, subFamily);
                    }).catch(error => showNotify(error, 'danger'));
                })
        }
    }

    function setCompletedMode() {
        const form = document.querySelector('#editForm');
        const elements = form.querySelectorAll('input, textarea, select, button');

        elements.forEach(element => element.disabled = true);
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

    $(document).ready(async function() {
        await init();
        update(share);
        edit(share.id);
    });

</script>