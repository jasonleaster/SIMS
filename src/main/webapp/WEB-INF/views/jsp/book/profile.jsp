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
    <!-- Bootstrap Core CSS -->
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- Bootstrap Core JavaScript -->
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>

    <style>
        body {padding-top: 70px;}
    </style>

</head>

<body>

<%@include file="../common/navBar.jsp"%>

<div class="container">
    <div class="row">
        <c:if test="${not empty book}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">图书信息</h3>
                </div>
                <div class="panel-body">

                    <div class="col-sm-4">
                        <div class="thumbnail">
                            <img src="/static/books/pdf/${book.isbn}.png" alt="hello" width="350" height="500">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <ul>
                            <li>BookName: <c:out value="${book.bookname}"/></li>
                            <li>Author: <c:out value="${book.author}"/></li>
                            <li>Publisher:  <c:out value="${book.publisher}"/></li>
                            <li>Price:  <c:out value="${book.price}"/></li>
                            <li>ISBN: <c:out value="${book.isbn}"/></li>
                        </ul>
                    </div>
                    <div class="col-sm-4">
                        <div class="row">
                            <a href="/books/download/${book.isbn}" class="btn btn-info" role="button">Download The PDF</a>
                        </div>
                        <br>
                        <div class="row">
                            <a href="#" class="btn btn-info" role="button">XXXX</a>
                        </div>

                    </div>

                </div>
            </div>
        </c:if>

    </div>

</div>
</body>
</html>
