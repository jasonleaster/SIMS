<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/14
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false" %>
<html>
<head>
    <%@include file="../common/common-header.jsp"%>
</head>
<body>

<%@include file="../common/navBar.jsp"%>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">创建新用户</h3>
    </div>
    <div class="panel-body">
        <form id="userCreateForm"  class="form-horizontal" method="post"action="/user/create.html">

            <div class="form-group">
                <label class="col-sm-4 control-label" for="username">姓名</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" required/>
                </div>
            </div>

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
    $.validator.setDefaults( {
        submitHandler: function () {
            alert( "submitted!" );
        }
    } );

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
                    required: "Please enter a username",
                    minlength: "Your username must consist of at least 2 characters"
                },
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },
                email: "你好世界alid email address",
                agree: "Please accept our policy"
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

        $( "#signupForm1" ).validate( {
            rules: {
                firstname1: "required",
                lastname1: "required",
                username1: {
                    required: true,
                    minlength: 2
                },
                password1: {
                    required: true,
                    minlength: 5
                },
                confirm_password1: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password1"
                },
                email1: {
                    required: true,
                    email: true
                },
                agree1: "required"
            },
            messages: {
                firstname1: "Please enter your firstname",
                lastname1: "Please enter your lastname",
                username1: {
                    required: "Please enter a username",
                    minlength: "Your username must consist of at least 2 characters"
                },
                password1: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password1: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },
                email1: "Please enter a valid email address",
                agree1: "Please accept our policy"
            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( "help-block" );

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents( ".col-sm-5" ).addClass( "has-feedback" );

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                } else {
                    error.insertAfter( element );
                }

                // Add the span element, if doesn't exists, and apply the icon classes to it.
                if ( !element.next( "span" )[ 0 ] ) {
                    $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
                }
            },
            success: function ( label, element ) {
                // Add the span element, if doesn't exists, and apply the icon classes to it.
                if ( !$( element ).next( "span" )[ 0 ] ) {
                    $( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
                $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
            },
            unhighlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
                $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
            }
        } );
    } );
</script>

</body>
</html>
