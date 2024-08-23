<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase">
							<spring:message code="holidays.title" />
						</h1>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="float-right holidayBtns">
					<span id="currentYear"></span>
					<div class="fc-button-group">
						<button id="previousButton" type="button"
							class="btn btn-primary btn-sm mr-1">
							<span class="fc-icon fc-icon-chevron-left"></span>
						</button>
						<button id="nextButton" type="button"
							class="btn btn-primary btn-sm">
							<span class="fc-icon fc-icon-chevron-right"></span>
						</button>
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
										<spring:message code="holidays.sub.title" />
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

							<span class="title"><spring:message code="filters" /></span>

							<div class="form-group">
								<select id="usersDropdown" class="form-control input" name="userDTO">
									<option></option>
									<c:forEach items="${usersDTO}" var="userDTO">
										<option value="${userDTO.userId}" ${filters.userId == userDTO.userId ? 'selected' : ''}>
											<spring:message code="${userDTO.name} ${userDTO.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>

							<div class="form-group mb-2">
								<select id="projectFilterDropdown" class="form-control input">
									<option></option>
									<c:forEach items="${projects}" var="project">
										<option value="${project.id}" ${filters.projectId == project.id ? 'selected' : ''}>
											<spring:message code="${project.name}" />
										</option>
									</c:forEach>
								</select>
							</div>

							<div class="row mt-2">
								<div class="col">
									<button type="button" class="btn btn-outline-secondary btn-sm w-100" style="font-size: 12px" onclick="resetCalendar()">
										<em class="fas fa-sync"></em> <spring:message code="clear" />
									</button>
								</div>

								<div class="col">
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="filterCalendar()">
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
			<div class="calendar"></div>
		</div>
	</div>
</div>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<script>

	let $ = jQuery.noConflict();

	let calendar;

	let selectedDayColor = '#FFAE00';
	let approvedColor = '#229954';
	let rejectedColor = '#E74C3C';

	let userId ;
	let projectId;

	let selectedYear = new Date().getFullYear();
	let disabledWeekDays = ${user.displacement.displacement.id == 1 ? [ 0, 6] : [0] };

	$(document).ready(function() {

		calendar = initCalendar();

		setCurrentYear(selectedYear);

		$('#previousButton').click(function() {

			selectedYear = calendar.getYear() - 1;

			calendar.setYear(selectedYear);
			calendar.setDisabledDays(getHolidays(selectedYear), true);
			calendar.setDataSource(getUserHolidays());

			setCurrentYear(selectedYear);
		});

		$('#nextButton').click(function() {

			selectedYear = calendar.getYear() + 1;

			calendar.setYear(selectedYear);
			calendar.setDisabledDays(getHolidays(selectedYear));
			calendar.setDataSource(getUserHolidays());

			setCurrentYear(selectedYear);
		});

		/* Select 2 */
		$('#projectFilterDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#usersDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('user.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */
	});

	function initCalendar() {

		return new Calendar('.calendar', {

			enableContextMenu : true,
			language : '${locale}',
			dayContextMenu: function(e) {
				dateClickedEvent(e);
			},
			dataSource: getUserHolidays(),
			disabledWeekDays: disabledWeekDays,
			disabledDays: getHolidays(),
			mouseOnDay: function(e) {
				getPopup(e);
			},
			mouseOutDay: function(e) {
				leavePopup(e);
			},
		});
	}

	function getHolidays(year) {

		let currentYear = year || new Date().getFullYear();

		return ''; // { }.map(holiday => (new Date(currentYear, holiday.month - 1, holiday.day)));
	}

	function dateClickedEvent(e) {

		let dateClicked = String(e.date);

		for (let valor of e.calendar._dataSource) {

			if (String(valor.startDate) === dateClicked) {

				if (valor.color !== approvedColor && valor.color !== rejectedColor) {
					deleteEvent(e);
				}

				$('.calendar-context-menu').hide();
			}
		}
	}

	function getPopup(e) {

		if (e.events.length > 0) {

			let event = e.events[0];
			let usernames = event.usernames;

			let content = '<div class="event-tooltip-content">';

			usernames.forEach(u => {
				content += '<div class="event-username">' + u + '</div>'
			});

			content += '</div>'

			$(e.element).popover({
				trigger: 'manual',
				container: 'body',
				html: true,
				content: content
			});

			$(e.element).popover('show');
		}
	}

	function leavePopup(e) {

		if (e.events.length > 0) {
			$(e.element).popover('hide');
		}
	}

	function setCurrentYear(year) {
		$('#currentYear').text(year);
	}

	function getUserHolidays() {

		let userHolidays = [];

		let queryParams = '';

		queryParams += userId ? '&userId=' + userId : '';
		queryParams += projectId ? '&projectId=' + projectId : '';

		$.ajax({
			async: false,
			type: 'GET',
			url: '/holidays/admin/calendar?year=' + selectedYear + queryParams,
			success: function (data) {

				if (data) {

					userHolidays = data.map(d => ({
						startDate: new Date(d.date),
						endDate: new Date(d.date),
						color: d.color,
						usernames: d.usernames
					}));
				}
			}
		});

		return userHolidays;
	}

	function filterCalendar() {

		projectId = $('#projectFilterDropdown').val();
		userId = $('#usersDropdown').val();

		calendar.setDataSource(getUserHolidays());
	}

	function resetCalendar() {

		$('#projectFilterDropdown').val(null).trigger('change');
		$('#usersDropdown').val(null).trigger('change');

		projectId = undefined;
		userId = undefined;

		calendar.setDataSource(getUserHolidays());
	}

</script>