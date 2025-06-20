<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="countries.title" />
						</h1>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="page-header float-right">
					<!--
						<button type="button" class="btn btn-outline-secondary btn-sm" data-toggle="modal" data-target="#filterModal">
							<spring:message code="filters" />
						</button>
					-->
					<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#createModal">
						<spring:message code="create" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<div class="content">
	<div class="row h-100">
		<div class="col h-100">
			<div class="card">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="countries.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<thead>
								<tr>
									<th><spring:message code="countries.table.id" /></th>
									<th><spring:message code="countries.table.name" /></th>
									<th><spring:message code="countries.table.tag" /></th>
									<th><spring:message code="countries.table.actions" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5><spring:message code="countries.create.btn" /></h5>
				</div>
			</div>
			
			<div class="modal-body">
				<form id="createForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="createName" class="col-form-label"><spring:message code="countries.table.name" /></label>
								<input id="createName" name="name" type="text" class="form-control" required />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="createTag" class="col-form-label"><spring:message code="countries.table.tag" /></label>
								<input id="createTag" name="tag" type="text" class="form-control" required maxlength="2">
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
						<button id="createBtn" type="button" class="btn btn-default btn-sm" onclick="create()">
							<spring:message code="create" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5><spring:message code="countries.update.btn" /></h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="editName" class="col-form-label"><spring:message code="countries.table.name" /></label>
								<input id="editName" name="name" type="text" class="form-control" required>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="editTag" class="col-form-label"><spring:message code="countries.table.tag" /></label>
								<input id="editTag" name="tag" type="text" class="form-control" required maxlength="2">
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
						<button id="saveBtn" type="button" class="btn btn-default btn-sm">
							<spring:message code="save" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>

	let locale = '${locale}';

	$(document).ready(function() {

		let columns = ['id', 'name', 'tag', 'id']
		let endpoint = '/v1/countries';
		let actions = [ { action: 'edit', permission: 'edit_countries' }, { action: 'delete', permission: 'edit_countries' }]

		customDataTable = new CustomDataTable(columns, endpoint, null, actions);
		dTable = createDataTable('#dTable', customDataTable, locale);
		customDataTable.setCurrentTable(dTable);
	});

</script>