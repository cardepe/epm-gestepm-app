const activityCenterEndpoint = '/v1/activity-centers'

let currentActivityCenter;

$(document).ready(async function() {
    await loadRest();
    initializeSelects();
});

async function loadRest() {
    currentActivityCenter = await getActivityCenterById(currentUser.activityCenterId);
}
function initializeSelects() {

    const editForm = document.querySelector('#editForm');

    const activityCenterSelect = createSelect2($(editForm.querySelector('[name="activityCenterId"]')), activityCenterEndpoint, null, null, null, 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="roleId"]')), 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="levelId"]')), 'editForm');
    createBasicSelect2($(editForm.querySelector('[name="state"]')), 'editForm');

    if (currentUser) {
        activityCenterSelect.append(new Option(currentActivityCenter.name, currentActivityCenter.id, true, true));
    }
}

async function getActivityCenterById(id) {
    const response = await axios.get('/v1/activity-centers/' + id);
    return response.data.data;
}

function create() {

    const createFromJQ = $('#editForm');

    if (!isValidForm('#editForm')) {
        createFromJQ.addClass('was-validated');
    } else {

        showLoading();
        createFromJQ.removeClass('was-validated');

        const form = document.querySelector('#editForm');

        const name = form.querySelector('[name="name"]').value;
        const surnames = form.querySelector('[name="surnames"]').value;
        const activityCenterId = form.querySelector('[name="activityCenterId"]').value;
        const email = form.querySelector('[name="email"]').value;
        const password = form.querySelector('[name="password"]').value;
        const signingId = form.querySelector('[name="signingId"]').value;
        const roleId = form.querySelector('[name="roleId"]').value;
        const levelId = form.querySelector('[name="levelId"]').value;
        const state = form.querySelector('[name="state"]').value;
        const workingHours = form.querySelector('[name="workingHours"]').value;

        axios.patch(endpoint, {
            name: name,
            surnames: surnames,
            activityCenterId: activityCenterId,
            email: email,
            password: password === 'random-password' ? null : password,
            signingId: signingId,
            roleId: roleId,
            levelId: levelId,
            state: state,
            workingHours: workingHours
        }).then(() => {
            location.reload();
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}