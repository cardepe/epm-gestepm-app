<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>

.border-red {
	border: 1px solid #dc3545;
	border-radius: .25rem;
}

#materialRequiredContent .form-check-label {
	font-size: 14px;
}

</style>

<div class="breadcrumbs">
	<div class="breadcrumbs-inner">
		<div class="row m-0">
			<div class="col">
				<div class="page-header float-left">
					<div class="page-title">
						<h1 class="text-uppercase"><spring:message code="sidebar.signing.page" /></h1>
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
									
									<div style="font-size: 12px">${ userName }</div>
									<div style="font-size: 11px"><spring:message code="signing.page.title" /></div>
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
							<form id="userSigningForm" class="needs-validation">
							
								<input id="usStateInput" type="hidden" name="state" value="${ userSigningStarted == false ? 0 : 1 }" />
								<input id="usDispShareId" type="hidden" name="dispShareId" />
								<input id="usMaterials" type="hidden" name="materials" />
								<input id="usMrSignature" type="hidden" name="mrSignature" />
								<input id="geolocationInput" type="hidden" name="geolocation" />
							
								<div class="row">
									<div class="col">
										<div class="form-group">
											<select id="projectDropdown" class="form-control input" name="project" ${ currentUser == true && userSigningStarted == false ? '' : 'disabled' }>
												<option></option>
												<c:forEach items="${projects}" var="project">
													<option value="${project.id}" ${ currentUserSigning.project.name == project.name ? 'selected' : '' }>
														<spring:message code="${project.name}" />
													</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col">
										<button id="startUserSigningBtn" type="button" class="btn btn-sm btn-${ userSigningStarted == false ? 'success' : 'danger' } w-100 text-white" ${ currentUser == false ? 'disabled' : '' }><i class="fa fa-play" style="font-size: 12px; margin-right: 5px"></i> <spring:message code="signing.page.${ userSigningStarted == false ? 'start' : 'end' }" /></button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-12 col-xl-9 h-100">
			<div class="card mb-0">
				<div class="card-body">
					<div class="title mb-0">
						<spring:message code="signing.page.title" />
					</div>

					<div class="table-responsive">
						<table id="dTable" class="table table-striped table-borderer dataTable w-100">
							<caption class="d-none">
								NO SONAR
							</caption>
							<thead>
								<tr>
									<th id="thId"><spring:message code="signing.page.table.id" /></th>
									<th id="thProjectName"><spring:message code="signing.page.table.project.name" /></th>
									<th id="thStartDate"><spring:message code="signing.page.table.start.date" /></th>
									<th id="thEndDate"><spring:message code="signing.page.table.end.date" /></th>
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

<!-- MODAL -->
<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createModalForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="signing.page.modal.title" />
						</h5>
					</div>
				</div>
				<div class="modal-body">		
					
					<div id="textDiv">
						<div class="row">
							<div class="col">
								<span style="font-size: 14px"><spring:message code="signing.page.modal.info" /></span>
							</div>
						</div>
					</div>
					
					<div id="materialDiv">
						<div class="row">
							<div class="col">
								<span style="font-size: 14px"><spring:message code="shares.materials.required.create.info" /></span>
							</div>
						</div>
							
						<div class="row mt-4">
							<div id="materialRequiredContent" class="col">
								
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12 col-md-6 mt-2">
								<input name="signatureOp" id="mrSignaturePadOp" type="hidden"/>
	
						  		<div id="mrSignature-pad-op" class="signature-pad">
							    	<div class="signature-pad--body">
							      		<canvas></canvas>
							    	</div>
							    	<div class="signature-pad--footer">
							      		<div class="description"><spring:message code="shares.intervention.create.signature.two" /></div>
						      			<div class="signature-pad--actions">
						        			<div>
									          	<button id="mrClearSignatureOp" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
						        			</div>
						        			<div></div>
						      			</div>
						    		</div>
						  		</div>
							</div>
						</div>
						
						<div id="checkMaterialContentSpanError" class="row mt-2 d-none">
							<div class="col">
								<span class="errorSpanText"><spring:message code="shares.materials.required.create.error" /></span>
							</div>
						</div>
					</div>

				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
						</div>
						<div class="float-right">
							<button id="createDispShareBtn" type="button" class="btn btn-sm btn-info"><spring:message code="shares.displacement.create.title" /></button>
							<button id="createSigningBtn" type="button" class="btn btn-sm btn-success"><spring:message code="init" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<!-- MODAL -->
