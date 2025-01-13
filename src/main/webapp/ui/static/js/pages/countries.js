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

        axios.post('/v1/countries', {
            name: createForm.querySelector('[name="name"]').value,
            tag: createForm.querySelector('[name="tag"]').value
        }).then(() => {
            dTable.ajax.reload();
            showNotify(messages.countries.create.success);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => {
                hideLoading();
                createModal.modal('hide');
            });
    }
}

function validateForm() {

    let name = $('#createName').val();
    let tag = $('#createTag').val();

    return !name || !tag;
}

function edit(id) {

    const editModal = $('#editModal');
    const saveBtn = $('#saveBtn');
    const editElement = document.getElementById('editModal');
    const editForm = editElement.querySelector('#editForm');

    axios.get('/v1/countries/' + id).then((response) => {
        editModal.find('#editName').val(response.data.data.name);
        editModal.find('#editTag').val(response.data.data.tag);
        editModal.modal('show');
    });

    saveBtn.click(function() {

        showLoading();

        axios.put('/v1/countries/' + id, {
            name: editForm.querySelector('[name="name"]').value,
            tag: editForm.querySelector('[name="tag"]').value
        }).then(() => {
            dTable.ajax.reload();
            showNotify(messages.countries.update.success);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => {
                hideLoading();
                editModal.modal('hide');
            });
    });
}

function remove(id) {

    if (confirm(messages.countries.delete.alert)) {

        showLoading();

        axios.delete('/v1/countries/' + id).then(() => {
            dTable.ajax.reload();
            showNotify(messages.countries.delete.success);
        }).catch(error => showNotify(error.response.data.detail, 'danger'))
            .finally(() => hideLoading());
    }
}
