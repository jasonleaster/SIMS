<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>BookOcean Homepage - Find the book</title>

    <!-- Bootstrap Core CSS -->
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/static/css/shop-homepage.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<%@include file="./common/navBar.jsp"%>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <div class="col-md-3">
            <p class="lead">Category</p>
            <div class="list-group">
                <a href="/books/query/type/0" class="list-group-item">Computer Science</a>
                <a href="/books/query/type/1" class="list-group-item">Machine Learning</a>
                <a href="/books/query/type/2" class="list-group-item">Novel</a>
            </div>
        </div>

        <div class="col-md-9">
            <div class="row">
                <c:if test="${not empty books}">
                    <c:forEach var="book" items="${books}">
                        <div class="col-sm-4 col-lg-4 col-md-4">
                            <div class="thumbnail">
                                <img src="/static/books/pdf/${book.isbn}.png" alt="" width="320" height="150">
                                <div class="caption">
                                    <h4 class="pull-right">${book.price}</h4>
                                    <h4><a href="/books/query/${book.isbn}"><c:out value="${book.bookname}"></c:out></a>
                                    </h4>
                                    <p>${book.description}</p>
                                </div>
                                <div class="ratings">
                                    <p class="pull-right">15 reviews</p>
                                    <p>
                                        <span class="glyphicon glyphicon-star"></span>
                                        <span class="glyphicon glyphicon-star"></span>
                                        <span class="glyphicon glyphicon-star"></span>
                                        <span class="glyphicon glyphicon-star"></span>
                                        <span class="glyphicon glyphicon-star"></span>
                                    </p>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>

</div>
<!-- /.container -->

<div class="container">

    <hr>

    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright &copy; Your Website 2014</p>
            </div>
        </div>
    </footer>

</div>
<!-- /.container -->

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>

</html>
