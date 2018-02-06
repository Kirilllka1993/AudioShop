<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.content"/>
<html>
<head>
    <title>Error</title>
</head>
<body>
<div class="container">
    <h1>Error!</h1>
    <p>${message}</p>
    <%--<p><c:out value="${sessionScope.message}"/></p>--%>
</div>
</body>
</html>
