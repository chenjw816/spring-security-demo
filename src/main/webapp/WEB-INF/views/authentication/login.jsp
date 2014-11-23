<%--
  Created by IntelliJ IDEA.
  User: yamorn
  Date: 2014/11/22
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>

<c:if test="${not empty requestScope.error}">
    <div class="error">${requestScope.error}</div>
</c:if>
<c:if test="${not empty requestScope.msg}">
    <div class="msg">${requestScope.msg}</div>
</c:if>
<form name='loginForm'
      action="<c:url value='/login' />" method='POST'>

    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='j_username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='j_password'/></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit"
                                   value="submit"/></td>
        </tr>
    </table>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

</form>
</body>
</html>
