<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="adt" uri="http://suboch.by/jsp/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="Cart"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%-- icon --%>
    <link rel="shortcut icon" href="../../images/favicon.ico"/>
    <%-- CSS --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../css/sidebar.css">
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
    <h1><fmt:message key="cart.header.itemsInCart"/></h1>
    <hr>
    <div id="itemsTable" class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="purchase.info.trackTitle"/></th>
                <th><fmt:message key="purchase.info.trackPrice"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="1" scope="page"/>
            <c:forEach var="track" items="${cartItems}">
                <tr>
                    <td>${i}</td>
                    <td><a href="/s?command=show_element&type=track&id=${track.trackId}">${track.title}</a></td>
                    <td>${track.price}</td>
                    <td>
                        <form class="remove-from-cart-form" method="post" action="/s">
                            <a class="remove-from-cart-link" href="/s?command=remove_from_cart&trackId=${track.trackId}">Remove</a>
                        </form>
                    </td>
                </tr>
                <c:set var="i" value="${i+1}" scope="page"/>
            </c:forEach>
            </tbody>
        </table>
        <adt:emptyList items="${cartItems}"><fmt:message key="cart.empty"/></adt:emptyList>
    </div>

    <form id="makePurchaseForm" method="post" action="/s">
        <button type="submit" class="btn-custom" name="command" value="make_purchase"><fmt:message key="cart.buy"/></button>
    </form>
</main>

<%-- Footer --%>
<c:import url="../../WEB-INF/jspf/footer.jsp"/>
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/jquery.form.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/cart.js"></script>
</body>
</html>
