<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false" %>
<html>

<head>
  <%@include file="../common/common-header.jsp"%>
</head>

<body>
<table>
  <tr>
    <th>用户名</th>
    <th>邮箱</th>
  </tr>

  <c:if test="${not empty users}">
    <c:forEach var="user" items="${users}">
      <tr>
        <td><c:out value="${user.username}"/> </td>
        <td><c:out value="${user.email}"/> </td>
        <td>
          <a href="/users/modify/${user.email}" class="btn btn-info" role="button">Modify User's Information</a>
        </td>
        <td>
          <a href="/users/delete/${user.email}" class="btn btn-info" role="button">Delete User</a>
        </td>
      </tr>
    </c:forEach>
  </c:if>

</table>
</body>
</html>
