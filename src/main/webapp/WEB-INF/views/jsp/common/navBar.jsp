<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false" %>

<!-- Navigation -->
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
            <a class="navbar-brand" href="/home">Book Ocean</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/books/search">Search Book</a>
                </li>
                <li>
                    <a href="/books/create">UpLoad Your Book</a>
                </li>
                <c:if test="${not empty admin}">
                    <li>
                        <a href="/users/query/all">User Management(Unsupported)</a>
                    </li>
                    <li>
                        <a href="/records/query/all">Records Management(Unsupported)</a>
                    </li>
                </c:if>

                <c:if test="${not empty user}">
                    <li>
                        <a href="/records/query?userId=${user.email}">My records</a>
                    </li>
                </c:if>

                <li>
                    <a href="#">Contact</a>
                </li>
                <li>
                    <a href="#">About</a>
                </li>

                <c:if test="${not empty user}">
                    <li>
                        <a href="/users/logout">LogOut</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>