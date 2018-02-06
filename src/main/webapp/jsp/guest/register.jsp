<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="authorization.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%-- icon --%>
    <link rel="shortcut icon" href="../../images/favicon.ico"/>
    <%-- CSS --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/register.css">
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap3_player.css">
</head>
<body>
<main class="container">
    <div class="row">
        <nav class="col-md-6">
            <ul>
                <li class="nav-menu-item nav-menu-item-active" onclick="showForm('register-section', this)"><fmt:message
                        key="registration.label"/></li>
                <li class="nav-menu-item nav-menu-item-inactive" onclick="showForm('log-in-section', this)"><fmt:message
                        key="login.label"/></li>
            </ul>
        </nav>
        <section class="col-md-6 section-description md-section">
            <h3><fmt:message key="authorization.section.header"/></h3>
        </section>
    </div>
    <div class="row">
        <section id="register-section" class="active-section col-md-6">
            <form name="registration" id="registration" method="post" action="/s">
                <div class="row">
                    <input type="text" name="firstName" placeholder="<fmt:message key="registration.form.firstName"/>"
                           required>
                </div>
                <div class="row">
                    <input type="text" name="lastName" placeholder="<fmt:message key="registration.form.lastName"/>"
                           required>
                </div>
                <div class="row">
                    <input type="text" name="login" placeholder="<fmt:message key="registration.form.username"/>"
                           pattern="^[a-zA-Z][a-zA-Z0-9_]{4,}$" required>
                </div>
                <div class="row">
                    <input type="text" name="email" placeholder="<fmt:message key="registration.form.email"/>"
                           pattern="^.+@.+[.].+$" required>
                </div>
                <div class="row">
                    <input type="password" name="password"
                           placeholder="<fmt:message key="registration.form.password"/>"
                           pattern="^.*(?=.{6,})(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+).*$" required>
                </div>
                <div class="row">
                    <input type="password" name="passwordConfirm"
                           placeholder="<fmt:message key="registration.form.confirmPassword"/>"
                           pattern="^.*(?=.{6,})(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+).*$" required>
                </div>
                <div class="row">
                    <div class="input-wrap">
                        <input id="accept" type="checkbox" name="termsAccept" value="unchecked" required>
                        <label for="accept"></label>
                        <span><fmt:message key="registration.form.acceptTerms.text"/> <a href=""><fmt:message
                                key="registration.form.acceptTerms.agreement"/></a></span>
                    </div>
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="register"><fmt:message
                            key="registration.label"/></button>
                </div>
            </form>
        </section>
        <section id="log-in-section" class="inactive-section col-md-6">
            <form name="login" id="log-in" method="post" action="/s">
                <div class="row">
                    <input type="text" name="authorizationName" placeholder="<fmt:message key="login.form.authName"/>"
                           required>
                </div>
                <div class="row">
                    <input type="password" name="password" placeholder="<fmt:message key="login.form.password"/>"
                           required>
                </div>
                <div class="row">
                    <button class="btn-custom" type="submit" name="command" value="log_in"><fmt:message
                            key="login.label"/></button>
                </div>
            </form>
        </section>
        <section class="col-md-6 section-paragraph">
            <p><fmt:message key="authorization.section.p1"/></p>
            <p><fmt:message key="authorization.section.p2"/></p>
            <p><fmt:message key="authorization.section.p3"/></p>
        </section>
    </div>
    <form class="row" action="/s" method="get">
        <select name="locale">
            <option value="en_US">English</option>
            <option value="ru_RU">Русский</option>
        </select>
        <button class="btn-custom" type="submit" name="command" value="change_language"><fmt:message
                key="label.language"/></button>
    </form>
</main>
<%-- JS libraries --%><%--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>--%>
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/jquery.form.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/classie.js"></script>
<script src="../../js/script.js"></script>
<script src="../../js/bootstrap3_player.js"></script>
<script src="../../js/registration_login.js"></script>

</body>
</html>