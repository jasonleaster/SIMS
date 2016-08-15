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
    <form id="userCreateForm"  class="form-horizontal" method="post"action="/register">

      <div class="form-group">
        <label class="col-sm-4 control-label" for="username">姓名</label>
        <div class="col-sm-5">
          <input type="text" class="form-control" id="username" name="username" placeholder="Username" required/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label" for="email">邮箱</label>
        <div class="col-sm-5">
          <input type="email" class="form-control" id="email" name="email" placeholder="Email" required/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label" for="password">密码</label>
        <div class="col-sm-5">
          <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label" for="confirm_password">确认密码</label>
        <div class="col-sm-5">
          <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Confirm password" required/>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-5 col-sm-offset-4">
          <div class="checkbox">
            <label>
              <input type="checkbox" id="agree" name="agree" value="agree" required/>同意访问协议
            </label>
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-9 col-sm-offset-4">
          <button type="submit" class="btn btn-primary" name="signup" value="Sign up">Sign up</button>
        </div>
      </div>
    </form>
  </div>
</div>

<script type="text/javascript" charset="utf-8">

  $( document ).ready( function () {
    $( "#userCreateForm" ).validate( {
      rules: {
        username: {
          required: true,
          minlength: 2
        },
        password: {
          required: true,
          minlength: 5
        },
        confirm_password: {
          required: true,
          minlength: 5,
          equalTo: "#password"
        },
        email: {
          required: true,
          email: true
        },
        agree: "required"
      },
      messages: {
        username: {
          required: "请输入用户名",
          minlength: "你的名字至少包含两个字符"
        },
        password: {
          required: "请输入密码",
          minlength: "你的密码不能少于5个字符"
        },
        confirm_password: {
          required: "请重新确认您的密码",
          minlength: "你的密码不能少于5个字符",
          equalTo: "Please enter the same password as above"
        },
        email: "请输入正确的Email地址",
        agree: "注册用户必须同意并遵守本协议"
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
