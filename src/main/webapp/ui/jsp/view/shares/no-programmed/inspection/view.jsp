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
</div>

<script>
    let locale = '${locale}';
    let share;
    let inspection;

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

    $(document).ready(async function () {
        await getInspection();
        await getShare(inspection.share.id);
        init();
        update();
        save();
    });

</script>