<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tld" uri="http://suboch.by/jsp/" %>
<%@ taglib prefix="adt" uri="http://suboch.by/jsp/" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="comments.title"/></title>
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
    <link rel="stylesheet" href="../../css/select2.min.css">
    <link rel="stylesheet" href="../../css/select_box.css">
    <link rel="stylesheet" href="../../css/sidebar.css">
    <%-- JS libraries --%>
    <script src="../../js/classie.js"></script>
    <script src="../../js/script.js"></script>
</head>
<body>
<!--Navigation -->
<c:import url="../../WEB-INF/jspf/admin_navigation.jsp"/>

<main class="container">
    <div id="side-nav" class="sidenav">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <a href="../../jsp/admin/create.jsp"><fmt:message key="menu.admin.create"/></a>
        <a href="../../jsp/admin/clients.jsp"><fmt:message key="menu.admin.clients"/></a>
        <a href="../../jsp/admin/comments.jsp"><fmt:message key="menu.admin.comments"/></a>
    </div>
    <div id="main">
        <div class="row">
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;<fmt:message key="menu.openMenu"/></span>
        </div>
        <c:forEach var="comment" items="${commentList}">
            <div class="row">
                <div class="col-sm-1">
                    <img class="img-responsive avatar-nav"
                         src="/s?command=load_image&elementId=${comment.right.accountId}&target=account"
                         onerror="this.src='../../images/default_album.png'" alt="">
                </div>

                <div class="col-sm-5">
                    <div class="panel panel-default" style="background-color:transparent">
                        <div class="panel-heading" style="background-color:transparent">
                            <strong>${comment.right.login}</strong> <span
                                class="text-muted">${comment.left.dateTime}</span>
                        </div>
                        <div class="panel-body">
                                ${comment.left.text}
                        </div>
                    </div>
                </div>
            </div>
            <hr>
        </c:forEach>
        <adt:emptyList items="${commentList}"><fmt:message key="comments.empty"/></adt:emptyList>
    </div>
</main>

<%-- Footer --%>
<c:import url="../../WEB-INF/jspf/footer.jsp"/>

<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/select2/select2.min.js"></script>
<script type="text/javascript">
    $(".select-box").select2();
</script>
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
