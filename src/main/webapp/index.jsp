<%--
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body><%--
<jsp:forward page="/s?command=forward&forward_action=index"/>--%>
<c:choose>
    <c:when test="${visitor.role eq 'GUEST'}">
        <jsp:forward page="/jsp/guest/register.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="/jsp/user/main.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>
