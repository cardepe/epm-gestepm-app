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
						<h1 class="text-uppercase"><spring:message code="users.title" /></h1>
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
										<spring:message code="users.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-2">
								<div class="col">
									<div class="form-group">
										<button type="button" class="btn btn-success btn-sm w-100" data-toggle="modal" data-target="#createModal" ${user.role.id == jspUtil.getRolId('ROLE_JEFE_PROJECTO') ? 'disabled' : ''}>
											<spring:message code="users.create.btn" />
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
							<span class="title"><spring:message code="users.search" /></span>
							
							<div class="form-group">
								<select id="usersDropdown" data-table-filter="userId" class="form-control input" name="userDTO">
									<option></option>
									<c:forEach items="${usersDTO}" var="userDTO">
										<option value="${userDTO.userId}" data-info="${userDTO}">
											<spring:message code="${userDTO.name} ${userDTO.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group">
								<select id="stateFilterDropdown" data-table-filter="state" class="form-control input">
									<option></option>
									<option value="0">
										<spring:message code="user.detail.state.active" />
									</option>
									<option value="1">
										<spring:message code="user.detail.state.inactive" />
									</option>
								</select>
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
		</div>

		<div class="col-md-12 col-xl-9 h-100">
			<div class="card mb-0">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="users.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="id"><spring:message code="users.table.id" /></th>
									<th id="userName"><spring:message code="users.table.name" /></th>
									<th id="userSurnames"><spring:message code="users.table.surnames" /></th>
									<th id="roleName"><spring:message code="users.table.role" /></th>
									<th id="subRoleName"><spring:message code="users.table.subrole" /></th>
									<th id="actions" class="all"><spring:message code="users.table.actions" /></th>
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
						<spring:message code="users.create.btn" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="createUserForm" class="needs-validation">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="name" class="col-form-label"><spring:message code="users.table.name" /></label>
								<input id="name" name="name" type="text" class="form-control" required>
							</div>
						</div>
						<div class="col">
							<div class="form-group">
								<label for="surnames" class="col-form-label"><spring:message code="users.table.surnames" /></label>
								<input id="surnames" name="surnames" type="text" class="form-control" required>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="email" class="col-form-label"><spring:message code="users.table.email" /></label>
								<input id="email" name="email" type="email" class="form-control" required>
							</div>
						</div>
						<div class="col">
							<div class="form-group">
								<label for="password" class="col-form-label"><spring:message code="users.table.password" /></label>
								<input id="password" name="password" type="password" class="form-control" required>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="countryId" class="col-form-label"> 
									<spring:message code="user.detail.user.country" />
								</label>
								
								<select id="countryId" name="countryId" class="form-control" required>
									<c:forEach items="${countries}" var="country">
										<option value="${country.id}">${country.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="col-md-6">
							<div class="form-group">
								<label for="activityCenterId" class="col-form-label"> 
									<spring:message code="user.detail.user.activity.center" />
								</label>
								
								<select id="activityCenterId" name="activityCenterId" class="form-control" required>
									<c:forEach items="${activityCenters}" var="activityCenter">
										<option value="${activityCenter.id}">${activityCenter.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
									
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="roleId" class="col-form-label"><spring:message code="users.table.role" /></label>
								<select id="roleId" name="roleId" class="form-control" required>
<!-- 									<option disabled selected="selected"> -->
<%-- 										<spring:message code="users.table.role" /> --%>
<!-- 									</option> -->
									<c:forEach items="${roles}" var="role">
										<option value="${role.id}">
											<spring:message code="${role.roleName}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="subRoleId" class="col-form-label"><spring:message code="users.table.subrole" /></label>
								<select id="subRoleId" name="subRoleId" class="form-control" required>
<!-- 									<option disabled selected="selected"> -->
<%-- 										<spring:message code="users.table.subrole" /> --%>
<!-- 									</option> -->
									<c:forEach items="${subRoles}" var="subRole">
										<option value="${subRole.id}">
											<spring:message code="${subRole.rol}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="form-group">
								<label for="signingId" class="col-form-label"><spring:message code="users.table.signingid" /></label>
								<input id="signingId" name="signingId" type="number" class="form-control" required>
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
						<button id="create" type="button" class="btn btn-sm btn-success"><spring:message code="create" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	let dropdowns = [
		{ id: '#usersDropdown', placeholder: '${jspUtil.parseTagToText('user.selectable')}' },
		{ id: '#stateFilterDropdown', placeholder: '${jspUtil.parseTagToText('user.state.selectable')}' }
	];
	
	$(document).ready(function() {

		dropdowns.forEach(function(item) {
			$(item.id).select2({allowClear: true, placeholder: item.placeholder, dropdownCssClass: 'selectStyle'});
		});

		$('.btnDetail').click(function(event) {

			var button = $(event.relatedTarget);
			var action = button.data('action');

			alert(action);
		});

		$('#create').click(function() {

			if (validateForm()) {
				$('#createUserForm').addClass('was-validated');
			} else {
				showLoading();
				
				$('#createUserForm').removeClass('was-validated');
			
				$.ajax({
					type: "POST",
					url: "/users/create",
					data: $('#createUserForm').serialize(),
					success: function(msg) {
						dTable.ajax.reload();
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
		});
	});

	function validateForm() {
		var name = $('#name').val(); // document.getElementById('name');
		var surnames = $('#surnames').val(); // document.getElementById('surnames');
		var email = $('#email').val(); // document.getElementById('email');
		var password = $('#password').val(); // document.getElementById('password');
		var countryId = $('#countryId').val();
		var activityCenterId = $('#activityCenterId').val();
		var roleId = $('#roleId').val(); // document.getElementById('roleId');
		var subRoleId = $('#subRoleId').val(); // document.getElementById('subRoleId');

		return !name || !surnames || !email || !isValidEmailAddress(email) || !password || !countryId || !activityCenterId || !roleId || !subRoleId;
	}
	
	function deleteUser(id) {
		var ok = confirm("${jspUtil.parseTagToText('users.delete.alert')}");

		if (ok) {
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/users/delete/" + id,
				success: function(msg) {
					dTable.ajax.reload();
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

<script>

	document.addEventListener("DOMContentLoaded", function() {
		dTable = generateDataTable();
		handleDatatableFromParams();
		onChangePage();
	});

	function generateDataTable() {
		return $('#dTable').DataTable({
			searching: false,
			responsive: true,
			processing: true,
			serverSide: true,
			lengthMenu: [ 10, 25, 50, 75, 100 ],
			ajax: '/users/dt',
			rowId: 'us_id',
			language: {
				url: '/ui/static/lang/datatables/${locale}.json'
			},
			columns: [
				{ data: 'us_id' },
				{ data: 'us_name' },
				{ data: 'us_surnames' },
				{ data: 'ro_roleName' },
				{ data: 'sr_subRoleName' },
				{ data: null }
			],
			columnDefs: [
				{ className: 'text-center', targets: '_all' },
				{ defaultContent: '${tableActionButtons}', orderable: false, targets: -1 }
			],
			dom: "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			drawCallback: function() {
				parseActionButtons(${user.role.id});
			}
		});
	}

	function handleDatatableFromParams() {
		const queryParams = new URLSearchParams(window.location.search);

		let userId = queryParams.get('userId');
		let state = queryParams.get('state');
		let pageNumber = queryParams.get('pageNumber');

		const filterForm = document.querySelector('[data-table-filter="form"]');

		filterForm.querySelector('[data-table-filter="userId"]').value = userId;
		filterForm.querySelector('[data-table-filter="state"]').value = state;

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
