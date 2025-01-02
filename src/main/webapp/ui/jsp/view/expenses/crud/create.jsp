<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.form-control[readonly] {
	background-color: #fff;
}

#update {
	display: none;
}
</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="expenses.personal.create.title" />
						</h1>
					</div>
				</div>
			</div>
			
			<div class="col">
				<div class="float-right calendarBtns">
					<a href="/expenses/personal" class="btn btn-standard btn-sm">
						<span class="fc-icon fc-icon-chevron-left"></span> <spring:message code="back" />
					</a>
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
										<spring:message code="signing.personal.date" />
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
							<div class="row">
								<div class="col">
									<h4 class="font-weight-bold mb-2">${sheetName}</h4>
									<h6>${ project.name }</h6>
								</div>
							</div>
							
							<div class="row mt-4">
								<div class="col">
									<div class="form-group">
										<button id="submit" type="button" class="btn btn-success btn-sm w-100">
											<spring:message code="submit" />
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
						<spring:message code="expenses.personal.create.table.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="expense"><spring:message
											code="expenses.personal.create.table.expense" /></th>
									<th id="description"><spring:message
											code="expenses.personal.create.table.description" /></th>
									<th id="date"><spring:message
											code="expenses.personal.create.table.date" /></th>
									<th id="amount"><spring:message
											code="expenses.personal.create.table.amount" /></th>
									<th id="actions" class="all"><spring:message
											code="expenses.personal.create.table.actions" /></th>
								</tr>
							</thead>
						</table>
						
						<div class="float-right">
							<button id="openExpenseModalBtn" class="btn btn-sm btn-info" data-toggle="modal" data-target=".expenses-modal" data-action="create" onclick="checkMaxExpenseRows(event)">
								<spring:message code="expenses.personal.create.table.add" />
							</button>
						</div>
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
						<spring:message code="expenses.personal.create.modal.title" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="modalForm" class="needs-validation">
					<!-- Select expense type -->
					<div class="row" id="expenseType">
						<div class="col-md-12">
							<div class="form-group">
								<select id="priceType" name="priceType" class="form-control">
									<option disabled selected="selected">
										<spring:message code="expenses.personal.create.expense.placeholder" />
									</option>
									<c:forEach items="${priceTypes}" var="priceType">
										<option value="${priceType.id}" data-info="${priceType}">
											<spring:message code="${priceType.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<!-- / Select expense type -->
					
					<!-- Expenses depending type -->
					<div id="expenses">
						<div id="expensesContent">
						</div>
					</div>
					<!-- / Expenses depending type -->
					
					<input id="priceTypeName" name="priceTypeName" class="form-control input" type="hidden" readonly />
								
					<input id="creationDate" name="creationDate" class="form-control input input-date" type="hidden" value="${ dateTime }" readonly />
					
					<input id="rowId" name="rowId" class="form-control input" type="hidden" readonly />
				</form>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" id="back"
							class="btn btn-sm">
							<spring:message code="back" />
						</button>
					</div>
					<div class="float-right">
						<button id="add" type="button" class="btn btn-sm btn-success">
							<spring:message code="save" />
						</button>
						
						<button id="update" type="button" class="btn btn-sm btn-success">
							<spring:message code="save" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- EXPENSES DEPENDING TYPE -->
