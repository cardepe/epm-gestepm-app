const activityCenterEndpoint = '/v1/activity-centers'
const usersEndpoint = '/v1/users';

let currentActivityCenter;
let currentResponsible;

$(document).ready(async function() {
    await loadRest();
    initializeSelects();
});

async function loadRest() {
    currentActivityCenter = await getActivityCenterById(currentProject.activityCenterId);
    currentResponsible = await Promise.all(currentProject.responsibleIds.map(id => getUserById(id)));
}

function initializeSelects() {
    const params = { roleIds: 4 }; // TODO: change to TEAM_LEADER_ID
    const getCustomName = user => `${user.name} ${user.surnames}`;

    // # EditForm
    const editForm = document.querySelector('#editForm');

    createBasicSelect2($(editForm.querySelector('[name="isStation"]')), 'editForm');
    const activityCenterSelect = createSelect2($(editForm.querySelector('[name="activityCenterId"]')), activityCenterEndpoint, null, null, null, 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="isTeleworking"]')), 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="forumId"]')), 'editForm');
    const responsibleSelect = createSelect2($(editForm.querySelector('[name="responsibleIds"]')), usersEndpoint, params, null, getCustomName, 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="state"]')), 'editForm');

    if (currentProject) {
        activityCenterSelect.append(new Option(currentActivityCenter.name, currentActivityCenter.id, true, true));

        currentResponsible.forEach(responsible => {
            responsibleSelect.append(new Option(responsible.name + ' ' + responsible.surnames, responsible.id, true, true));
        });
    }
}

async function getActivityCenterById(id) {
    const response = await axios.get('/v1/activity-centers/' + id);
    return response.data.data;
}

async function getUserById(id) {
    const response = await axios.get('/v1/users/' + id);
    return response.data.data;
}

function edit() {

    const editFromJQ = $('#editForm');

    if (!isValidForm('#editForm')) {
        editFromJQ.addClass('was-validated');
    } else {

        showLoading();
        editFromJQ.removeClass('was-validated');

        const form = document.querySelector('#editForm');

        const name = form.querySelector('[name="name"]').value;
        const isStation = form.querySelector('[name="isStation"]').value;
        const activityCenterId = form.querySelector('[name="activityCenterId"]').value;
        const objectiveCost = form.querySelector('[name="objectiveCost"]').value;
        const startDate = form.querySelector('[name="startDate"]').value;
        const endDate = form.querySelector('[name="endDate"]').value;
        const forumId = form.querySelector('[name="forumId"]').value;
        const isTeleworking = form.querySelector('[name="isTeleworking"]').value;
        const selectResponsible = form.querySelector('[name="responsibleIds"]');
        const responsibleIds = Array.from(selectResponsible.selectedOptions).map(option => option.value);
        const state = form.querySelector('[name="state"]').value;

        axios.put(endpoint, {
            name: name,
            isStation: isStation,
            activityCenterId: activityCenterId,
            objectiveCost: objectiveCost,
            startDate: startDate,
            endDate: endDate,
            forumId: forumId ? forumId : null,
            isTeleworking: isTeleworking,
            responsibleIds: responsibleIds,
            state: state
        }).then(() => {
            location.reload();
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
