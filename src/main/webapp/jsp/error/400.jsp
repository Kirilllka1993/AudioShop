<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>400</title>
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
</head>
<body>

<c:if test="${visitor.role == 'GUEST'}">
    <c:redirect url="/jsp/guest/register.jsp"/>
</c:if>

<h1>400</h1>
<a href="../../jsp/user/main.jsp">Main page</a>

</body>
</html>
