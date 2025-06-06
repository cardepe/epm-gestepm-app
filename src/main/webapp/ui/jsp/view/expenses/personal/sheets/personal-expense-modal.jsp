<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="personalExpenseModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="z-index: 100000">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="personal.expenses" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="personalExpenseForm" class="needs-validation">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="type" />
                                    <select name="priceType" class="form-control" required>
                                        <option value="" selected="selected">
                                            <spring:message code="select.placeholder" />
                                        </option>
                                        <option value="FLY"><spring:message code="price.type.fly" /></option>
                                        <option value="FOOD"><spring:message code="price.type.food" /></option>
                                        <option value="GAS"><spring:message code="price.type.gas" /></option>
                                        <option value="GASOIL"><spring:message code="price.type.gasoil" /></option>
                                        <option value="GASOLINE"><spring:message code="price.type.gasoline" /></option>
                                        <option value="HOTEL"><spring:message code="price.type.hotel" /></option>
                                        <option value="KMS"><spring:message code="price.type.kms" /></option>
                                        <option value="OTHER"><spring:message code="price.type.other" /></option>
                                        <option value="PARKING"><spring:message code="price.type.parking" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>

                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="payment.type" />
                                    <select name="paymentType" class="form-control" required>
                                        <option value="" selected="selected">
                                            <spring:message code="select.placeholder" />
                                        </option>
                                        <option value="EPM_TARGET"><spring:message code="payment.type.epm_target" /></option>
                                        <option value="OTHER"><spring:message code="payment.type.other" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label id="quantityLabel" class="col-form-label w-100"><spring:message code="quantity" />
                                    <input name="quantity" type="number" class="form-control" value="1" required>
                                    <span class="text-info only-kms d-none"><em><small><spring:message code="price.type.kms.info" /></small></em></span>
                                </label>
                            </div>
                        </div>

                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="amount" />
                                    <input name="amount" type="number" class="form-control" required>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="date" />
                                    <input name="startDate" type="datetime-local" class="form-control" required>
                                </label>
                            </div>
                        </div>

                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="files" />
                                    <input type="file" class="form-control" name="files"  accept=".jpg, .jpeg, .png, .pdf" multiple>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="description" />
                                    <textarea name="description" class="form-control" required></textarea>
                                </label>
                            </div>
                        </div>
                    </div>

                    <input id="rowId" name="rowId" class="form-control input" type="hidden" readonly />
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close" />
                        </button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-default btn-sm" onclick="createOrUpdate()">
                            <spring:message code="save" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const modal = document.querySelector('#personalExpenseModal')
    const form = document.querySelector('#personalExpenseForm');

    let filesData = [];

    function onChangePriceType() {
        const priceType = form.querySelector('[name="priceType"]');

        priceType.addEventListener('change', (event) => {
            const priceType = event.target.value;
            displayQuantityLabel(priceType);
            displayQuantityInfo(priceType);
            displayAmountInput(priceType);
        });

        onChangeQuantity();
    }

    function onChangeQuantity() {
        const quantity = form.querySelector('[name="quantity"]');
        const priceType = form.querySelector('[name="priceType"]');
        const amount = form.querySelector('[name="amount"]');

        quantity.addEventListener('input', (event) => {
            if (priceType.value === 'KMS') {
                amount.value = event.target.value * 0.25;
            }
        })
    }

    function displayQuantityLabel(priceType) {
        const label = form.querySelector('#quantityLabel');

        const springMessageNode = label.childNodes[0];
        if (springMessageNode.nodeType === Node.TEXT_NODE || springMessageNode.nodeType === Node.ELEMENT_NODE) {
            if (priceType === 'KMS') {
                springMessageNode.textContent = messages.personalExpense.quantity.kms;
            } else if (priceType === 'GAS') {
                springMessageNode.textContent = messages.personalExpense.quantity.gas;
            } else if (priceType === 'GASOIL') {
                springMessageNode.textContent = messages.personalExpense.quantity.gasoil;
            } else if (priceType === 'GASOLINE') {
                springMessageNode.textContent = messages.personalExpense.quantity.gasoline;
            } else {
                springMessageNode.textContent = messages.personalExpense.quantity.default;
            }
        }
    }

    function displayQuantityInfo(priceType) {
        const onlyKms = form.querySelector('.only-kms')
        if (priceType === 'KMS') {
            onlyKms.classList.remove('d-none');
        } else {
            onlyKms.classList.add('d-none');
        }
    }

    function displayAmountInput(priceType) {
        form.querySelector('[name="amount"]').disabled = priceType === 'KMS';
    }

    function createOrUpdate() {
        const id = modal.hasAttribute('data-id') ? modal.getAttribute('data-id') : null;
        if (!isValidForm('#personalExpenseForm')) {
            return;
        }

        form.classList.remove('was-validated');
        $('#personalExpenseModal').modal('hide');

        showLoading();

        const endpoint = id
            ? '/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses/' + id
            : '/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses';
        const method = id ? 'patch' : 'post';
        const data = {
            description: form.querySelector('[name="description"]').value,
            priceType: form.querySelector('[name="priceType"]').value,
            startDate: form.querySelector('[name="startDate"]').value,
            quantity: form.querySelector('[name="quantity"]').value,
            amount: form.querySelector('[name="amount"]').value,
            paymentType: form.querySelector('[name="paymentType"]').value,
            files: filesData
        };

        filesData = [];

        axios[method](endpoint, data).then((response) => {
            form.reset();
            modal.removeAttribute('data-id');
            dTable.ajax.reload();
            showNotify(messages.personalExpense.create.success.replace('{0}', response.data.data.id));
        }).catch(error => showNotify(error.response.data.detail, 'danger')).finally(() => hideLoading());
    }

    function edit(id) {
        axios.get('/v1/expenses/personal/sheets/' + personalExpenseSheet.id + '/expenses/' + id).then((response) => {
            const personalExpense = response.data.data;
            form.querySelector('[name="priceType"]').value = personalExpense.priceType;
            form.querySelector('[name="paymentType"]').value = personalExpense.paymentType;
            form.querySelector('[name="quantity"]').value = personalExpense.quantity;
            form.querySelector('[name="amount"]').value = personalExpense.amount;
            form.querySelector('[name="startDate"]').value = personalExpense.startDate;
            // TODO: FILES ??
            form.querySelector('[name="description"]').value = personalExpense.description;
            modal.setAttribute('data-id', id);
            $('#personalExpenseModal').modal('show');
        });
    }

    function getFiles() {
        form.querySelector('[name="files"]').addEventListener('change', function(event) {
            const files = event.target.files;

            for (let i = 0; i < files.length; i++) {
                const reader = new FileReader();
                reader.onloadend = function() {
                    filesData.push({
                        name: files[i].name,
                        content: reader.result.split(',')[1]
                    });
                }
                reader.readAsDataURL(files[i]);
            }
        });
    }

    $(document).ready(function() {
        onChangePriceType();
        getFiles();
    });

</script>