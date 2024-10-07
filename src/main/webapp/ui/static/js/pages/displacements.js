function filter(isReset) {

	let filters = [];

	if (!isReset) {

		let id = document.querySelector('#filterIdInput');
		let name = document.querySelector('#filterNameInput');
		let activityCenterId = document.querySelector('#filterActivityCenterInput');
		let type = document.querySelector('#filterTypeInput');

		if (id.value) { filters.push({'ids': id.value}); }
		if (name.value) { filters.push({'name': name.value}); }
		if (activityCenterId.value) { filters.push({'activityCenterIds': activityCenterId.value}); }
		if (type.value) { filters.push({'type': type.value}); }

	} else {
		$('#filterForm')[0].reset();
	}

	customDataTable.setFilters(filters)
	dTable.ajax.reload();
}

function create() {

	const createModal = $('#createModal');
	const createFromJQ = $('#createForm');
	const createElement = document.getElementById('createModal');
	const createForm = createElement.querySelector('#createForm');

	if (validateForm('create')) {
		createFromJQ.addClass('was-validated');
	} else {

		showLoading();
		createFromJQ.removeClass('was-validated');

		axios.post('/v1/displacements', {
			name: createForm.querySelector('[name="name"]').value,
			activityCenterId: createForm.querySelector('[name="activityCenterId"]').value,
			type: createForm.querySelector('[name="type"]').value,
			totalTime: timePassed(createForm.querySelector('[name="totalTime"]').value)
		}).then(() => {
			createFromJQ[0].reset();
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.displacements.create.success);
		}).catch(error => showNotify(error, 'danger'))
			.finally(() => {
				hideLoading();
				createModal.modal('hide');
			});
	}
}

function validateForm(action) {

	let name = $('#' + action + 'Name').val();
	let activityCenterId = $('#' + action + 'ActivityCenter').val();
	let type = $('#' + action + 'Type').val();
	let totalTime = $('#' + action + 'TotalTime').val();

	return !name || !activityCenterId || !type || !totalTime;
}

function edit(id) {

	const editModal = $('#editModal');
	const editFromJQ = $('#editForm');
	const saveBtn = $('#saveBtn');
	const editElement = document.getElementById('editModal');
	const editForm = editElement.querySelector('#editForm');

	axios.get('/v1/displacements/' + id).then((response) => {
		editModal.find('#editName').val(response.data.data.name);
		editModal.find('#editActivityCenter').val(response.data.data.activityCenter.id);
		editModal.find('#editType').val(response.data.data.type);
		editModal.find('#editTotalTime').val(minutesToTime(response.data.data.totalTime));
		editModal.modal('show');
	});

	saveBtn.click(function() {

		if (validateForm('edit')) {
			editFromJQ.addClass('was-validated');
		} else {

			showLoading();
			editFromJQ.removeClass('was-validated');

			axios.put('/v1/displacements/' + id, {
				name: editForm.querySelector('[name="name"]').value,
				activityCenterId: editForm.querySelector('[name="activityCenterId"]').value,
				type: editForm.querySelector('[name="type"]').value,
				totalTime: timePassed(editForm.querySelector('[name="totalTime"]').value)
			}).then(() => {
				dTable.ajax.reload(function () {
					dTable.page(dTable.page()).draw(false);
				}, false);
				showNotify(messages.displacements.update.success);
			}).catch(error => showNotify(error, 'danger'))
				.finally(() => {
					hideLoading();
					editModal.modal('hide');
				});
		}
	});
}

function remove(id) {

	if (confirm(messages.displacements.delete.alert)) {

		showLoading();

		axios.delete('/v1/displacements/' + id).then(() => {
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.displacements.delete.success);
		}).catch(error => showNotify(error, 'danger'))
			.finally(() => hideLoading());
	}
}

function formatType(type) {
	if (type === 'PUBLIC_TRANSPORT') {
		return messages.displacements.publictransport;
	} else if (type === 'VEHICLE') {
		return messages.displacements.vehicle;
	}

	return 'UNDEFINED';
}