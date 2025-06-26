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
                userId: currentUser.id,
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

function exportTimeControl() {
    const form = document.querySelector('#exportForm');
    const editFormJQ = $('#exportForm');

    if (!isValidForm('#exportForm')) {
        editFormJQ.addClass('was-validated');
    } else {
        editFormJQ.removeClass('was-validated');

        const startDate = form.querySelector('[name="startDate"]').value;
        const endDate = form.querySelector('[name="endDate"]').value;
        const queryParams = '?startDate=' + startDate + '&endDate=' + endDate + '&userId=' + currentUser.id;

        window.open('/v1/time-controls/export' + queryParams, '_blank').focus();

        editFormJQ.modal('hide');
    }
}