<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col">
        <div class="table-responsive">
            <table id="dTable" class="table table-striped table-borderer dataTable w-100">
                <thead>
                <tr>
                    <th><spring:message code="project.detail.table.materials.required.name.es"/></th>
                    <th><spring:message code="project.detail.table.materials.required.name.fr"/></th>
                    <th><spring:message code="project.detail.table.materials.required.required"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<div id="createModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="add"/></h5>
                </div>
            </div>

            <div class="modal-body">
                <form id="createForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="project.detail.table.materials.required.name.es"/>
                                    <input type="text" name="nameEs" class="form-control" required/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="project.detail.table.materials.required.name.fr"/>
                                    <input type="text" name="nameFr" class="form-control" required/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="project.detail.table.materials.required.required"/>
                                    <select name="required" class="form-control select2 stationSelect" required>
                                        <option value=""><spring:message code="select.placeholder" /></option>
                                        <option value="true"><spring:message code="yes" /></option>
                                        <option value="false"><spring:message code="no" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close"/>
                        </button>
                    </div>
                    <div class="float-right">
                        <button type="button" class="btn btn-default btn-sm" onclick="create()">
                            <spring:message code="create"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="edit" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="editForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="project.detail.table.materials.required.name.es"/>
                                    <input type="text" name="nameEs" class="form-control" required/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="project.detail.table.materials.required.name.fr"/>
                                    <input type="text" name="nameFr" class="form-control" required/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100 bootstrap-select"><spring:message code="project.detail.table.materials.required.required"/>
                                    <select name="required" class="form-control select2 stationSelect" required>
                                        <option value=""><spring:message code="select.placeholder" /></option>
                                        <option value="true"><spring:message code="yes" /></option>
                                        <option value="false"><spring:message code="no" /></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer clearfix">
                <div class="w-100">
                    <div class="float-left">
                        <button type="button" class="btn btn-sm" data-dismiss="modal">
                            <spring:message code="close" />
                        </button>
                    </div>
                    <div class="float-right">
                        <button id="saveBtn" type="button" class="btn btn-default btn-sm">
                            <spring:message code="save" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
