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
							<spring:message code="holidays.admin.title" />
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
										<spring:message code="holidays.admin.sub.title" />
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
						<spring:message code="holidays.admin.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message
											code="holidays.admin.table.id" /></th>
									<th id="thName"><spring:message
											code="holidays.admin.table.name" /></th>
									<th id="thDate"><spring:message
											code="holidays.admin.table.date" /></th>
									<th id="thCountry"><spring:message
											code="holidays.admin.table.activity.center" /></th>
									<th id="thActivityCenter"><spring:message
											code="holidays.admin.table.activity.center" /></th>
									<th id="thActions" class="all"><spring:message
											code="holidays.admin.table.actions" /></th>
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
						<spring:message code="holidays.admin.create.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="createHolidayForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="name" class="col-form-label"><spring:message
										code="holidays.admin.table.name" /></label> <input id="name"
									name="name" type="text" class="form-control" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="day" class="col-form-label"><spring:message
										code="holidays.admin.table.day" /></label> <input id="day"
									name="day" type="number" class="form-control" max="31" required>
							</div>
						</div>
						
						<div class="col">
							<div class="form-group">
								<label for="month" class="col-form-label"><spring:message
										code="holidays.admin.table.month" /></label>
										
								<select
									id="month" name="month" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="holidays.admin.month.placeholder" />
									</option>
									<c:forEach items="${months}" var="month">
										<option value="${month.key}">
											<spring:message code="${month.value}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="holidays.admin.table.activity.center" /></label>

								<select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="holidays.admin.activity.center.placeholder" />
									</option>
									<c:forEach items="${countries}" var="activityCenter">
										<option value="${activityCenter.id}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="holidays.admin.table.activity.center" /></label> 

								<select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option value="" selected="selected">
										<spring:message
											code="holidays.admin.activity.center.placeholder" />
									</option>
									<c:forEach items="${activityCenters}" var="activityCenter">
										<option value="${activityCenter.id}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
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
						<spring:message code="activity.centers.edit.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editHolidayForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="name" class="col-form-label"><spring:message
										code="holidays.admin.table.name" /></label> <input id="name"
									name="name" type="text" class="form-control" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="day" class="col-form-label"><spring:message
										code="holidays.admin.table.day" /></label> <input id="day"
									name="day" type="number" class="form-control" max="31" required>
							</div>
						</div>
						
						<div class="col">
							<div class="form-group">
								<label for="month" class="col-form-label"><spring:message
										code="holidays.admin.table.month" /></label>
										
								<select
									id="month" name="month" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="holidays.admin.month.placeholder" />
									</option>
									<c:forEach items="${months}" var="month">
										<option value="${month.key}">
											<spring:message code="${month.value}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="holidays.admin.table.activity.center" /></label>

								<select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option disabled value="" selected="selected">
										<spring:message
											code="holidays.admin.activity.center.placeholder" />
									</option>
									<c:forEach items="${countries}" var="activityCenter">
										<option value="${activityCenter.id}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message
										code="holidays.admin.table.activity.center" /></label> 

								<select
									id="activityCenter" name="activityCenter" class="form-control"
									required>
									<option value="" selected="selected">
										<spring:message
											code="holidays.admin.activity.center.placeholder" />
									</option>
									<c:forEach items="${activityCenters}" var="activityCenter">
										<option value="${activityCenter.id}">
											<spring:message code="${activityCenter.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
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
	$(document).ready(function() {

		/* Datatables */
		var dTable = $('#dTable').DataTable({
			"lengthChange" : false,
			"searching" : false,
			"responsive" : true,
			"processing" : true,
			"serverSide" : true,
			"ajax" : "/admin/holidays/dt",
			"rowId" : "ho_id",
			"language" : {
				"url" : "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "asc"]],
			"columns" : [ {
				"data" : "ho_id"
			}, {
				"data" : "ho_name"
			}, {
				"data" : "ho_date"
			}, {
				"data" : "ho_country"
			}, {
				"data" : "ho_activityCenter"
			}, {
				"data" : null
			} ],
			"columnDefs" : [
			{ "targets": [0], "visible": false },
			{
				"className" : "text-center",
				"targets" : "_all"
			}, {
				"orderable" : false,
				"targets" : 2
			}, {
				"defaultContent": "-",
				"targets" : 4
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
				$('#createHolidayForm').addClass('was-validated');
			} else {

				showLoading();
				
				$('#createHolidayForm').removeClass('was-validated');

				$.ajax({
					type : "POST",
					url : "/admin/holidays/create",
					data : $('#createHolidayForm').serialize(),
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
		var name = $('#name').val();
		var day = $('#day').val();
		var month = $('#month').val();
		var activityCenter = $('#activityCenter').val();

		return !name || !day || !month || !activityCenter;
	}

	function deleteHoliday(id) {
		var ok = confirm("${jspUtil.parseTagToText('holidays.admin.delete.alert')}");

		if (ok) {
			
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/admin/holidays/delete/" + id,
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

	function editHoliday(id, name, date, activityCenter, activityCenter) {

		var day = date.split('/')[0];
		var month = date.split('/')[1];
		
		$('#editModal').find('#name').val(name);
		$('#editModal').find('#day').val(day);
		$('#editModal').find('#month').val(month);
		
		$('#editModal').find('#activityCenter option').filter(function() {
		    return this.text == activityCenter;
		}).attr('selected', true);

		$('#editModal').find('#activityCenter option').filter(function() {
		    return this.text == activityCenter; 
		}).attr('selected', true);
		
		$('#editModal').modal('show');	

		$('#save').unbind('click').click(function() {

			showLoading();
			
			$.ajax({
				type: "POST",
				url: "/admin/holidays/update/" + id,
				data: $('#editHolidayForm').serialize(),
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