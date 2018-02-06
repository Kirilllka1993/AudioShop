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
    <title><fmt:message key="track.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%-- icon --%>
    <link rel="shortcut icon" href="../../images/favicon.ico"/>
    <%-- CSS --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap3_player.css">
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
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;<fmt:message
                    key="menu.openMenu"/></span>
        </div>
        <div class="row">
            <h1>${currentTrack.title}</h1>
        </div>
        <div class="row">
            <c:choose>
                <c:when test="${currentAlbum!=null && currentAlbum.image != null}">
                    <img class="img-responsive col-md-4"
                         src="/s?command=load_image&elementId=${currentTrack.albumId}&target=album"/>
                </c:when>
                <c:otherwise>
                    <img class="img-responsive col-md-4" src="../../images/default_album.png">
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <hr>
    <h2><fmt:message key="track.header.info"/></h2>
    <div class="row">
        <div class="table-responsive col-md-8">
            <table class="table table-condensed">
                <tbody>
                <tr>
                    <td><fmt:message key="menu.artists"/></td>
                    <td>
                        <adt:null test="${currentArtist}">
                            No artist
                        </adt:null>
                        <adt:notNull test="${currentArtist}">
                            <a href="/s?command=show_element&id=${currentArtist.artistId}&type=artist">${currentArtist.name}</a>
                        </adt:notNull>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="menu.albums"/></td>
                    <td>
                        <adt:null test="${currentAlbum}">
                            No album
                        </adt:null>
                        <adt:notNull test="${currentAlbum}">
                            <a href="/s?command=show_element&id=${currentAlbum.albumId}&type=album">${currentAlbum.title}</a>
                        </adt:notNull>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="track.genre"/></td>
                    <td>
                        <adt:null test="${currentGenre}">
                            No genre
                        </adt:null>
                        <adt:notNull test="${currentGenre}">
                            ${currentGenre.name}
                        </adt:notNull>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <hr>
    <h2><fmt:message key="track.header.purchase"/></h2>
    <div class="row">
        <h2>${currentTrack.price}$</h2>
        <form class="add-to-cart-form" method="post" action="/s">
            <button type="submit" class="btn-custom add-to-cart-button"
                    name="command" value="add_to_cart">
                <adt:notCartItem test="${currentTrack}" items="${cartItems}">
                    <fmt:message key="main.label.addToCart"/>
                </adt:notCartItem>
                <adt:cartItem test="${currentTrack}" items="${cartItems}">
                    In cart
                </adt:cartItem>
            </button>
            <input type="hidden" name="trackId" value="${currentTrack.trackId}">
        </form>
    </div>
    <hr>

    <h2><fmt:message key="track.header.playTrack"/></h2>
    <div class="row">
        <button class="btn-custom btn-play"
                onclick='playTrack(${currentTrack.trackId}, "${currentTrack.location}")'>
        </button>
    </div>
    <div class="row">
        <audio id="player" controls
               data-info-album-art="/s?command=load_image&elementId=${currentTrack.albumId}&target=album"
               data-info-album-title=""
               data-info-artist=""
               data-info-title="${currentTrack.title}"
               data-info-label=""
               data-info-year=""
               data-info-att-link="}">
            <source src="" type="audio/mpeg">
        </audio>
    </div>
    <hr>
    <h2><fmt:message key="track.header.leaveComments"/></h2>
    <div class="row">
        <form id="commentForm" action="/s" method="post">
            <label for="comment">Comment:</label>
            <textarea id="comment" class="form-control" rows="3" maxlength="500" name="comment" form="commentForm"
                      placeholder="Comment track..." style="background-color:transparent"></textarea>
            <button class="btn-custom " type="submit" name="command" value="leave_comment">Comment</button>
        </form>
    </div>
    <hr>

    <div id="comments">
        <c:forEach var="comment" items="${trackCommentList}">
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
        </c:forEach>
        <adt:emptyList items="${trackCommentList}"><fmt:message key="track.noComment"/></adt:emptyList>
    </div>

</main>

<c:import url="../../WEB-INF/jspf/footer.jsp"/>
<script src="../../js/jquery-3.1.1.min.js"></script>
<script src="../../js/bootstrap-3.3.1.min.js"></script>
<script src="../../js/jquery.form.min.js"></script>
<script src="../../js/bootstrap3_player.js"></script>
<script src="../../js/play_track.js"></script>
<script src="../../js/cart.js"></script>
<script src="../../js/comment.js"></script>

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
