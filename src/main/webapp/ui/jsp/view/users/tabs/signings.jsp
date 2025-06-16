<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col">
        <div class="float-right calendarBtns">
            <div class="fc-button-group">
                <button type="button" class="btn btn-success btn-sm mr-1" data-toggle="modal" data-target="#createModal"><spring:message code="user.detail.signing.admin.btn" /></button>
                <a href="/signings/teleworking?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="user.detail.signing.teleworking" /></a>
                <a href="/signing/manual?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="user.detail.signing.manual" /></a>
                <a href="/signing/personal/time-control?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="signing.hours.bag" /></a>
                <button type="button" class="btn btn-primary btn-sm mr-1" data-toggle="modal" data-target="#exportModal"><spring:message code="user.detail.signing.gen" /></button>
                <button type="button" class="btn btn-primary btn-sm mr-1" onclick="calendar.prev()">
                    <span class="fc-icon fc-icon-chevron-left"></span>
                </button>
                <button type="button" class="btn btn-primary btn-sm" onclick="calendar.next()">
                    <span class="fc-icon fc-icon-chevron-right"></span>
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 calendar-content">
        <div id="calendar" class="personalCalendar"></div>
    </div>
</div>

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="user.detail.signing.admin.btn" /></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="createForm">
                    <input type="hidden" name="userId" value="${currentUser.id}" />
                    <input id="geolocationInput" type="hidden" name="geolocation" />

                    <div class="row">
                        <div class="col">
                            <select class="form-control input select-sm" style="width: 100%" name="signingType" onchange="updateCreateForm(this.value)" required>
                                <option disabled selected="selected">
                                    <spring:message code="signing.type.selectable" />
                                </option>
                                <option value="ps"><spring:message code="signing.personal.title" /></option>
                                <option value="ums"><spring:message code="signing.manual.calendar.title" /></option>
                            </select>
                        </div>
                    </div>

                    <div id="manualTypeRow" class="row mt-2" style="display: none">
                        <div class="col">
                            <select class="form-control input select-sm" name="manualTypeId" onchange="updateForm(this.value)">
                                <option></option>
                                <c:forEach items="${manualSigningTypes}" var="manualSigningType">
                                    <option value="${manualSigningType.id}">
                                        <spring:message code="${manualSigningType.name}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mt-2">
                        <div class="col-sm-12 col-md-6">
                            <input type="datetime-local" style="font-size: 12px" class="form-control" name="startDate">
                        </div>

                        <div class="col-sm-12 col-md-6">
                            <input type="datetime-local" style="font-size: 12px" class="form-control" name="endDate">
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
                        <button type="submit" class="btn btn-sm btn-success" onclick="createSigning()"><spring:message code="create" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="edit" /></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="editForm">
                    <input name="shareId" type="hidden" />
                    <input name="shareType" type="hidden" />

                    <div class="row">
                        <div class="col-6">
                            <input type="datetime-local" class="form-control input" name="startDate">
                        </div>

                        <div class="col-6">
                            <input type="datetime-local" class="form-control input" name="endDate">
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
                        <button type="submit" class="btn btn-sm btn-success" onclick="editSigning()"><spring:message code="edit" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="exportModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <form id="exportForm">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title">
                        <h5 id="modalTitle">
                            <spring:message code="user.detail.signing.gen" />
                        </h5>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <div class="form-group mb-1">
                                <label class="col-form-label w-100"><spring:message code="start.date"/>
                                    <input name="startDate" type="datetime-local" class="form-control mt-1" />
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col">
                            <div class="form-group mb-1">
                                <label class="col-form-label w-100"><spring:message code="end.date"/>
                                    <input name="endDate" type="datetime-local" class="form-control mt-1" />
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="w-100">
                        <div class="float-left">
                            <button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
                        </div>
                        <div class="float-right">
                            <button type="button" class="btn btn-sm btn-success" onclick="exportTimeControl(true)"><spring:message code="export.woffu" /></button>
                            <button type="button" class="btn btn-sm btn-success" onclick="exportTimeControl()"><spring:message code="export" /></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<script>
    let calendar;

    $(document).ready(function() {
        initializeSelects();
        initializeCalendar();
        getLocation();
    });

    function initializeSelects() {
        const createForm = document.querySelector('#createForm');
        const createSelects = createForm.querySelectorAll('select');

        createSelects.forEach(select => {
            createBasicSelect2($(select), 'createForm');
        });
    }

    function initializeCalendar() {
        const calendarEl = document.getElementById('calendar');

        calendar = new FullCalendar.Calendar(calendarEl, {
            locale: 'es',
            plugins : [ 'timeGrid' ],
            defaultView: 'timeGridWeek',
            header: false,
            allDaySlot: false,
            timeFormat: 'H:mm',
            timeZone: 'UTC',
            height: 'parent',
            events: function (fetchInfo, successCallback, failureCallback) {
                const params = {
                    userId: ${currentUser.id},
                    startDate: fetchInfo.startStr,
                    endDate: fetchInfo.endStr
                };

                axios.get('/v1/time-controls', { params: params }).then((response) => {
                    let timeControls = response.data.data.map(timeControl => ({
                        id: timeControl.id,
                        title: getTitle(timeControl),
                        start: timeControl.startDate,
                        end: timeControl.endDate,
                        color: getColor(timeControl.type),
                        textColor: '#fff',
                        extendedProps: {
                            detailUrl: timeControl.detailUrl,
                            type: timeControl.type
                        }
                    }));

                    successCallback(timeControls);
                }).catch(error => failureCallback(error));
            },
            eventClick: function(info) {
                const calendarSigning = info.event;
                const detailUrl = calendarSigning.extendedProps.detailUrl;

                if (detailUrl) {
                    window.location.href = detailUrl;
                } else {
                    loadModalForm(calendarSigning);
                }
            }
        });

        calendar.render();
    }

    function getTitle(timeControl) {
        return timeControl.description ? timeControl.description : getSigningText(timeControl.type);
    }

    function getColor(type) {
        if (type === 'DISPLACEMENT_SHARES') {
            return '#CC00C8';
        } else if (type === 'MANUAL_SIGNINGS') {
            return '#D8E112';
        } else if (type === 'PERSONAL_SIGNINGS') {
            return '#0062CC';
        } else {
            return '#12E1DE';
        }
    }

    function updateCreateForm(signingType) {
        const manualTypeRow = $('#manualTypeRow');

        signingType === 'ums' ? manualTypeRow.show() : manualTypeRow.hide();
    }

    function createSigning() {

        const createModal = $('#createModal');
        const createFromJQ = $('#createForm');

        if (!isValidForm('#createForm')) {
            createFromJQ.addClass('was-validated');
        } else {

            showLoading();
            createFromJQ.removeClass('was-validated');

            const form = document.querySelector('#createForm');

            const signingType = form.querySelector('[name="signingType"]').value;

            let signingEndpoint = null;

            if (signingType === 'ps') {
                signingEndpoint = '/signing/personal';
            } else if (signingType === 'ums') {
                signingEndpoint = '/signing/manual';
            }

            axios.post(signingEndpoint, createFromJQ.serialize())
                .then(() => calendar.refetchEvents())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => { hideLoading(); createModal.modal('hide'); });
        }
    }

    function loadModalForm(calendarSigning) {
        const allowedTypes = ['CONSTRUCTION_SHARES', 'DISPLACEMENT_SHARES', 'INSPECTIONS', 'MANUAL_SIGNINGS',
            'PERSONAL_SIGNINGS', 'PROGRAMMED_SHARES', 'WORK_SHARES'];

        const id = calendarSigning.id;
        const type = calendarSigning.extendedProps.type;

        if (!allowedTypes.some(allowedType => type.includes(allowedType))) {
            return;
        }

        const startDate = new Date(calendarSigning.start).toISOString().slice(0, 16);
        const endDate = new Date(calendarSigning.end).toISOString().slice(0, 16);

        const form = document.querySelector('#editForm');

        form.querySelector('[name="shareId"]').value = id;
        form.querySelector('[name="shareType"]').value = type;
        form.querySelector('[name="startDate"]').value = startDate;
        form.querySelector('[name="endDate"]').value = endDate;

        $('#editModal').modal('show');
    }

    function editSigning() {

        const editModal = $('#editModal');
        const editFromJQ = $('#editForm');

        if (!isValidForm('#editForm')) {
            editFromJQ.addClass('was-validated');
        } else {

            showLoading();
            editFromJQ.removeClass('was-validated');

            axios.post('/signing/share/update', editFromJQ.serialize())
                .then(() => calendar.refetchEvents())
                .catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => { hideLoading(); editModal.modal('hide'); });
        }
    }

    function exportTimeControl(isWoffu) {
        const form = document.querySelector('#exportForm');
        const editFormJQ = $('#exportForm');

        if (!isValidForm('#exportForm')) {
            editFormJQ.addClass('was-validated');
        } else {
            editFormJQ.removeClass('was-validated');

            const startDate = form.querySelector('[name="startDate"]').value;
            const endDate = form.querySelector('[name="endDate"]').value;
            const queryParams = '?startDate=' + startDate + '&endDate=' + endDate + '&userId=' + ${currentUser.id};

            window.open('/v1/time-controls/export' + (isWoffu ? '-woffu' : '') + queryParams, '_blank').focus();

            editFormJQ.modal('hide');
        }
    }

</script>