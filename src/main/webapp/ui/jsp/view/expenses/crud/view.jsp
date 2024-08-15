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
							<spring:message code="expenses.personal.view.title" />
						</h1>
					</div>
				</div>
			</div>
			
			<div class="col">
				<div class="float-right calendarBtns">
					<a href="${backUrl}" class="btn btn-standard btn-sm">
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
									<h4 class="font-weight-bold mb-2">${expenseSheet.name}</h4>
									<h6>${ expenseSheet.project.name }</h6>
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
									<div class="img-profile-circle float-left">
										<img class="user-avatar rounded-circle"
											src="/ui/static/images/profile.png" alt="me" height="40" />
									</div>

									<div style="font-size: 12px">${ userLoaded.name } ${ userLoaded.surnames }</div>
									<div style="font-size: 11px">
										<spring:message code="signing.personal.date" />
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
						<spring:message code="expenses.personal.view.table.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable"
							class="table table-striped table-borderer dataTable">
							<caption class="d-none">NO SONAR</caption>
							<thead>
								<tr>
									<th id="expense"><spring:message
											code="expenses.personal.view.table.id" /></th>
									<th id="description"><spring:message
											code="expenses.personal.view.table.description" /></th>
									<th id="justification"><spring:message
											code="expenses.personal.view.table.justification" /></th>
									<th id="date"><spring:message
											code="expenses.personal.view.table.date" /></th>
									<th id="amount"><spring:message
											code="expenses.personal.view.table.amount" /></th>
									<th id="actions" class="all"><spring:message
											code="expenses.personal.view.table.actions" /></th>
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
	var $ = jQuery.noConflict();

	$(document).ready(function() {

		/* Datatables */
		var table = $('#dTable').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"pageLength": 7,
			"ajax": "/expenses/personal/${expenseSheet.id}/dt",
			"rowId": "ex_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "ex_id" },
				{ "data": "pi_description" },
				{ "data": "ex_justification" },
				{ "data": "ex_reportDate" },
				{ "data": "ex_total" },
				{ "data": null }
			],
			"columnDefs": [
				{ 
					"className": "text-center", 
					"targets": [ 0, 1, 2, 3, 4, 5 ]
				},
			    {  
					"className": "text-right",
				    "render": function ( data, type, row ) {
                    	return data + ' €';
                	},
                	"targets": 4 
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
	});

	function parseActionButtons() {

		var tableRows = $('#dTable tbody tr');
		var table = $('#dTable').DataTable();
		
		tableRows.each(function(i) {

			var lastColumn = $(this).children().last();
			var emList = lastColumn.children();
			var expenseId = $(this).attr('id');
			
			var rowId = 'row' + i;
			$(this).attr('id', rowId);
			
			emList.each(async function(index) {
				
				if (index == 0) { // pdf
					var expenseFile = await getFile(expenseId);
					if (!expenseFile) {
						return $(this).hide();
					}
		            var filename = expenseFile.name;      
		            var url = 'data:image/' + expenseFile.ext + ';base64,' + expenseFile.content;

		            $(this).attr({ 'download': filename, 'href': url});  
		            $(this).attr('onclick', 'downloadFile("' + url + '")');
				}
			});
		});
	}

	function getFile(expenseId) {
		return $.ajax({
		    url: '/expenses/file/' + expenseId,
		    type: 'GET',
		 });
	}

	function downloadFile(url) {
		window.open = url;
	}

</script>