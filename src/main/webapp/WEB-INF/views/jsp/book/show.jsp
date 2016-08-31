<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <%@include file="../common/common-header.jsp"%>
</head>

<body>

<table>
    <tr>
        <th>ISBN</th>
        <th>书籍名称</th>
        <th>作者</th>
        <th>价格</th>
        <th>出版社</th>
        <th>出版日期</th>
        <th>书籍类型</th>
        <th>书籍编码</th>
        <th>馆藏地址</th>
    </tr>
    <tr>
        <c:if test="${empty books}">
            books is Empty!
        </c:if>

        <c:if test="${not empty books}">
            <c:forEach var="book" items="${books}">
                <c:out value="${book.isbn}"/>
                <td><c:out value="${book.isbn}"/> </td>
                <td><c:out value="${book.bookname}"/> </td>
                <td><c:out value="${book.price}"/> </td>
                <td><c:out value="${book.author}"/> </td>
            </c:forEach>
        </c:if>
    </tr>
</table>

</body>
</html>
