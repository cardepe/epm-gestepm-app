<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="materialModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="z-index: 100000">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="shares.intervention.detail.materials.add.title" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="materialForm" class="needs-validation">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="materialDescription" class="col-form-label"><spring:message code="shares.intervention.create.materials.desc" /></label>
                                <input id="materialDescription" name="description" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="materialUnits" class="col-form-label"><spring:message code="shares.intervention.create.materials.uds" /></label>
                                <input id="materialUnits" name="units" type="number" class="form-control" required>
                            </div>
                        </div>

                        <div class="col">
                            <div class="form-group">
                                <label for="materialReference" class="col-form-label"><spring:message code="shares.intervention.create.materials.ref" /></label>
                                <input id="materialReference" name="reference" type="text" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <input id="rowId" name="rowId" class="form-control input" type="hidden" readonly />
                </form>
            </div>
            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" data-dismiss="modal" class="btn btn-sm"><spring:message code="back" /></button>
                    </div>
                    <div class="float-right">
                        <button id="materialBtn" type="button" class="btn btn-sm btn-success"><spring:message code="save" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    let materialModal = $('#materialModal');
    let materialForm = $('#materialForm');
    let materialBtn = $('#materialBtn');
    let dataAction = 'create';
    let lastMaterialId = 0;

    $(document).ready(function() {
        materialBtn.click(function () {
            if (validateModal()) {
                materialForm.addClass('was-validated');
            } else {
                materialForm.removeClass('was-validated');
                dataAction === 'create' ? addMaterial() : updateMaterial();
            }
        });

        materialModal.on("hidden.bs.modal", function () {
            materialForm[0].reset();
        });
    });

    function edit(id) {
        dataAction = id == null ? 'create' : 'update';

        if (dataAction === 'update') {
            setFormData(id);
        }

        materialModal.modal('show');
    }

    function addMaterial() {
        let tool = getFormData(materialForm);
        tool['id'] = ++lastMaterialId;

        materialsModified = true;
        materialsDataTable.row.add(tool).draw();
        materialModal.modal('hide');

        if (materialsDataTable.data().count() >= 5) {
            materialBtn.prop("disabled", true);
        }
    }

    function updateMaterial() {
        let tool = getFormData(materialForm);
        tool['id'] = $('#rowId').val();

        materialsModified = true;
        materialsDataTable.row('#' + tool.rowId).data(tool).draw();
        materialModal.modal('hide');
    }

    function removeMaterial(id) {
        materialsDataTable.row('#' + id).remove().draw();
        materialsModified = true;
        if (materialsDataTable.data().count() < 5 && materialBtn.is(":disabled")) {
            materialBtn.prop("disabled", false);
        }
    }

    function validateModal() {
        let description = document.getElementById('materialDescription');
        let units = document.getElementById('materialUnits');
        let reference = document.getElementById('materialReference');

        return (!description.value.length || !units.value.length || !reference.value.length);
    }

    function getFormData(form) {
        let unIndexedArray = form.serializeArray();
        let indexedArray = {};

        $.map(unIndexedArray, function(n){
            indexedArray[n['name']] = n['value'];
        });

        return indexedArray;
    }

    function setFormData(id) {
        let data = materialsDataTable.row('#' + id).data();

        Object.keys(data).forEach(function(key) {
            let value = data[key];
            materialForm.find('input[name="' + key + '"]').val(value);
        });

        $('#rowId').val(id);
    }

</script>