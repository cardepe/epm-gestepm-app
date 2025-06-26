<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil" />

<div class="table-responsive">
    <table id="dTable" class="table table-striped table-borderer dataTable w-100">
        <thead>
        <tr>
            <th><spring:message code="id" /></th>
            <th><spring:message code="date" /></th>
            <th><spring:message code="status" /></th>
            <th><spring:message code="actions" /></th>
        </tr>
        </thead>
    </table>
</div>

<div id="declineModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5><spring:message code="user.detail.holidays.decline.title" /></h5>
                </div>
            </div>
            <div class="modal-body">
                <form id="declineForm">
                    <input type="hidden" id="holidayId" />

                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label class="col-form-label w-100"><spring:message code="user.detail.holidays.decline.observations" />
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
                        <button type="button" class="btn btn-sm btn-success" onclick="declineHoliday()"><spring:message code="decline" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
