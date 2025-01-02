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
							<spring:message code="expenses.personal.title" />
						</h1>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<form id="modalForm" action="/expenses/personal/create">
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
											<spring:message code="expenses.personal.sub.title" />
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
											<button id="openModal" type="button" class="btn btn-success btn-sm w-100"
												data-toggle="modal"
												data-action="create">
												<spring:message code="expenses.personal.create" />
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="widget">
							<div class="header">
								<div class="h3">
									<spring:message code="expenses.personal.pendingamount" />
								</div>
							</div>
							<div class="body">
								<div class="widget-display">${totalPendingAmount}</div>
								<div class="h3 text-secondary text-uppercase font-weight-500">
									<spring:message code="eur" />
								</div>
							</div>
							<div class="footer">
								<span class="h4 text-secondary text-truncate"><spring:message
										code="expenses.personal.totalpendingpaid" /></span> <span
									class="h4 ml-8"> ${totalApprovedExpenseSheets} </span>
							</div>
						</div>

						<div class="widget">
							<div class="header">
								<div class="h3">
									<spring:message code="expenses.personal.reports" />
								</div>
							</div>
							<div class="body">
								<div class="widget-display text-secondary">
									${totalPendingExpensesSheet}</div>
								<div class="h3 text-secondary text-uppercase font-weight-500">
									<spring:message code="expenses.personal.opened" />
								</div>
							</div>
							<div class="footer">
								<span class="h4 text-secondary text-truncate"><spring:message
										code="expenses.personal.lastpendingexpense" /></span> <span
									class="h4 ml-8"> <fmt:formatDate
										value="${lastPendingExpenseSheetDate}" pattern="dd/MM/yyyy" /></span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-12 col-xl-9 h-100">
				<div class="card">
					<div class="card-body">
						<div class="title mb-0">
							<spring:message code="expenses.personal.table.title" />
						</div>

						<div class="table-responsive">
							<table id="dTable"
								class="table table-striped table-borderer dataTable">
								<caption class="d-none">NO SONAR</caption>
								<thead>
									<tr>
										<th id="id"><spring:message
												code="expenses.personal.table.id" /></th>
										<th id="description"><spring:message
												code="expenses.personal.table.description" /></th>
										<th id="project"><spring:message
												code="expenses.personal.table.project" /></th>
										<th id="status"><spring:message
												code="expenses.personal.table.status" /></th>
										<th id="date"><spring:message
												code="expenses.personal.table.date" /></th>
										<th id="amount"><spring:message
												code="expenses.personal.table.amount" /></th>
										<th id="actions" class="all"><spring:message
												code="expenses.personal.table.actions" /></th>
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
	<div id="modal" class="modal fade expenses-modal" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="expenses.personal.modal.title" />
						</h5>
					</div>
				</div>
				<div class="modal-body">

					<!-- Sheet name -->
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label for="sheetName" class="col-form-label"> <spring:message
										code="expenses.personal.sheetname" />
								</label>
								<spring:message
									code="expenses.personal.sheetname.placeholder"
									var="placeholderSheetName" />
								<input type="text" class="form-control"
									placeholder="${placeholderSheetName}" id="sheetName"
									name="sheetName" required>
							</div>
						</div>
					</div>
					<!-- / Sheet name -->
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" id="back" data-dismiss="modal" class="btn btn-sm">
								<spring:message code="back" />
							</button>
						</div>
						<div class="float-right">
							<button id="save" type="submit" class="btn btn-sm btn-success">
								<spring:message code="create" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /MODAL -->
</form>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<script>
	var file;

	$(document).ready(function() {

		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */

		/* Datatables */
		var table = $('#dTable').DataTable({
			"lengthChange": false,
			"responsive": true,
			"processing": true,
			"pageLength": 8,
			"serverSide": true,
			"ajax": "/expenses/personal/dt",
			"rowId": "es_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[0, "desc"]],
			"columns": [
				{ "data": "es_id" },
				{ "data": "es_name" },
				{ "data": "pr_projectName" },
				{ "data": "es_status" },
				{ "data": "es_creationDate" },
				{ "data": "es_total" },
				{ "data": null }
			],
			"columnDefs": [
				{ 
					"className": "text-center", 
					"targets": [ 0, 1, 2, 3, 4, 6 ]
				},
				{  
					"className": "text-right",
				    "render": function ( data, type, row ) {
                        return parseStatus(data);
                	},
                	"targets": 3
                },
                {  
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY');
                	},
                	"targets": 4 
                },
			    {  
					"className": "text-right",
				    "render": function ( data, type, row ) {
                    	return data + ' €';
                	},
                	"targets": 5 
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

		$('#searchInput').keyup(function(){
			table.search($(this).val()).draw();
		})
		/* End Datatables */
		
		$('#openModal').click(function () {
			if (!$('#projectDropdown').val()) {
				return alert("${jspUtil.parseTagToText('expense.create.alert')}");
			}

			$('.expenses-modal').modal('show');
		});
	});

	function deleteExpense(id) {
		var ok = confirm("${jspUtil.parseTagToText('expense.delete.alert')}");

		if (ok) {
			$.ajax({
				type: "DELETE",
				url: "/expenses/delete/" + id,
				success: function(msg) {
					$('#dTable').DataTable().ajax.reload();
					showNotify(msg, 'success');
				},
				error: function(e) {
					showNotify(e.responseText, 'danger');
				}
			});
		}
	}
	
</script>