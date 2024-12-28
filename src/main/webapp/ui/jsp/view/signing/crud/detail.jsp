<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>

.border-red {
	border: 1px solid #dc3545;
	border-radius: .25rem;
}

a[disabled] {
	opacity: .65;
	cursor: not-allowed;
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col-10">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="signing.detail.title" arguments="${ userSigning.id }" /></h1>
					</div>
				</div>
			</div>
			
			<div class="col-2">
				<div class="float-right calendarBtns">
					<a href="/signing" class="btn btn-standard btn-sm">
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
										<img class="user-avatar rounded-circle" src="/ui/static/images/profile.png" alt="me" height="40" />
									</div>
									
									<div style="font-size: 12px">${ userSigning.user.name } ${ userSigning.user.surnames }</div>
									<div style="font-size: 11px"><spring:message code="signing.detail.sub.title" /></div>
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
							<div class="describers mt-2">

								<div class="row mb-2">
									<div class="col">
										<h5 style="font-weight: 400; text-decoration: underline">
											<spring:message code="time.control.detail.info.label" />
										</h5>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<span><spring:message
												code="signing.detail.project"
												arguments="${userSigning.project.name}" /></span>
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
							<div class="describers mt-2">

								<div class="row mb-2">
									<div class="col">
										<h5 style="font-weight: 400; text-decoration: underline">
											<spring:message code="signing.geolocation" />
										</h5>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<a href="https://www.google.com/maps/place/${userSigning.startLocation}" target="_blank" class="btn btn-sm btn-success w-100"><spring:message code="signing.geolocation.start" /></a>
									</div>

									<div class="col">
										<a href="https://www.google.com/maps/place/${userSigning.endLocation}" target="_blank" class="btn btn-sm btn-success w-100" ${ empty userSigning.endLocation ? 'disabled onclick="return false"' : '' }><spring:message code="signing.geolocation.end" /></a>
									</div>
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
						<spring:message code="signing.detail.sub.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="signing.page.table.id" /></th>
									<th id="thStartDate"><spring:message code="signing.page.table.start.date" /></th>
									<th id="thEndDate"><spring:message code="signing.page.table.end.date" /></th>
									<th id="thShareType"><spring:message code="signing.page.table.share.type" /></th>
									<th id="thActions" class="all"><spring:message code="signing.page.table.actions" /></th>
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

	var $=jQuery.noConflict();
	
	$(document).ready(function() {
		
		/* Datatables */
		var dTable = $('#dTable').DataTable({
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"lengthMenu": [ 10, 25, 50, 75, 100 ],
			"ajax": "/signing/${userSigning.id}/dt",
			"rowId": "st_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "st_id" },
				{ "data": "st_startDate" },
				{ "data": "st_endDate" },
				{ "data": "st_shareType" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "orderable": false, "targets": "_all" },
				{  
				    "render": function ( data ) {
						return parseId(data);
                	},
                	"targets": 0
                },
				{
                	"targets": [ 1, 5 ],
                    "visible": false,
                    "searchable": false,
                    "defaultContent": ""
                },
				{  
				    "render": function ( data ) {
					    if (!data) { return '-'; } 
					    else { return moment(data).format('DD/MM/YYYY HH:mm'); }
                	},
                	"targets": [2, 3]
                },
                {  
				    "render": function ( data, type, row ) {
                        return parseShareType(data);
                	},
                	"targets": 4
                },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});
		/* End Datatables */
		
	});

	function parseId(data) {
		return data.split('_')[0];
	}
	
	function parseShareType(data) {
		if (data === 'cs') {
			return '<span class="badge badge-success"> ${jspUtil.parseTagToText('cs')} </span>';
		} else if (data === 'ds') {
			return '<span class="badge badge-warning"> ${jspUtil.parseTagToText('ds')} </span>';
		}
		else if (data === 'ips') {
			return '<div class="badge badge-secondary"> ${jspUtil.parseTagToText('ips')} </div>';
		} else if (data === 'is') {
			return '<span class="badge badge-primary"> ${jspUtil.parseTagToText('is')} </span>';
		} else if (data === 'ws') {
			return '<span class="badge badge-info"> ${jspUtil.parseTagToText('ws')} </span>';
		}
		
		return '';
	}
	
	function parseActionButtons() {

	}
</script>