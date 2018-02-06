<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="adt" uri="http://suboch.by/jsp/" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../images/favicon.ico"/>
    <%-- CSS libraries --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%-- Custom CSS --%>
    <link rel="stylesheet" type="text/css" href="../../css/nav_tabs.css">
    <%-- JS libraries --%>
    <script src="../../js/classie.js"></script>
    <%-- Custom JS --%>
    <script src="../../js/script.js"></script>
</head>
<body>

<c:choose>
    <c:when test="${visitor.role eq 'ADMIN'}">
        <jsp:include page="../../WEB-INF/jspf/admin_navigation.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../../WEB-INF/jspf/user_navigation.jsp"/>
    </c:otherwise>
</c:choose>

<main class="container">
    <div class="row">
        <nav class="col-md-12">
            <ul>
                <li class="nav-menu-item nav-menu-item-active" onclick="showForm('profile-settings', this)">
                    <fmt:message key="settings.changeProfile"/></li>
                <li class="nav-menu-item nav-menu-item-inactive" onclick="showForm('account-settings', this)">
                    <fmt:message key="settings.changeAccount"/></li>
            </ul>
        </nav>
    </div>

    <div class="row">
        <section id="profile-settings" class="active-section col-md-12">
            <h2><fmt:message key="settings.text.picture"/></h2>
            <div class="row">
                <c:choose>
                    <c:when test="${account.avatar != null}">
                        <img class="avatar" src="/s?command=load_image&elementId=${account.accountId}&target=account"/>
                    </c:when>
                    <c:otherwise>
                        <img class="avatar" src="../../images/default_avatar.jpg">
                    </c:otherwise>
                </c:choose>
            </div>
            <form method="post" action="/s" enctype="multipart/form-data">
                <div class="row">
                    <fmt:message key="settings.file"/> <input type="file" name="avatar">
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="change_avatar" onclick="">
                        <fmt:message
                                key="settings.uploadImage"/></button>
                </div>
            </form>
            <hr>
            <h2><fmt:message key="settings.text.name"/></h2>
            <form id="changeNameForm" method="post" action="/s">
                <div class="row">
                    <fmt:message key="registration.form.firstName"/>
                    <input type="text" name="firstName" value="${account.firstName}">
                </div>
                <div class="row">
                    <fmt:message key="registration.form.lastName"/>
                    <input id="lastNameId" type="text" name="lastName" value="${account.lastName}">
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="change_personal_info" onclick="">
                        <fmt:message key="settings.changeName"/>
                    </button>
                </div>
            </form>
            <hr>
            <h2><fmt:message key="settings.text.username"/></h2>
            <form id="changeLoginForm" method="post" action="/s">
                <div class="row">
                    <fmt:message key="registration.form.username"/>
                    <input type="text" name="login" value="${account.login}"
                           maxlength="40"
                           pattern="^\\p{L}(\\p{L}|\\p{N}|[_])*$">
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="change_login" onclick=""><fmt:message
                            key="settings.changeLogin"/></button>
                </div>
            </form>
        </section>

        <section id="account-settings" class="inactive-section form col-md-12">
            <h2><fmt:message key="settings.text.email"/></h2>
            <form id="changeEmailForm" method="post" action="/s">
                <div class="row">
                    <fmt:message key="registration.form.email"/>
                    <input type="text" name="email" value="${account.email}"
                           pattern="^.+@.+[.].+$">
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="change_email" onclick=""><fmt:message
                            key="settings.changeEmail"/></button>
                </div>
            </form>
            <hr>
            <h2><fmt:message key="settings.text.password"/></h2>
            <form id="changePasswordForm" method="post" action="/s">
                <div class="row">
                    <fmt:message key="settings.form.oldPassword"/>
                    <input type="password" name="oldPassword"
                           pattern="^.*(?=.{6,})(?=.*(\\p{Ll})+)(?=.*(\\p{N})+).*$"
                           required>
                </div>
                <div class="row">
                    <fmt:message key="settings.form.newPassword"/>
                    <input type="password" name="newPassword"
                           pattern="^.*(?=.{6,})(?=.*(\\p{Ll})+)(?=.*(\\p{N})+).*$"
                           required>
                </div>
                <div class="row">
                    <fmt:message key="settings.form.newPasswordConfirm"/>
                    <input type="password" name="newPasswordConfirm"
                           pattern="^.*(?=.{6,})(?=.*(\\p{Ll})+)(?=.*(\\p{N})+).*$"
                           required>
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="change_password" onclick="">
                        <fmt:message key="settings.changePassword"/></button>
                </div>
            </form>
            <hr>
            <h2><fmt:message key="settings.text.delete"/></h2>
            <a class="delete-option" href="/s?command=delete_account"><fmt:message key="settings.delete"/></a>
        </section>
    </div>
</main>

<%-- Footer --%>
<jsp:include page="../../WEB-INF/jspf/footer.jsp"/>
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/jquery.form.min.js"></script>
<script src="../../js/settings.js"></script>
</body>
</html>
