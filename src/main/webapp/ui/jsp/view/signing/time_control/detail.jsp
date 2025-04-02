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
							<spring:message code="time.control.detail.title" arguments="${actualDate}" />
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
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>

	const locale = '${locale}';
	const currentDate = '${actualDate}';

	$(document).ready(function() {

		const startOfDay = moment(currentDate).startOf('day').format('YYYY-MM-DDTHH:mm:ss');
		const endOfDay = moment(currentDate).endOf('day').format('YYYY-MM-DDTHH:mm:ss');

		const columns = [
			{ data: 'type' },
			{ data: 'startDate' },
			{ data: 'endDate' }
		]
		const endpoint = '/v1/time-controls?userId=${id}&startDate=' + startOfDay + '&endDate=' + endOfDay;
		const columnDefs = [
			{
				className: 'text-center',
				targets: '_all'
			},
			{
				targets: 0,
				render: function (data) {
					return getSigningText(data);
				}
			},
			{
				targets: [1, 2],
				render: function (data) {
					return moment(data).format('HH:mm:ss');
				}
			}
		]

		dTable = $('#dTable').DataTable({
			lengthChange: false,
			searching: false,
			responsive: true,
			processing: true,
			serverSide: true,
			ordering: false,
			ajax: endpoint,
			rowId: 'id',
			language: {
				url: '/ui/static/lang/datatables/${locale}.json'
			},
			columns: columns,
			columnDefs: columnDefs,
			dom: "<'top'>rt<'bottom'><'clear'>"
		});
	});

</script>