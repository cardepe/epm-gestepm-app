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
						<h1 class="text-uppercase"><spring:message code="modified.signing.title" /></h1>
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
										<spring:message code="modified.signing.sub.title" />
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
							
							<span class="title"><spring:message code="users.search" /></span>
							
							<div class="form-group">
								<select id="usersDropdown" class="form-control input" name="userDTO">
									<option></option>
									<c:forEach items="${usersDTO}" var="userDTO">
										<option value="${userDTO.userId}" data-info="${userDTO}">
											<spring:message code="${userDTO.name} ${userDTO.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="row mt-2">
								<div class="col">
									<button type="button" class="btn btn-outline-secondary btn-sm w-100" style="font-size: 12px" onclick="resetTable()">
										<em class="fas fa-sync"></em> <spring:message code="clear" />
									</button>
								</div>
								
								<div class="col">
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="searchUser()">
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
						<spring:message code="modified.signing.title" />
					</div>

					<div class="table-responsive">
						<table id="msTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="modified.signing.table.id" /></th>
									<th id="thSigningId"><spring:message code="modified.signing.table.id" /></th>
									<th id="thSigningType"><spring:message code="modified.signing.table.type" /></th>
									<th id="thName"><spring:message code="modified.signing.table.name" /></th>
									<th id="thSurnames"><spring:message code="modified.signing.table.surnames" /></th>
									<th id="thStartDate"><spring:message code="modified.signing.table.start.date" /></th>
									<th id="thEndDate"><spring:message code="modified.signing.table.end.date" /></th>
									<th id="thActions" class="all"><spring:message code="modified.signing.table.actions" /></th>
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
	var msTable;
	
	$(document).ready(function() {

		/* Select 2 */
		$('#usersDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('user.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */
		
		/* Datatables */
		msTable = $('#msTable').DataTable({
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"lengthMenu": [ 10, 25, 50, 75, 100 ],
			"ajax": "/signing/modified-list/dt",
			"rowId": "ms_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "ms_id" },
				{ "data": "ms_signingId" },
				{ "data": "ms_signingType" },
				{ "data": "ms_name" },
				{ "data": "ms_surnames" },
				{ "data": "ms_startDate" },
				{ "data": "ms_endDate" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "targets": "_all" },
				{ "targets": [0], "visible": false },
				{ "render": function (data) { return parseShareType(data); }, "targets": 2 },
				{ "render": function (data) { return moment(data).format('DD/MM/YYYY hh:mm'); }, "targets": [5, 6] },
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
		msTable.ajax.url('/signing/modified-list/dt').load();
	}

	function searchUser() {
		var userId = $('#usersDropdown').val();
		msTable.ajax.url('/signing/modified-list/dt?userId=' + userId).load();
	}
	
	function deleteModifiedSigning(id) {

		var ok = confirm("${jspUtil.parseTagToText('users.delete.alert')}");

		if (ok) {
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/users/delete/" + id,
				success: function(msg) {
					$('#msTable').DataTable().ajax.reload();
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

	function parseShareType(data) {

		if (data === 'ds') {
			return '<div class="badge badge-secondary"> ${jspUtil.parseTagToText('ds')} </div>';
		} else if (data === 'ps') {
			return '<span class="badge badge-primary"> ${jspUtil.parseTagToText('ps')} </span>';
		} else if (data === 'us') {
			return '<span class="badge badge-success"> ${jspUtil.parseTagToText('us')} </span>';
		} else if (data === 'ums') {
			return '<span class="badge badge-info"> ${jspUtil.parseTagToText('ums')} </span>';
		}

		return '';
	}

	function approveModifiedSigning(id) {

		var ok = confirm("${jspUtil.parseTagToText('modified.signing.approve.alert')}");

		if (ok) {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/signing/modified-list/approve/" + id,
				success: function(msg) {
					$('#msTable').DataTable().ajax.reload();
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

	function declineModifiedSigning(id) {

		var ok = confirm("${jspUtil.parseTagToText('modified.signing.decline.alert')}");

		if (ok) {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/signing/modified-list/decline/" + id,
				success: function(msg) {
					$('#msTable').DataTable().ajax.reload();
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