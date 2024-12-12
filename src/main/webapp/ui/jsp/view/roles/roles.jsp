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
						<h1 class="text-uppercase"><spring:message code="roles.title" /></h1>
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
										<spring:message code="roles.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-2">
								<div class="col">
									<div class="form-group">
										<button type="button" class="btn btn-success btn-sm w-100" data-toggle="modal" data-target="#createModal">
											<spring:message code="roles.create.btn" />
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
						<spring:message code="roles.title" />
					</div>

					<div class="table-responsive">
						<table id="rTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="id"><spring:message code="roles.table.id" /></th>
									<th id="rol"><spring:message code="roles.table.name" /></th>
									<th id="price"><spring:message code="roles.table.price" /></th>
									<th id="actions" class="all"><spring:message code="roles.table.actions" /></th>
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
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="roles.create.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="createRoleForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="name" class="col-form-label"><spring:message code="roles.table.name" /></label>
								<input id="name" name="name" type="text" class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="price" class="col-form-label"><spring:message code="roles.table.price" /></label>
								<input id="price" name="price" type="number" class="form-control">
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
					</div>
					<div class="float-right">
						<button id="create" type="submit" class="btn btn-sm btn-success"><spring:message code="create" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<div id="editModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="roles.edit.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editRoleForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="name" class="col-form-label"><spring:message code="roles.table.name" /></label>
								<input id="name" name="name" type="text" class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="price" class="col-form-label"><spring:message code="roles.table.price" /></label>
								<input id="price" name="price" type="number" class="form-control">
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-right">
						<button id="save" type="submit" class="btn btn-sm btn-success"><spring:message code="save" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	var $=jQuery.noConflict();
	
	$(document).ready(function() {

		/* Datatables */
		$('#rTable').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"ajax": "/roles/dt",
			"rowId": "sr_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "asc"]],
			"columns": [
				{ "data": "sr_id" },
				{ "data": "sr_rol" },
				{ "data": "sr_price" },
				{ "data": null }
			],
			"columnDefs": [
				{ "targets": [0], "visible": false },
				{ "className": "text-center", "targets": "_all" },
				{  
				    "render": function ( data, type, row ) {
                    	return data + '€';
                	},
                	"targets": 2
                },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */

		$('.btnDetail').click(function(event) {

			var button = $(event.relatedTarget);
			var action = button.data('action');

			alert(action);
		});

		$('#create').click(function() {

			showLoading();
			
			$.ajax({
				type: "POST",
				url: "/roles/create",
				data: $('#createRoleForm').serialize(),
				success: function(msg) {
					$('#rTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createModal').modal('hide');
		});

	});

	function deleteRole(id) {
		var ok = confirm("${jspUtil.parseTagToText('role.delete.alert')}");

		if (ok) {

			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/roles/delete/" + id,
				success: function(msg) {
					$('#rTable').DataTable().ajax.reload();
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

	function editRole(id, name, price) {
		$('#editModal').find('#name').val(name);
		$('#editModal').find('#price').val(price);
		$('#editModal').modal('show');	

		$('#save').unbind('click').click(function() {

			showLoading();
			
			$.ajax({
				type: "POST",
				url: "/roles/update/" + id,
				data: $('#editRoleForm').serialize(),
				success: function(msg) {
					$('#rTable').DataTable().ajax.reload();
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