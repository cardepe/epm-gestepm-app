<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive">
    <table id="dTable" class="table table-striped table-borderer dataTable w-100">
        <thead>
        <tr>
            <th><spring:message code="id" /></th>
            <th><spring:message code="name" /></th>
            <th><spring:message code="activity.center" /></th>
            <th><spring:message code="level" /></th>
            <th><spring:message code="role" /></th>
            <th><spring:message code="actions" /></th>
        </tr>
        </thead>
    </table>
</div>