<div id="expensesDependingType">
	<!-- Expenses form for Lunch, Hotel, Place -->
	<div class="expenses-1 expenses-2 expenses-3 expenses-5 expenses-6">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="startDate" class="col-form-label"> <spring:message
							code="expenses.personal.create.date" />
					</label>
					<spring:message code="expenses.personal.create.date.placeholder"
						var="placeholderDate" />
					<input type="date" class="form-control"
						placeholder="${placeholderDate}" id="startDate"
						name="startDate" required>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="justification" class="col-form-label"> <spring:message
							code="expenses.personal.create.justification" />
					</label>
					<spring:message
						code="expenses.personal.create.justification.placeholder"
						var="placeholderJustification" />
					<input type="text" class="form-control"
						placeholder="${placeholderJustification}" id="justification"
						name="justification">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="total" class="col-form-label"> <spring:message
							code="expenses.personal.create.amount" />
					</label>
					<spring:message code="expenses.personal.create.amount.placeholder"
						var="placeholderAmount" />
					<input type="number" class="form-control"
						placeholder="${placeholderAmount}" id="total" min="0" name="total" required>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="type" class="col-form-label"> <spring:message
							code="expenses.personal.create.type" />
					</label> <select id="paymentType" name="paymentType"
						class="form-control" required>
						<option disabled selected>
							<spring:message code="expenses.personal.create.type.placeholder" />
						</option>
						<c:forEach items="${paymentTypes}" var="paymentType">
							<option value="${paymentType.id}" data-info="${paymentType}">
								<spring:message code="${paymentType.name}" />
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col">
				<div id="fileForm" class="form-group">
					<label for="image" class="col-form-label"> <spring:message
							code="expenses.personal.create.image" />
					</label> <input type="file" class="form-control-file" id="file"
						name="file" accept=".jpg, .jpeg, .png, .pdf" onchange="setFile(event)"
						lang="${locale}">
				</div>
			</div>
		</div>
	</div>
	<!-- /Expenses form for Lunch, Hotel, Place -->

	<!-- Expenses form for Miles/KM -->
	<div class="expenses-4">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label for="startDate" class="col-form-label"> <spring:message
							code="expenses.personal.create.date" />
					</label>
					<spring:message code="expenses.personal.create.date.placeholder"
						var="placeholderDate" />
					<input type="date" class="form-control"
						placeholder="${placeholderDate}" id="startDate"
						name="startDate" required>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label for="total" class="col-form-label"> <spring:message
							code="expenses.personal.create.distance" />
					</label>
					<spring:message code="expenses.personal.create.distance.placeholder"
						var="placeholderDistance" />
					<input type="number" class="form-control"
						placeholder="${placeholderDistance}" id="total" min="0" name="total" required>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="measurement" class="col-form-label"> <spring:message
							code="expenses.personal.create.measurement" />
					</label> <span id="measurement"><spring:message
							code="expenses.personal.create.measurement.km" /></span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label for="rate" class="col-form-label"> <spring:message
							code="expenses.personal.create.rate" />
					</label> <span id="rate"></span><span> EUR</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label for="justification" class="col-form-label"> <spring:message
							code="expenses.personal.create.justification" />
					</label>
					<spring:message
						code="expenses.personal.create.justification.placeholder"
						var="placeholderJustification" />
					<input type="text" class="form-control"
						placeholder="${placeholderJustification}" id="justification"
						name="justification">
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="type" class="col-form-label"> <spring:message
							code="expenses.personal.create.type" />
					</label> <select id="paymentType" name="paymentType"
						class="form-control" required>
						<option disabled selected>
							<spring:message code="expenses.personal.create.type.placeholder" />
						</option>
						<c:forEach items="${paymentTypes}" var="paymentType">
							<option value="${paymentType.id}" data-info="${paymentType}">
								<spring:message code="${paymentType.name}" />
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<!-- /Expenses form for Miles/KM -->
	
