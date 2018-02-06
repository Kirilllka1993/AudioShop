<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="adt" uri="http://suboch.by/jsp/" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%-- icon --%>
    <link rel="shortcut icon" href="../../images/favicon.ico"/>
    <%-- CSS --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/main.css">
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap3_player.css">
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

<main>
    <div class="container">
        <!-- Content Section -->
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2 class="section-heading"><fmt:message key="main.welcomeText.head"/> ${account.login}!</h2>
                        <p class="lead section-lead"><fmt:message key="main.welcomeText.p1"/></p>
                        <p class="section-paragraph"><fmt:message key="main.welcomeText.p2"/></p>
                    </div>
                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <p class="section-lead"><fmt:message key="main.textBeforeItems"/></p>
                    </div>
                </div>
            </div>
        </section>

        <c:forEach var="track" items="${tracksOnPage}">
            <%-- <c:set var="currentTrack" value="${test}"/>--%>
            <div class="col-md-4 col-sm-6 item">
                <div class="thumbnail">
                    <img src="/s?command=load_image&elementId=${track.albumId}&target=album"
                         onerror="this.src='../../images/default_album.png'" alt="">
                    <div class="caption">
                        <h4><a href="/s?command=show_element&type=track&id=${track.trackId}">${track.title}</a></h4>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-4 col-sm-4">
                                    <p class="price">${track.price}$</p>
                                </div>
                                <div class="col-md-8 col-sm-8">
                                    <form class="add-to-cart-form" method="post" action="/s">
                                        <button type="submit" class="btn-custom add-to-cart-button"
                                                name="command" value="add_to_cart">
                                            <adt:notCartItem test="${track}" items="${cartItems}">
                                                <fmt:message key="main.label.addToCart"/>
                                            </adt:notCartItem>
                                            <adt:cartItem test="${track}" items="${cartItems}">
                                                In cart
                                            </adt:cartItem>
                                        </button>
                                        <input type="hidden" name="trackId" value="${track.trackId}">
                                    </form>
                                    <button class="btn-custom btn-play"
                                            onclick='playTrack(${track.trackId}, "${track.location}")'>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <div class="col-md-12">
            <ul class="pagination pagination-sm">
                <li><a href="/s?command=switch_page&toFirstPage=true&toLastPage=false">«</a></li>
                <c:forEach var="i" begin="1" end="${pageAmount}">
                    <li><a href="/s?command=switch_page&pageNumber=${i}&toFirstPage=false&toLastPage=false">${i}</a>
                    </li>
                </c:forEach>
                <li><a href="/s?command=switch_page&toFirstPage=false&toLastPage=true">»</a></li>
            </ul>
        </div>
    </div>
</main>


<%-- Footer --%>
<c:import url="../../WEB-INF/jspf/footer.jsp"/>

<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/jquery.form.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/classie.js"></script>
<script src="../../js/script.js"></script>
<script src="../../js/pagination.js"></script>
<script src="../../js/bootstrap3_player.js"></script>
<script src="../../js/play_track.js"></script>
<script src="../../js/cart.js"></script>

</body>
</html>
