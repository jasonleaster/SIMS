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


<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Book Ocean</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li>
          <a href="#">About</a>
        </li>
        <li>
          <a href="#">Services</a>
        </li>
        <li>
          <a href="#">Contact</a>
        </li>
      </ul>
    </div>
    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container -->
</nav>

<div class="row">

</div>


<div class="container">
  <h2>Search Results </h2>
  <p>Here is the search results:</p>
  <table class="table table-condensed">
    <thead>
    <tr>
      <th>ISBN</th>
      <th>Book Name</th>
      <th>Author</th>
      <th><th> <!-- View Detail-->
      <th><th> <!-- Delete -->
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty books}">
        <c:forEach var="book" items="${books}">
        <tr>
            <td><c:out value="${book.isbn}"></c:out></td>
            <td><c:out value="${book.bookname}"></c:out></td>
            <td><c:out value="${book.author}"></c:out></td>
            <td><a href="/books/query/${book.isbn}"    class="btn btn-info" role="button">View Detail</a></td>
            <td><a href="/books/download/${book.isbn}" class="btn btn-info" role="button">Download The PDF</a></td>
        </tr>
        </c:forEach>
    </c:if>
    </tbody>
  </table>
</div>












<script type="text/javascript">
  $('.datepicker').datepicker({
    format: 'yyyy-mm-dd'
  });
</script>

<script>

  $( document ).ready( function () {
    $( "#bookForm" ).validate( {
      rules: {
        isbn: {
          required: true,
          minlength: 10
        },
        bookname: {
          required: true,
          minlength: 2
        },
        author: {
          required: true,
          minlength: 5,
        },
        price: {
          required: true,
          number: true
        },
        publisher:{
          required:false
        },
        publisheddate:{
          required:true,
          dateISO:true
        },
        booktype:{
          required:false
        },
        codeinlib:{
          required:false
        },
        locationinlib:{
          required:false
        },
        description:{
          required:true
        },
        file:{
          required:true
        },
        agree: "required"
      },
      messages: {
        isbn: {
          required: "书籍的ISBN必须填写",
          minlength: "长度不能少于10个字符"
        },
        bookname: {
          required: "书籍的名称必须填写",
          minlength: "长度不能少于2个字符"
        },
        file:{
          required: "必须上传相应的pdf文件"
        }
        // wait to complete.
      },
      errorElement: "em",
      errorPlacement: function ( error, element ) {
        // Add the `help-block` class to the error element
        error.addClass( "help-block" );

        if ( element.prop( "type" ) === "checkbox" ) {
          error.insertAfter( element.parent( "label" ) );
        } else {
          error.insertAfter( element );
        }
      },
      highlight: function ( element, errorClass, validClass ) {
        $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
      },
      unhighlight: function (element, errorClass, validClass) {
        $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
      }
    } );

  } );
</script>

</body>
</html>

