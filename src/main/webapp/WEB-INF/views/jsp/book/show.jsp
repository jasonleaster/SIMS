<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>

<!-- Don't for get the @pageEncoding option in JSP, otherwise, chinese character will be shown correctly -->
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false" %> <!--Don't for get This !! Otherwise, you can use the extention language in JSP ! -->
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

    <c:if test="${not empty books}">
        <c:forEach var="book" items="${books}">
            <tr>
                <td><c:out value="${book.isbn}"/> </td>
                <td><c:out value="${book.isbn}"/> </td>
                <td><c:out value="${book.bookname}"/> </td>
                <td><c:out value="${book.price}"/> </td>
                <td><c:out value="${book.author}"/> </td>
                <td>
                    <a href="/books/download/${book.isbn}" class="btn btn-info" role="button">Download</a>
                </td>
                <td>
                    <a href="/books/delete/${book.isbn}" class="btn btn-info" role="button">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>

</table>

</body>
</html>
