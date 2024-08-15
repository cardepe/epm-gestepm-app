<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col-10">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="time.control.detail.title"
								arguments="${actualDate}" />
						</h1>
					</div>
				</div>
			</div>
			
			<div class="col-2">
				<div class="float-right calendarBtns">
					<a href="/signing/personal/time-control${urlPattern}" class="btn btn-standard btn-sm">
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

									<div style="font-size: 12px">${ timeControlDTO.username }</div>
									<div style="font-size: 11px">
										<spring:message code="time.control.detail.desc" />
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
											<spring:message code="time.control.detail.info.label" />
										</h5>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.journey.label"
												arguments="${timeControlDTO.journey}" /></span>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.start.date.label"
												arguments="${timeControlDTO.startHour}" /></span>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.end.date.label"
												arguments="${timeControlDTO.endHour}" /></span>
									</div>
								</div>
								
								
								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.total.label"
												arguments="${timeControlDTO.totalHours}" /></span>
									</div>
								</div>
								
								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.breaks.label"
												arguments="${timeControlDTO.breaks}" /></span>
									</div>
								</div>
								
								<div class="row">
									<div class="col">
										<span><spring:message
												code="time.control.detail.diff.label"
												arguments="${timeControlDTO.difference}" /></span>
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
					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thType"><spring:message code="time.control.detail.table.type" /></th>
									<th id="thStartHour"><spring:message code="time.control.detail.table.start.hour" /></th>
									<th id="thEndHour"><spring:message code="time.control.detail.table.end.hour" /></th>
<%-- 									<th id="thActions"><spring:message code="projects.table.actions" /></th> --%>
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
		dTable = $('#dTable').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"iDisplayLength": 31,
			"ordering": false,
			"ajax": "/signing/personal/time-control/${id}/dt?date=${actualDate}",
			"rowId": "tc_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "tc_type" },
				{ "data": "tc_startHour" },
				{ "data": "tc_endHour" }
			],
			"columnDefs": [
				{ "className": "text-center", "orderable": false, "targets": "_all" },
                {  
				    "render": function ( data, type, row ) {
					    if (!data) {
						    return '';
						}
						
					    return moment(data).format('HH:mm:ss');
                	},
                	"targets": [1, 2]
                }
			],
			"dom": "<'top'>rt<'bottom'><'clear'>"
		});
		/* End Datatables */
		
	});
	
</script>