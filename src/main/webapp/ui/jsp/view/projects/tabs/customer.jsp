<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="customerForm" class="needs-validation">
    <div class="row">
        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="project.detail.customer.name"/>
                    <input type="text" name="name" class="form-control" value="${customer.name}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="project.detail.customer.email.1"/>
                    <input type="text" name="mainEmail" class="form-control" value="${customer.mainEmail}" required/>
                </label>
            </div>
        </div>

        <div class="col-sm-12 col-md-4">
            <div class="form-group">
                <label class="col-form-label w-100"><spring:message code="project.detail.customer.email.1"/>
                    <input type="text" name="secondaryEmail" class="form-control" value="${customer.secondaryEmail}" />
                </label>
            </div>
        </div>
    </div>

    <div class="row mt-2 actionable">
        <div class="col text-right">
                <button type="button" class="btn btn-standard btn-sm movile-full" onclick="editCustomer()"><spring:message code="save"/></button>
        </div>
    </div>
</form>
