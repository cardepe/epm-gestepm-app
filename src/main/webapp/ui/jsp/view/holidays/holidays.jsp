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

							<ul class="mt-2">
								<li class="listSignings">
									<span><spring:message code="holidays.last.year" /> <span id="lastYearHolidaysCount">${user.lastYearHolidaysCount}</span></span>
								</li>

								<li class="listSignings">
									<span><spring:message code="holidays.current.year" /> <span id="currentYearHolidaysCount">${user.currentYearHolidaysCount}</span></span>
								</li>
							</ul>

							<div class="row">
								<div class="col">
									<div class="form-group">
										<button type="button" id="save" class="btn btn-sm btn-success w-100" disabled><spring:message code="save" /></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<div class="card">
						<div class="card-body">
							<div id="accordion">
								<div class="accordion-header border-bottom border-light">
									<h5>
										<button class="btn btn-link collapsed" data-toggle="collapse"
											data-target="#legalHolidays" aria-expanded="true"
											aria-controls="legalHolidays"><spring:message code="holidays.free.days" /></button>
									</h5>
								</div>

								<div id="legalHolidays" class="collapse" data-parent="#accordion">
									<ul class="">
										<c:forEach items="${holidays}" var="holiday">
											<li class="listSignings">
												<span><fmt:formatNumber pattern="00" value="${holiday.day}" />/<fmt:formatNumber pattern="00" value="${holiday.month}" /> - <spring:message code="${holiday.name}" /></span>
											</li>
										</c:forEach>
									</ul>
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

	let selectedDayColor = '#FFAE00';
	let approvedColor = '#229954';
	let rejectedColor = '#E74C3C';

	let selectedYear = new Date().getFullYear();
	let disabledWeekDays = ${user.activityCenter.activityCenter.id == 1 ? [ 0, 6] : [0] };

	let currentYearHolidaysCount = ${user.currentYearHolidaysCount};
	let lastYearHolidaysCount = ${user.lastYearHolidaysCount};
	let backupCurrentYearHolidaysCount = currentYearHolidaysCount;
	let backupLastYearHolidaysCount = lastYearHolidaysCount;

	let backupUserHolidays = [];
	let removedUserHolidays = [];
	let addedUserHolidays = [];

	$(document).ready(function() {

		let holidaysJson = ${holidaysJson};
		let calendar = initCalendar();

		setCurrentYear(selectedYear);

		function initCalendar() {

			return new Calendar('.calendar', {

				enableContextMenu : true,
				language : '${locale}',
				dayContextMenu: function(e) {
					dateClickedEvent(e);
				},
				clickDay: function(e) {
					addEvent(e.date);
				},
				dataSource: getUserHolidays(),
				disabledWeekDays: disabledWeekDays,
				disabledDays: getHolidays()
			});
		}

		function getUserHolidays() {

			let userHolidays = [];

			$.ajax({
				async: false,
				type: 'GET',
				url: '/holidays/calendar?year=' + selectedYear,
				success: function (data) {

					userHolidays = data.map(d => ({
						startDate: new Date(d.date),
						endDate: new Date(d.date),
						color: d.color
					}));
				}
			});

			backupUserHolidays = userHolidays.map(d => (d.startDate));
			removedUserHolidays = [];
			addedUserHolidays = [];

			return userHolidays;
		}

		function getHolidays(year) {

			let currentYear = year || new Date().getFullYear();

			return holidaysJson.map(holiday => (new Date(currentYear, holiday.month - 1, holiday.day)));
		}

		function addEvent(date) {

			let dataSource = calendar.getDataSource();

			if (dayIsSelected(date)) {
				return;
			}

			if (!isAllowedToTakeDays()) {
				return showNotify('${jspUtil.parseTagToText('holidays.max.days.selected')}', 'danger');
			}

			let event = {
				startDate: date,
				endDate: date,
				color: selectedDayColor
			}

			if (isInArray(removedUserHolidays, date)) {
				removedUserHolidays = removedUserHolidays.filter(h => h.getTime() !== date.getTime());
			}

			if (!isInArray(backupUserHolidays, date)) {
				addedUserHolidays.push(date);
			}

			dataSource.push(event);
			calendar.setDataSource(dataSource);

			updateUserHolidaysCount('remove');
			$('#save').removeAttr('disabled');
		}

		function deleteEvent(event) {

			let dataSource = calendar.getDataSource();

			for (let d in dataSource) {

				let holiday = dataSource[d].startDate;

				if (holiday.getTime() === event.date.getTime()) {

					if (isInArray(addedUserHolidays, event.date)) {
						addedUserHolidays = addedUserHolidays.filter(h => h.getTime() !== event.date.getTime());
					}

					if (isInArray(backupUserHolidays, event.date)) {
						removedUserHolidays.push(event.date);
					}

					dataSource.splice(d, 1);
					calendar.setDataSource(dataSource);

					updateUserHolidaysCount('add');
					$('#save').removeAttr('disabled');
				}
			}
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

		function updateUserHolidaysCount(action) {

			if (action === 'add') {

				if (backupLastYearHolidaysCount < lastYearHolidaysCount) {
					lastYearHolidaysCount++;
				} else {
					currentYearHolidaysCount++;
				}

			} else if (action === 'remove') {

				if (lastYearHolidaysCount > 0) {
					lastYearHolidaysCount--;
				} else {
					currentYearHolidaysCount--;
				}
			}

			$('#lastYearHolidaysCount').text(lastYearHolidaysCount);
			$('#currentYearHolidaysCount').text(currentYearHolidaysCount);
		}

		function isAllowedToTakeDays() {
			return lastYearHolidaysCount > 0 || currentYearHolidaysCount > 0;
		}

		function dayIsSelected(date) {

			let daysSelected = calendar.getDataSource();

			for (let d in daysSelected) {

				let holiday = daysSelected[d].startDate;

				if (holiday.getTime() === date.getTime()) {
					return true;
				}
			}

			return false;
		}

		function setCurrentYear(year) {
			$('#currentYear').text(year);
		}

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

		$('#save').click(function () {

			showLoading();

			let notifyStatus = true;

			$.ajax({
				async: false,
				url: '/holidays/delete',
				type: 'POST',
				data: JSON.stringify(removedUserHolidays),
				contentType: 'application/json',
				error: function() {
					notifyStatus = false;
				}
			});

			$.ajax({
				async: false,
				url: '/holidays/create',
				type: 'POST',
				data: JSON.stringify(addedUserHolidays),
				contentType: 'application/json',
				error: function() {
					notifyStatus = false;
				}
			});

			if (notifyStatus) {
				showNotify('${jspUtil.parseTagToText('holidays.request.success')}', 'success');
			} else {
				showNotify('${jspUtil.parseTagToText('holidays.request.error')}', 'danger');
			}

			$('#save').attr("disabled", true);

			hideLoading();
		})

		function isInArray(array, value) {
			return !!array.find(item => { return item.getTime() === value.getTime() });
		}
	});

</script>