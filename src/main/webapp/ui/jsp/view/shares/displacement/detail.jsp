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
                        <h1 class="text-uppercase"><spring:message code="shares.displacement.title"/></h1>
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
                <spring:message code="info"/>
            </div>

            <form id="editForm" class="needs-validation">
                <div class="row">
                    <div class="col col-md-6">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="project"/>
                                <select class="form-control input mt-1" name="projectId" required>
                                    <option></option>
                                    <c:forEach items="${projects}" var="project">
                                        <option value="${project.id}" ${project.id == displacementShare.projectId ? 'selected' : ''}><spring:message code="${project.name}"/></option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                    </div>

                    <div class="col col-md-6">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="description" />
                                <input name="description" type="text" class="form-control mt-1" value="${displacementShare.description}" required />
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col col-md-6">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="start.date"/>
                                <input name="startDate" type="datetime-local" class="form-control mt-1" value="${displacementShare.startDate}" required />
                            </label>
                        </div>
                    </div>

                    <div class="col col-md-6">
                        <div class="form-group mb-1">
                            <label class="col-form-label w-100"><spring:message code="end.date"/>
                                <input name="endDate" type="datetime-local" class="form-control mt-1" value="${displacementShare.endDate}" required />
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label class="col-form-label w-100"><spring:message code="shares.displacement.observations" />
                                <textarea name="observations" type="text" class="form-control">${displacementShare.observations}</textarea>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row actionable">
                    <div class="col text-right">
                        <button id="editBtn" type="button" class="btn btn-standard btn-sm movile-full"><spring:message code="save"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.13.17/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/static/js/select2/select2-utils.js?v=<%= System.currentTimeMillis() %>"></script>

<script>

    let locale = '${locale}';
    let canUpdate = ${canUpdate};

    $(document).ready(function() {
        initializeSelects();
        initialize();
        save();
        setReturnButtonUrl();
    });

    function initializeSelects() {
        const form = document.querySelector('#editForm');
        const selects = form.querySelectorAll('select');

        selects.forEach(select => {
            createBootstrapSelect2($(select));
        });
    }

    function initialize() {
        if (!canUpdate) {
            const editForm = document.querySelector('#editForm');

            editForm.querySelector('.actionable').style.display = 'none';

            editForm.querySelectorAll('input, select, textarea, button').forEach(el => {
                el.disabled = true;
            });
        }
    }

    function save() {
        const editBtn = $('#editBtn');
        const editFormJQ = $('#editForm');
        const editForm = document.querySelector('#editForm');

        editBtn.click(async () => {

            if (!isValidForm('#editForm')) {
                editFormJQ.addClass('was-validated');
            } else {

                showLoading();
                editFormJQ.removeClass('was-validated');

                const projectId = editForm.querySelector('[name="projectId"]').value;
                const description = editForm.querySelector('[name="description"]').value;
                const startDate = editForm.querySelector('[name="startDate"]').value;
                const endDate = editForm.querySelector('[name="endDate"]').value;
                const observations = editForm.querySelector('[name="observations"]').value;

                let params = {
                    projectId: projectId,
                    description: description,
                    startDate: startDate,
                    endDate: endDate,
                    ...(observations && {observations})
                };

                axios.put('/v1' + window.location.pathname, params).then(() => {
                    showNotify(messages.shares.displacement.update.success);
                }).catch(error => showNotify(error.response.data.detail, 'danger'))
                    .finally(() => hideLoading());
            }
        })
    }

    function setReturnButtonUrl() {
        let lastPageUrl = '/shares/displacement';

        if (document.referrer) {
            const lastPagePath = new URL(document.referrer).pathname;
            const queryParams = document.referrer.split('?')[1];

            lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');
        }

        $('#returnBtn').attr('href', lastPageUrl);
    }

</script>