<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="displacements.title" />
						</h1>
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
										<img class="user-avatar rounded-circle"
											src="/ui/static/images/profile.png" alt="me" height="40" />
									</div>

									<div style="font-size: 12px">${ user.name } ${ user.surnames }</div>
									<div style="font-size: 11px">
										<spring:message code="displacements.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-2">
								<div class="col">
									<div class="form-group">
										<button type="button" class="btn btn-success btn-sm w-100"
											data-toggle="modal" data-target="#createModal">
											<spring:message code="create" />
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-12 col-xl-9 h-100">
			<div class="card">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="displacements.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message
											code="displacements.table.id" /></th>
									<th id="thTitle"><spring:message
											code="displacements.table.title" /></th>
									<th id="thActivityCenter"><spring:message
											code="displacements.table.activity.center" /></th>
									<th id="thType"><spring:message
											code="displacements.table.displacement.type" /></th>
									<th id="thTotalTime"><spring:message
											code="displacements.table.total.time" /></th>
									<th id="thActions" class="all"><spring:message
											code="displacements.table.actions" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- MODAL -->
<div id="createModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="displacements.create.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="createDisplacementForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="title" class="col-form-label"><spring:message
										code="displacements.table.title" /></label> <input id="title"
									name="title" type="text" class="form-control" required>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="displacements.table.activity.center" /></label> <select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="displacements.activity.center.placeholder" />
									</option>
									<c:forEach items="${activityCenters}" var="activityCenter">
										<option value="${activityCenter.id}"
											data-info="${activityCenter}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col">
							<div class="form-group">
								<label for="displacementType" class="col-form-label"><spring:message
										code="displacements.table.displacement.type" /></label> <select
									id="displacementType" name="displacementType"
									class="form-control" required>
									<option disabled value="" selected="selected">
										<spring:message
											code="displacements.displacement.type.placeholder" />
									</option>
									<option value="1"><spring:message code="displacements.type.1" /></option>
									<option value="0"><spring:message code="displacements.type.0" /></option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="totalTime" class="col-form-label"><spring:message
										code="displacements.table.total.time" /></label> <input type="time"
									class="form-control" id="totalTime" name="totalTime" required>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" class="btn btn-sm" data-dismiss="modal">
							<spring:message code="cerrar" />
						</button>
					</div>
					<div class="float-right">
						<button id="create" type="button" class="btn btn-sm btn-success">
							<spring:message code="create" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- MODAL -->
<div id="editModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="displacements.edit.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editDisplacementForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="title" class="col-form-label"><spring:message
										code="displacements.table.title" /></label> <input id="title"
									name="title" type="text" class="form-control" required>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="displacements.table.activity.center" /></label> <select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="displacements.activity.center.placeholder" />
									</option>
									<c:forEach items="${activityCenters}" var="activityCenter">
										<option value="${activityCenter.id}"
											data-info="${activityCenter}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col">
							<div class="form-group">
								<label for="displacementType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label> 
								<select id="displacementType" name="displacementType" class="form-control" required>
									<option disabled value="" selected="selected">
										<spring:message code="displacements.displacement.type.placeholder" />
									</option>
									<option value="1"><spring:message code="displacements.type.1" /></option>
									<option value="0"><spring:message code="displacements.type.0" /></option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="totalTime" class="col-form-label"><spring:message
										code="displacements.table.total.time" /></label> <input type="time"
									class="form-control" id="totalTime" name="totalTime" required>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" class="btn btn-sm" data-dismiss="modal">
							<spring:message code="cerrar" />
						</button>
					</div>
					<div class="float-right">
						<button id="save" type="button" class="btn btn-sm btn-success">
							<spring:message code="save" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<script>
	var $ = jQuery.noConflict();
	
	$(document).ready(function() {

		/* Datatables */
		var dTable = $('#dTable').DataTable({
			"lengthChange" : false,
			"searching" : false,
			"responsive" : true,
			"processing" : true,
			"serverSide" : true,
			"ajax" : "/admin/displacements/dt",
			"rowId" : "di_id",
			"language" : {
				"url" : "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "asc"]],
			"columns" : [ {
				"data" : "di_id"
			}, {
				"data" : "di_title"
			}, {
				"data" : "di_activityCenter"
			}, {
				"data" : "di_displacementType"
			}, {
				"data" : "di_totalTime"
			}, {
				"data" : null
			} ],
			"columnDefs" : [ 
			{ "targets": [0], "visible": false },
			{
				"className" : "text-center",
				"targets" : "_all"
			}, {
				"defaultContent" : "${tableActionButtons}",
				"orderable" : false,
				"targets" : -1
			} ],
			"dom" : "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback" : function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */

		$('#create').click(function() {

			if (validateForm()) {
				$('#createDisplacementForm').addClass('was-validated');
			} else {
				
				showLoading();
				
				$('#createDisplacementForm').removeClass('was-validated');

				$.ajax({
					type : "POST",
					url : "/admin/displacements/create",
					data : $('#createDisplacementForm').serialize(),
					success : function(msg) {
						dTable.ajax.reload();
						hideLoading();
						showNotify(msg, 'success');
					},
					error : function(e) {
						hideLoading();
						showNotify(e.responseText, 'danger');
					}
				});

				$('#createModal').modal('hide');
			}
		});
	});

	function validateForm() {
		var title = $('#title').val();
		var activityCenter = $('#activityCenter').val();
		var displacementType = $('#displacementType').val();
		var totalTime = $('#totalTime').val();

		return !title || !activityCenter || !displacementType || !totalTime;
	}

	function deleteDisplacement(id) {
		var ok = confirm("${jspUtil.parseTagToText('displacement.delete.alert')}");

		if (ok) {

			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/admin/displacements/delete/" + id,
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

	function editDisplacement(id, title, activityCenter, displacementType, totalTime) {
		
		$('#editModal').find('#title').val(title);
		
		$('#editModal').find('#activityCenter option').filter(function() {
		    return this.text == activityCenter; 
		}).attr('selected', true);

		$('#editModal').find('#displacementType option').filter(function() {
		    return this.text == displacementType; 
		}).attr('selected', true);

		$('#editModal').find('#totalTime').val(totalTime);
		$('#editModal').modal('show');	

		$('#save').unbind('click').click(function() {

			showLoading();
			
			$.ajax({
				type: "POST",
				url: "/admin/displacements/update/" + id,
				data: $('#editDisplacementForm').serialize(),
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

			$('#editModal').modal('hide');
		});
	}
	
</script>