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

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1>Book Ocean</h1>
                    <div class="description">
                        <p>
                            Find the book which you want!
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Login to our site</h3>
                            <p>Enter your user ID (your email) and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form id="userCreateForm" role="form" action="/register" method="post" class="login-form">

                            <div class="form-group">
                                <label class="sr-only" for="username">Username</label>
                                <input type="text" class="form-text form-control" id="username" name="username" placeholder="Username" required/>
                            </div>

                            <div class="form-group">
                                <label class="sr-only" for="email">UserID</label>
                                <input type="email" name="email" placeholder="UserID/Email..." class="form-username form-control" id="email" required/>
                            </div>

                            <div class="form-group">
                                <label class="sr-only" for="password">Password</label>
                                <input type="password" class="form-password form-control" id="password" name="password" placeholder="Password" required/>
                            </div>

                            <div class="form-group">
                                <label class="sr-only" for="confirmedPassword">Confirmed Password</label>
                                <input type="password" class="form-password form-control" id="confirmedPassword" name="confirmedPassword" placeholder="Confirm password" required/>
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

                            <button type="submit" class="btn">Sign in!</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 social-login">
                    <h3>...or login with:</h3>
                    <div class="social-login-buttons">
                        <a class="btn btn-link-2" href="#">
                            <i class="fa fa-facebook"></i> Facebook
                        </a>
                        <a class="btn btn-link-2" href="#">
                            <i class="fa fa-twitter"></i> Twitter
                        </a>
                        <a class="btn btn-link-2" href="#">
                            <i class="fa fa-google-plus"></i> Google Plus
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>




<!--[if lt IE 10]>
<script src="/static/js/placeholder.js"></script>
<![endif]-->

<script type="text/javascript" charset="utf-8">

    $( document ).ready( function () {
        $( "#userCreateForm" ).validate( {
            rules: {
                username:{
                  required: true
                },
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirmedPassword:{
                    required: true
                }
            },
            messages: {
                password: {
                    required: "Please enter the password",
                    minlength: "The length of the password must longer than 5 char"
                },
                confirmedPassword:{
                    required: "Please enter the confirmed password"
                },
                email: "Please enter the correct password, like: hello@gmail.com"
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



