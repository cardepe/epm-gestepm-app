<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
    .clearSignatureButton {
        color: #333;
        font-weight: bold;
        line-height: 2.25em;
        text-decoration: underline;
        border: 0;
        background: transparent;
        font-size: 0.75em;
    }

    .signature-pad {
        position: relative;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        font-size: 10px;
        width: 100%;
        height: 100%;
        max-width: 700px;
        max-height: 200px;
        min-height: 160px;
        border: 1px solid #ced4da;
        background-color: #fff;
        /* box-shadow: 0 1px 4px rgba(0, 0, 0, 0.27), 0 0 40px rgba(0, 0, 0, 0.08) inset; */
        border-radius: 4px;
        padding: 16px;
    }

    .signature-pad::before, .signature-pad::after {
        position: absolute;
        z-index: -1;
        content: "";
        width: 40%;
        height: 10px;
        bottom: 10px;
        background: transparent;
        /* box-shadow: 0 8px 12px rgba(0, 0, 0, 0.4); */
    }

    .signature-pad::before {
        left: 20px;
        -webkit-transform: skew(-3deg) rotate(-3deg);
        transform: skew(-3deg) rotate(-3deg);
    }

    .signature-pad::after {
        right: 20px;
        -webkit-transform: skew(3deg) rotate(3deg);
        transform: skew(3deg) rotate(3deg);
    }

    .signature-pad--body {
        position: relative;
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        border: 1px solid lightgray;
    }

    .signature-pad--body
    canvas {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        border-radius: 4px;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.02) inset;
    }

    .signature-pad--footer {
        color: gray;
        text-align: center;
        font-size: 1.2em;
        margin-top: 8px;
    }

    .signature-pad--actions {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
        margin-top: -20px;
    }
</style>

<form id="${param.prefix}InterventionForm" action="/shares/intervention/no-programmed/detail/${ share.id }/files" method="POST" class="needs-validation" enctype="multipart/form-data">
    <div class="row">
        <div class="col-md-6">
            <div class="form-group mb-1">
                <spring:message code="shares.intervention.create.intervention.desc.placeholder" var="placeholder" />
                <label class="col-form-label w-100"><spring:message code="shares.intervention.create.intervention.desc" />
                    <textarea class="form-control mt-1" placeholder="${placeholder}" name="description" rows="4" style="resize: none" required></textarea>
                </label>
            </div>

            <div class="form-group mb-1">
                <label class="col-form-label w-100">
                    <spring:message code="shares.intervention.create.equipment.hours" />
                    <input name="equipmentHours" type="number" class="form-control mt-1" />
                </label>

            </div>
        </div>

        <div class="col-md-6">
            <div class="form-group mb-0">
                <label class="col-form-label w-100">
                    <spring:message code="shares.intervention.create.intervention.files" />
                    <input type="file" class="form-control mt-1" name="files" accept=".jpg, .jpeg, .png" multiple>
                </label>
            </div>

            <div class="visibility-id">
                <div class="form-group">
                    <label class="col-form-label w-100">
                        <spring:message code="shares.intervention.create.intervention.material.files" />
                        <input type="file" class="form-control mt-1" name="materialsFile" accept=".xlsx, .xls, .csv, .pdf">
                    </label>
                </div>
            </div>
        </div>
    </div>

    <div class="visibility-id">
        <div class="row mt-2">
            <div class="col text-right">
                <button type="button" class="btn btn-standard btn-sm movile-full" onclick="showMaterialModal(null, 'create')"><spring:message code="shares.intervention.detail.add.material.btn" /></button>
            </div>
        </div>

        <div class="row mb-2">
            <div class="col">
                <div class="table-responsive">
                    <table class="table table-striped table-borderer dataTable w-100 materials-table">
                        <thead>
                            <tr>
                                <th><spring:message code="shares.intervention.create.materials.desc" /></th>
                                <th><spring:message code="shares.intervention.create.materials.uds" /></th>
                                <th><spring:message code="shares.intervention.create.materials.ref" /></th>
                                <th class="all"><spring:message code="shares.displacement.table.actions" /></th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>

        <div class="row mb-2">
            <div class="col-sm-12 col-md-6 mt-2">
                <input name="signature" type="hidden"/>
                <input name="signatureOp" type="hidden"/>

                <div class="signature-pad">
                    <div class="signature-pad--body">
                        <canvas class="signature-canvas"></canvas>
                    </div>
                    <div class="signature-pad--footer">
                        <div class="description"><spring:message code="shares.intervention.create.signature" /></div>
                        <div class="signature-pad--actions">
                            <div>
                                <button id="clearSignature" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
                            </div>
                            <div></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 col-md-6 mt-2">
                <div id="signature-pad-op" class="signature-pad">
                    <div class="signature-pad--body">
                        <canvas class="signature-op-canvas"></canvas>
                    </div>
                    <div class="signature-pad--footer">
                        <div class="description"><spring:message code="shares.intervention.create.signature.two" /></div>
                        <div class="signature-pad--actions">
                            <div>
                                <button id="clearSignatureOp" class="clearSignatureButton" type="button"><i class="fa fa-redo"></i></button>
                            </div>
                            <div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <input type="text" class="form-control" placeholder="<spring:message code="shares.intervention.create.client.name" />" name="clientName" />
            </div>
            <div class="col text-right">
                <div class="form-group">
                    <label><input type="checkbox" name="clientNotif"> <spring:message code="shares.intervention.create.share.client.notif.mail" /></label>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col text-right">
            <button type="button" class="btn btn-danger btn-sm movile-full intervention-complete-btn"><spring:message code="finish" /></button>
        </div>
    </div>

    <input id="updateId" name="id" class="form-control input" type="hidden" readonly />
</form>