<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/20
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1"/>
    <meta name="description" content="XXXXXX"/>
    <meta name="author" content="刘子健"/>

    <title>图书管理系统</title>

    <!-- Bootstrap Core CSS -->
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>

    <script src="/static/bootstrap/js/jquery.js"></script>

    <!-- For check inputted form -->
    <script src="/static/bootstrap/js/jquery.validate.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>

    <style>
        body {padding-top: 70px;}
    </style>

</head>
<body>

<%@include file="../common/navBar.jsp"%>

<div class="container">
    <h2>Search Results </h2>
    <p>Here is the search results:</p>
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>Record ID</th>
            <th>Book ID</th>
            <th>User ID</th>
            <th>Record Type</th>
            <th>Date</th>
            <th><th> <!-- View Detail-->
            <th><th> <!-- Delete -->
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty records}">
            <c:forEach var="record" items="${records}">
                <tr>
                    <td><c:out value="${record.id}"></c:out></td>
                    <td><c:out value="${record.bookId}"></c:out></td>
                    <td><c:out value="${record.userId}"></c:out></td>
                    <td><c:out value="${record.recordtype}"></c:out></td>
                    <td><c:out value="${record.date}"></c:out></td>
                    <c:if test="${not empty admin}">
                        <td><a href="/records/delete/${record.id}" class="btn btn-info" role="button">Delete</a></td>
                    </c:if>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <c:if test="${not empty pageInfo}">
        <div id="pagination">

            <ul class="pagination">

                <c:if test="${pageInfo.isHasPrevPage()}">
                    <li class="page-item">
                        <a class="page-link" href="${pageInfo.URL}?pageNum=${pageInfo.currentPageNum - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </li>
                </c:if>


                    <%--<li class="page-item active">--%>
                    <%--<a class="page-link" href="#">1 <span class="sr-only">(current)</span></a>--%>
                    <%--</li>--%>
                    <%--<li class="page-item"><a class="page-link" href="#">2</a></li>--%>
                    <%--<li class="page-item"><a class="page-link" href="#">3</a></li>--%>
                    <%--<li class="page-item"><a class="page-link" href="#">4</a></li>--%>
                    <%--<li class="page-item"><a class="page-link" href="#">5</a></li>--%>

                <c:if test="${pageInfo.isHasNextPage()}">
                    <li class="page-item">
                        <a class="page-link" href="${pageInfo.URL}?pageNum=${pageInfo.currentPageNum + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">Next</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </c:if>
</div>

</body>
</html>

