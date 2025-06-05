<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty currentUser.forumUsername}">
        <span><spring:message code="user.detail.user.username" />: ${currentUser.forumUsername}</span>
    </c:when>
    <c:otherwise>
        <form id="forumForm" class="needs-validation">
            <div class="row mt-2">
                <div class="col-sm-12 col-md-2">
                    <input type="text" name="username" class="form-control mr-2" placeholder="<spring:message code="name"/>" required />
                </div>
                <div class="col-sm-12 col-md-1">
                    <button type="button" class="btn btn-standard btn-sm mt-1 w-100" onclick="createForumUser()"><spring:message code="create"/></button>
                </div>
            </div>
        </form>
    </c:otherwise>
</c:choose>

<script>

    function createForumUser() {

        const createFromJQ = $('#forumForm');

        if (!isValidForm('#forumForm')) {
            createFromJQ.addClass('was-validated');
        } else {

            showLoading();
            createFromJQ.removeClass('was-validated');

            const form = document.querySelector('#forumForm');
            const username = form.querySelector('[name="username"]').value;

            axios.patch(endpoint, {
                forumUsername: username
            }).then(() => {
                location.reload();
            }).catch(error => showNotify(error.response.data.detail, 'danger'))
                .finally(() => hideLoading());
        }
    }

</script>