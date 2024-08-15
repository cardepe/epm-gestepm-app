<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<script>
	let $ = jQuery.noConflict();
</script>

<style>

.form-control[readonly],
.form-control[disabled] {
	background-color: #fff;
}

@media (max-width: 575.98px) {

	.movile-full {
		width: 100%;
	}
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col-10">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase" style="padding: 5px 0"><spring:message code="shares.intervention.detail.title" arguments="${share.id}" /> - ${share.project.name}</h1>
					</div>
					
					<span class="page-sub-title">${share.forumTitle}</span>
				</div>
			</div>
			<div class="col-2">
				<div class="float-right calendarBtns">
					<c:choose>
						<c:when test="${ share.state == 3 }">
							<button id="endInterventionAndBackBtn" type="button" class="btn btn-danger btn-sm"><span class="fc-icon fc-icon-chevron-left"></span> <spring:message code="shares.intervention.detail.top.btn" /></button>
						</c:when>
						<c:otherwise>
							<a href="/shares/intervention" class="btn btn-standard btn-sm">
								<span class="fc-icon fc-icon-chevron-left"></span> <spring:message code="back" />
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<c:set var="localeCode" value="${pageContext.response.locale}" />

<div class="content">
	<div class="card h-100">
		<div class="card-body p-0 h-100">
			<div class="container-fluid h-100">
				<div class="row h-100">
					<div class="col-sm-12 col-md-3">
						<div class="nav flex-column nav-pills p-4" id="v-pills-tab" role="tablist" aria-orientation="vertical">
						  	<a class="nav-link active" id="v-pills-desc-tab" data-toggle="pill" href="#v-pills-desc" role="tab" aria-controls="v-pills-desc" aria-selected="true"><spring:message code="shares.intervention.detail.step.1" /></a>
							
							<c:choose>
								<c:when test="${(intervention != null && intervention.action == 2) || (intervention == null && share.lastDiagnosis == '0') }">
									<a class="nav-link" id="v-pills-actions-tab" ${ share.family.id == null ? '' : 'data-toggle="pill"' } href="#v-pills-actions" role="tab" aria-controls="v-pills-actions" aria-selected="false"><spring:message code="shares.intervention.detail.step.2" /></a>
								</c:when>
								<c:when test="${(intervention != null && intervention.action == 1) || (intervention == null && share.lastDiagnosis == '1') }">
									<a class="nav-link" id="v-pills-actions-tab" ${ share.family.id == null ? '' : 'data-toggle="pill"' } href="#v-pills-actions" role="tab" aria-controls="v-pills-actions" aria-selected="false"><spring:message code="shares.intervention.detail.step.3" /></a>
								</c:when>
								<c:otherwise>
									<a class="nav-link" id="v-pills-actions-tab" ${ share.family.id == null ? '' : 'data-toggle="pill"' } href="#v-pills-actions" role="tab" aria-controls="v-pills-actions" aria-selected="false"><spring:message code="shares.intervention.detail.step.6" /></a>
								</c:otherwise>
							</c:choose>
						  	
						  	<c:if test="${user.role.id == jspUtil.getRolId('ROLE_JEFE_PROJECTO') || user.role.id == jspUtil.getRolId('ROLE_SUPERVISOR_TECNICO') || user.role.id == jspUtil.getRolId('ROLE_ADMIN')}">
							  	<a class="nav-link" id="v-pills-call-tab" ${ share.family.id == null ? '' : 'data-toggle="pill"' } href="#v-pills-call" role="tab" aria-controls="v-pills-call" aria-selected="false"><spring:message code="shares.intervention.detail.step.4" /></a>
						  	</c:if>
							<hr class="w-100">
							<a class="nav-link" id="v-pills-inter-tab" ${ share.family.id == null ? '' : 'data-toggle="pill"' } href="#v-pills-inter" role="tab" aria-controls="v-pills-inter" aria-selected="false"><spring:message code="shares.intervention.detail.step.5" /></a>
						</div>
					</div>
					<div class="col-sm-12 col-md-9 h-100 bg-light">
						<div class="tab-content" id="v-pills-tabContent">
							<div class="tab-pane fade show active" id="v-pills-desc" role="tabpanel" aria-labelledby="v-pills-desc-tab">
								<form action="/shares/intervention/no-programmed/detail/${ share.id }/update" method="POST" enctype="multipart/form-data" class="needs-validation">
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label for="family" class="col-form-label"> <spring:message code="shares.intervention.create.family" /></label> 
												<select id="family" name="family" class="form-control" ${ share.family.id == null ? '' : 'disabled' } onchange="updateSubFamilySelect()" required>
													<option value="" ${ share.family.id == null ? 'selected' : '' }>
														<spring:message code="shares.intervention.create.family.placeholder" />
													</option>
													<c:forEach items="${families}" var="family">
														<option ${ share.family.id == family.id ? 'selected' : '' } value="${family.id}" data-info="${family.brand} ${family.model} ${family.enrollment}">
															<c:choose>
																<c:when test="${localeCode == 'es'}">
																	${family.nameES}
																</c:when>
																<c:otherwise>
																	${family.nameFR}
																</c:otherwise>
															</c:choose>
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										
										<div class="col-lg-6">
											<div class="form-group">
												<label for="subFamily" class="col-form-label"> <spring:message code="shares.intervention.create.sub.family" /></label> 
												<select id="subFamily" name="subFamily" class="form-control" ${ share.family.id == null ? '' : 'disabled' } onchange="updatePosibleForumTitle()" required>
													<c:choose>
														<c:when test="${ share.subFamily.id == null }">
															<option value="" selected="selected">
																<spring:message code="shares.intervention.create.sub.family.placeholder" />
															</option>
														</c:when>
														<c:otherwise>
															<option value="${ share.subFamily.id }" selected="selected">
																<c:choose>
																	<c:when test="${localeCode == 'es'}">
																		${ share.subFamily.nameES }
																	</c:when>
																	<c:otherwise>
																		${ share.subFamily.nameFR }
																	</c:otherwise>
																</c:choose>
															</option>
														</c:otherwise>
													</c:choose>																														
												</select>
											</div>
										</div>
									</div>
									
									<div class="row">												
										<div class="col-lg-6"> 
											<div class="form-group"> 
												<label for="files" class="col-form-label"> <spring:message code="shares.intervention.create.intervention.files" /></label>
												<input type="file" class="form-control" id="files" name="files" accept=".jpg, .jpeg, .png" multiple ${ share.family.id == null ? '' : 'disabled' }>
		 									</div>
		 								</div>
		 								
		 								<div class="col-md-6">
											<div class="form-group">
												<label for="operatorName" class="col-form-label"> 
													<spring:message code="shares.intervention.create.operator.name" />
												</label>
												<input id="operatorName" type="text" class="form-control" value="${share.user.name} ${share.user.surnames}" readonly />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col">
											<div class="form-group mb-0">
												<label for="faultDescInput" class="col-form-label"> 
													<spring:message code="shares.intervention.create.fault.desc" />
												</label>
												<spring:message code="shares.intervention.create.fault.desc.placeholder" var="placeholderInterventionDescription" />
												<textarea class="form-control"
													placeholder="${placeholderInterventionDescription}" id="faultDescInput"
													name="description" rows="6" style="resize: none" ${ share.family.id == null ? '' : 'disabled' } required>${ share.description }</textarea>
											</div>
										</div>
									</div>
									
									<c:choose>
										<c:when test="${empty share.family}">
										
											<div class="row mt-2">
												<div class="col text-right">
													<span id="forumTitleSpan" style="font-size: 12px; font-style: italic; text-transform: uppercase">-</span>
												</div>
											</div>
											<div class="row mt-2">
												<div class="col text-right">
													<button type="submit" class="btn btn-standard btn-sm movile-full"><spring:message code="save" /></button>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="row mt-2">
												<div class="col text-right">
													<a href="${postForumUrl}" target="_blank" style="font-size: 12px; font-style: italic"><spring:message code="shares.intervention.detail.forum.url" /></a>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</form>								
							</div>
							
							<div class="tab-pane fade" id="v-pills-actions" role="tabpanel" aria-labelledby="v-pills-actions-tab">
								<c:choose>
									<c:when test="${ share.state == 1 or share.state == 2 }">
										<div class="row">
											<div class="col">
												<span><spring:message code="shares.intervention.detail.init.intervention.${share.lastDiagnosis == null ? 0 : share.lastDiagnosis}" /></span>
											</div>
										</div>
										
										<form id="startProcessForm" action="/shares/intervention/no-programmed/detail/${ share.id }/init" method="POST">
										
											<input type="hidden" id="actionType" name="action" />
													
											<div class="form-group row mt-4">
												<label for="secondTechnical" class="col-md-12 col-lg-2 col-form-label"> <spring:message code="shares.intervention.create.technical.2" />:</label> 
												
												<div class="col-md-12 col-lg-4">
													<select id="secondTechnical" name="secondTechnical" class="form-control">
														<option value="" disabled selected="selected">
															<spring:message code="shares.intervention.create.technical.placeholder" />
														</option>
														<c:forEach items="${usersTeam}" var="technical">
															<c:if test="${ user.id != technical.userId }">
																<option value="${technical.userId}">
																	<spring:message code="${technical.name} ${technical.surnames}" />
																</option>
															</c:if>
														</c:forEach>
													</select>
												</div>
											</div>
													
											<div class="row mt-4 mb-2">
												<div class="col">	
													<c:choose>
														<c:when test="${share.lastDiagnosis == '1'}">
															<button id="initIntBtn" type="button" class="btn btn-standard movile-full mt-2" ${ userSigning == null && !havePrivileges ? 'disabled' : ''}><spring:message code="shares.intervention.detail.init.intervention.btn" /></button>
														</c:when>
														<c:when test="${share.lastDiagnosis == '2'}">
															<button id="initSegBtn" type="button" class="btn btn-standard movile-full mt-2" ${ userSigning == null && !havePrivileges ? 'disabled' : ''}><spring:message code="shares.intervention.detail.init.tracing.btn" /></button>
														</c:when>
														<c:otherwise>
															<button id="initDiaBtn" type="button" class="btn btn-standard movile-full mt-2" ${ (hasRole && userSigning != null) || (hasRole && havePrivileges) ? '' : 'disabled' }><spring:message code="shares.intervention.detail.init.diagnostic.btn" /></button>
														</c:otherwise>
													</c:choose>											
												
												</div>
											</div>
											
											<div class="row">
												<div class="col-sm-12 col-md-6">	
													<c:if test="${ hasRole == false }">
														<div class="alert alert-danger" style="font-size: 12px" role="alert">
															<spring:message code="share.detail.init.diag.role" />
														</div>
													</c:if>
												</div>
											</div>
											
											<div class="row">
												<div class="col-sm-12 col-md-6">	
													<c:if test="${ userSigning == null && !havePrivileges }">
														<div class="alert alert-danger" style="font-size: 12px" role="alert">
															<spring:message code="signing.page.not.enable" />
														</div>
													</c:if>
												</div>
											</div>
										</form>
									</c:when>
									<c:when test="${ share.state == 3 }">
										<jsp:include page="intervention-form.jsp">
											<jsp:param name="prefix" value="complete" />
										</jsp:include>
									</c:when>
									<c:otherwise>
										<div class="row">
											<div class="col text-center">
												<span class="font-italic"><spring:message code="shares.intervention.detail.closed.intervention" /></span>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							
							<div class="tab-pane fade" id="v-pills-call" role="tabpanel" aria-labelledby="v-pills-call-tab">
															
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="noticeDateInput" class="col-form-label"> 
												<spring:message code="shares.intervention.create.notice.date" />
											</label>
											<input id="noticeDateInput" type="datetime-local" class="form-control" value="${fn:replace(share.noticeDate, ' ', 'T')}" readonly />
										</div>
									</div>
									
									<div class="col-md-6">
										<div class="form-group">
											<label for="endDateInput" class="col-form-label"> 
												<spring:message code="shares.intervention.create.end.date" />
											</label>
											<input id="endDateInput" type="datetime-local" class="form-control" value="${fn:replace(share.endDate, ' ', 'T')}" readonly />
										</div>
									</div>
								</div>

								<c:if test="${empty share.endDate && share.state != 4}">
									<div class="row">
										<div class="col text-right">
											<form action="/shares/intervention/no-programmed/detail/${ share.id }/close" method="POST" onclick="showLoading()">
												<button type="submit" class="btn btn-danger btn-sm movile-full" ${ share.state == 2 ? '' : 'disabled' }><spring:message code="shares.intervention.detail.end.share" /></button>
											</form>
										</div>
									</div>
								</c:if>
							</div>

							<div class="tab-pane fade" id="v-pills-inter" role="tabpanel" aria-labelledby="v-pills-inter-tab">
								<div class="row">
									<div class="col">
										<div class="table-responsive">
											<table id="interventionsTable" class="table table-striped table-borderer dataTable w-100">
												<caption class="d-none">NO SONAR</caption>
												<thead>
													<tr>
														<th id="thId"><spring:message code="shares.intervention.table.id" /></th>
														<th id="thStartDate"><spring:message code="shares.intervention.create.fault.start.hour" /></th>
														<th id="thEndDate"><spring:message code="shares.intervention.create.fault.end.hour" /></th>
														<th id="thAction"><spring:message code="shares.intervention.table.action" /></th>
														<th id="thActions" class="all"><spring:message code="shares.displacement.table.actions" /></th>
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
	</div>
</div>

<jsp:include page="material-modal.jsp" />

<!-- MODAL -->
<div id="editInterventionModal" class="modal fade intervention-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5><spring:message code="shares.intervention.update.modal.title" /></h5>
				</div>
			</div>
			<div class="modal-body">
				<jsp:include page="intervention-form.jsp">
					<jsp:param name="prefix" value="update" />
				</jsp:include>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" id="updateCloseBtn" data-dismiss="modal" class="btn btn-sm">
							<spring:message code="back" />
						</button>
					</div>
					<div class="float-right">
						<button id="updateSaveBtn" type="button" class="btn btn-sm btn-success">
							<spring:message code="save" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<script>

	let modalTitle = $("#modalTitle");
	let actionType;

	let interventionToUpdate;
	let interventionsTable;
	let interventionForm;

	let editInterventionModal = $('#editInterventionModal');
	let endInterventionBtn = $('.intervention-complete-btn'), endInterventionAndBackBtn  = $('#endInterventionAndBackBtn');

	let pill = $('a[data-toggle="pill"]');

	let canvas, canvasOp;
	let signaturePad, signaturePadOp;

	let fullForm = ${ share.state == 3 && (intervention.action == 1 || intervention.action == 2) };

	$(document).ready(function () {

		endInterventionBtn.click(() => submitInterventionForm(${share.id}, null, 'create', false));
		endInterventionAndBackBtn.click(() => submitInterventionForm(${share.id}, null, 'create', true));

		pill.on('show.bs.tab', function(e) {
			let target = $(e.target).attr("href");
			if (target === '#v-pills-inter') {
				if (!interventionsTable) {
					loadInterventionsTable();
				}
			}
		});

		pill.on('shown.bs.tab', function(e) {
			let target = $(e.target).attr("href");
			if (target === '#v-pills-actions') {
				updateInterventionForm('create', ${intervention.action});

				if (fullForm) {
					preloadSignatures();
					loadMaterialsDataTable(false, null, null);
				}
			}
		});

		$('#initIntBtn').click(function () {
			startTechProcess(1);
		});

		$('#initDiaBtn').click(function () {
			startTechProcess(2);
		});

		$('#initSegBtn').click(function () {
			startTechProcess(3);
		});

		window.onresize = resizeCanvas;

		$('#clearSignature').click(function () {
			signaturePad.clear();
		});

		$('#clearSignatureOp').click(function () {
			signaturePadOp.clear();
		});

		function startTechProcess(type) {

			showLoading();

			$('#actionType').val(type);
			$('#startProcessForm').submit();
		}

		var started = $('#family').is(':disabled');

		if (started) {
			$('.nav-pills a[href="#v-pills-actions"]').tab('show');
		}

		editInterventionModal.on('shown.bs.modal', function () {
			preloadSignatures(interventionToUpdate.signature, interventionToUpdate.signatureOp);
		});

		editInterventionModal.on('hidden.bs.modal', function () {
			hideInterventionModal();
		});
	});

	function parseShareType(data) {
		if (data === 1) {
			return '<div class="badge badge-warning"> ${jspUtil.parseTagToText('shares.intervention.detail.inc')} </div>';
		} else if (data === 2) {
			return '<span class="badge badge-success"> ${jspUtil.parseTagToText('shares.intervention.detail.dia')} </span>';
		} else if (data === 3) {
			return '<span class="badge badge-info"> ${jspUtil.parseTagToText('shares.intervention.detail.tra')} </span>';
		}
		
		return '';
	}
	
	function getInterventionDetails() {
		let description = interventionForm.find('textarea[name="description"]').val();
		let clientName = interventionForm.find('input[name="clientName"]').val();
		let clientNotif = interventionForm.find('input[name="clientNotif"]').prop('checked');
		let equipmentHours = interventionForm.find('input[name="equipmentHours"]').val();
		let files = interventionForm.find('input[name="files"]').prop('files');
		let materialsFile = interventionForm.find('input[name="materialsFile"]').prop('files')[0];
		let materials = materialsTable ? materialsTable.rows().data().toArray() : [];

		let formData = new FormData();
		formData.append('description', description ? description : '');

		if (signaturePad && !signaturePad.isEmpty()) {
			formData.append('signature', signaturePad.toDataURL());
		}
		if (signaturePadOp && !signaturePadOp.isEmpty()) {
			formData.append('signatureOp', signaturePadOp.toDataURL());
		}
		if (clientName) {
			formData.append('clientName', clientName);
		}
		if (clientNotif) {
			formData.append('clientNotif', clientNotif);
		}
		if (materials.length > 0) {
			for (let i = 0; i < materials.length; i++) {
				if (materials[i].id) {
					formData.append('materials[' + i + '].id', materials[i].id);
				}
				formData.append('materials[' + i + '].units', materials[i].units);
				formData.append('materials[' + i + '].description', materials[i].description);
				formData.append('materials[' + i + '].reference', materials[i].reference);
			}
		}
		if (files.length > 0) {
			Array.from(files).forEach(file => {
				formData.append('files', file);
			});
		}
		if (materialsFile) {
			formData.append('materialsFile', materialsFile);
		}
		if (equipmentHours) {
			formData.append('equipmentHours', equipmentHours);
		}

		return formData;
	}

	function validateForm() {
		let description = interventionForm.find('textarea[name="description"]').val();
		return (!description);
	}

	function getFormData(form) {
	    let unIndexedArray = form.serializeArray();
		let indexedArray = {};

	    $.map(unIndexedArray, function(n){
	        indexedArray[n['name']] = n['value'];
	    });

	    return indexedArray;
	}

	function parseInterventionsActionButtons() {
		let tableRows = $('#interventionsTable tbody tr');
		
		tableRows.each(function() {

			let interventionId = $(this).attr('id');

			if (!interventionId) {
				return;
			}

			let data = interventionsTable.row($(this)).data();

			let orderId = data.iss_orderId;
			let materialsFileExt = data.iss_materialsFileExt;
			let interventionType = data.iss_action;

			let baseUrl = '/shares/intervention/no-programmed/detail/${share.id}/' + orderId + '/';

			let lastColumn = $(this).children().last();
			let emList = lastColumn.children();

			emList.each(function(index) {
				if (index === 0) {
					$(this).attr('onclick', 'openInterventionModal(\'${share.id}\', \'' + interventionId + '\', ' + interventionType + ')');
				} else if (index === 1) {
					$(this).wrap('<a href="' + baseUrl + 'pdf/" target="_blank"></a>');
				} else if (index === 2) {
					if (!materialsFileExt) { $(this).remove(); }
					$(this).wrap('<a href="' + baseUrl + 'materials/" target="_blank"></a>');
				} else if (index === 3) {
					$(this).wrap('<a href="' + baseUrl + 'materials/pdf" target="_blank"></a>');
				}
			});
		});
	}

	function updatePosibleForumTitle() {

		let family = $('#family');
		let familyStr = family.find(':selected').text();
		let familyDataInfo = family.find(':selected').attr('data-info');
		let subFamilyStr = $('#subFamily').find(':selected').text();

		let forumTitle = '${postForumTitle} ' + familyStr + ' ' + familyDataInfo + ' ' + subFamilyStr;
		
		$('#forumTitleSpan').text(forumTitle);
	}

	function updateSubFamilySelect() {

		let familyId = $('#family').val();

		if (familyId) {
			
			$.ajax({
				type: "GET",
				url: "/admin/families/" + familyId + "/subfamilies",
				success: function(msg) {
					msg = '<option disabled selected="selected"><spring:message code="shares.intervention.create.sub.family.placeholder" /></option>' + msg;
					$('#subFamily').html(msg);
				}
			});
			
		} else {
			$('#subFamily').html('<option disabled selected="selected"><spring:message code="shares.intervention.create.sub.family.placeholder" /></option>');
			$('#forumTitleSpan').text('-');
		}
	}

	function openInterventionModal(shareId, interventionId, interventionType) {
		const saveBtn = $('#updateSaveBtn');
		const url = '/shares/no-programmed/' + shareId + '/intervention/' + interventionId;

		axios.get(url).then((response) => {
			updateInterventionForm('edit', interventionType);

			interventionToUpdate = response.data;
			interventionForm.find('textarea[name="description"]').val(interventionToUpdate.description);
			interventionForm.find('input[name="equipmentHours"]').val(interventionToUpdate.equipmentHours);
			interventionForm.find('input[name="clientName"]').val(interventionToUpdate.clientName);

			if (interventionType === 1 || interventionType === 2) {
				loadMaterialsDataTable(true, shareId, interventionId);
			}

			editInterventionModal.modal('show');
		});

		saveBtn.click(() => submitInterventionForm(shareId, interventionId, 'edit', false));
	}

	function hideInterventionModal() {
		const saveBtn = $('#updateSaveBtn');
		saveBtn.off('click');

		interventionForm[0].reset();
	}

	function submitInterventionForm(shareId, interventionId, action, goBack) {
		if (validateForm()) {
			interventionForm.addClass('was-validated');
		} else {
			showLoading();
			interventionForm.removeClass('was-validated');

			const formData = getInterventionDetails();
			if (action === 'create')  {
				const url = '/shares/intervention/no-programmed/detail/' + shareId + '/end';
				axios.post(url, formData).then(() => {
					interventionForm.submit();
					if (goBack) { window.location.href = '/shares/intervention'; }
				}).catch(error => showNotify(error.responseText, 'danger')).finally(() => {
					resetForm();
					hideLoading();
				});
			} else if (action === 'edit') {
				const url = '/shares/no-programmed/' + shareId + '/intervention/' + interventionId;
				axios.put(url, formData).then(() => {
					interventionsTable.ajax.reload();
					showNotify(messages.shares.noprogrammed.intervention.update.success);
				}).catch(error => showNotify(error, 'danger')).finally(() => {
					resetForm();
					hideLoading();
					editInterventionModal.modal('hide');
				});
			}

			function resetForm() {
				$('#updateSaveBtn').off();
			}
		}
	}

	function preloadSignatures(signature, signatureOp) {
		if (signaturePad) {
			signaturePad.clear();
		}
		if (signaturePadOp) {
			signaturePadOp.clear();
		}

		canvas = interventionForm[0].querySelector('.signature-canvas');
		canvasOp = interventionForm[0].querySelector('.signature-op-canvas');

		resizeCanvas();

		signaturePad = new SignaturePad(canvas);
		if (typeof signature !== 'undefined' && signature !== '') {
			signaturePad.fromDataURL(signature);
		}

		signaturePadOp = new SignaturePad(canvasOp);
		if (typeof signatureOp !== 'undefined' && signatureOp !== '') {
			signaturePadOp.fromDataURL(signatureOp);
		}


	}

	function resizeCanvas() {
		let ratio = Math.max(window.devicePixelRatio || 1, 1);

		if (canvas) {
			canvas.width = canvas.offsetWidth * ratio;
			canvas.height = canvas.offsetHeight * ratio;
			canvas.getContext("2d").scale(ratio, ratio);
		}

		if (canvasOp) {
			canvasOp.width = canvasOp.offsetWidth * ratio;
			canvasOp.height = canvasOp.offsetHeight * ratio;
			canvasOp.getContext("2d").scale(ratio, ratio);
		}
	}

	function loadMaterialsDataTable(serverSide, shareId, interventionId) {
		let endpoint = '/shares/no-programmed/' + shareId + '/intervention/' + interventionId + '/materials';
		if (materialsTable) {
			materialsTable.destroy();
		}
		materialsTable = interventionForm.find('.materials-table').DataTable({
			lengthChange: false,
			responsive: true,
			processing: true,
			pageLength: 3,
			serverSide: serverSide,
			ajax: serverSide ? { url: endpoint, dataSrc: function (res) {
				res.recordsTotal = res.length;
				res.recordsFiltered = res.length;
				return res;
			}} : undefined,
			language: {
				url: '/ui/static/lang/datatables/${locale}.json'
			},
			rowId: 'id',
			columns: [
				{ data: 'description' },
				{ data: 'units' },
				{ data: 'reference' },
				{ data: null }
			],
			columnDefs: [
				{ className: "text-center", targets: "_all" },
				{
					defaultContent: "${tableActionButtons}",
					width: "170px",
					orderable: false,
					targets: -1
				}
			],
			dom: "<'top'>rt<'bottom'p><'clear'>",
			drawCallback: function() {
				parseMaterialsActionButtons();
			},
			initComplete: function() {
				this.api().settings()[0].oFeatures.bServerSide = false;
			}
		});
	}

	function updateInterventionForm(action, type) {
		let visibilityId = $('.visibility-id');

		interventionForm = $(action === 'create' ? '#completeInterventionForm' : '#updateInterventionForm');

		action === 'create' ? endInterventionBtn.show() : endInterventionBtn.hide();
		type === 1 || type === 2 ? visibilityId.show() : visibilityId.hide();
	}

	function loadInterventionsTable() {
		interventionsTable = $('#interventionsTable').DataTable({
			"lengthChange": false,
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"ajax": {
				url: "/shares/intervention/no-programmed/detail/${share.id}/dt",
				dataSrc: 'listOfDataObjects'
			},
			"rowId": "iss_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[1, "desc"]],
			"columns": [
				{"data": "iss_orderId"},
				{"data": "iss_startDate"},
				{"data": "iss_endDate"},
				{"data": "iss_action"},
				{"data": null}
			],
			"columnDefs": [{
				"className": "text-center",
				"targets": "_all"
			},
				{
					"render": function (data) {
						if (!data) {
							return "-";
						}
						return moment(data).format('DD/MM/YYYY HH:mm');
					},
					"targets": [1, 2]
				},
				{
					"render": function (data) {
						return parseShareType(data);
					},
					"targets": 3
				},
				{
					"defaultContent": "${interventionTableActionButtons}",
					"orderable": false,
					"targets": -1
				}],
			"dom": "<'top'i>rt<'bottom'p><'clear'>",
			"drawCallback": function () {
				parseInterventionsActionButtons();
			}
		});
	}

</script>