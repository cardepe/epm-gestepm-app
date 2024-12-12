<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col-10">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="signing.personal.title" /></h1>
					</div>
				</div>
			</div>
			<div class="col-2">
				<div class="float-right calendarBtns">
					<div class="fc-button-group">
						<button id="previousButton" type="button" class="btn btn-primary btn-sm mr-1">
							<span class="fc-icon fc-icon-chevron-left"></span>
						</button>
						<button id="nextButton" type="button" class="btn btn-primary btn-sm">
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
										<img class="user-avatar rounded-circle" src="/ui/static/images/profile.png" alt="me" height="40" />
									</div>
									
									<div style="font-size: 12px">${ user.name } ${ user.surnames }</div>
									<div style="font-size: 11px"><spring:message code="signing.personal.date" /></div>
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
							<a href="/signing/personal/time-control" class="btn btn-sm btn-success w-100 text-white"><i class="fa fa-shopping-bag"></i> <spring:message code="signing.hours.bag" /></a>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-12">
					<div class="card">
						<div class="card-body">
							<ul class="mb-0">
								<li class="listSignings">
									<div class="box bg-primary"></div>
									<span><spring:message code="signing.today.timer" arguments="${todayTimer}" /></span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-12 col-xl-9 h-100">
			<div id="calendar" class="personalCalendar" class="h-100"></div>
		</div>
	</div>
</div>

<!-- SIGNING MODAL -->
<div id="editSigningModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="editSigningModalTitle">
						<spring:message code="user.detail.absences.create" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="editSigningForm">
					<input id="shareId" name="shareId" type="hidden" />
					<input id="shareType" name="shareType" type="hidden" />

					<div class="row">
						<div class="col-6">
							<input type="datetime-local" class="form-control input" id="startDate" name="startDate">
						</div>

						<div class="col-6">
							<input type="datetime-local" class="form-control input" id="endDate" name="endDate">
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
						<button id="editSigningBtn" type="submit" class="btn btn-sm btn-success"><spring:message code="edit" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var $=jQuery.noConflict();
	var calendar;
	
	document.addEventListener('DOMContentLoaded', function() {

		var calendarEl = document.getElementById('calendar');

		calendar = new FullCalendar.Calendar(calendarEl, {
			locale: 'es',
			plugins : [ 'timeGrid' ],
			defaultView: 'timeGridWeek',
			header: false,
			allDaySlot: false,
			timeFormat: 'H:mm',
			height: 'parent',
			events: '/signing/personal/calendar',
			eventClick: function(info) {

				var shareId = info.event.id.split('_')[0];
				var shareType = info.event.id.split('_')[1];

				if (shareType === 'ds') {
					$('#editSigningModalTitle').text("${jspUtil.parseTagToText('shares.edit.ds.title')}");
				} else if (shareType === 'ps') {
					$('#editSigningModalTitle').text("${jspUtil.parseTagToText('shares.edit.ps.title')}");
				} else if (shareType === 'us') {
					$('#editSigningModalTitle').text("${jspUtil.parseTagToText('shares.edit.ps.title')}");
				} else if (shareType === 'ums') {
					$('#editSigningModalTitle').text("${jspUtil.parseTagToText('shares.edit.ps.title')}");
				}

				viewShare(shareId, shareType);
			}
		});

		calendar.render();
	});
	
	$('#previousButton').click(function() {
		calendar.prev();
	});
	
	$('#nextButton').click(function() {
		calendar.next();
	});

	$('#editSigningBtn').click(function() {

		showLoading();

		$.ajax({
			type: "POST",
			url: "/signing/share/request-update",
			data: $('#editSigningForm').serialize(),
			success: function(msg) {
				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});

		$('#editSigningModal').modal('hide');
	});

	async function viewShare(id, type) {

		var share = await getShare(id, type);

		if (type === 'ds') {

			var hours = share.manualHours.split(':')[0];
			var minutes = share.manualHours.split(':')[1];

			var endDate = moment(share.displacementDate).add(minutes, 'm').add(hours, 'h').format().split('+')[0];

			$('#startDate').val(share.displacementDate);
			$('#endDate').val(endDate);

		} else {

			var form = document.forms['editSigningForm'];

			initForm(share, form);
		}

		$('#shareId').val(id);
		$('#shareType').val(type);

		$('#editSigningModal').modal('show');
	}

	function getShare(id, type) {

		let url;

		if (type === 'ds') {
			url = '/shares/displacement/' + id;
		} else if (type === 'ps') {
			url = '/signing/personal/' + id;
		} else if (type === 'us') {
			url = '/signing/user/' + id;
		} else if (type === 'ums') {
			url = '/signing/manual/' + id;
		}

		return $.ajax({
			url: url,
			type: 'GET'
		});
	}

	function initForm(share, form) {

		Object.keys(share).forEach(function (key) {
			if (form.elements[key]) {
				if (form.elements[key].type === 'checkbox') {
					form.elements[key].checked = share[key];
				} else {
					form.elements[key].value = share[key];
				}
			}
		});
	}

</script>