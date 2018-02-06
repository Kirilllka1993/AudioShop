<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${visitor.locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row nav-footer">
            <div class="col-md-3 col-sm-3  col-sx-3 footer-wrap">
                <header><fmt:message key="footer.menu"/></header>
                <ul>
                    <li><a href="/jsp/user/main.jsp"><fmt:message key="nav.home"/></a></li>
                    <li><a href="/jsp/user/catalog_tracks.jsp"><fmt:message key="nav.catalog"/></a></li>
                    <li><a href="/jsp/user/items.jsp"><fmt:message key="nav.items"/></a></li>
                </ul>
            </div>
            <div class="col-md-3  col-sm-3 col-sx-3  footer-wrap">
                <header><fmt:message key="footer.profile"/></header>
                <ul>
                    <li><a href="/jsp/user/settings.jsp"><fmt:message key="nav.account.settings"/></a></li>
                    <li><a href="/jsp/user/purchases.jsp"><fmt:message key="nav.account.purchases"/></a></li>
                    <li><a href="/s?command=log_out"><fmt:message key="nav.account.logout"/></a></li>
                </ul>
            </div>
            <div class="col-md-3  col-sm-3  col-sx-3 footer-wrap">
                <header><fmt:message key="label.language"/></header>
                <ul>
                    <li><a href="/s?command=change_language&locale=en_US"><fmt:message
                            key="label.language.en"/></a></li>
                    <li><a href="/s?command=change_language&locale=ru_RU"><fmt:message
                            key="label.language.ru"/></a></li>
                </ul>
            </div>
            <div class="col-md-3  col-sm-3 col-sx-3 footer-wrap">
                <header><fmt:message key="footer.links"/></header>
                <ul>
                    <li><a href="https://www.facebook.com/" target="_blank"><fmt:message key="footer.links.facebook"/></a></li>
                    <li><a href="https://twitter.com/" target="_blank"><fmt:message key="footer.links.twitter"/></a></li>
                </ul>
            </div>
        </div>
        <div class="row copyright-wrap">
            <div class="col-md-12 col-sm-12">
                <p><fmt:message key="footer.copyright"/></p>
            </div>
        </div>
    </div>
</footer>
