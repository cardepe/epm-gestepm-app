<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>

.content {
	overflow: hidden;
}

.form-control[readonly] {
	background-color: #fff;
}

#saveUserBtn {
	display: none;
}

.tab-content > .tab-pane {
    display: block;
    visibility: hidden;
    height: 0;
}

.tab-content > .active {
    visibility: visible;
}

.calendar-content {
	height: calc(100vh - 310px);
}

#select2-createShareManualTypeDropdown-container .select2-selection__placeholder,
#select2-createShareProjectDropdown-container .select2-selection__placeholder {
	color: #495057;
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col-10">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="user.detail.title" arguments="${userDetail.name}" /></h1>
					</div>
				</div>
			</div>
			<div class="col-2">
				<div class="float-right calendarBtns">
					<a id="returnBtn" href="${pageContext.request.contextPath}/users" class="btn btn-standard btn-sm">
						<span class="fc-icon fc-icon-chevron-left"></span> <spring:message code="back" />
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<div class="content">
	<div class="card h-100">
		<div class="card-body p-0 h-100">
			<div class="container-fluid h-100">
				<div class="row h-100">
					<div class="col-sm-12 col-md-3">
						<div class="nav flex-column nav-pills p-4" id="v-pills-tab" role="tablist" aria-orientation="vertical">
						  	<a class="nav-link" id="v-pills-absences-tab" data-toggle="pill" href="#v-pills-absences" role="tab" aria-controls="v-pills-absences" aria-selected="false"><spring:message code="user.detail.absences" /></a>
							<a class="nav-link" id="v-pills-signing-tab" data-toggle="pill" href="#v-pills-signing" role="tab" aria-controls="v-pills-signing" aria-selected="false"><spring:message code="user.detail.signing" /></a>
							<a class="nav-link" id="v-pills-forum-tab" data-toggle="pill" href="#v-pills-forum" role="tab" aria-controls="v-pills-forum" aria-selected="false"><spring:message code="user.detail.forum" /></a>
							<a class="nav-link" id="v-pills-expenses-tab" data-toggle="pill" href="#v-pills-expenses" role="tab" aria-controls="v-pills-expenses" aria-selected="false"><spring:message code="user.detail.expenses" /></a>
						  	<a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true"><spring:message code="user.detail.info" /></a>
						  	<a class="nav-link" id="v-pills-projects-tab" data-toggle="pill" href="#v-pills-projects" role="tab" aria-controls="v-pills-projects" aria-selected="false"><spring:message code="user.detail.projects" /></a>
							<a class="nav-link" id="v-pills-holidays-tab" data-toggle="pill" href="#v-pills-holidays" role="tab" aria-controls="v-pills-holidays" aria-selected="false"><spring:message code="user.detail.holidays" /></a>
						</div>
					</div>
					<div class="col-sm-12 col-md-9 bg-light overflow-auto">
						<div class="tab-content" id="v-pills-tabContent">

							<div class="tab-pane fade" id="v-pills-absences" role="tabpanel" aria-labelledby="v-pills-absences-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.absences" />
										</div>
									</div>

									<div class="col text-right">
										<button type="button" class="btn btn-standard btn-sm" data-toggle="modal" data-target="#createModal"><spring:message code="user.detail.absences.create" /></button>
									</div>
								</div>

						  		<div class="table-responsive">
								  	<table id="dTableAbsences" class="table table-striped table-borderer dataTable w-100">
										<caption class="d-none">
											NO SONAR
										</caption>
										<thead>
											<tr>
												<th id="id"><spring:message code="user.detail.absences.id" /></th>
												<th id="absenceType"><spring:message code="user.detail.absences.type" /></th>
												<th id="date"><spring:message code="user.detail.absences.date" /></th>
												<th id="actions" class="all"><spring:message code="user.detail.absences.actions" /></th>
											</tr>
										</thead>
									</table>
								</div>
						  	</div>

						  	<div class="tab-pane fade" id="v-pills-signing" role="tabpanel" aria-labelledby="v-pills-signing-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.signing" />
										</div>
									</div>
									<div class="col">
										<div class="float-right calendarBtns">
											<div class="fc-button-group">
												<sec:authorize access="hasAuthority('ROLE_JEFE_PROYECTO')">
													<button type="button" class="btn btn-success btn-sm mr-1" data-toggle="modal" data-target="#createSigningModal"><spring:message code="user.detail.signing.admin.btn" /></button>
												</sec:authorize>
												<a href="/signings/teleworking?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="user.detail.signing.teleworking" /></a>
												<a href="/signing/manual?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="user.detail.signing.manual" /></a>
												<a href="/signing/personal/time-control?user=${userDetail.id}" class="btn btn-warning btn-sm mr-1 text-white"><spring:message code="signing.hours.bag" /></a>
												<button type="button" class="btn btn-primary btn-sm mr-1" data-toggle="modal" data-target="#signinExportModal"><spring:message code="user.detail.signing.gen" /></button>
												<button type="button" class="btn btn-primary btn-sm mr-1" onclick="calendar.prev()">
													<span class="fc-icon fc-icon-chevron-left"></span>
												</button>
												<button type="button" class="btn btn-primary btn-sm" onclick="calendar.next()">
													<span class="fc-icon fc-icon-chevron-right"></span>
												</button>
											</div>
										</div>
									</div>
								</div>

						  		<div class="row h-100">
						  			<div class="col-md-12 calendar-content">
									  	<div id="calendar" class="h-100"></div>
						  			</div>
						  		</div>
						  	</div>

						  	<div class="tab-pane fade" id="v-pills-forum" role="tabpanel" aria-labelledby="v-pills-forum-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.forum" />
										</div>
									</div>
								</div>

								<c:choose>
									<c:when test="${not empty userDetail.username}">
										<span><spring:message code="user.detail.user.username" />: ${userDetail.username}</span>
									</c:when>
									<c:otherwise>
										<form id="userForumForm" method="POST" action="/forum/create">
											<input name="userId" type="hidden" value="${userDetail.id}" />

											<div class="row">
												<div class="col text-center">
													<span class="font-italic"><spring:message code="user.detail.forum.empty" /></span>
												</div>
											</div>
											<div class="row">
												<div class="col text-center">
													<div class="form-group form-inline justify-content-center mt-3">
														<input id="username" name="username" type="text"
															class="form-control text-center" value="${userDetail.username}"
															placeholder="<spring:message code="user.detail.user.username" />"
															required />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col text-center">
													<button id="createUserForum" type="button" class="btn btn-primary btn-sm"><spring:message code="save" /></button>
												</div>
											</div>
										</form>
									</c:otherwise>
								</c:choose>
						  	</div>

						  	<div class="tab-pane fade" id="v-pills-expenses" role="tabpanel" aria-labelledby="v-pills-expenses-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.expenses" />
										</div>
									</div>
								</div>

								<div class="table-responsive">
									<table id="dTableExpenses" class="table table-striped table-borderer dataTable w-100">
										<thead>
											<tr>
												<th><spring:message code="id"/></th>
												<th><spring:message code="description"/></th>
												<th><spring:message code="project"/></th>
												<th><spring:message code="status"/></th>
												<th><spring:message code="date"/></th>
												<th><spring:message code="amount"/></th>
												<th><spring:message code="actions"/></th>
											</tr>
										</thead>
									</table>
								</div>
						  	</div>

							<div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">

								<form id="userInfoForm" class="needs-validation">
									<div class="row">
							  			<div class="col">
											<div class="title mb-4 pb-2">
												<spring:message code="user.detail.info" />
											</div>
										</div>

										<div class="col text-right">
											<button id="saveUserBtn" type="button" class="btn btn-accent btn-sm"><spring:message code="save" /></button>
											<button id="editUserBtn" type="button" class="btn btn-standard btn-sm" ${user.role.id == jspUtil.getRolId('ROLE_JEFE_PROJECTO') ? 'disabled' : ''}><spring:message code="edit" /></button>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="name" class="col-form-label">
													<spring:message code="user.detail.user.name" />
												</label>
												<input id="name" name="name" type="text" class="form-control" value="${userDetail.name}" required readonly />
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="surnames" class="col-form-label">
													<spring:message code="user.detail.user.surnames" />
												</label>
												<input id="surnames" name="surnames" type="text" class="form-control" value="${userDetail.surnames}" required readonly />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="email" class="col-form-label">
													<spring:message code="user.detail.user.email" />
												</label>
												<input id="email" name="email" type="email" class="form-control" value="${userDetail.email}" required readonly />
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="password" class="col-form-label">
													<spring:message code="user.detail.user.password" />
												</label>
												<input id="password" name="password" type="password" class="form-control" value="${userDetail.password}" required readonly />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="countryId" class="col-form-label">
													<spring:message code="user.detail.user.country" />
												</label>

												<select id="countryId" name="countryId" class="form-control" required disabled>
													<c:forEach items="${countries}" var="country">
														<option value="${country.id}" ${userDetail.activityCenter.country.id == country.id ? 'selected' : ''}>${country.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="activityCenterId" class="col-form-label">
													<spring:message code="user.detail.user.activity.center" />
												</label>

												<select id="activityCenterId" name="activityCenterId" class="form-control" required disabled>
													<c:forEach items="${activityCenters}" var="activityCenter">
														<option value="${activityCenter.id}" ${userDetail.activityCenter.id == activityCenter.id ? 'selected' : ''}>${activityCenter.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="roleId" class="col-form-label">
													<spring:message code="user.detail.user.role" />
												</label>

												<select id="roleId" name="roleId" class="form-control" required disabled>
													<c:forEach items="${roles}" var="role">
														<option value="${role.id}" ${userDetail.role.id == role.id ? 'selected' : ''}>
															<spring:message code="${role.roleName}" />
														</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="subRoleId" class="col-form-label">
													<spring:message code="user.detail.user.subrole" />
												</label>

												<select id="subRoleId" name="subRoleId" class="form-control" required disabled>
													<c:forEach items="${subRoles}" var="subRole">
														<option value="${subRole.id}" ${userDetail.subRole.id == subRole.id ? 'selected' : ''}>
															<spring:message code="${subRole.rol}" />
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="workingHours" class="col-form-label">
													<spring:message code="user.detail.user.working.hours" />
												</label>

												<input id="workingHours" name="workingHours" type="number" class="form-control" value="${userDetail.workingHours}" required readonly />
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<label for="signingId" class="col-form-label">
													<spring:message code="users.table.signingid" />
												</label>

												<input id="signingId" name="signingId" type="number" class="form-control" value="${userDetail.signingId}" required readonly />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label for="stateInput" class="col-form-label">
													<spring:message code="user.detail.user.state" />
												</label>

												<select id="stateInput" name="state" class="form-control" required disabled>
													<option value="0" ${userDetail.state == 0 ? 'selected' : ''}>
														<spring:message code="user.detail.state.active" />
													</option>
													<option value="1" ${userDetail.state == 1 ? 'selected' : ''}>
														<spring:message code="user.detail.state.inactive" />
													</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>

							<div class="tab-pane fade" id="v-pills-projects" role="tabpanel" aria-labelledby="v-pills-projects-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.projects" />
										</div>
									</div>

									<sec:authorize access="hasAuthority('ROLE_RRHH')">
										<div class="col text-right">
											<button type="button" class="btn btn-standard btn-sm" data-toggle="modal" data-target="#createProjectModal"><spring:message code="user.detail.projects.create" /></button>
										</div>
									</sec:authorize>
								</div>

						  		<div class="table-responsive">
								  	<table id="dTableProjects" class="table table-striped table-borderer dataTable w-100">
										<caption class="d-none">
											NO SONAR
										</caption>
										<thead>
											<tr>
												<th id="thPrId"><spring:message code="projects.table.id" /></th>
												<th id="thPrName"><spring:message code="projects.table.name" /></th>
												<th id="thPrStartDate"><spring:message code="projects.table.start.date" /></th>
												<th id="thPrEndDate"><spring:message code="projects.table.objective.date" /></th>
												<th id="thPrActions" class="all"><spring:message code="projects.table.actions" /></th>
											</tr>
										</thead>
									</table>
								</div>
						  	</div>

						  	<div class="tab-pane fade" id="v-pills-holidays" role="tabpanel" aria-labelledby="v-pills-holidays-tab">

						  		<div class="row">
						  			<div class="col">
								  		<div class="title mb-4 pb-2">
											<spring:message code="user.detail.holidays" />
										</div>
									</div>
								</div>

						  		<div class="table-responsive">
								  	<table id="dTableHolidays" class="table table-striped table-borderer dataTable w-100">
										<caption class="d-none">
											NO SONAR
										</caption>
										<thead>
											<tr>
												<th id="id"><spring:message code="user.detail.holidays.id" /></th>
												<th id="date"><spring:message code="user.detail.holidays.date" /></th>
												<th id="status"><spring:message code="user.detail.holidays.status" /></th>
												<th id="actions" class="all"><spring:message code="user.detail.holidays.actions" /></th>
											</tr>
										</thead>
									</table>
								</div>
						  	</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- MODAL -->
<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="user.detail.absences.create" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="addAbsenceForm">
					<div class="row">
						<div class="col">
							<select id="absenceType" class="form-control input" style="width: 100%" name="absenceType">
								<c:forEach items="${absenceTypes}" var="absenceType">
									<option value="${absenceType.id}">
										<spring:message code="${absenceType.name}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row mt-4">
						<div class="col">
							<input type="date" class="form-control input" id="date" name="date">
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
						<button id="addAbsence" type="submit" class="btn btn-sm btn-success"><spring:message code="add" /></button>
					</div>
				</div>
			</div>
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

<!-- SIGNING CREATE MODAL -->
<div id="createSigningModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5><spring:message code="user.detail.signing.admin.btn" /></h5>
				</div>
			</div>

			<div class="modal-body">
				<form id="createSigningForm">
					<input type="hidden" name="userId" value="${userDetail.id}" />
					<input type="hidden" name="state" value="0" />
					<input type="hidden" name="manualDisplacement" value="1">
					<input id="geolocationInput" type="hidden" name="geolocation" />

					<div class="row">
						<div class="col">
							<select id="signingTypeDropdown" class="form-control input" style="width: 100%" name="signingType" onchange="modifyCreateSigningForm(this)" required>
								<option disabled selected="selected">
									<spring:message code="signing.type.selectable" />
								</option>
								<option value="ps"><spring:message code="signing.personal.title" /></option>
								<option value="ums"><spring:message code="signing.manual.calendar.title" /></option>
							</select>
						</div>
					</div>

					<div id="createShareProjectRow" class="row mt-2" style="display: none">
						<div class="col">
							<select id="createShareProjectDropdown" class="form-control input" name="project">
								<option></option>
								<c:forEach items="${projects}" var="project">
									<option value="${project.id}" data-info="${project.id}">
										<spring:message code="${project.name}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div id="createShareManualTypeRow" class="row mt-2" style="display: none">
						<div class="col">
							<select id="createShareManualTypeDropdown" class="form-control input" name="manualTypeId" onchange="updateForm(this.value)">
								<option></option>
								<c:forEach items="${manualSigningTypes}" var="manualSigningType">
									<option value="${manualSigningType.id}">
										<spring:message code="${manualSigningType.name}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div id="createShareActivityCenterRow" class="row mt-2" style="display: none">
						<div class="col">
							<select id="createShareActivityCenterDropdown" class="form-control input" name="activityCenter">
								<option></option>
							</select>
						</div>
					</div>

					<div id="createShareDisplacementDateRow" class="row mt-2" style="display: none">
						<div class="col-sm-12 col-md-6">
							<input type="time" style="font-size: 12px" class="form-control" id="manualHoursInput" name="manualHours">
						</div>

						<div class="col-sm-12 col-md-6">
							<input type="datetime-local" style="font-size: 12px" class="form-control" id="displacementDateInput" name="displacementDate">
						</div>
					</div>

					<div id="createShareDateRow" class="row mt-2" style="display: none">
						<div class="col-sm-12 col-md-6">
							<input type="datetime-local" style="font-size: 12px" class="form-control" id="startDateInput" name="startDate">
						</div>

						<div class="col-sm-12 col-md-6">
							<input type="datetime-local" style="font-size: 12px" class="form-control" id="endDateInput" name="endDate">
						</div>
					</div>

					<div id="row-desc" class="row mt-2" style="display: none">
						<div class="col">
							<div class="form-group">
								<textarea style="font-size: 12px" id="descriptionInput" class="form-control" name="description" rows="3"></textarea>
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
						<button id="signingCreateButton" type="submit" class="btn btn-sm btn-success"><spring:message code="create" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- SIGNIN EXPORT MODAL -->
<div id="signinExportModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<form id="signinExportForm" method="GET" action="/signing/personal/${userDetail.id}/excel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="user.detail.signing.gen" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
							<select id="signinExportMonthInput" class="form-control input" style="width: 100%" name="month" required>
								<option disabled selected="selected">
									<spring:message code="time.control.month.placeholder" />
								</option>
								<c:forEach items="${months}" var="month">
									<option value="${month.key}">
										<spring:message code="${month.value}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row mt-2">
						<div class="col">
							<select id="signinExportYearInput" class="form-control input" style="width: 100%" name="year" required>
								<option disabled selected="selected">
									<spring:message code="time.control.year.placeholder" />
								</option>
								<c:forEach items="${years}" var="year">
									<option value="${year}">
										<spring:message code="${year}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
						</div>
						<div class="float-right">
							<button id="signinExportWoffuButton" type="button" class="btn btn-sm btn-success"><spring:message code="export.woffu" /></button>
							<button id="signinExportButton" type="submit" class="btn btn-sm btn-success"><spring:message code="export" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<!-- CREATE PROJECT MODAL -->
<div id="createProjectModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="user.detail.projects.create" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="addProjectForm">
					<div class="row">
						<div class="col">
							<select id="projectDropdown" class="form-control input selectpicker" data-style="userSelectPicker" data-live-search="true" multiple style="width: 100%" name="projectId">
								<c:forEach items="${notProjects}" var="notProject">
									<option value="${notProject.id}">
										<spring:message code="${notProject.projectName}" />
									</option>
								</c:forEach>
							</select>
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
						<button id="addProject" type="submit" class="btn btn-sm btn-success"><spring:message code="add" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- DECLINE EXPENSE MODAL -->
<div id="declineExpenseModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="user.detail.expenses.decline.title" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="declineExpenseForm">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label class="col-form-label w-100"><spring:message code="user.detail.expenses.decline.observations" />
									<textarea name="observations" class="form-control" rows="6"></textarea>
								</label>
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
						<button type="button" class="btn btn-sm btn-success" onclick="declineAction()"><spring:message code="decline" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /END DECLINE EXPENSE MODAL -->

<!-- DECLINE HOLIDAY MODAL -->
<div id="declineHolidayModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="user.detail.holidays.decline.title" />
					</h5>
				</div>
			</div>
			<div class="modal-body">
				<form id="declineHolidayForm">
					<input type="hidden" id="deHolidayId" />

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="deHolidayObservations" class="col-form-label"><spring:message code="user.detail.holidays.decline.observations" /></label>
								<textarea id="deHolidayObservations" name="observations" class="form-control" rows="6"></textarea>
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
						<button id="addDeclineHolidayBtn" type="button" class="btn btn-sm btn-success"><spring:message code="decline" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /END DECLINE HOLIDAY MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<script>
	let calendar;

	document.addEventListener('DOMContentLoaded', function() {

		$('#createShareProjectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#createShareManualTypeDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('manualsigning.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		/* Calendar */
		const calendarEl = document.getElementById('calendar');

		calendar = new FullCalendar.Calendar(calendarEl, {
			locale: 'es',
			plugins : [ 'timeGrid' ],
			defaultView: 'timeGridWeek',
			header: false,
			allDaySlot: false,
			timeFormat: 'H:mm',
			timeZone: 'UTC',
			height: 'parent',
			events: function (fetchInfo, successCallback, failureCallback) {
				const params = {
					userId: ${userDetail.id},
					startDate: fetchInfo.startStr,
					endDate: fetchInfo.endStr
				};

				axios.get('/v1/time-controls', { params: params }).then((response) => {
					let timeControls = response.data.data.map(timeControl => ({
						id: timeControl.id,
						title: getTitle(timeControl),
						start: timeControl.startDate,
						end: timeControl.endDate,
						color: getColor(timeControl.type),
						textColor: '#fff',
						extendedProps: {
							type: timeControl.type
						}
					}));

					successCallback(timeControls);
				}).catch(error => failureCallback(error));
			},
			eventClick: function(info) {
				const calendarSigning = info.event;
				const id = calendarSigning.id;
				const type = calendarSigning.extendedProps.type;

				if (type === 'TELEWORKING_SIGNING') {
					window.location.href = '/signings/teleworking/' + id;
				} else if (type === 'CONSTRUCTION_SHARES') {
					window.location.href = '/shares/construction/' + id;
				} else if (type === 'DISPLACEMENT_SHARES') {
					window.location.href = '/shares/displacement/' + id;
				} else if (type === 'PROGRAMMED_SHARES') {
					window.location.href = '/shares/programmed/' + id;
				} else if (type === 'WORK_SHARES') {
					window.location.href = '/shares/work/' + id;
				} else {
					loadModalForm(calendarSigning);
				}
			}
		});

		calendar.render();
	});
	/* End Calendar */

	$(document).ready(function() {

		getLocation();

		/* Select 2 */

		$('#projectDropdown').selectpicker();

		/* End Select 2 */

		/* Datatables */
		var dTableHolidays = $('#dTableHolidays').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"pageLength": 7,
			"ajax": "/users/${userDetail.id}/holidays/dt",
			"rowId": "uh_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "desc"]],
			"columns": [
				{ "data": "uh_id" },
				{ "data": "uh_date" },
				{ "data": "uh_status" },
				{ "data": null }
			],
			"columnDefs": [
				{ "targets": [0], "visible": false },
				{ "className": "text-center", "targets": "_all" },
				{
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY');
                	},
                	"targets": 1
                },
				{
					"className": "text-right",
				    "render": function ( data, type, row ) {
                        return parseStatus(data);
                	},
                	"targets": 2
                },
				{ "defaultContent": "${tableValidateActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseValidateActionButtons();
			}
		});

		var dTableAbsences = $('#dTableAbsences').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"pageLength": 7,
			"ajax": "/users/${userDetail.id}/absences/dt",
			"rowId": "uh_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[2, "desc"]],
			"columns": [
				{ "data": "ua_id" },
				{ "data": "at_absenceType" },
				{ "data": "ua_date" },
				{ "data": null }
			],
			"columnDefs": [
				{ "targets": [0], "visible": false },
				{
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY');
                	},
                	"targets": 2
                },
				{ "className": "text-center", "targets": "_all" },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons();
			}
		});

		initializePersonalExpensesDataTables();

		var dTableProjects = $('#dTableProjects').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"pageLength": 7,
			"ajax": "/users/${userDetail.id}/projects/dt",
			"rowId": "pr_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "asc"]],
			"columns": [
				{ "data": "pr_id" },
				{ "data": "pr_name" },
				{ "data": "pr_startDate" },
				{ "data": "pr_endDate" },
				{ "data": null }
			],
			"columnDefs": [
				{ "targets": [0], "visible": false },
				{ "className": "text-center", "targets": "_all" },
				{
				    "render": function ( data, type, row ) {
                    	return moment(data).format('DD/MM/YYYY');
                	},
                	"targets": [2, 3]
                },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function(settings, json) {
				parseProjectActionButtons();
			}
		});
		/* End Datatables */

		$('#editUserBtn').click(function() {

			$('#saveUserBtn').toggle();

			var editingMode = $('#saveUserBtn').css('display') != 'none';

			$('#name').attr('readonly', !editingMode);
			$('#surnames').attr('readonly', !editingMode);
			$('#email').attr('readonly', !editingMode);
			$('#password').attr('readonly', !editingMode);
			$('#countryId').attr('disabled', !editingMode);
			$('#activityCenterId').attr('disabled', !editingMode);
			$('#roleId').attr('disabled', !editingMode);
			$('#subRoleId').attr('disabled', !editingMode);
			$('#workingHours').attr('readonly', !editingMode);
			$('#signingId').attr('readonly', !editingMode);
			$('#stateInput').attr('disabled', !editingMode);
		});

		$('#saveUserBtn').click(function() {

			if (validateForm()) {
				$('#userInfoForm').addClass('was-validated');
			} else {
				showLoading();

				$('#userInfoForm').removeClass('was-validated');

				$.ajax({
					type: "POST",
					url: "/users/${userDetail.id}/update",
					data: $('#userInfoForm').serialize(),
					success: function(msg) {
						$('#editUserBtn').click();
						hideLoading();
						showNotify(msg, 'success');
					},
					error: function(e) {
						hideLoading();
						showNotify(e.responseText, 'danger');
					}
				});
			}
		});

		$('#addAbsence').click(function() {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/users/${userDetail.id}/absences/create",
				data: $('#addAbsenceForm').serialize(),
				success: function(msg) {
					dTableAbsences.ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createModal').modal('hide');
		});

		$('#editSigningBtn').click(function() {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/signing/share/update",
				data: $('#editSigningForm').serialize(),
				success: function(msg) {
					calendar.refetchEvents();
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

		$('#createUserForum').click(function() {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/forum/create",
				data: $('#userForumForm').serialize(),
				success: function(msg) {
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});
		});

		$('#createShareProjectDropdown').change(function() {

			let activityCenterId = $('#createShareProjectDropdown option:selected').attr('data-info');
			let callbackFunction = function(html) {
				$('#createShareActivityCenterDropdown').html(html);
			}

			loadDisplacements(activityCenterId, callbackFunction);
		});

		$('#signinExportButton').click(function() {
			$('#signinExportModal').modal('hide');
		});

		$('#signinExportWoffuButton').click(function() {

			var month = $('#signinExportMonthInput').val();
			var year = $('#signinExportYearInput').val();

			var queryparams = '';

			if (month || year) {
				queryparams = '?' + (month ? 'month=' + month : '') + (year ? (month ? '&' : '') + 'year=' + year : '');
			}

			window.open('/signing/personal/${userDetail.id}/woffu/excel' + queryparams, '_blank').focus();

			$('#signinExportModal').modal('hide');
		});

		$('#addProject').click(function() {

			showLoading();

			$.ajax({
				type: "POST",
				url: "/users/${userDetail.id}/projects/create",
				data: $('#addProjectForm').serialize(),
				success: function(msg) {
					dTableProjects.ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createProjectModal').modal('hide');
		});

		$('#addDeclineHolidayBtn').click(function() {

			showLoading();

			var holidayId = $('#deHolidayId').val();

			$.ajax({
				type: "POST",
				url: "/holidays/decline/" + holidayId,
				data: $('#declineHolidayForm').serialize(),
				success: function(msg) {
					$('#dTableHolidays').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#declineHolidayModal').modal('hide');
		});

		// Clear project dropdown
		$('#createProjectModal').on('hidden.bs.modal', function (e) {
			$("#projectDropdown").val(null).trigger("change");
		});

		$('#signingCreateButton').click(function() {

			if (validateCreateSigningForm()) {

				$('#createSigningForm').addClass('was-validated');

			} else {

				showLoading();

				$('#createSigningForm').removeClass('was-validated');

				const selectedSigningType = $('#createSigningForm #signingTypeDropdown').val();

				if (selectedSigningType == 'ps'){
					submitSigning('/signing/personal');
				} else if (selectedSigningType == 'ums') {
					submitSigning('/signing/manual');
				} else if (selectedSigningType == 'ds') {
					submitSigning('/shares/displacement');
				}
			}
		});
	});

	function modifyCreateSigningForm(control) {

		const signingType = control.value;

		$('#createShareProjectRow').hide();
		$('#createShareManualTypeRow').hide();
		$('#createShareActivityCenterRow').hide();
		$('#createShareDateRow').hide();
		$('#createShareDisplacementDateRow').hide();
		$('#row-desc').hide();

		if (signingType === 'ps' || signingType === 'ums') {
			$('#createShareDateRow').show();
		}

		if (signingType === 'ds') {
			$('#createShareProjectRow').show();
		}

		if (signingType === 'ums') {
			$('#createShareManualTypeRow').show();
		}

		if (signingType === 'ds') {
			$('#createShareActivityCenterRow').show();
			$('#createShareDisplacementDateRow').show();
		}
	}

	function validateForm() {

		var name = $('#name').val(); // document.getElementById('name');
		var surnames = $('#surnames').val(); // document.getElementById('surnames');
		var email = $('#email').val(); // document.getElementById('email');
		var password = $('#password').val(); // document.getElementById('password');
		var roleId = $('#roleId').val(); // document.getElementById('roleId');
		var subRoleId = $('#subRoleId').val(); // document.getElementById('subRoleId');
		var workingHours = $('#workingHours').val();
		var signingId = $('#signingId').val();
		var stateInput = $('#stateInput').val();

		return !name || !surnames || !email || !isValidEmailAddress(email) || !password || !roleId || !subRoleId || !workingHours || !signingId || !stateInput;
	}

	function validateCreateSigningForm() {

		const type = $('#createSigningForm #signingTypeDropdown').val();
		let project = $('#createSigningForm #createShareProjectDropdown').val();
		let manualType = $('#createSigningForm #createShareManualTypeDropdown').val();
		let activityCenter  = $('#createSigningForm #createShareActivityCenterDropdown').val();
		let manualHours = $('#createSigningForm #manualHoursInput').val();
		let displacementDate = $('#createSigningForm #displacementDateInput').val();
		let startDate = $('#createSigningForm #startDateInput').val();
		let endDate = $('#createSigningForm #endDateInput').val();
		// let description = $('#createSigningForm #descriptionInput').val();

		if (type === 'us') {
			manualType = true;
			activityCenter  = true;
			displacementDate = true;
			manualHours = true;
		} else if (type === 'ps') {
			manualType = true;
			project = true;
			activityCenter  = true;
			displacementDate = true;
			manualHours = true;
		} else if (type === 'ums') {
			project = true;
			activityCenter  = true;
			displacementDate = true;
			manualHours = true;
		} else if (type === 'ds') {
			manualType = true;
			startDate = true;
			endDate = true;
		}

		return !type || !project || !manualType || !activityCenter  || !manualHours || !displacementDate || !startDate || !endDate;
	}

	function loadModalForm(calendarSigning) {
		const allowedTypes = ['CONSTRUCTION_SHARES', 'DISPLACEMENT_SHARES', 'INSPECTIONS', 'MANUAL_SIGNINGS',
			'PERSONAL_SIGNINGS', 'PROGRAMMED_SHARES', 'WORK_SHARES'];

		const id = calendarSigning.id;
		const type = calendarSigning.extendedProps.type;

		if (!allowedTypes.some(allowedType => type.includes(allowedType))) {
			return;
		}

		const startDate = new Date(calendarSigning.start).toISOString().slice(0, 16);
		const endDate = new Date(calendarSigning.end).toISOString().slice(0, 16);

		const form = document.querySelector('#editSigningForm');

		form.querySelector('[name="shareId"]').value = id;
		form.querySelector('[name="shareType"]').value = type;
		form.querySelector('[name="startDate"]').value = startDate;
		form.querySelector('[name="endDate"]').value = endDate;

		$('#editSigningModal').modal('show');
	}

	function parseActionButtons() {

		var tableRows = $('#dTableAbsences tbody tr');

		tableRows.each(function() {

			var absenceId = $(this).attr('id');
			var lastColumn = $(this).children().last();
			var emList = lastColumn.children();

			emList.each(function(index) {

				if (index == 0) {
					$(this).attr('onclick', 'deleteAbsence(' + absenceId + ')');
				}
			});
		});
	}

	function parseProjectActionButtons() {

		var tableRows = $('#dTableProjects tbody tr');

		tableRows.each(function() {

			var projectId = $(this).attr('id');
			var lastColumn = $(this).children().last();
			var emList = lastColumn.children();

			emList.each(function(index) {

				if (index == 0) {
					$(this).attr('onclick', 'deleteProject(' + projectId + ')');
				}
			});
		});
	}

	function deleteAbsence(absenceId) {

		showLoading();

		$.ajax({
			type: "POST",
			url: "/users/${userDetail.id}/absences/delete/" + absenceId,
			success: function(msg) {
				$('#dTableAbsences').DataTable().ajax.reload();
				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function parseValidateActionButtons() {
		var tableRows = $('#dTableHolidays tbody tr');

		tableRows.each(function() {

			var holidayId = $(this).attr('id');
			var status = jQuery($(this).children().get(1));
			var lastColumn = $(this).children().last();
			var emList = lastColumn.children();

			emList.each(function(index) {

				if (index == 0) { // validate
					$(this).attr('onclick', 'validateHoliday(' + holidayId + ')');

					if (!canValidate(status)) {
						$(this).remove();
					}
				} else if (index == 1) { // decline
					$(this).attr('onclick', 'openDeclineHolidayModal(' + holidayId + ')');

					if (!canValidate(status)) {
						$(this).remove();
					}
				} else if (index == 2) { // trash
					$(this).attr('onclick', 'deleteHoliday(' + holidayId + ')');

					if (canValidate(status)) {
						$(this).remove();
					}
				}
			});
		});
	}

	function validateHoliday(holidayId) {
		showLoading();

		$.ajax({
			type: "POST",
			url: "/holidays/validate/" + holidayId,
			data: { },
			success: function(msg) {
				$('#dTableHolidays').DataTable().ajax.reload();
				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function declineHoliday(holidayId) {
		showLoading();

		$.ajax({
			type: "POST",
			url: "/holidays/decline/" + holidayId,
			data: { },
			success: function(msg) {
				$('#dTableHolidays').DataTable().ajax.reload();
				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function deleteHoliday(holidayId) {
		var ok = confirm("${jspUtil.parseTagToText('holidays.admin.delete.alert')}");

		if (ok) {
			showLoading();

			$.ajax({
				type: "DELETE",
				url: "/holidays/delete/" + holidayId,
				data: { },
				success: function(msg) {
					$('#dTableHolidays').DataTable().ajax.reload();
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

	function canValidate(status) {
		var roleId = ${user.role.id};
		var rejected = status.children().hasClass('badge-danger');
		var pending = status.children().hasClass('badge-secondary');
		var approved = status.children().hasClass('badge-primary');

		if (approved || rejected) {
			return false;
		} else if (pending && roleId >= ${jspUtil.getRolId('ROLE_RRHH')}) { // RRHH can approve
			return true;
		}

		return false;
	}

	function openDeclineHolidayModal(holidayId) {

		$('#deHolidayId').val(holidayId);
		$('#declineHolidayModal').modal('show');
	}

	function deleteProject(projectId) {
		showLoading();

		$.ajax({
			type: "DELETE",
			url: "/users/${userDetail.id}/projects/delete/" + projectId,
			data: { },
			success: function(msg) {
				$('#dTableProjects').DataTable().ajax.reload();
				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {
				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function submitSigning(endpoint) {

		showLoading();

		$.ajax({
			type: "POST",
			url: endpoint,
			data: $('#createSigningForm').serialize(),
			success: function(msg) {

				calendar.refetchEvents();
				$('#createSigningModal').modal('hide');

				hideLoading();
				showNotify(msg, 'success');
			},
			error: function(e) {

				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}

	function updateForm(type) {

		if (type == 8) {
			$('#row-desc').show();
		} else {
			$('#row-desc').hide();
		}
	}

</script>

<script>
	let locale = '${locale}';
	let dTableExpenses = null;

	document.addEventListener("DOMContentLoaded", function() {
		setReturnButtonUrl();
	});

	function setReturnButtonUrl() {
		if (document.referrer) {

			const lastPagePath = new URL(document.referrer).pathname;
			const queryParams = document.referrer.split('?')[1];
			const lastPageUrl = lastPagePath + (queryParams ? '?' + queryParams : '');

			if (lastPagePath === '/users') {
				$('#returnBtn').attr('href', lastPageUrl);
			}
		}
	}

	function initializePersonalExpensesDataTables() {
		let columns = ['id', 'description', 'project.name', 'status', 'createdAt', 'amount', 'id']
		let endpoint = '/v1/expenses/personal/sheets';
		let actions = [
			{
				action: 'file-pdf',
				url: '/v1/expenses/personal/sheets/{id}/export',
				permission: 'edit_personal_expenses_sheet'
			},
			{
				action: 'validate',
				permission: 'edit_personal_expenses_sheet',
				conditionGroups: [
					{
						conditions: [
							{ key: 'status', value: ['PENDING'], operation: '===' },
							{ key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: ${user.role.id} }
						],
					},
					{
						conditions: [
							{ key: 'status', value: ['APPROVED'], operation: '===' },
							{ key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: ${user.role.id} }
						]
					}
				]
			},
			{
				action: 'decline',
				permission: 'edit_personal_expenses_sheet',
                conditionGroups: [
                    {
                        conditions: [
                            { key: 'status', value: ['PENDING'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_PL_ID'], operation: '>=', current: ${user.role.id} }
                        ],
                    },
                    {
                        conditions: [
                            { key: 'status', value: ['APPROVED'], operation: '===' },
                            { key: 'roleId', value: ['ROLE_ADMINISTRATION_ID'], operation: '>=', current: ${user.role.id} }
                        ]
                    }
                ]
			},
			{
				action: 'view',
				url: '/expenses/personal/sheets/{id}',
				permission: 'edit_personal_expenses_sheet'
			}
		]
		let expand = ['project']
		let filters = [{'createdBy': ${userDetail.id}}]
		let orderable = [[0, 'DESC']]
		let columnDefs = [
			{
				targets: 3,
				render: function (data) {
					return parseStatusToBadge(data);
				}
			},
			{
				targets: 4,
				render: function (data) {
					return moment(data).format('DD-MM-YYYY HH:mm');
				}
			},
			{
				targets: 5,
				render: function (data) {
					return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(data);
				}
			}
		]

		customDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, orderable, columnDefs);
		dTableExpenses = createDataTable('#dTableExpenses', customDataTable, locale);
		customDataTable.setCurrentTable(dTableExpenses);
	}

	function parseStatusToBadge(status) {
		if (status === 'PENDING') {
			return '<div class="badge badge-warning">' + messages.status.pending + '</div>';
		} else if (status === 'APPROVED') {
			return '<span class="badge badge-primary">' + messages.status.approved + '</span>';
		} else if (status === 'PAID') {
			return '<span class="badge badge-success">' + messages.status.paid + '</span>';
		} else if (status === 'REJECTED') {
			return '<span class="badge badge-danger">' + messages.status.rejected + '</span>';
		}
	}

	function validate(personalExpenseSheetId) {

		showLoading();

		const data = dTableExpenses.row("#" + personalExpenseSheetId).data();
		const nextStatus = getNextStatus(data.status);

		if (nextStatus) {
			axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
				status: nextStatus
			}).then(() => {
				dTableExpenses.ajax.reload();
				showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
			}).catch(error => showNotify(error.response.data.detail, 'danger'))
					.finally(() => hideLoading());
		}
	}

	function decline(personalExpenseSheetId) {
		const modal = $('#declineExpenseModal');
		modal.attr('data-id', personalExpenseSheetId);
		modal.modal('show');
	}

	function declineAction() {
		showLoading();

		const modal = $('#declineExpenseModal');
		const personalExpenseSheetId = modal.data('id');
		const form = document.querySelector('#declineExpenseForm');
		const obervations = form.querySelector('[name="observations"]')

		axios.patch('/v1/expenses/personal/sheets/' + personalExpenseSheetId, {
			status: 'REJECTED',
			observations: obervations.value
		}).then(() => {
			dTableExpenses.ajax.reload();
			showNotify(messages.personalExpenseSheet.update.success.replace('{0}', personalExpenseSheetId))
		}).catch(error => showNotify(error.response.data.detail, 'danger'))
				.finally(() => {
					hideLoading();
					modal.modal('hide');
				});
	}

	function getNextStatus(status) {
		if (status === 'PENDING') {
			return 'APPROVED';
		} else if (status === 'APPROVED') {
			return 'PAID';
		}
		return null;
	}

	function getTitle(timeControl) {
		return timeControl.description ? timeControl.description : getSigningText(timeControl.type);
	}

	function getColor(type) {
		if (type === 'DISPLACEMENT_SHARES') {
			return '#CC00C8';
		} else if (type === 'MANUAL_SIGNINGS') {
			return '#D8E112';
		} else if (type === 'PERSONAL_SIGNINGS') {
			return '#0062CC';
		} else {
			return '#12E1DE';
		}
	}

</script>
