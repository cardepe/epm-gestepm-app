<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>

.form-control[readonly] {
	background-color: #fff;
}

em[disabled] {
	cursor: not-allowed !important;
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="shares.intervention.title" /></h1>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>

<div class="content huge-page">
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
										<spring:message code="shares.intervention.sub.title" />
									</div>
								</div>
							</div>

							<div class="row mt-4">
								<div class="col">
									<div class="form-group">
										<select id="projectDropdown" class="form-control input" name="project" required ${ havePrivileges ? '' : 'disabled' }>
											<option></option>
											<c:forEach items="${projects}" var="project">
												<option value="${project.id}" data-info="${project.station};${project.customerEmail}" ${ userSigning.project.id == project.id ? 'selected' : '' }>
													<spring:message code="${project.name}" />
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col">
									<div class="form-group">
										<button id="create" type="button" class="btn btn-success btn-sm w-100">
											<spring:message code="shares.intervention.create.btn" />
										</button>
									</div>
								</div>
							</div>
							
							<c:if test="${ userSigning == null && !havePrivileges }">
								<div class="alert alert-danger" style="font-size: 12px" role="alert">
									<spring:message code="signing.page.not.enable" />
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col">
					<div class="card">
						<div class="card-body" data-table-filter="form">
							
							<span class="title"><spring:message code="filters" /></span>

							<div class="form-group mt-2 mb-2">
								<input id="shareIdInput" data-table-filter="id" type="number" class="form-control input" placeholder="<spring:message code="shares.intervention.table.id" />" />
							</div>

							<div class="form-group mt-2 mb-2">
								<select id="sharesTypeDropdown" data-table-filter="type" class="form-control input">
									<option></option>
									<c:forEach items="${sharesType}" var="shareType">
										<option value="${shareType}"><spring:message code="${shareType}" /></option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group mb-2">
								<select id="projectFilterDropdown" data-table-filter="project" class="form-control input">
									<option></option>
									<c:forEach items="${projects}" var="project">
										<option value="${project.id}" data-info="${project.station}"><spring:message code="${project.name}" /></option>
									</c:forEach>
								</select>
							</div>

			
							<div class="form-group mb-2">
								<select id="stateFilterDropdown" data-table-filter="progress" class="form-control input">
									<option></option>
									<option value="1"><spring:message code="shares.intervention.table.state.open" /></option>
									<option value="0"><spring:message code="shares.intervention.table.state.close" /></option>
								</select>
							</div>

							<sec:authorize access="hasAuthority('ROLE_JEFE_PROYECTO') or hasAuthority('ROLE_RRHH') or hasAuthority('ROLE_ADMIN')">
								<div class="form-group">
									<select id="usersDropdown" data-table-filter="user" class="form-control input" name="userDTO">
										<option></option>
										<c:forEach items="${usersDTO}" var="userDTO">
											<option value="${userDTO.userId}" data-info="${userDTO}"><spring:message code="${userDTO.name} ${userDTO.surnames}" /></option>
										</c:forEach>
									</select>
								</div>
							</sec:authorize>
							
							<div class="row mt-2">
								<div class="col">
									<button type="button" class="btn btn-outline-secondary btn-sm w-100" style="font-size: 12px" onclick="resetTable()">
										<em class="fas fa-sync"></em> <spring:message code="clear" />
									</button>
								</div>
								
								<div class="col">
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="filterShares()">
										<em class="fas fa-search"></em> <spring:message code="search" />
									</button>
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
									<button type="button" class="btn btn-outline-success btn-sm w-100" style="font-size: 12px" onclick="exportShares()">
										<em class="fas fa-download"></em> <spring:message code="shares.export.zip.btn" />
									</button>
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
						<spring:message code="shares.intervention.title" />
					</div>
					
					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="shares.intervention.table.id" /></th>
									<th id="thProjectId"><spring:message code="shares.intervention.table.name" /></th>
									<th id="thForumTitle"><spring:message code="shares.intervention.table.forum.title" /></th>
									<th id="thStartDate"><spring:message code="shares.intervention.table.start.date" /></th>
									<th id="thState"><spring:message code="shares.intervention.table.state" /></th>
									<th id="thShareType"><spring:message code="shares.intervention.table.type" /></th>
									<th id="thActions" class="all"><spring:message code="shares.intervention.table.actions" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<input id="userLanguageInput" value="${ language }" type="hidden" />

<form id="createShareForm">
	<input id="shareProjectId" name="projectId" type="hidden" required>
	<input id="shareClientNotif" name="clientNotif" type="hidden" required>
</form>

<!-- Used to update signing in prog view -->
<form id="updateProgForm">
	<input id="updateSharePrSignature" type="hidden" required>
	<input id="updateSharePrSignatureOp" type="hidden" required>
</form>

<!-- MODAL -->
<div id="createSelectorModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createSelectorForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.displacement.create.btn" />
						</h5>
					</div>
				</div>
				<div class="modal-body">		
					<div class="row">
						<div class="col-md-12 col-lg-3">
							<button id="btnConstructorShare" type="button" class="btn btn-sm btn-info w-100"><spring:message code="shares.displacement.create.construction.btn" /></button>
						</div>
						
						<div class="col-md-12 col-lg-3">
							<button id="btnProgrammedShare" type="button" class="btn btn-sm btn-info w-100"><spring:message code="shares.displacement.create.programmed.btn" /></button>
						</div>
						
						<div class="col-md-12 col-lg-3">
							<button id="btnNoProgrammedShare" type="button" class="btn btn-sm btn-info w-100"><spring:message code="shares.displacement.create.no.programmed.btn" /></button>
						</div>
						
						<div class="col-md-12 col-lg-3">
							<button id="btnWorkShare" type="button" class="btn btn-sm btn-info w-100"><spring:message code="shares.displacement.create.others.btn" /></button>
						</div>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- MODAL -->
<div id="clientNotifModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-title">
					<h5 id="modalTitle">
						<spring:message code="notif" />
					</h5>
				</div>
			</div>
			<div class="modal-body">		
				<div class="row">
					<div class="col">
						<span><spring:message code="shares.create.client.notif" /></span>
					</div>
				</div>
			</div>
			<div class="modal-footer clearfix">
				<div class="w-100">
					<div class="float-left">
						<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
					</div>
					
					<div class="float-right">
						<button id="btnRefuseNotif" type="button" class="btn btn-sm btn-danger"><spring:message code="no" /></button>
						<button id="btnConfirmNotif" type="button" class="btn btn-sm btn-success"><spring:message code="yes" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- MODAL -->
<div id="createConstModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createConstForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.construction.close.modal.title" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="startDateConstInput" class="col-form-label"><spring:message code="shares.construction.start.date" /></label>
							<input type="datetime-local" class="form-control" id="startDateConstInput" name="startDate" disabled>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="endDateConstInput" class="col-form-label"><spring:message code="shares.construction.end.date" /></label>
							<input type="datetime-local" class="form-control" id="endDateConstInput" name="endDate">
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observationsConstInput" class="col-form-label"><spring:message code="shares.construction.description" /></label>
								<textarea id="observationsConstInput" name="observations" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<input type="file" class="form-control-file" id="filesConstInput"
									name="files" accept="image/x-png,image/gif,image/jpeg" onchange="setConstFiles(event)"
									lang="${locale}" multiple>
							</div>
						</div>
						
						<div class="col text-right">
							<div class="form-group">
								<label><input type="checkbox" id="clientNotifConstInput" name="clientNotif"> <spring:message code="shares.intervention.create.share.client.notif.mail" /></label>
							</div>
						</div>
					</div>
					
					<div class="row mb-2">
						<div class="col-sm-12 col-md-6 mt-2">
							<input name="signatureOp" id="constSignaturePadOp" type="hidden"/>

					  		<div id="constSignature-pad-op" class="signature-pad">
						    	<div class="signature-pad--body">
						      		<canvas></canvas>
						    	</div>
						    	<div class="signature-pad--footer">
						      		<div class="description"><spring:message code="shares.intervention.create.signature.two" /></div>
					      			<div class="signature-pad--actions">
					        			<div>
								          	<button id="constClearSignatureOp" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
					        			</div>
					        			<div></div>
					      			</div>
					    		</div>
					  		</div>
						</div>
					</div>
					
					<input type="hidden" id="idConstInput" name="id" required />
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
						<div class="float-right">
							<button id="createConstBtn" type="button" class="btn btn-sm btn-success"><spring:message code="finish" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- MODAL -->
<div id="createProgModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createProgForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.programmed.close.modal.title" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="startDateProgInput" class="col-form-label"><spring:message code="shares.construction.start.date" /></label>
							<input type="datetime-local" class="form-control" id="startDateProgInput" name="startDate" disabled>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="endDateProgInput" class="col-form-label"><spring:message code="shares.construction.end.date" /></label>
							<input type="datetime-local" class="form-control" id="endDateProgInput" name="endDate">
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="secondTechnicalInput" class="col-form-label"><spring:message code="shares.intervention.create.technical.2" /></label>
							<select id="secondTechnicalInput" name="secondTechnical" class="form-control"></select>
						</div>
					</div>

					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observationsProgInput" class="col-form-label"><spring:message code="shares.construction.description" /></label>
								<textarea id="observationsProgInput" name="observations" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
					
					<div class="row mt-2">
						<div class="col-lg-6">
							<div class="form-group">
								<input type="file" class="form-control-file" id="filesProgInput"
									name="files" accept="image/x-png,image/gif,image/jpeg" onchange="setProgFiles(event)"
									lang="${locale}" multiple>
							</div>
						</div>

						<div class="col-lg-6 text-right">
							<div class="form-group">
								<label><input type="checkbox" id="clientNotifProgInput" name="clientNotif"> <spring:message code="shares.intervention.create.share.client.notif.mail" /></label>
							</div>
						</div>
					</div>
					
					<div class="row mb-2">
						<div class="col-sm-12 col-md-6 mt-2">
							<input name="signature" id="progSignaturePad" type="hidden"/>
							<input name="signatureOp" id="progSignaturePadOp" type="hidden"/>

							<div id="progSignature-pad" class="signature-pad">
						    	<div class="signature-pad--body">
						      		<canvas></canvas>
						    	</div>
						    	<div class="signature-pad--footer">
						      		<div class="description"><spring:message code="shares.intervention.create.signature" /></div>
					      			<div class="signature-pad--actions">
					        			<div>
								          	<button id="progClearSignature" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
					        			</div>
					        			<div></div>
					      			</div>
					    		</div>
					  		</div>
					  	</div>
					  	
					  	<div class="col-sm-12 col-md-6 mt-2">
					  		<div id="progSignature-pad-op" class="signature-pad">
						    	<div class="signature-pad--body">
						      		<canvas></canvas>
						    	</div>
						    	<div class="signature-pad--footer">
						      		<div class="description"><spring:message code="shares.intervention.create.signature.two" /></div>
					      			<div class="signature-pad--actions">
					        			<div>
								          	<button id="progClearSignatureOp" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
					        			</div>
					        			<div></div>
					      			</div>
					    		</div>
					  		</div>
						</div>
					</div>
					
					<input type="hidden" id="idProgInput" name="id" required />
					<input type="hidden" id="projectIdProgInput" name="id" required />
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
						<div class="float-right">
							<button id="createNoPrShareBtn" type="button" class="btn btn-sm btn-info"><spring:message code="shares.intervention.create.nopr.title" /></button>
							<button id="createProgBtn" type="button" class="btn btn-sm btn-success"><spring:message code="finish" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- CREATE WORK MODAL -->
<div id="createWorkModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createWorkForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.work.close.modal.title" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="startDateWorkInput" class="col-form-label"><spring:message code="shares.work.start.date" /></label>
							<input type="datetime-local" class="form-control" id="startDateWorkInput" name="startDate" disabled>
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="endDateWorkInput" class="col-form-label"><spring:message code="shares.work.end.date" /></label>
							<input type="datetime-local" class="form-control" id="endDateWorkInput" name="endDate">
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observationsWorkInput" class="col-form-label"><spring:message code="shares.work.description" /></label>
								<textarea id="observationsWorkInput" name="observations" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<input type="file" class="form-control-file" id="filesWorkInput"
									name="files" accept="image/x-png,image/gif,image/jpeg" onchange="setFiles(event)"
									lang="${locale}" multiple>
							</div>
						</div>

						<div class="col-lg-6 text-right">
							<div class="form-group">
								<label><input type="checkbox" id="clientNotifWorkInput" name="clientNotif"> <spring:message code="shares.intervention.create.share.client.notif.mail" arguments="${clientMail}" /></label>
							</div>
						</div>
					</div>
					
					<div class="row mb-2">
						<div class="col-sm-12 col-md-6 mt-2">
							<input name="signatureOp" id="workSignaturePadOp" type="hidden"/>

					  		<div id="workSignature-pad-op" class="signature-pad">
						    	<div class="signature-pad--body">
						      		<canvas></canvas>
						    	</div>
						    	<div class="signature-pad--footer">
						      		<div class="description"><spring:message code="shares.intervention.create.signature.two" /></div>
					      			<div class="signature-pad--actions">
					        			<div>
								          	<button id="workClearSignatureOp" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
					        			</div>
					        			<div></div>
					      			</div>
					    		</div>
					  		</div>
						</div>
					</div>
					
					<input type="hidden" id="idWorkInput" name="id" required />
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="cerrar" /></button>
						</div>
						<div class="float-right">
							<button id="createWorkBtn" type="button" class="btn btn-sm btn-success"><spring:message code="finish" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /CREATE WORK MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>

	var $=jQuery.noConflict();
	
	var filesConstInput;
	var filesProgInput;
	var filesWorkInput;

	var dispShareId;
	var shareSelectedType;

	var offset;
	var limit;
	
	$(document).ready(function() {

		var progWrapper = document.getElementById("progSignature-pad");
		var progWrapperOp = document.getElementById("progSignature-pad-op");

		var constWrapperOp = document.getElementById("constSignature-pad-op");

		var workWrapperOp = document.getElementById("workSignature-pad-op");

		var mrWrapperOp = document.getElementById("mrSignature-pad-op");

		if (progWrapper && progWrapperOp) {
			
			var progCanvas = progWrapper.querySelector("canvas");
			var progCanvasOp = progWrapperOp.querySelector("canvas");
			
			var progSignaturePad = new SignaturePad(progCanvas, {
				backgroundColor : 'rgb(255, 255, 255)'
			});
	
			var progSignaturePadOp = new SignaturePad(progCanvasOp, {
				backgroundColor : 'rgb(255, 255, 255)'
			});
	
			function progResizeCanvas(image1, image2) {
	
				var ratio = Math.max(window.devicePixelRatio || 1, 1);
	
				progCanvas.width = progCanvas.offsetWidth * ratio;
				progCanvas.height = progCanvas.offsetHeight * ratio;
				progCanvas.getContext("2d").scale(ratio, ratio);
	
				progCanvasOp.width = progCanvasOp.offsetWidth * ratio;
				progCanvasOp.height = progCanvasOp.offsetHeight * ratio;
				progCanvasOp.getContext("2d").scale(ratio, ratio);
	
				// signaturePad.clear();

				if (image1) {
					progSignaturePad.fromDataURL(image1);
				}

				if (image2) {
					progSignaturePadOp.fromDataURL(image2);
				}
			}

			window.onresize = progResizeCanvas;
	
			$('#progClearSignature').click(function () {
				progSignaturePad.clear();
			});
	
			$('#progClearSignatureOp').click(function () {
				progSignaturePadOp.clear();
			});
	
			$("#createProgModal").on('shown.bs.modal', function(){

				var image1 = $('#updateSharePrSignature').val();
				var image2 = $('#updateSharePrSignatureOp').val();
				
				progResizeCanvas(image1, image2);
			});
		}

		if (constWrapperOp) {

			var constCanvasOp = constWrapperOp.querySelector("canvas");

			var constSignaturePadOp = new SignaturePad(constCanvasOp, {
				backgroundColor : 'rgb(255, 255, 255)'
			});
	
			function constResizeCanvas() {
	
				var ratio = Math.max(window.devicePixelRatio || 1, 1);
	
				constCanvasOp.width = constCanvasOp.offsetWidth * ratio;
				constCanvasOp.height = constCanvasOp.offsetHeight * ratio;
				constCanvasOp.getContext("2d").scale(ratio, ratio);
			}

			window.onresize = constResizeCanvas;
	
			$('#constClearSignatureOp').click(function () {
				constSignaturePadOp.clear();
			});
	
			$("#createConstModal").on('shown.bs.modal', function(){
				constResizeCanvas();
			});
		}

		if (workWrapperOp) {

			var workCanvasOp = workWrapperOp.querySelector("canvas");

			var workSignaturePadOp = new SignaturePad(workCanvasOp, {
				backgroundColor : 'rgb(255, 255, 255)'
			});
	
			function workResizeCanvas() {
	
				var ratio = Math.max(window.devicePixelRatio || 1, 1);
	
				workCanvasOp.width = workCanvasOp.offsetWidth * ratio;
				workCanvasOp.height = workCanvasOp.offsetHeight * ratio;
				workCanvasOp.getContext("2d").scale(ratio, ratio);
			}

			window.onresize = workResizeCanvas;
	
			$('#workClearSignatureOp').click(function () {
				workSignaturePadOp.clear();
			});
	
			$("#createWorkModal").on('shown.bs.modal', function(){
				workResizeCanvas();
			});
		}

		if (mrWrapperOp) {

			var mrCanvasOp = mrWrapperOp.querySelector("canvas");

			var mrSignaturePadOp = new SignaturePad(mrCanvasOp, {
				backgroundColor : 'rgb(255, 255, 255)'
			});
	
			function mrResizeCanvas() {
	
				var ratio = Math.max(window.devicePixelRatio || 1, 1);
	
				mrCanvasOp.width = mrCanvasOp.offsetWidth * ratio;
				mrCanvasOp.height = mrCanvasOp.offsetHeight * ratio;
				mrCanvasOp.getContext("2d").scale(ratio, ratio);
			}

			window.onresize = mrResizeCanvas;
	
			$('#mrClearSignatureOp').click(function () {
				mrSignaturePadOp.clear();
			});
		}

		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#projectFilterDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#sharesTypeDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('shares.type.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#stateFilterDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('shares.state.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		$('#usersDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('user.selectable')}",
			dropdownCssClass: 'selectStyle'
		});

		/* End Select 2 */

		$('#create').click(function () {

			if (!$('#projectDropdown').val()) {
				return alert("${jspUtil.parseTagToText('share.create.alert')}");
			}
			
			$('#createSelectorModal').modal('show');
		});

		/* INITIALIZING SHARE */
		$('#btnConstructorShare').click(function() {
			
			shareSelectedType = 'cs';
			createConstructionShare();
			$('#createSelectorModal').modal('hide');
		});

		$('#btnProgrammedShare').click(function() {

			shareSelectedType = 'ips';
			
// 			var dropdownInfo = $('#projectDropdown').find(':selected').attr('data-info');
// 			var customerEmail = dropdownInfo.split(';')[1];

// 			if (customerEmail && shareSelectedType == 'ips') {
// 				$('#clientNotifModal').modal('show');
// 			} else {
				createProgrammedShare(false);
// 			}
			
			$('#createSelectorModal').modal('hide');
		});

		$('#btnNoProgrammedShare').click(function () {
			const userId = ${ user.id };
			const projectId = $('#projectDropdown').val();
			createNoProgrammedShare(userId, projectId);

			$('#createSelectorModal').modal('hide');
		});

		$('#btnWorkShare').click(function () {
			shareSelectedType = 'ws';
			createWorkShare();
			$('#createSelectorModal').modal('hide');
		});
		/* END INITIALIZE SHARE */

		$('#btnRefuseNotif').click(function() {

			if (shareSelectedType == 'ips') {
				createProgrammedShare(false);
			}

			$('#clientNotifModal').modal('hide');
		});

		$('#btnConfirmNotif').click(function() {

			if (shareSelectedType == 'ips') {
				createProgrammedShare(true);
			}

			$('#clientNotifModal').modal('hide');
		});

		function createConstructionShare() {

			showLoading();
			
			var projectId = $('#projectDropdown').val();

			$('#shareProjectId').val(projectId);

			$.ajax({
				type: "POST",
				url: "/shares/intervention/construction/create",
				data: $('#createShareForm').serialize(),
				success: function(data) {

					dispShareId = null; // reset
					
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(data.msg, 'success');
					
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});
		}

		function createProgrammedShare(checkedClientNotif) {

			showLoading();
			
			var projectId = $('#projectDropdown').val();
			
			$('#shareProjectId').val(projectId);

			if (checkedClientNotif) {
				$('#shareClientNotif').val(true);
			} else {
				$('#shareClientNotif').val(false);
			}

			$.ajax({
				type: "POST",
				url: "/shares/intervention/programmed/create",
				data: $('#createShareForm').serialize(),
				success: function(msg) {
					
					dispShareId = null; // reset
					
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
					
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});
		}

		function createWorkShare() {

			showLoading();
			
			var projectId = $('#projectDropdown').val();
			
			$('#shareProjectId').val(projectId);

			$.ajax({
				type: "POST",
				url: "/shares/work/create",
				data: $('#createShareForm').serialize(),
				success: function(msg) {
					
					dispShareId = null; // reset
					
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
					
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});
		}

		$('#createNoPrShareBtn').click(function() {

			showLoading();
			
			if (progSignaturePad) {
				var progSignature = document.getElementById('progSignaturePad');
				progSignature.value = progSignaturePad.toDataURL();
			}

			if (progSignaturePadOp) {
				var progSignatureOp = document.getElementById('progSignaturePadOp');
				progSignatureOp.value = progSignaturePadOp.toDataURL();
			}

			var data = new FormData();
			data.append('id', $('#idProgInput').val());
			data.append('startDate', $('#startDateProgInput').val());
			data.append('observations', $('#observationsProgInput').val());
			data.append('clientNotif', $('#clientNotifProgInput').prop('checked') ? true : false);
			data.append('signature', $('#progSignaturePad').val());
			data.append('signatureOp', $('#progSignaturePadOp').val());
			
			if (filesProgInput) {
				for (var file of filesProgInput) { 
					data.append('files', file);
				}
			}

			$.ajax({
				type: "POST",
				url: "/shares/intervention/programmed/update",
				enctype: 'multipart/form-data',
				processData: false,
		        contentType: false,
		        cache: false,
				data: data,
				success: function(msg) {
					const userId = ${ user.id };
					const projectId = $('#projectIdProgInput').val();
					createNoProgrammedShare(userId, projectId);
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			// redirect
			$('#createProgModal').modal('hide');
		});
		
		$('#createConstBtn').click(function () {

			showLoading();
			
			if (constSignaturePadOp) {
				var constSignatureOp = document.getElementById('constSignaturePadOp');
				constSignatureOp.value = constSignaturePadOp.toDataURL();
			}

			var data = new FormData();
			data.append('id', $('#idConstInput').val());
			data.append('startDate', $('#startDateConstInput').val());
			data.append('endDate', $('#endDateConstInput').val());
			data.append('observations', $('#observationsConstInput').val());
			data.append('clientNotif', $('#clientNotifConstInput').prop('checked') ? true : false);
			data.append('signatureOp', $('#constSignaturePadOp').val());
			
			if (filesConstInput) {
				for (var file of filesConstInput) { 
					data.append('files', file);
				}
			}
			
			$.ajax({
				type: "POST",
				url: "/shares/intervention/construction/finish",
				// data: $('#createConstForm').serialize(),
				enctype: 'multipart/form-data',
				processData: false,
		        contentType: false,
		        cache: false,
				data: data,
				success: function(msg) {
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createConstModal').modal('hide');
		});

		$('#createProgBtn').click(function () {

			showLoading();
			
			if (progSignaturePad) {
				var progSignature = document.getElementById('progSignaturePad');
				progSignature.value = progSignaturePad.toDataURL();
			}

			if (progSignaturePadOp) {
				var progSignatureOp = document.getElementById('progSignaturePadOp');
				progSignatureOp.value = progSignaturePadOp.toDataURL();
			}

			var data = new FormData();
			data.append('id', $('#idProgInput').val());
			data.append('startDate', $('#startDateProgInput').val());
			data.append('endDate', $('#endDateProgInput').val());

			if ($('#secondTechnicalInput').val()) {
				data.append('secondTechnical', $('#secondTechnicalInput').val());
			}

			data.append('observations', $('#observationsProgInput').val());
			data.append('clientNotif', $('#clientNotifProgInput').prop('checked') ? true : false);
			data.append('signature', $('#progSignaturePad').val());
			data.append('signatureOp', $('#progSignaturePadOp').val());
			
			if (filesProgInput) {
				for (var file of filesProgInput) { 
					data.append('files', file);
				}
			}
			
			$.ajax({
				type: "POST",
				url: "/shares/intervention/programmed/finish",
				// data: $('#createProgForm').serialize(),
				enctype: 'multipart/form-data',
				processData: false,
		        contentType: false,
		        cache: false,
				data: data,
				success: function(msg) {
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createProgModal').modal('hide');
		});

		$('#createWorkBtn').click(function () {

			showLoading();
			
			if (workSignaturePadOp) {
				var workSignatureOp = document.getElementById('workSignaturePadOp');
				workSignatureOp.value = workSignaturePadOp.toDataURL();
			}
			
			var data = new FormData();
			data.append('id', $('#idWorkInput').val());
			data.append('startDate', $('#startDateWorkInput').val());
			data.append('endDate', $('#endDateWorkInput').val());
			data.append('observations', $('#observationsWorkInput').val());
			data.append('clientNotif', $('#clientNotifWorkInput').prop('checked') ? true : false);
			data.append('signatureOp', $('#workSignaturePadOp').val());
			
			if (filesWorkInput) {
				for (var file of filesWorkInput) { 
					data.append('files', file);
				}
			}
			
			$.ajax({
				type: "POST",
				url: "/shares/work/finish",
				enctype: 'multipart/form-data',
				processData: false,
		        contentType: false,
		        cache: false,
				data: data,
				success: function(msg) {
					$('#dTable').DataTable().ajax.reload();
					hideLoading();
					showNotify(msg, 'success');
				},
				error: function(e) {
					hideLoading();
					showNotify(e.responseText, 'danger');
				}
			});

			$('#createWorkModal').modal('hide');
		});

		/* On Close Modal */		
		$('#createWorkModal').on('hidden.bs.modal', function (e) {
			  $(this)
			    .find("input,textarea,select")
			       .val('')
			       .end()
			    .find("input[type=checkbox], input[type=radio]")
			       .prop("checked", "")
			       .end();
		});

		$('#checkMaterialRequiredModal').on('hidden.bs.modal', function (e) {
			
			$('#checkMaterialContentSpanError').addClass("d-none");
		});
	});

	function setFiles(event) {
		filesWorkInput = event.target && event.target.files;
	}

	function setConstFiles(event) {
		filesConstInput = event.target && event.target.files;
	}
	
	function setProgFiles(event) {
		filesProgInput = event.target && event.target.files;
	}

	function getIpsShare(id) {
		return $.ajax({
		    url: '/shares/intervention/programmed/' + id,
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

	async function closeShare(id, shareType, startDate) {

		var startDateValue = moment(startDate).format().slice(0,19);
		
		if (shareType === 'cs') {

			$('#idConstInput').val(id);
			$('#startDateConstInput').val(startDateValue);
			$('#endDateConstInput').val(moment().format().slice(0,19));
			
			$('#createConstModal').modal('show');
			
		} else if (shareType === 'ips') {

			var share = await getIpsShare(id);
			var members = await getProjectMembers(share.projectId);

			var selectInputMembers = convertMembersToHtml(members);

			$('#idProgInput').val(id);
			$('#projectIdProgInput').val(share.projectId);
			$('#startDateProgInput').val(startDateValue);
			$('#endDateProgInput').val(moment().format().slice(0,19));
			$('#secondTechnicalInput').html(selectInputMembers);

			$('#observationsProgInput').val(share.observations);

			$('#updateSharePrSignature').val(share.signature);
			$('#updateSharePrSignatureOp').val(share.signatureOp);

			$('#createProgModal').modal('show');
			
		} else if (shareType === 'ws') {

			$('#idWorkInput').val(id);
			$('#startDateWorkInput').val(startDateValue);
			$('#endDateWorkInput').val(moment().format().slice(0,19));
						
			$('#createWorkModal').modal('show');
		}
	}

	function resetTable() {
		const filterForm = document.querySelector('[data-table-filter="form"]');
		const inputsNumber = filterForm.querySelectorAll('input[type="number"]');
		const inputsSelect = filterForm.querySelectorAll('select');

		inputsNumber.forEach(input => {$(input).val(''); })
		inputsSelect.forEach(input => {$(input).val('').trigger('change'); })

		dTable.ajax.url('/shares/intervention/dt').load();
		window.history.replaceState({ }, '', '/shares/intervention');
	}

	function exportShares() {

		let url = '/shares/intervention/files';

		const id = $('#shareIdInput').val();
		const type = $('#sharesTypeDropdown').val();
		const project = $('#projectFilterDropdown').val();
		const progress = $('#stateFilterDropdown').val();
		let user = $('#usersDropdown').val();

		if (user === undefined) {
			user = '';
		}

		url += '?id=' + id + '&type=' + type + '&project=' + project + '&progress=' + progress + '&user=' + user;
		url += '&offset=' + offset + '&limit=' + limit;

		window.open(url, '_blank').focus();
	}

	function deleteFault(shareId, shareType) {

		var ok = confirm("${jspUtil.parseTagToText('share.default.delete.alert')}");

		if (ok) {

			var restUrl = '';

			if (shareType === 'cs') {
				restUrl = '/shares/intervention/construction/delete/' + shareId;
			} else if (shareType === 'is') {
				restUrl = '/shares/intervention/no-programmed/delete/' + shareId;
			} else if (shareType === 'ips') {
				restUrl = '/shares/intervention/programmed/delete/' + shareId;
			} else if (shareType === 'ws') {
				restUrl = '/shares/work/' + shareId;
			}			

			$.ajax({
				type: "DELETE",
				url: restUrl,
				success: function(msg) {
					$('#dTable').DataTable().ajax.reload();
					showNotify(msg, 'success');
				},
				error: function(e) {
					showNotify(e.responseText, 'danger');
				}
			});
		}
	}

	function getProjectMembers(projectId) {

		let result;

		$.ajax({
			url: "/projects/" + projectId + "/members",
			type: "get",
			dataType: "json",
			async: false,
			success: function(data) {
				result = data;
			}
		});

		return result;
	}

	function convertMembersToHtml(data) {

		let html = '<option value="" disabled selected="selected"><spring:message code="shares.intervention.create.technical.placeholder" /></option>';

		data.forEach(function(element, i) {
			html += '<option value="' + element.userId + '">' + element.name + ' ' + element.surnames + '</option>';
		});

		return html;
	}

	function generateDataTable() {
		return $('#dTable').DataTable({
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"lengthMenu": [ 10, 25, 50, 75, 100 ],
			"ajax": "/shares/intervention/dt",
			"rowId": "st_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"columns": [
				{ "data": "st_id" },
				{ "data": "st_projectId" },
				{ "data": "st_forumTitle", "defaultContent": "-" },
				{ "data": "st_startDate" },
				{ "data": "st_endDate" },
				{ "data": "st_shareType" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "targets": "_all" },
				{
					"render": function ( data, type, row ) {
						return parseId(data);
					},
					"targets": 0
				},
				{
					"render": function ( data, type, row ) {
						if (!data) {
							return '-';
						} else {
							return moment(data).format('DD/MM/YYYY HH:mm');
						}
					},
					"targets": 3
				},
				{
					"render": function ( data, type, row ) {
						if (!data) {
							return "${jspUtil.parseTagToText('shares.intervention.table.state.open')}";
						} else {
							return "${jspUtil.parseTagToText('shares.intervention.table.state.close')}";
						}
					},
					"targets": 4
				},
				{
					"render": function ( data ) {
						return parseShareType(data);
					},
					"targets": 5
				},
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 },
				{ "orderable": false, "targets": "_all" }
			],
			"dom": "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			"drawCallback": function(settings) {
				offset = settings._iDisplayStart;
				limit = settings._iDisplayLength;
				parseActionButtons('${userRole}', ${ userSigning != null }, ${ havePrivileges }, '${ userSigning.project.name }');
			},
			"initComplete": function() {
				const queryParams = new URLSearchParams(window.location.search);
				const pageNumber = queryParams.get('pageNumber');

				if (pageNumber) {
					dTable.page(pageNumber - 1).draw(false);
				}
			}
		});
	}

	function parseShareType(data) {
		if (data === 'ips') {
			return '<div class="badge badge-secondary"> ${jspUtil.parseTagToText('ips')} </div>';
		} else if (data === 'is') {
			return '<span class="badge badge-primary"> ${jspUtil.parseTagToText('is')} </span>';
		} else if (data === 'cs') {
			return '<span class="badge badge-success"> ${jspUtil.parseTagToText('cs')} </span>';
		} else if (data === 'ws') {
			return '<span class="badge badge-info"> ${jspUtil.parseTagToText('ws')} </span>';
		}

		return '';
	}
	
</script>

<script>

	document.addEventListener("DOMContentLoaded", function() {
		dTable = generateDataTable();
		handleDatatableFromParams();
		onChangePage();
	});

	function handleDatatableFromParams() {
		const queryParams = new URLSearchParams(window.location.search);

		let id = queryParams.get('id');
		let type = queryParams.get('type');
		let projectId = queryParams.get('project');
		let progress = queryParams.get('progress');
		let userId = queryParams.get('user');
		let pageNumber = queryParams.get('pageNumber');

		const filterForm = document.querySelector('[data-table-filter="form"]');

		filterForm.querySelector('[data-table-filter="id"]').value = id;
		filterForm.querySelector('[data-table-filter="type"]').value = type;
		filterForm.querySelector('[data-table-filter="project"]').value = projectId;
		filterForm.querySelector('[data-table-filter="progress"]').value = progress;
		filterForm.querySelector('[data-table-filter="user"]').value = userId;

		filterShares(pageNumber);
	}

	function onChangePage() {
		dTable.on('page.dt', function() {
			const pageInfo = dTable.page.info();
			const currentURL = new URL(window.location.href);
			currentURL.searchParams.set('pageNumber', pageInfo.page + 1);

			window.history.pushState(null, '', currentURL.toString());
		});
	}

</script>
