<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>

    .border-red {
        border: 1px solid #dc3545;
        border-radius: .25rem;
    }

    #row-desc {
        display: none;
    }

</style>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0">
            <div class="col">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="sidebar.signing.page" /></h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="row h-100">
        <div class="col-md-12 col-xl-3">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <div class="img-profile-circle float-left">
                                        <img class="user-avatar rounded-circle" src="/ui/static/images/profile.png" alt="me" height="40" />
                                    </div>

                                    <div style="font-size: 12px">${ userName }</div>
                                    <div style="font-size: 11px"><spring:message code="signing.manual.page.title" /></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-body">
                            <form id="userManualSigningForm" class="needs-validation">

                                <input type="hidden" id="userIdInput" name="userId" value="${userId}" required />
                                <input type="hidden" id="geolocationInput" name="geolocation" />

                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <select id="manualSigningTypeDropdown" class="form-control input" name="manualTypeId" required onchange="updateForm(this.value)">
                                                <option></option>
                                                <c:forEach items="${manualSigningTypes}" var="manualSigningType">
                                                    <option value="${manualSigningType.id}">
                                                        <spring:message code="${manualSigningType.name}" />
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group">
                                            <input type="datetime-local" style="font-size: 12px" class="form-control" id="startDateInput" name="startDate" required>
                                        </div>
                                    </div>

                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group">
                                            <input type="datetime-local" style="font-size: 12px" class="form-control" id="endDateInput" name="endDate" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <input type="file" style="font-size: 12px" class="form-control-file" id="file"
                                                   name="file" accept="image/x-png,image/gif,image/jpeg,application/pdf" onchange="setFile(event)"
                                                   lang="${locale}">
                                        </div>
                                    </div>
                                </div>

                                <div id="row-desc" class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <textarea id="description" style="font-size: 12px" name="description" class="form-control" rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col">
                                        <button id="userManualSigningBtn" type="button" class="btn btn-sm btn-success w-100 text-white"><i class="fa fa-play" style="font-size: 12px; margin-right: 5px"></i> <spring:message code="signing.manual.page.start" /></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-12 col-xl-9 h-100">
            <div class="card mb-0">
                <div class="card-body">
                    <div class="title mb-0">
                        <spring:message code="signing.manual.page.title" />
                    </div>

                    <div class="table-responsive">
                        <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                            <caption class="d-none">
                                NO SONAR
                            </caption>
                            <thead>
                                <tr>
                                    <th id="thId"><spring:message code="signing.page.table.id" /></th>
                                    <th id="thType"><spring:message code="signing.page.table.type.name" /></th>
                                    <th id="thStartDate"><spring:message code="signing.page.table.start.date" /></th>
                                    <th id="thEndDate"><spring:message code="signing.page.table.end.date" /></th>
                                    <th id="thActions" class="all"><spring:message code="signing.page.table.actions" /></th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
    var file;

    $(document).ready(function() {

        getLocation();

        /* Select 2 */

        $('#manualSigningTypeDropdown').select2({
            allowClear: true,
            placeholder: "${jspUtil.parseTagToText('manualsigning.selectable')}",
            dropdownCssClass: 'selectStyle'
        });

        /* End Select 2 */

        /* Datatables */
        var dTable = $('#dTable').DataTable({
            "searching": false,
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "lengthMenu": [ 10, 25, 50, 75, 100 ],
            "ajax": "/signing/manual/dt${empty userId ? '' : '?user='}${userId}",
            "rowId": "ums_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "order": [[0, "desc"]],
            "columns": [
                { "data": "ums_id" },
                { "data": "ums_manualTypeId" },
                { "data": "ums_startDate" },
                { "data": "ums_endDate" },
                { "data": null }
            ],
            "columnDefs": [
                { "className": "text-center", "targets": "_all" },
                {
                    "render": function ( data ) {
                        if (!data) { return '-'; }
                        else { return moment(data).format('DD/MM/YYYY HH:mm'); }
                    },
                    "targets": [2, 3]
                },
                { "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
            ],
            "dom": "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
            "drawCallback": function() {
                parseActionButtons(${trashVisible});
            }
        });
        /* End Datatables */

        $('#userManualSigningBtn').click(function () {

            if (validateForm()) {
                $('#userManualSigningForm').addClass('was-validated');
                $($('#manualSigningTypeDropdown').data('select2').$container).addClass('border-red');
            } else {
                $($('#manualSigningTypeDropdown').data('select2').$container).removeClass('border-red');

                showLoading();

                var form = $('#userManualSigningForm')[0];
                var formData = new FormData(form);

                if (file) {
                    formData.append('justification', file);
                }

                $.ajax({
                    type: "POST",
                    url: "/signing/manual",
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    cache: false,
                    data: formData,
                    success: function (msg) {
                        $('#dTable').DataTable().ajax.reload();
                        clearForm();
                        hideLoading();
                        showNotify(msg, 'success');
                    },
                    error: function (e) {
                        hideLoading();
                        showNotify(e.responseText, 'danger');
                    }
                });
            }
        });
    });

    function validateForm() {

        var manualType = document.getElementById('manualSigningTypeDropdown');
        var startDate = document.getElementById('startDateInput');
        var endDate = document.getElementById('endDateInput');

        return (!manualType.value.length || !startDate.value.length || !endDate.value.length);
    }

    function setFile(event) {
        file = event.target && event.target.files && event.target.files[0];
    }

    function updateForm(type) {

        if (type == 8) {
            $('#row-desc').show();
        } else {
            $('#row-desc').hide();
        }
    }

    function clearForm() {
        $('#startDateInput').val('');
        $('#endDateInput').val('');
        $('#file').val('');
        $("#manualSigningTypeDropdown").val(null).trigger("change");
    }

    function deleteManualSigning(id) {

        var ok = confirm("${jspUtil.parseTagToText('signing.manual.delete.alert')}");

        if (ok) {
            showLoading();

            $.ajax({
                type: "DELETE",
                url: "/signing/manual/" + id,
                success: function(msg) {
                    $('#dTable').DataTable().ajax.reload();
                    hideLoading();
                    showNotify(msg, 'success');
                },
                error: function(e) {
                    hideLoading();
                    showNotify(e.responseText, 'danger');
                }
            });
        }
    }

</script>