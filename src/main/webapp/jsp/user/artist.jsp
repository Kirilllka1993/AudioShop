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
    <title><fmt:message key="menu.artist"/></title>
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
        <a href="../../jsp/user/catalog_artists.jsp"><fmt:message key="menu.artists"/></a>
        <a href="../../jsp/user/catalog_albums.jsp"><fmt:message key="menu.albums"/></a>
        <a href="../../jsp/user/catalog_tracks.jsp"><fmt:message key="menu.tracks"/></a>
    </div>
    <div id="main">
        <div class="row">
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;<fmt:message key="menu.openMenu"/></span>
        </div>
        <div class="row">
            <h1>${currentArtist.name}</h1>
        </div>
        <div class="row">
            <c:choose>
                <c:when test="${currentArtist.image != null}">
                    <img class="img-responsive col-md-4" src="/s?command=load_image&elementId=${currentArtist.artistId}&target=artist">
                </c:when>
                <c:otherwise>
                    <img  class="img-responsive col-md-4" src="../../images/default_artist.png">
                </c:otherwise>
            </c:choose>
        </div>
        <hr>
        <h2><fmt:message key="artist.header.description"/></h2>
        <div class="row">
            <p>${currentArtist.description}</p>
        </div>
        <hr>
        <h2><fmt:message key="artist.header.info"/></h2>
        <div class="row">
            <div class="table-responsive col-md-8">
                <table class="table table-condensed">
                    <tbody>
                    <tr>
                        <td><fmt:message key="artist.country"/></td>
                        <td>${currentArtist.country} </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="artist.albumAmount"/></td>
                        <td>${fn:length(currentArtistAlbums)}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <hr>
        <h2><fmt:message key="menu.albums"/></h2>
        <div class="row">
            <div class="table-responsive col-md-8">
                <table class="table table-condensed">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="album.albumTitle"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="1" scope="page"/>
                    <c:forEach var="album" items="${currentArtistAlbums}">
                        <tr>
                            <td>${i}</td>
                            <td><a href="/s?command=show_element&type=album&id=${album.albumId}">${album.title}</a></td>
                        </tr>
                        <c:set var="i" value="${i+1}" scope="page"/>
                    </c:forEach>
                    </tbody>
                </table>
                <adt:emptyList items="${currentArtistAlbums}"><fmt:message key="artist.noAlbums"/></adt:emptyList>
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
