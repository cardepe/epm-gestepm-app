<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>

.form-control[readonly] {
	background-color: #fff;
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="shares.displacement.title" /></h1>
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
						<form id="createForm" action="/shares/displacement/create" method="GET">							
							<div class="card-body">
								<div class="row">
									<div class="col">
										<div class="img-profile-circle float-left">
											<img class="user-avatar rounded-circle"
												src="/ui/static/images/profile.png" alt="me" height="40" />
										</div>
	
										<div style="font-size: 12px">${ user.name } ${ user.surnames }</div>
										<div style="font-size: 11px">
											<spring:message code="shares.displacement.sub.title" />
										</div>
									</div>
								</div>
	
								<div class="row mt-4">
									<div class="col">
										<div class="form-group">
											<select id="projectDropdown" class="form-control input" name="project" required>
												<option></option>
												<c:forEach items="${projects}" var="project">
													<option value="${project.id}" data-info="${project.activityCenter.id}">
														<spring:message code="${project.name}" />
													</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col">
										<div class="form-group">
											<button id="create" type="button" class="btn btn-success btn-sm w-100">
												<spring:message code="shares.displacement.create.btn" />
											</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-12 col-xl-9 h-100">
			<div class="card">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="shares.displacement.title" />
					</div>
					
					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="shares.displacement.table.id" /></th>
									<th id="thActivityCenter"><spring:message code="shares.displacement.table.activity.center" /></th>
									<th id="thTitle"><spring:message code="shares.displacement.table.project.name" /></th>
									<th id="thTotalTime"><spring:message code="shares.displacement.table.total.time" /></th>
									<th id="actions" class="all"><spring:message code="shares.displacement.table.actions" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- CREATE MODAL -->
<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createDispForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.displacement.create.btn" />
						</h5>
					</div>
				</div>
				<div class="modal-body">		
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="displacement" class="col-form-label"><spring:message code="shares.displacement.table.activity.center" /></label>
								<select id="displacement" name="displacement" class="form-control" onchange="loadDisplacement()" required>
									<option disabled selected="selected">
										<spring:message code="shares.displacement.table.activity.center" />
									</option>
									<c:forEach items="${teamLeaders}" var="teamLeader">
										<option value="${teamLeader.userId}">
											<spring:message code="${teamLeader.name} ${teamLeader.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="displacementDate" class="col-form-label"><spring:message code="shares.displacement.table.total.time" /></label>
								<input id="displacementDate" name="displacementDate" type="datetime-local" class="form-control" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="manualHours" class="col-form-label"><spring:message code="shares.displacement.total.time" /></label>
							<input type="time" class="form-control" id="manualHours" name="manualHours" required>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="displacementType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label> 
							<select id="displacementType" name="displacementType" class="form-control" required>
								<option disabled value="" selected="selected">
									<spring:message code="displacements.displacement.type.placeholder" />
								</option>
								<option value="PUBLIC_TRANSPORT"><spring:message code="displacements.type.PUBLIC_TRANSPORT" /></option>
								<option value="VEHICLE"><spring:message code="displacements.type.VEHICLE" /></option>
							</select>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observations" class="col-form-label"><spring:message code="shares.displacement.observations" /></label>
								<textarea id="observations" name="observations" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
					
					<input id="manualDisplacement" name="manualDisplacement" type="hidden" class="form-control" value="1" required>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
						<div class="float-right">
							<button id="createShareBtn" type="button" class="btn btn-sm btn-success"><spring:message code="create" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /CREATE MODAL -->

<!-- VIEW MODAL -->
<div id="viewModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="viewDispForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.displacement.view.btn" />
						</h5>
					</div>
				</div>
				<div class="modal-body">		
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="displacement" class="col-form-label"><spring:message code="shares.displacement.table.activity.center" /></label>
								<select id="displacement" name="displacement" class="form-control" readonly disabled>
									<option disabled selected="selected">
										<spring:message code="shares.displacement.table.activity.center" />
									</option>
									<c:forEach items="${teamLeaders}" var="teamLeader">
										<option value="${teamLeader.userId}">
											<spring:message code="${teamLeader.name} ${teamLeader.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="displacementDate" class="col-form-label"><spring:message code="shares.displacement.table.total.time" /></label>
								<input id="displacementDate" name="displacementDate" type="datetime-local" class="form-control" required readonly>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="manualHours" class="col-form-label"><spring:message code="shares.displacement.total.time" /></label>
							<input type="time" class="form-control" id="manualHours" name="manualHours" readonly>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="displacementType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label> 
							<select id="displacementType" name="displacementType" class="form-control" readonly disabled>
								<option value="1"><spring:message code="displacements.type.1" /></option>
								<option value="0"><spring:message code="displacements.type.0" /></option>
							</select>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observations" class="col-form-label"><spring:message code="shares.displacement.observations" /></label>
								<textarea id="observations" name="observations" class="form-control" rows="6" readonly></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /VIEW MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	var $=jQuery.noConflict();
	
	$(document).ready(function() {

		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */

		/* Datatables */
		$('#dTable').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"ajax": "/shares/displacement/dt",
			"rowId": "ds_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[3, "desc"]],
			"columns": [
				{ "data": "ds_id" },
				{ "data": "di_title" },
				{ "data": "pr_name" },
				{ "data": "ds_displacementDate" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "targets": "_all" },
				{  
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY HH:mm');
                	},
                	"targets": 3
                },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */

		$('#create').click(function () {

			if (!$('#projectDropdown').val()) {
				return alert("${jspUtil.parseTagToText('share.create.alert')}");
			}

			let activityCenterId = $('#projectDropdown option:selected').attr('data-info');
			let callbackFunction = function(html) {
				$('#displacement').html(html);
				$('#createModal').modal('show');
			}

			loadDisplacements(activityCenterId, callbackFunction);
		});

		$('#createShareBtn').click(function () {

			if (validateForm()) {
				$('#createDispForm').addClass('was-validated');
			} else {
				
				showLoading();
				
				$('#createDispForm').removeClass('was-validated');
			
				var projectId = $('#projectDropdown').val();
	
				// enable to send POST
				$('#createModal :input').attr('disabled', false);	
				
				$.ajax({
					type: "POST",
					url: "/shares/displacement?project=" + projectId,
					data: $('#createDispForm').serialize(),
					success: function(response) {
						$('#dTable').DataTable().ajax.reload();
						hideLoading();
						showNotify(response.msg, 'success');				
					},
					error: function(e) {
						hideLoading();
						showNotify(e.responseText, 'danger');
					}
				});
	
				$('#createModal').modal('hide');
			}
		});

		// Cleaning Modal
		$('#createModal').on('hidden.bs.modal', function (e) {
			$('#createModal :input').val('');
			$('#createModal :input').prop('checked', false);
			$('#createModal :input').attr('disabled', false);			
		});
	});

	function validateForm() {
		var displacement = document.getElementById('displacement');
		var displacementDate = document.getElementById('displacementDate');
		var manualHours = document.getElementById('manualHours');
		var displacementType = document.getElementById('displacementType');

		return (!displacement.value.length || !displacementDate.value.length || !manualHours.value.length || !displacementType.value.length);
	}

	function getDisplacement(id) {
		return $.ajax({
		    url: '/v1/displacements/' + id,
		    type: 'GET'
		 });
	}

	function loadDisplacement() {
		let id =  $('#displacement').val();

		axios.get('/v1/displacements/' + id).then((response) => {
			let displacement = response.data.data;

			let manualHoursInput = $('#createModal #manualHours');
			let typeInput = $('#createModal #displacementType');

			manualHoursInput.val(minutesToTime(displacement.totalTime));
			manualHoursInput.attr('disabled', true);

			typeInput.val(displacement.type);
			typeInput.attr('disabled', true);
		});
	}

	function minutesToTime(minutes) {
		return new Date(minutes * 60 * 1000).toISOString().substring(11, 16);
	}

	function formatType(type) {
		if (type === 'PUBLIC_TRANSPORT') {
			return messages.displacements.publictransport;
		} else if (type === 'VEHICLE') {
			return messages.displacements.vehicle;
		}

		return 'UNDEFINED';
	}

</script>