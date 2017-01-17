<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@include file="../common/common-header.jsp"%>
</head>
<body>

<div class="panel-body">
    <form id="bookSearch" method="post" class="form-horizontal" action="/users/query">
        <div class="form-group">
            <label class="col-sm-4 control-label" for="username">姓名</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="username" name="username" placeholder="User Name" required/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label" for="email">邮箱</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="email" name="email" placeholder="Email" required/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-4">
                <button type="submit" class="btn btn-primary" name="searchBook" value="searchBook">Search</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
