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
						<h1 class="text-uppercase"><spring:message code="projects.view.title" /></h1>
					</div>
				</div>
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
										<spring:message code="projects.view.sub.title" />
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
						<div class="card-body">
							<span class="title"><spring:message code="filters" /></span>
							
							<div class="form-group mt-2">
								<select id="projectDropdown" class="form-control input">
									<option></option>
									<c:forEach items="${projectListDTO}" var="projectDTO">
										<option value="${projectDTO.id}">
											<spring:message code="${projectDTO.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group mt-2 mb-2">
								<select id="responsableDropdown" class="form-control input">
									<option></option>
									<c:forEach items="${responsables}" var="responsable">
										<option value="${responsable.userId}">
											<spring:message code="${responsable.name} ${responsable.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="custom-control custom-checkbox">
							    <input type="checkbox" class="custom-control-input" id="checkboxIsStation">
							    <label class="custom-control-label mr-3" for="checkboxIsStation" style="font-size: 12px; padding-top: 3px"><spring:message code="projects.filter.station.checkbox" /></label>
							</div>
							
							<div class="row mt-2">
								<div class="col">
									<button type="button" class="btn btn-outline-secondary btn-sm w-100" style="font-size: 12px" onclick="resetTable()">
										<em class="fas fa-sync"></em> <spring:message code="clear" />
									</button>
								</div>
								
								<div class="col">
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="filterProjects()">
										<em class="fas fa-search"></em> <spring:message code="search" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-xl-9 h-100">
			<div class="card mb-0">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="projects.view.title" />
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

<!-- VIEW MODAL -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="infoModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<div class="col">
						<h5 class="modal-title" id="infoModalLabel"><spring:message code="projects.view.modal.title" /></h5>
					</div>
					<div class="col">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
				
			</div>
			<div class="modal-body" style="font-size: 14px">

			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" class="btn btn-sm" data-dismiss="modal">
							<spring:message code="close" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /INFO MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	var dtable;

	$(document).ready(function() {

		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#responsableDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('responsable.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */
		
		/* Datatables */
		dTable = $('#dTable').DataTable({
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"lengthMenu": [ 10, 25, 50, 75, 100 ],
			"ajax": "/projects/view/dt",
			"rowId": "pr_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "pr_id" },
				{ "data": "pr_name" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "targets": "_all" },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */

	});

	function resetTable() {
		dTable.ajax.url('/projects/view/dt').load();
	}

	function filterProjects() {
		var projectId = $('#projectDropdown').val();
		var responsableId = $('#responsableDropdown').val();
		var station = $('#checkboxIsStation').prop('checked') ? 1 : null;
		
		dTable.ajax.url('/projects/view/dt?projectId=' + projectId + '&responsableId=' + responsableId + (station == 1 ? '&station=' + station : '')).load();
	}

	function showProjectResponsables(projectId) {

		$.ajax({
			type: "GET",
			url: "/projects/" + projectId + '/responsables/html',
			success: function(msg) {
				$('.modal-body').html(msg);
			},
			error: function(e) {
				$('.modal-body').html('Error');
			}
		});		
		
		$('#viewModal').modal();
	}
	
</script>