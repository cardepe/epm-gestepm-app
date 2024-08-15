<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="expenses.corrective.title" />
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
										<img class="user-avatar rounded-circle" src="/ui/static/images/profile.png" alt="me" height="40" />
									</div>

									<div style="font-size: 12px">${ user.name } ${ user.surnames }</div>
									<div style="font-size: 11px">
										<spring:message code="expenses.corrective.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-4">
								<div class="col">
									<div class="form-group">
										<select id="projectDropdown" class="form-control input"
											name="project">
											<option></option>
											<c:forEach items="${projects}" var="project">
												<option value="${project.id}" data-info="${project}">
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
										<button id="openModal" type="button" class="btn btn-success btn-sm w-100">
											<spring:message code="expenses.corrective.create" />
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
						<spring:message code="expenses.corrective.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="expenses.corrective.table.id" /></th>
									<th id="thProject"><spring:message code="expenses.personal.table.project" /></th>
									<th id="thCost"><spring:message code="expenses.corrective.table.cost" /></th>
									<th id="thDescription"><spring:message code="expenses.personal.table.description" /></th>
									<th id="thDate"><spring:message code="expenses.corrective.table.date" /></th>
									<th id="thActions" class="all"><spring:message code="expenses.personal.table.actions" /></th>
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
<div id="modal" class="modal fade corrective-modal" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="expense.corrective.create.modal.title" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="createCorrectiveForm">
				
					<input id="projectInput" type="hidden" name="project" />
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="createCostInput" class="col-form-label"><spring:message code="expense.corrective.create.modal.cost" /></label> 
								<input id="createCostInput" name="cost" type="number" class="form-control" required>
							</div>
						</div>
					</div>
							
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="createDescriptionInput" class="col-form-label"><spring:message code="expense.corrective.create.modal.description" /></label>
								<textarea id="createDescriptionInput" name="description" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" id="back" data-dismiss="modal" class="btn btn-sm">
							<spring:message code="back" />
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
<div id="editModal" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="expense.corrective.create.modal.title" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editCorrectiveForm">					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="editCostInput" class="col-form-label"><spring:message code="expense.corrective.create.modal.cost" /></label> 
								<input id="editCostInput" name="cost" type="number" class="form-control" required>
							</div>
						</div>
					</div>
							
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="editDescriptionInput" class="col-form-label"><spring:message code="expense.corrective.create.modal.description" /></label>
								<textarea id="editDescriptionInput" name="description" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" id="back" data-dismiss="modal" class="btn btn-sm">
							<spring:message code="back" />
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

		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */

		/* Datatables */
		var dTable = $('#dTable').DataTable({
			"lengthChange": false,
			"responsive": true,
			"processing": true,
			"pageLength": 10,
			"serverSide": true,
			"ajax": "/expenses/corrective/dt",
			"rowId": "ec_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[2, "desc"]],
			"columns": [
				{ "data": "ec_id" },
				{ "data": "pr_projectName" },
				{ "data": "ec_cost" },
				{ "data": "ec_description" },
				{ "data": "ec_creationDate" },				
				{ "data": null }
			],
			"columnDefs": [
				{ "targets": [0], "visible": false },
                {  
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY');
                	},
                	"targets": 4
                },
			    {  
				    "render": function ( data, type, row ) {
                    	return data + ' €';
                	},
                	"targets": 2 
                },
                {
					"className" : "text-center",
					"targets" : "_all"
				}, 
			    { 
    			    "defaultContent": "${tableActionButtons}", 
    			    "width": "217px",
    			    "orderable": false, 
    			    "targets": -1 
    			}
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */

		$('#openModal').click(function () {
			
			if (!$('#projectDropdown').val()) {
				return alert("${jspUtil.parseTagToText('expense.corrective.create.alert')}");
			}

			$('.corrective-modal').modal('show');
		});

		$('#create').click(function() {

			if (validateForm()) {
				$('#createCorrectiveForm').addClass('was-validated');
			} else {
				
				showLoading();

				var projectId = $('#projectDropdown').val();
				$('#projectInput').val(projectId);
				
				$('#createCorrectiveForm').removeClass('was-validated');

				$.ajax({
					type : "POST",
					url : "/expenses/corrective/create",
					data : $('#createCorrectiveForm').serialize(),
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

				$('.corrective-modal').modal('hide');
			}
		});
	});

	function validateForm() {
		var cost = $('#createCostInput').val();
		var description = $('#createDescriptionInput').val();

		return !cost || !description;
	}	

	function editCorrective(id, cost, description) {
		
		$('#editModal').find('#editCostInput').val(cost);
		$('#editModal').find('#editDescriptionInput').val(description);
		$('#editModal').modal('show');	

		$('#save').unbind('click').click(function() {

			showLoading();
			
			$.ajax({
				type: "POST",
				url: "/expenses/corrective/update/" + id,
				data: $('#editCorrectiveForm').serialize(),
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
	
	function deleteCorrective(id) {
		
		var ok = confirm("${jspUtil.parseTagToText('expense.corrective.delete.alert')}");

		if (ok) {

			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/expenses/corrective/delete/" + id,
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