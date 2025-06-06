const view = '/users'
const endpoint = '/v1/users';

function initializeDataTables() {
	let columns = ['id', 'fullName', 'activityCenter.name', 'level.name', 'role.name', 'id']
	let actions = [
		{
			action: 'view',
			url: view + '/{id}',
			permission: 'read_users'
		},
		{
			action: 'delete',
			permission: 'edit_users'
		}
	]
	let expand = ['activityCenter', 'role', 'level']
	let filters = [];
	let columnsDef = [
		{
			targets: 4,
			render: function (data) {
				return parseRoleToBadge(data);
			}
		}
	]

	customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnsDef);
	dTable = createDataTable('#dTable', customDataTable, locale);
	customDataTable.setCurrentTable(dTable);
}

function initializeSelects() {
	const createForm = document.querySelector('#createForm');
	const createSelects = createForm.querySelectorAll('select');

	createSelects.forEach(select => {
		createBasicSelect2($(select), 'createForm');
	});

	const filterForm = document.querySelector('#filterForm');
	const filterSelects = filterForm.querySelectorAll('select');

	filterSelects.forEach(select => {
		createBasicSelect2($(select), 'filterForm');
	});
}

function filterByParams() {

	const queryParams = new URLSearchParams(window.location.search);

	let filters = [];

	let ids = queryParams.get('ids');
	let nameContains = queryParams.get('nameContains');
	let activityCenterIds = queryParams.get('activityCenterIds');
	let roleIds = queryParams.get('roleIds');
	let levelIds = queryParams.get('levelIds');
	let state = queryParams.get('state');
	let pageNumber = queryParams.get('pageNumber');

	const filterForm = document.querySelector('#filterForm');

	filterForm.querySelector('[name="id"]').value = ids;
	filterForm.querySelector('[name="nameContains"]').value = nameContains;
	filterForm.querySelector('[name="activityCenterId"]').value = activityCenterIds;
	filterForm.querySelector('[name="roleId"]').value = roleIds;
	filterForm.querySelector('[name="levelId"]').value = levelIds;
	filterForm.querySelector('[name="state"]').value = state;

	if (ids) { filters.push({ 'ids': ids }); }
	if (nameContains) { filters.push({ 'nameContains': nameContains }); }
	if (activityCenterIds) { filters.push({ 'activityCenterIds': activityCenterIds }); }
	if (roleIds) { filters.push({ 'roleIds': roleIds }); }
	if (levelIds) { filters.push({ 'levelIds': levelIds }); }
	if (state) { filters.push({ 'state': state }); }
	if (pageNumber) { filters.push({ 'pageNumber': pageNumber }); }

	customDataTable.setFilters(filters);
}

function filter(isReset) {

	let filters = [];

	if (!isReset) {

		let form = document.querySelector('#filterForm');

		let id = form.querySelector('[name="id"]');
		let nameContains = form.querySelector('[name="nameContains"]');
		let activityCenterId = form.querySelector('[name="activityCenterId"]');
		let roleId = form.querySelector('[name="roleId"]');
		let levelId = form.querySelector('[name="levelId"]');
		let state = form.querySelector('[name="state"]');

		id.value && filters.push({'ids': id.value});
		nameContains.value && filters.push({'nameContains': nameContains.value});
		activityCenterId.value && filters.push({'activityCenterIds': activityCenterId.value});
		roleId.value && filters.push({'roleIds': roleId.value});
		levelId.value && filters.push({'levelIds': levelId.value});
		state.value && filters.push({'state': state.value});
	} else {
		resetForm('#filterForm');
	}

	customDataTable.setFilters(filters);
	dTable.order([[0]]).ajax.reload();
}

function create() {

	const createFromJQ = $('#createForm');

	if (!isValidForm('#createForm')) {
		createFromJQ.addClass('was-validated');
	} else {

		showLoading();
		createFromJQ.removeClass('was-validated');

		const form = document.querySelector('#createForm');

		const name = form.querySelector('[name="name"]').value;
		const surnames = form.querySelector('[name="surnames"]').value;
		const email = form.querySelector('[name="email"]').value;
		const password = form.querySelector('[name="password"]').value;
		const activityCenterId = form.querySelector('[name="activityCenterId"]').value;
		const signingId = form.querySelector('[name="signingId"]').value;
		const roleId = form.querySelector('[name="roleId"]').value;
		const levelId = form.querySelector('[name="levelId"]').value;

		axios.post(endpoint, {
			name: name,
			surnames: surnames,
			email: email,
			password: password,
			activityCenterId: activityCenterId,
			signingId: signingId ? signingId : null,
			roleId: roleId,
			levelId: levelId
		}).then((response) => {
			const user = response.data.data;
			window.location.replace(view + '/' + user.id);
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
			.finally(() => hideLoading());
	}
}

function remove(id) {

	if (confirm(messages.user.delete.alert.replace('{0}', id))) {

		showLoading();

		axios.delete(endpoint + '/' + id).then(() => {
			dTable.ajax.reload(function () {
				dTable.page(dTable.page()).draw(false);
			}, false);
			showNotify(messages.user.delete.success.replace('{0}', id));
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
			.finally(() => hideLoading());
	}
}

function parseRoleToBadge(roleName) {
	let badgeColor = '';

	if (roleName === 'ROLE_CLIENTE') {
		badgeColor = 'primary';
	} else if (roleName === 'ROLE_OFICINA') {
		badgeColor = 'info';
	} else if (roleName === 'ROLE_OPERARIO') {
		badgeColor = 'warning ';
	} else if (roleName === 'ROLE_JEFE_PROYECTO') {
		badgeColor = 'dark';
	} else if (roleName === 'ROLE_SUPERVISOR_TECNICO') {
		badgeColor = 'secondary';
	} else if (roleName === 'ROLE_RRHH') {
		badgeColor = 'success';
	} else if (roleName === 'ROLE_ADMINISTRACION') {
		badgeColor = 'light';
	} else if (roleName === 'ROLE_ADMIN') {
		badgeColor = 'danger';
	}

	return '<div class="badge badge-pill badge-' + badgeColor + '">' + roleName + '</div>';
}