<div id="createDispModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="createDispForm" class="needs-validation">
				<div class="modal-header">
					<div class="modal-title">
						<h5 id="modalTitle">
							<spring:message code="shares.displacement.create.btn" />
						</h5>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="displacementProjectDropdown" class="col-form-label"><spring:message code="shares.displacement.table.project.name" /></label>
								<select id="displacementProjectDropdown" class="form-control" required>
									<option></option>
									<c:forEach items="${displacementProjects}" var="project">
										<option value="${project.id}" data-info="${project.activityCenter.id}">
											<spring:message code="${project.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col">
							<div class="form-group">
								<label for="activityCenter" class="col-form-label"><spring:message code="shares.displacement.table.activity.center" /></label>
								<select id="activityCenter" name="activityCenter" class="form-control" onchange="loadDisplacement()" required>
									<option disabled selected="selected">
										<spring:message code="shares.displacement.table.activity.center" />
									</option>
									<c:forEach items="${teamLeaders}" var="teamLeader">
										<option value="${teamLeader.userId}">
											<spring:message code="${teamLeader.name} ${teamLeader.surnames}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<label for="manualHours" class="col-form-label"><spring:message code="shares.displacement.total.time" /></label>
							<input type="time" class="form-control" id="manualHours" name="manualHours">
						</div>
						
						<div class="col-sm-12 col-md-6">
							<label for="displacementType" class="col-form-label"><spring:message code="displacements.table.displacement.type" /></label>
							<select id="displacementType" name="displacementType" class="form-control">
								<option disabled value="" selected="selected">
									<spring:message code="displacements.displacement.type.placeholder" />
								</option>
								<option value="PUBLIC_TRANSPORT"><spring:message code="displacements.type.PUBLIC_TRANSPORT" /></option>
								<option value="VEHICLE"><spring:message code="displacements.type.VEHICLE" /></option>
							</select>
						</div>
					</div>
					
					<div class="row">
						<div class="col">
							<div class="form-group">
								<label for="observations" class="col-form-label"><spring:message code="shares.displacement.observations" /></label>
								<textarea id="observations" name="observations" class="form-control" rows="6"></textarea>
							</div>
						</div>
					</div>
				
					<input id="manualDisplacement" name="manualDisplacement" type="hidden" class="form-control" value="0" required>
					<input id="displacementDate" name="displacementDate" type="hidden" class="form-control" required>
				</div>
				<div class="modal-footer clearfix">
					<div class="w-100">
						<div class="float-left">
							<button type="button" class="btn btn-sm" data-dismiss="modal"><spring:message code="close" /></button>
						</div>
						<div class="float-right">
							<button id="createDispBtn" type="button" class="btn btn-sm btn-success"><spring:message code="ccreate" /></button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- /MODAL -->

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>

	var $=jQuery.noConflict();

	var dispShareId;
	
	$(document).ready(function() {

		getLocation();

		var mrWrapperOp = document.getElementById("mrSignature-pad-op");

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
	
			$("#createModal").on('shown.bs.modal', function(){
				mrResizeCanvas();
			});
		}
		
		/* Select 2 */
		$('#projectDropdown').select2({
			allowClear: true,
			placeholder: "${jspUtil.parseTagToText('project.selectable')}",
			dropdownCssClass: 'selectStyle'
		});
		/* End Select 2 */
		
		/* Datatables */
		var dTable = $('#dTable').DataTable({
			"searching": false,
			"responsive": true,
			"processing": true,
			"serverSide": true,
			"lengthMenu": [ 10, 25, 50, 75, 100 ],
			"ajax": "/signing/dt${empty userId ? '' : '?user='}${userId}",
			"rowId": "si_id",
			"language": {
				"url": "/ui/static/lang/datatables/${locale}.json"
			},
			"order": [[0, "desc"]],
			"columns": [
				{ "data": "si_id" },
				{ "data": "si_projectName" },
				{ "data": "si_startDate" },
				{ "data": "si_endDate", "defaultContent": "-" },
				{ "data": null }
			],
			"columnDefs": [
				{ "className": "text-center", "targets": "_all" },
				{  
				    "render": function ( data, type, row ) {
					    if (!data) { return '-'; } 
					    else { return moment(data).format('DD/MM/YYYY HH:mm'); }
                	},
                	"targets": [2, 3]
                },
				{ "defaultContent": "${tableActionButtons}", "orderable": false, "targets": -1 }
			],
			"dom": "<'top'i>rt<'bottom'<'row no-gutters'<'col'l><'col'p>>><'clear'>",
			"drawCallback": function(settings, json) {
				parseActionButtons(${trashVisible});
			}
		});
		/* End Datatables */

		/* Buttons Click Actions */
		$('#createDispShareBtn').click(function() {
			$('#createModal').modal('hide');
			$('#createDispModal').modal('show');
		});

		$('#displacementProjectDropdown').change(function() {

			let activityCenterId = $('#displacementProjectDropdown option:selected').attr('data-info');
			let callbackFunction = function(html) {
				$('#displacement').html(html);
			}

			loadDisplacements(activityCenterId, callbackFunction);
		});

		$('#createDispBtn').click(function() {

			var projectId = $('#displacementProjectDropdown').val();
			var noticeDate = moment().format().slice(0,19);
			
			$('#displacementDate').val(noticeDate);
			$('#manualHours').attr('disabled', false);
			
			$('#createDispModal').modal('hide');

			$.ajax({
				type: "POST",
				url: "/shares/displacement?project=" + projectId,
				data: $('#createDispForm').serialize(),
				success: function(response) {
					dispShareId = response.id;		
					$('#createDispShareBtn').hide();			
					$('#createModal').modal('show');
					showNotify(response.msg, 'success');
				},
				error: function(e) {
					showNotify(e.responseText, 'danger');
				}
			});
		});

		$('#createSigningBtn').click(function() {

			var state = $('#usStateInput').val();

			if (state == 0) {

				if (dispShareId) {
					$('#usDispShareId').val(dispShareId);
				}

				if ($('#materialDiv').is(':visible')) {

					if (areMaterialsRequiredSelected() == false) {
						$('#checkMaterialContentSpanError').removeClass("d-none");
						return;
					}
					
					var checkedMaterialsRequired = getCheckedMaterialsRequired();
					
					$('#usMaterials').val(checkedMaterialsRequired);

					if (!mrSignaturePadOp.isEmpty()) {
						$('#usMrSignature').val(mrSignaturePadOp.toDataURL());
					}
				}
			}
			
			submitUserSigning(state);
		});
		
		$('#startUserSigningBtn').click(function() {
			onStateUserSigningBtn($('#usStateInput').val());
		});
		/* End Button Click Actions */

		$('#createModal').on('hidden.bs.modal', function (e) {
			$('#createDispShareBtn').show();	
			$('#checkMaterialContentSpanError').addClass("d-none");
		});
	});

	function validateForm() {
		var projectName = document.getElementById('projectDropdown');

		return (!projectName.value.length);
	}

	function onStateUserSigningBtn(state) {

		if (state == 0) {

			if (validateForm()) {
				$($('#projectDropdown').data('select2').$container).addClass('border-red');
			} else {
				$($('#projectDropdown').data('select2').$container).removeClass('border-red');
	
				var projectId = $('#projectDropdown').val();
				var data = getMaterialsRequiredByProject(projectId);
	
				if (!data || data.length < 1) {
	
					$('#materialDiv').hide();
					$('#textDiv').show();
					
				} else {
	
					var html = convertMaterialsRequiredDataToHtml(data);
					
					$('#materialRequiredContent').html(html);
					
					$('#textDiv').hide();
					$('#materialDiv').show();
				}
	
				$('#createModal').modal('show');
			}
		} else if (state == 1) {
			submitUserSigning(1);
		}		
	}

	function submitUserSigning(state) {

		showLoading();

		$('#createSigningBtn').prop("disabled", true);

		$.ajax({
			type: "POST",
			url: "/signing",
			data: $('#userSigningForm').serialize(),
			success: function(response) {

				if (state == 0) {
					window.location.replace("/shares/intervention");
				} else if (state == 1) {
					
					changeUserSigningBtn(0);
					$('#dTable').DataTable().ajax.reload();
				}

				$('#createSigningBtn').prop("disabled", false);

				hideLoading();
			},
			error: function(e) {

				$('#createSigningBtn').prop("disabled", false);

				hideLoading();
				showNotify(e.responseText, 'danger');
			}
		});
	}
	
	function changeUserSigningBtn(state) {

		$('#usStateInput').val(state);

		if (state == 0) {

			$('#projectDropdown').select2("enable");
			
			$('#startUserSigningBtn').removeClass('btn-danger');
			$('#startUserSigningBtn').addClass('btn-success');

			$('#startUserSigningBtn').text("${jspUtil.parseTagToText('signing.page.start')}");
			
		} else if (state == 1) {

			$('#projectDropdown').select2("enable", false);
			
			$('#startUserSigningBtn').removeClass('btn-success');
			$('#startUserSigningBtn').addClass('btn-danger');

			$('#startUserSigningBtn').text("${jspUtil.parseTagToText('signing.page.end')}");
		}
	}

	// Material Functions

	function areMaterialsRequiredSelected() {

		var allCorrect = true;
		var inputs = $("#createModalForm :input[type='checkbox']");

		inputs.each(function() {

			if ($(this).prop('required')) {
				
				if ($(this).prop('checked') == false) {
					allCorrect = false;
					return false;
				}
			}
		});

		return allCorrect;
	}

	function getCheckedMaterialsRequired() {
		
		var inputs = $("#createModalForm :input[type='checkbox']");

		var value = '';

		inputs.each(function() {

			if ($(this).prop('checked')) {
				value += $(this).attr('id').replace('materialRequired', '') + ';';
			}
		});	

		if (value.length > 0) {
			value = value.substr(0, value.length - 1);
		}
		
		return value;
	}
	
	function getMaterialsRequiredByProject(projectId) {

		var result;

		$.ajax({
	        url: "/projects/" + projectId + "/materials-required",
	        type: "get",
	        dataType: "json",
	        async: false,
	        success: function(data) {
	            result = data;
	        } 
	     });

		return result;
	}

	function convertMaterialsRequiredDataToHtml(data) {

		var language = $('#userLanguageInput').val();
		var html = '';

		data.forEach(function(element, i) {

			if (i + 1 % 3 == 1) {
				html += '<div class="row">';
			}
			
			var isRequired = '';
			if (element.required == 1) {
				isRequired = 'required';
			}
			
			html += '<div class="col-4"><div class="form-group"> <div class="form-check form-check-inline"> <input type="checkbox" class="form-check-input" id="materialRequired' + element.id + '" value="1" style="width: 20px; height: 20px" ' + isRequired + '> <label class="form-check-label" for="materialRequired' + element.id + '">';

			if (language == 'es') {
				html += element.nameES;
			} else {
				html += element.nameFR;
			}

			if (element.required == 1) {
				html += ' <span style="color: red">*</span>';
			}
			
			html += '</label> </div> </div> </div>';	

			if (i + 1 % 3 == 0 || i == data.length) {
				html += '';
			}
		});

		return html;
	}

	// End Material Functions

	// Displacement Functions
	function loadDisplacement() {
		let id =  $('#displacement').val();

		axios.get('/v1/displacements/' + id).then((response) => {
			let displacement = response.data.data;

			let manualHoursInput = $('#manualHours');
			let typeInput = $('#displacementType');

			manualHoursInput.val(minutesToTime(displacement.totalTime));
			manualHoursInput.attr('disabled', true);

			typeInput.val(displacement.type);
			typeInput.attr('disabled', true);
		});
	}
	// End Displacement Functions
	
	// Signing Functions
	
	function deleteSigning(userSigningId) {

		var ok = confirm("${jspUtil.parseTagToText('signing.delete.alert')}");

		if (ok) {
			showLoading();
			
			$.ajax({
				type: "DELETE",
				url: "/signing/" + userSigningId,
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
		}
	}
	
	// End Signing Functions
	
</script>