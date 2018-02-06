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
    <title>Create</title>
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
        <a href="../../jsp/admin/create.jsp">Create</a>
        <a href="../../jsp/admin/edit.jsp">Edit</a>
        <a href="../../jsp/admin/clients.jsp">Clients</a>
        <a href="../../jsp/admin/comments.jsp">Comments</a>
    </div>
    <div id="main">
        <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Open menu</span>
        <h1>Create</h1>
        <hr>
        <div class="row">
            <nav class="col-md-12">
                <ul>
                    <li class="nav-menu-item nav-menu-item-active" onclick="showForm('edit-track', this)"><fmt:message
                            key="main.addNew.track"/></li>
                    <li class="nav-menu-item nav-menu-item-inactive" onclick="showForm('edit-artist', this)">
                        <fmt:message
                                key="main.addNew.artist"/></li>
                    <li class="nav-menu-item nav-menu-item-inactive" onclick="showForm('edit-album', this)"><fmt:message
                            key="main.addNew.album"/></li>
                    <li class="nav-menu-item nav-menu-item-inactive" onclick="showForm('edit-genre', this)"><fmt:message
                            key="main.addNew.genre"/></li>
                </ul>
            </nav>
        </div>

        <div class="row">
            <section id="edit-track" class="active-section col-md-12">
                <form action="/s" method="post" enctype="multipart/form-data">
                    <select class="select-box js-example-responsive" style="width: 50%" name="elementId">
                        <c:forEach items="${trackList}" var="track">
                            <option value="${track.trackId}">${track.title}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="type" value="track">
                    <button class="btn-custom" type="submit" name="command" value="edit_element">Edit</button>
                </form>
                <%--<form method="post" action="/s" enctype="multipart/form-data">
                    <div class="row">
                        <input type="text" name="title" placeholder="<fmt:message key="main.addNew.form.track"/>">
                    </div>
                    <div class="row">
                        <input type="file" name="file" accept="audio/mpeg">
                    </div>
                    <div class="row">
                        <p>Genres</p>
                        <select class="select-box js-example-responsive" style="width: 50%" name="genre">
                            <c:forEach items="${genreList}" var="genre">
                                <option value="${genre.genreId}">${genre.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <input type="text" name="price" placeholder="<fmt:message key="main.addNew.form.price"/>"
                               required>
                    </div>
                    <div class="row">
                        <button class="btn-custom" type="submit" name="command" value="add_track" onclick="">
                            <fmt:message
                                    key="main.addNew.addTrack"/></button>
                    </div>
                </form>--%>
            </section>
            <section id="edit-artist" class="inactive-section col-md-12">
                <form action="/s" method="get" enctype="multipart/form-data">
                    <select class="select-box js-example-responsive" style="width: 50%" name="elementId">
                        <c:forEach items="${artistList}" var="artist">
                            <option value="${artist.artistId}">${artist.name}</option>
                        </c:forEach>
                        <input type="hidden" name="type" value="artist">
                        <button class="btn-custom" type="submit" name="command" value="edit_element">Edit</button>
                    </select>
                </form>
                <%--<form method="post" action="/s" enctype="multipart/form-data">
                    <div class="row">
                        Choose file<input type="file" name="artistImage">
                    </div>
                    <div class="row">
                        <input type="text" name="artistName" placeholder="<fmt:message key="main.addNew.form.artist"/>"
                               required>
                    </div>
                    <div class="row">
                        <input type="text" name="artistCountry"
                               placeholder="<fmt:message key="main.addNew.form.country"/>"
                        >
                    </div>
                    <div class="row">
                        <p>Albums</p>
                        <select class="select-box js-example-responsive" multiple="multiple" style="width: 50%"
                                name="artistAlbums">
                            <c:forEach items="${albumList}" var="album">
                                <option value="${album.albumId}">${album.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <input type="text" name="artistDescription" class="description"
                               placeholder="<fmt:message key="main.addNew.form.description"/>">
                    </div>
                    <div class="row">
                        <button class="btn-custom" type="submit" name="command" value="add_artist" onclick="">
                            <fmt:message
                                    key="main.addNew.addArtist"/></button>
                    </div>
                </form>--%>
            </section>
            <section id="edit-album" class="inactive-section col-md-12">
                <form action="/s" method="get" enctype="multipart/form-data">
                    <select class="select-box js-example-responsive" style="width: 50%" name="elementId">
                        <c:forEach items="${albumList}" var="album">
                            <option value="${album.albumId}">${album.title}</option>
                        </c:forEach>
                        <input type="hidden" name="type" value="album">
                        <button class="btn-custom" type="submit" name="command" value="edit_element">Edit</button>
                    </select>
                </form>
                <%-- <form method="post" action="/s" enctype="multipart/form-data">
                     <div class="row">
                         Choose file<input type="file" name="albumImage">
                     </div>
                     <div class="row">
                         <input type="text" name="albumTitle" placeholder="<fmt:message key="main.addNew.form.album"/>"
                                required>
                     </div>
                     <div class="row">
                         <input type="date" name="albumReleaseDate"
                                placeholder="<fmt:message key="main.addNew.form.releaseDate"/>">
                     </div>
                     <div class="row">
                         <p>Tracks</p>
                         <select class="select-box js-example-responsive" multiple="multiple" style="width: 50%"
                                 name="albumTracks">
                             <c:forEach items="${trackList}" var="track">
                                 <option value="${track.trackId}">${track.title}</option>
                             </c:forEach>
                         </select>
                     </div>
                     <div class="row">
                         <button class="btn-custom" type="submit" name="command" value="add_album" onclick="">
                             <fmt:message
                                     key="main.addNew.addAlbum"/></button>
                     </div>
                 </form>--%>
            </section>
            <section id="edit-genre" class="inactive-section col-md-12">
                <form action="/s" method="get" enctype="multipart/form-data">
                    <select class="select-box js-example-responsive" style="width: 50%" name="elementId">
                        <c:forEach items="${genreList}" var="genre">
                            <option value="${genre.genreId}">${genre.name}</option>
                        </c:forEach>
                        <input type="hidden" name="type" value="genre">
                        <button class="btn-custom" type="submit" name="command" value="edit_element">Edit</button>
                    </select>
                </form>
                <%--<form method="post" action="/s">
                    <div class="row">
                        <input type="text" name="genreName" placeholder="<fmt:message key="main.addNew.form.genre"/>"
                               required>
                    </div>
                    <div class="row">
                        <button class="btn-custom" type="submit" name="command" value="add_genre" onclick="">
                            <fmt:message
                                    key="main.addNew.addGenre"/></button>
                    </div>
                </form>--%>
            </section>
        </div>
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