function filter(isReset) {

	let filters = [];

	if (!isReset) {

		let id = document.querySelector('#filterIdInput');
		let name = document.querySelector('#filterNameInput');
		let countryId = document.querySelector('#filterCountryInput');

		filters.push({'ids': id.value});
		filters.push({'name': name.value});
		filters.push({'countryIds': countryId.value});
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

	if (validateForm()) {
		createFromJQ.addClass('was-validated');
	} else {

		showLoading();
		createFromJQ.removeClass('was-validated');

		axios.post('/v1/activity-centers', {
			name: createForm.querySelector('[name="name"]').value,
			countryId: createForm.querySelector('[name="countryId"]').value
		}).then(() => {
			createFromJQ[0].reset();
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.activitycenters.create.success);
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
			.finally(() => {
				hideLoading();
				createModal.modal('hide');
			});
	}
}

function validateForm() {

	let name = $('#createName').val();
	let countryId = $('#createCountry').val();

	return !name || !countryId;
}

function edit(id) {

	const editModal = $('#editModal');
	const saveBtn = $('#saveBtn');
	const editElement = document.getElementById('editModal');
	const editForm = editElement.querySelector('#editForm');

	axios.get('/v1/activity-centers/' + id).then((response) => {
		editModal.find('#editName').val(response.data.data.name);
		editModal.find('#editCountry').val(response.data.data.country.id);
		editModal.modal('show');
	});

	saveBtn.click(function() {

		showLoading();

		axios.put('/v1/activity-centers/' + id, {
			name: editForm.querySelector('[name="name"]').value,
			countryId: editForm.querySelector('[name="countryId"]').value
		}).then(() => {
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.activitycenters.update.success);
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
			.finally(() => {
				hideLoading();
				editModal.modal('hide');
			});
	});
}

function remove(id) {

	if (confirm(messages.activitycenters.delete.alert)) {

		showLoading();

		axios.delete('/v1/activity-centers/' + id).then(() => {
			dTable.ajax.reload(function() { dTable.page(dTable.page()).draw(false); }, false);
			showNotify(messages.activitycenters.delete.success);
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
			.finally(() => hideLoading());
	}
}
