<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@include file="./common/common-header.jsp"%>
</head>

<body>

<%@include file="./common/sideBar.jsp"%>

<div id="page-content-wrapper">
  <div class="container">
    <div class="col-md-9">

      <header class="jumbotron hero-spacer" value="${user.userId}">
        <h1> 欢迎来到图书馆！</h1>
        <p>     世之奇伟、瑰怪、非常之观,常在险远,而人之所罕至焉.故非有志者不能至也.  -- 王安石</p>
        <p><a class="btn btn-primary btn-large">Call to action!</a>
        </p>
      </header>

      <div class="row">
        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book1.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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


        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book2.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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

        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book3.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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

        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book4.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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

        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book5.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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

        <div class="col-sm-4 col-lg-4 col-md-4">
          <div class="thumbnail">
            <img src="/images/book6.jpg" alt="" />
            <div class="caption-full">
              <h4><a href="#">毛茸茸</a></h4>
              <h5>日本/村上春树和安西水丸</h5>
              <h5>价格: 24.99 RMB</h5>
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
      </div>

    </div>
  </div>
</div>


</body>
</html>
