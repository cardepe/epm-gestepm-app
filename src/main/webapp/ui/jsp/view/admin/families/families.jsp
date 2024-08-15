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
							<spring:message code="families.admin.title" />
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
										<spring:message code="families.admin.title" />
									</div>
								</div>
							</div>

							<div class="row mt-2">
								<div class="col">
									<div class="form-group">
										<a href="/admin/families/create" class="btn btn-success btn-sm w-100">
											<spring:message code="create" />
										</a>
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
						<spring:message code="families.admin.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="families.admin.table.id" /></th>
									<th id="thNameES"><spring:message code="families.admin.table.name.es" /></th>
									<th id="thNameFR"><spring:message code="families.admin.table.name.fr" /></th>
									<th id="thActions" class="all"><spring:message code="families.admin.table.actions" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

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
			"ajax" : "/admin/families/dt",
			"rowId" : "fa_id",
			"language" : {
				"url" : "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "asc"]],
			"columns" : [ 
				{ "data" : "fa_id" }, 
				{ "data" : "fa_nameES" },
				{ "data" : "fa_nameFR" },
				{ "data" : null } 
			],
			"columnDefs" : [
			{ "targets": [0], "visible": false },
			{
				"className" : "text-center",
				"targets" : "_all"
			}, 
			{
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
		
	});

	function deleteFamily(id) {
		var ok = confirm("${jspUtil.parseTagToText('families.admin.delete.alert')}");

		if (ok) {
			
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/admin/families/delete/" + id,
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