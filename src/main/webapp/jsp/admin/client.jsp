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
    <title><fmt:message key="client.title"/></title>
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
    <div id="side-nav" class="sidenav">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <a href="../../jsp/admin/create.jsp"><fmt:message key="menu.admin.create"/></a>
<%--        <a href="../../jsp/admin/edit.jsp">Edit</a>--%>
        <a href="../../jsp/admin/clients.jsp"><fmt:message key="menu.admin.clients"/></a>
        <a href="../../jsp/admin/comments.jsp"><fmt:message key="menu.admin.comments"/></a>
    </div>
    <div id="main">
        <div class="row">
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;<fmt:message key="menu.openMenu"/></span>
        </div>
        <div class="row">
            <h1 style="display: inline">${currentAccount.login}</h1>
            <c:if test="${currentAccount.admin}">
                <span class="label label-default"><fmt:message key="client.admin"/></span>
            </c:if>
        </div>
        <div class="row">
            <img src="/s?command=load_image&elementId=${currentAccount.accountId}&target=account"
                 onerror="this.src='../../images/default_avatar.jpg'" alt="">
        </div>
        <hr>
        <h2><fmt:message key="client.personalInfo"/></h2>
        <div class="row">
            <div class="table-responsive col-md-8">
                <table class="table table-condensed">
                    <tbody>
                    <tr>
                        <td><fmt:message key="client.info.firstName"/></td>
                        <td>${currentAccount.firstName}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="client.info.lastName"/></td>
                        <td>${currentAccount.lastName}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="client.info.email"/></th>
                        <td>${currentAccount.email}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <hr>
        <h2><fmt:message key="client.purchases"/></h2>
        <div class="row">
            <div class="table-responsive col-md-8">
                <table class="table table-condensed">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="purchase.info.date"/></th>
                        <th><fmt:message key="purchase.info.size"/></th>
                        <th><fmt:message key="purchase.price"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="1" scope="page"/>
                    <c:forEach var="purchase" items="${currentClientPurchases}">
                        <tr>
                            <td>${i}</td>
                            <td> Date</td>
                            <td>${fn:length(purchase.tracksId)}</td>
                            <td>${purchase.totalPrice}$</td>
                            <td><a href="/s?command=show_element&type=purchase&id=${purchase.purchaseId}"><fmt:message key="purchase.view"/></a></td>
                        </tr>
                        <c:set var="i" value="${i+1}" scope="page"/>
                    </c:forEach>
                    </tbody>
                </table>
                <adt:emptyList items="${currentClientPurchases}"><fmt:message key="clients.noPurchases"/></adt:emptyList>
            </div>
        </div>
    </div>
</main>

<%-- Footer --%>
<c:import url="../../WEB-INF/jspf/footer.jsp"/>
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>

<script>
    function openNav() {
        document.getElementById("side-nav").style.width = "200px";
        document.getElementById("main").style.marginLeft = "100px";
    }

    function closeNav() {
        document.getElementById("side-nav").style.width = "0";
        document.getElementById("main").style.marginLeft = "0";
    }
</script>
</body>
</html>