</div>
<!-- /EXPENSES DEPENDING TYPE -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>
	var expenseType = $('#expenseType');
	var modalTitle = $("#modalTitle");
	var modalFooter = $(".modal-footer");
	var expensesDependingType = $("#expensesDependingType");
	var submit = $('#submit');
	var file;

	submit.prop('disabled', true);
	expensesDependingType.remove();

	var priceTypesJson = ${priceTypesJson};

	$(document).ready(function() {

		/* Datatables */
		var table = $('#dTable').DataTable({
			"lengthChange": false,
			"responsive": true,
			"processing": true,
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "priceTypeName" },
				{ "data": "justification" },
				{ "data": "creationDate" },
				{ "data": "total" },
				{ "data": null }
			],
			"columnDefs": [
				{ 
					"className": "text-center", 
					"targets": [ 0, 1, 2, 3, 4 ]
				},
			    {  
					"className": "text-right",
				    "render": function ( data, type, row ) {
                    	return ($('#rate').text() || 1) * data + ' €';
                	},
                	"targets": 3 
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
		
		initModalStyle();

		$('#dTable').on('draw.dt', function () {
			parseActionButtons();
		});
		
		$('#file').change(function (event) {
			file = event.target && event.target.files && event.target.files[0];
		});
		
		$('.expenses-modal').on('show.bs.modal', function (event) {
			// Get button that trigger modal
			var button = $(event.relatedTarget);
			var action = button.data('action');
			var rowId = button.data('id');
			setButtonAction(action);

			// Update
			if (action == 'edit') {
				setFormData(rowId);
			}
		});

		$('.expenses-modal').on('hidden.bs.modal', function (event) {
			// Reset modal form
			file = undefined;
			$('#modalForm').trigger("reset");
			$('#priceType').prop('selectedIndex', 0);
			initModalStyle();
		});

		$('#priceType').change(function () {
			var priceTypeId = $(this).val();
			setModalStyleFromPriceType(priceTypeId);
			$('#priceType option')
				.removeAttr('selected')
				.filter('[value="' + priceTypeId + '"]')
				.attr('selected', true);
		});

		$('#back').click(function () {
			initModalStyle();
			$('#modalForm').trigger("reset");
		});

		$('#add').click(function () {

			if (validateModal()) {
				$('#modalForm').addClass('was-validated');
			} else {
				$('#modalForm').removeClass('was-validated');
				addExpense();
			}
		});

		$('#update').click(function () {
			updateExpense();
		});

		$('#submit').click(function () {
			
			showLoading();
			var expenseSheet = getExpenseSheet(table.rows().data());
			
			$.ajax({
				url: '/expenses/create', 
				type: 'POST',
				data: JSON.stringify(expenseSheet),
				contentType: 'application/json',
				dataType: 'json',
				success: function (json) {
					var files = getFiles(json, expenseSheet.expenses);
					var promises = [];
					
					files.forEach(function (expense) {
						var data = new FormData();
						data.append('file', expense.file);
						
						promises.push($.ajax({
							type: "POST",
							url: "/expenses/" + expense.id + "/upload",
							enctype: 'multipart/form-data',
					        processData: false,
					        contentType: false,
					        cache: false,
							data: data
						}));
					
					});
	
					Promise.all(promises).then(function () { 
						hideLoading();
						window.location.replace("/expenses/personal");
					});
				}, 
				error: function (e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});
		});
	});

	function checkMaxExpenseRows(event) {

		let table = $('#dTable').DataTable();
		let expensesCount = table.rows().count();

		if (expensesCount === 40) {
			event.stopPropagation();
			showNotify('El máximo de gastos por hoja de gastos es de 40 filas.', 'danger');
		}
	}

	function validateModal() {
		var startDate = document.getElementById('startDate');
		var total = document.getElementById('total');
		var paymentType = document.getElementById('paymentType');

		return (!startDate.value.length || !total.value.length || !paymentType.value.length);
	}

	function getExpenseSheet(data) {
		var expenseSheet = {
			name: '${sheetName}',
			project: '${project.id}',
			expenses: [] 	
		}

		for (var i = 0; i < data.length; i++) {
			expenseSheet.expenses.push(data[i]);
		}

		return expenseSheet;
	}

	function getFiles(sheet, expenses) {
		return sheet.expenses
			.map(function (expense, i) {
			  return expenses[i].expenseFile && {id: expense.id, file: expenses[i].expenseFile};
			})
			.filter(function (expense, i) { return expense && expense.file});
	}

	function getFormData(form) {
	    var unindexed_array = form.serializeArray();
	    var indexed_array = {};

	    $.map(unindexed_array, function(n, i){
	        indexed_array[n['name']] = n['value'];
	    });

	    indexed_array['expenseFile'] = file;

	    return indexed_array;
	}

	function setFormData(i) {
		var table = $('#dTable').DataTable();
		var row = $('#row' + i);
		var data = table.row(row).data();
		setModalStyleFromPriceType(data.priceType);
		Object.keys(data).forEach(function (key) {
			var value = data[key];
			$('#' + key).val(value);
		});

		// Set row Id
		$('#rowId').val(i);
	}

	function setButtonAction(action) {
		if (action === 'edit') {
			$('#add').hide();
			$('#update').show();
		} else {
			$('#update').hide();
			$('#add').show();
		}
	}

	function initModalStyle() {
		modalTitle.html('<spring:message code="expenses.personal.modal.title" />')
		expenseType.show();
		// Reset expenses from price type
		$('#expenses').hide();
		$('#expensesContent >').appendTo(expensesDependingType);
		$('#rate').empty();
		modalFooter.hide();
	}

	function setModalStyleFromPriceType(priceTypeId) {
		var priceType = priceTypesJson.find(priceType => priceType.id === +priceTypeId);
		expenseType.hide();
		$('#priceTypeName').val($('#priceType option:selected').text().trim());
		modalTitle.html($('#priceTypeName').val());
		var expenseFromType = '.expenses-' + priceType.id;
		// Show expenses from price type
		expensesDependingType.find(expenseFromType).appendTo($('#expensesContent'));
		$('#expenses').show();
		$('#rate').text(priceType.amount);
		modalFooter.show();
	}

	function parseActionButtons() {

		var tableRows = $('#dTable tbody tr');
		var table = $('#dTable').DataTable();

		var info = table.page.info();
		var pageNumber = info.page;
		var pageLength = info.length;
		
		tableRows.each(function(i) {

			var lastColumn = $(this).children().last();
			var emList = lastColumn.children();
			
			var rowId = ((pageNumber * pageLength) + i);
			var row = 'row' + rowId;
			$(this).attr('id', row);
			
			emList.each(function(index) {
				
				if (index == 0) { // pdf
					var data = table.row(tableRows[rowId]).data();
					var expenseFile = data.expenseFile;
					if (!expenseFile) {
						return $(this).hide();
					}
		            var filename = expenseFile.name;      
		            var blob = new Blob([expenseFile]);
		            var url  = URL.createObjectURL(blob);

		            $(this).attr({ 'download': filename, 'href': url});  
		            $(this).attr('onclick', 'downloadFile("' + url + '")');
				} else if (index == 1) { // update
					$(this).attr('data-toggle', 'modal');
					$(this).attr('data-target', '.expenses-modal');
					$(this).attr('data-action', 'edit');
					$(this).attr('data-id', rowId);
				} else if (index == 2) { // delete
					$(this).attr('onclick', 'deleteExpense(' + rowId + ')');
				}
			});
		});
	}

	function addExpense() {
		var table = $('#dTable').DataTable();
		var expense = getFormData($('#modalForm'));
		table.row.add(expense).draw();
		$('.expenses-modal').modal('hide');
		submit.prop('disabled', false);
	}

	function downloadFile(url) {
		window.open = url;
	}

	function updateExpense(i) {
		var table = $('#dTable').DataTable();
		var expense = getFormData($('#modalForm'));
		var rowId = $('#rowId').val();
		table.row('#row' + rowId).data(expense).draw();
		$('.expenses-modal').modal('hide');
	}

	function deleteExpense(i) {
		var table = $('#dTable').DataTable();
		var row = $('#row' + i);
		table.row(row).remove().draw();
		submit.prop('disabled', !table.data().count());
	}

	function setFile(event) {
		file = event.target && event.target.files && event.target.files[0];
	}

</script>