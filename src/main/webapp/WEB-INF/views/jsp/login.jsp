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

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">创建新用户</h3>
  </div>
  <div class="panel-body">
    <form id="loginForm"  class="form-horizontal" method="post" action="/login">

      <div class="form-group">
        <label class="col-sm-4 control-label" for="email">邮箱</label>
        <div class="col-sm-5">
          <input type="text" class="form-control" id="email" name="email" placeholder="Email" required/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label" for="password">密码</label>
        <div class="col-sm-5">
          <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-9 col-sm-offset-4">
          <button type="submit" class="btn btn-primary" name="login" value="Login">Login</button>
        </div>
      </div>
    </form>
  </div>
</div>

<script type="text/javascript" charset="utf-8">

  $( document ).ready( function () {
    $( "#loginForm" ).validate( {
      rules: {
        email: {
          required: true,
          email: true
        },
        password: {
          required: true,
          minlength: 5
        }
      },
      messages: {
        password: {
          required: "请输入密码",
          minlength: "密码长度不能少于5个字符"
        },
        email: "请输入正确行使的Email地址, 如: hello@gmail.com"
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
