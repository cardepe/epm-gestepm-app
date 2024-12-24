<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0 align-items-center">
			<div class="col-md-12 col-lg-9">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="projects.title" /></h1>
					</div>
				</div>
			</div>

			<div class="col-md-12 col-lg-3">
				<form>
					<div class="col">
						<select id="yearDropdown" name="year" class="form-control form-control-sm">
							<option disabled value="" selected="selected">
								<spring:message code="time.control.year.placeholder" />
							</option>
							<c:forEach items="${years}" var="year">
								<option value="${year}">
									<spring:message code="${year}" />
								</option>
							</c:forEach>
						</select>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<div class="content huge-page">
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
										<spring:message code="projects.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-2">
								<div class="col">
									<div class="form-group">
										<button type="button" class="btn btn-success btn-sm w-100" data-toggle="modal" data-target="#createModal">
											<spring:message code="projects.create.btn" />
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col">
					<div class="card">
						<div class="card-body" data-table-filter="form">
							<span class="title"><spring:message code="filters" /></span>
							
							<div class="form-group mt-2">
								<select id="projectDropdown" data-table-filter="projectId" class="form-control input">
									<option></option>
									<c:forEach items="${projectListDTO}" var="projectDTO">
										<option value="${projectDTO.id}">
											<spring:message code="${projectDTO.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group mt-2 mb-2">
								<select id="responsibleDropdown" data-table-filter="responsibleId" class="form-control input">
									<option></option>
									<c:forEach items="${responsables}" var="responsable">
										<option value="${responsable.userId}">
											<spring:message code="${responsable.name} ${responsable.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="custom-control custom-checkbox">
							    <input type="checkbox" class="custom-control-input" data-table-filter="station" id="checkboxIsStation">
							    <label class="custom-control-label mr-3" for="checkboxIsStation" style="font-size: 12px; padding-top: 3px"><spring:message code="projects.filter.station.checkbox" /></label>
							</div>
							
							<div class="row mt-2">
								<div class="col">
									<button type="button" class="btn btn-outline-secondary btn-sm w-100" style="font-size: 12px" onclick="resetTable()">
										<em class="fas fa-sync"></em> <spring:message code="clear" />
									</button>
								</div>
								
								<div class="col">
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="filterTable()">
										<em class="fas fa-search"></em> <spring:message code="search" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		
			<div class="row">
				<div class="col">
					<div class="card">
						<div class="card-body">
							<form id="copyProjectForm">
								<span class="title"><spring:message code="projects.copy" /></span>
								
								<div class="form-group mt-2">
									<select id="projectCopyDropdown" class="form-control input" name="projectId" required>
										<option></option>
										<c:forEach items="${projectListDTO}" var="projectDTO">
											<option value="${projectDTO.id}">
												<spring:message code="${projectDTO.name}" />
											</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="row mt-2">
									<div class="col">
										<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="copyProject()">
											<em class="fas fa-copy"></em> <spring:message code="duplicate" />
										</button>
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
						<spring:message code="projects.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="projects.table.id" /></th>
									<th id="thName"><spring:message code="projects.table.name" /></th>
									<th id="thStartDate"><spring:message code="projects.table.start.date" /></th>
									<th id="thObjectiveDate"><spring:message code="projects.table.objective.date" /></th>
									<th id="thActions" class="all"><spring:message code="projects.table.actions" /></th>
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
<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createProjectForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="projects.create.btn" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="projectName" class="col-form-label"><spring:message code="projects.table.name" /></label>
								<input id="projectName" name="projectName" type="text" class="form-control" required>
							</div>
						</div>
						
						<div class="col-sm-12 col-md-6">
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
		
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="responsable" class="col-form-label"><spring:message code="projects.table.responsable" /></label>
								<select id="responsable" name="responsables" class="form-control" required>
									<option disabled selected="selected">
										<spring:message code="projects.table.responsable" />
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
								<label for="objectiveCost" class="col-form-label"><spring:message code="project.detail.project.objective.cost" /></label>
								<input id="objectiveCost" name="objectiveCost" type="number" step="any" class="form-control" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="startDate" class="col-form-label"><spring:message code="projects.table.start.date" /></label>
								<input type="date" class="form-control" id="startDate" name="startDate" required>
							</div>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="objectiveDate" class="col-form-label"><spring:message code="projects.table.objective.date" /></label>
								<input type="date" class="form-control" id="objectiveDate" name="objectiveDate" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="station" class="col-form-label"><spring:message code="projects.table.station" /></label>
								<select id="station" name="station" class="form-control">
									<option value="0" selected="selected">
										<spring:message code="projects.table.station.none" />
									</option>
									<option value="1">
										<spring:message code="projects.table.station.yes" />
									</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="forumIdDropdown" class="col-form-label"><spring:message code="projects.table.forum" /></label>
								<select id="forumIdDropdown" name="forumId" class="form-control" required>
									<option disabled selected="selected">
										<spring:message code="project.detail.forum.empty" />
									</option>
									<c:forEach items="${forumDTOs}" var="forumDTO">
										<option value="${forumDTO.id}">
											<spring:message code="[${forumDTO.id}] ${forumDTO.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
						</div>
						<div class="float-right">
							<button id="addMember" type="button" class="btn btn-sm btn-success"><spring:message code="create" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	$(document).ready(function() {

		/* Select 2 */
		$('#forumIdDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.detail.forum.empty')}",
			dropdownCssClass: 'selectStyle',
			containerCssClass: 'selectControlStyle'
		});

		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#responsibleDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('responsable.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		
		$('#projectCopyDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */

		$('.btnDetail').click(function(event) {

			var button = $(event.relatedTarget);
			var action = button.data('action');

			alert(action);
		});

		$('#addMember').click(function() {

			if (validateModal()) {
				$('#createProjectForm').addClass('was-validated');
			} else {
				showLoading();
				
				$('#createProjectForm').removeClass('was-validated');
				
				$.ajax({
					type: "POST",
					url: "/projects/create",
					data: $('#createProjectForm').serialize(),
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

				$('#createModal').modal('hide');	
			}

			return false;
		});
	});

	function validateModal() {
		var projectName = document.getElementById('projectName');
		var responsable = document.getElementById('responsable');
		var objectiveCost = document.getElementById('objectiveCost');
		var startDate = document.getElementById('startDate');
		var objectiveDate = document.getElementById('objectiveDate');
		var activityCenter = document.getElementById('activityCenter');

		return (!projectName.value.length || !responsable.value.length || !objectiveCost.value.length || !startDate.value.length || !objectiveDate.value.length || !activityCenter.value.length);
	}

	function copyProject() {

		if (!$('#projectCopyDropdown').val()) {
			return false;
		}

		showLoading();

		$.ajax({
			type: "POST",
			url: "/projects/copy",
			data: $('#copyProjectForm').serialize(),
			success: function(msg) {
				window.location.href = '/projects/' + msg;
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function excelProject(id) {

		const year = $('#yearDropdown').val();
		let queryParams = '';

		if (year) { queryParams = '?year=' + year; }

		window.open('/projects/' + id + '/excel' + queryParams, '_blank');
	}

	function deleteProject(id) {
		var ok = confirm("${jspUtil.parseTagToText('project.delete.alert')}");

		if (ok) {
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/projects/delete/" + id,
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

	function generateDataTable() {
		return $('#dTable').DataTable({
			searching: false,
			responsive: true,
			processing: true,
			serverSide: true,
			lengthMenu: [ 10, 25, 50, 75, 100 ],
			ajax: '/projects/dt',
			rowId: 'pr_id',
			language: {
				url: '/ui/static/lang/datatables/${locale}.json'
			},
			columns: [
				{ data: 'pr_id' },
				{ data: 'pr_name' },
				{ data: 'pr_startDate' },
				{ data: 'pr_objectiveDate' },
				{ data: null }
			],
			columnDefs: [
				{ className: 'text-center', targets: '_all' },
				{ orderable: false, targets: 2 },
				{
					render: function(data) {
						return moment(data).format('DD/MM/YYYY');
					},
					targets: 2
				},
				{
					render: function (data) {
						return moment(data).format('DD/MM/YYYY');
					},
					targets: 3
				},
				{ defaultContent: "${tableActionButtons}", orderable: false, targets: -1 }
			],
			dom: "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			drawCallback: function() {
				parseActionButtons();
			},
			initComplete: function() {
				const queryParams = new URLSearchParams(window.location.search);
				const pageNumber = queryParams.get('pageNumber');
				if (pageNumber) {
					dTable.page(pageNumber - 1).draw(false);
				}
			}
		});
	}
	
</script>

<script>

	document.addEventListener("DOMContentLoaded", function() {
		dTable = generateDataTable();
		handleDatatableFromParams();
		onChangePage();
	});

	function handleDatatableFromParams() {
		const queryParams = new URLSearchParams(window.location.search);

		let projectId = queryParams.get('projectId');
		let responsibleId = queryParams.get('responsibleId');
		let isStation = queryParams.get('station');
		let pageNumber = queryParams.get('pageNumber');

		const filterForm = document.querySelector('[data-table-filter="form"]');

		filterForm.querySelector('[data-table-filter="projectId"]').value = projectId;
		filterForm.querySelector('[data-table-filter="responsibleId"]').value = responsibleId;
		filterForm.querySelector('[data-table-filter="station"]').checked = isStation;

		filterTable(pageNumber);
	}

	function onChangePage() {
		dTable.on('page.dt', function() {
			const pageInfo = dTable.page.info();
			const currentURL = new URL(window.location.href);
			currentURL.searchParams.set('pageNumber', pageInfo.page + 1);

			window.history.pushState(null, '', currentURL.toString());
		});
	}

</script>
