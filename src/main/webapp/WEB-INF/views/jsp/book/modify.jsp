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

  <!-- For check inputted form -->
  <script src="/static/bootstrap/js/jquery.js"></script>
  <script src="/static/bootstrap/js/jquery.validate.js"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="/static/bootstrap/js/bootstrap.min.js"></script>

  <!-- 日期选择的插件 CSS和JS -->
  <script src="/static/bootstrap/js/bootstrap-datepicker.js"></script>
  <link href="/static/bootstrap/css/bootstrap-datepicker.css" rel="stylesheet">
  <style>
    body {padding-top: 70px;}
  </style>
</head>
<body>

<%@include file="../common/navBar.jsp"%>

<div class="container">
  <div class="row">

    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">图书修改</h3>
      </div>
      <div class="panel-body">
        <c:if test="${not empty book}">
          <form id="bookForm" enctype="multipart/form-data" method="post" class="form-horizontal" action="/books/modify/update">

            <div class="row">
              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="isbn">ISBN</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="isbn" name="isbn" value="${book.isbn}" placeholder="ISBN" readonly/>
                </div>
              </div>

              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="bookname">书籍名</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="bookname" name="bookname" value="${book.bookname}" placeholder="bookname" required/>
                </div>
              </div>
            </div>

            <br>

            <div class="row">

              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="author">作者</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="author" name="author" value="${book.author}" placeholder="Author" required/>
                </div>
              </div>

              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="price">价格</label>
                <div class="col-sm-2">
                  <input type="number" class="form-control" id="price" name="price"  value="${book.price}" placeholder="price" required/>
                </div>
              </div>

            </div>

            <br>

            <div class="row">
              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="publisher">出版社</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="publisher" name="publisher" value="${book.publisher}" placeholder="Publisher" required/>
                </div>
              </div>

              <div class="form-group-inline">
                <label class="col-sm-2 control-label">发行日期</label>
                <div class="col-sm-2">
                  <div class="input-group date" data-provide="datepicker" data-date-format="yyyy-mm-dd">
                    <input type="text" name="publisheddate" value="${book.publisheddate}" class="form-control">
                    <div class="input-group-addon">
                      <span class="glyphicon glyphicon-th"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <br>

            <div class="row">

              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="booktype">书籍类型</label>
                <div class="col-sm-2">
                  <select id="booktype" name="booktype" class="form-control">
                    <option value="0" <c:if test="${book.booktype == 0}">selected</c:if> >Computer Science</option>
                    <option value="1" <c:if test="${book.booktype == 1}">selected</c:if> >Machine Learning</option>
                    <option value="2" <c:if test="${book.booktype == 2}">selected</c:if> >Novel</option>
                  </select>
                </div>
              </div>

              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="codeinlib">馆藏编码</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="codeinlib" name="codeinlib" value="${book.codeinlib}" placeholder="codeinlib" required/>
                </div>
              </div>

            </div>

            <br>
            <div class="row">
              <div class="form-group-inline">
                <label class="col-sm-2 control-label" for="description">书籍描述</label>
                <div class="col-sm-5">
                                    <textarea class="form-control" id="description" name="description" rows="3">
                                        <c:out value="${book.description}"></c:out>
                                    </textarea>
                </div>
              </div>

            </div>
            <br>

            <div class="row">
              <div class="form-group">
                <label class="col-sm-2 control-label" for="preface">书籍封面</label>
                <div class="col-sm-3">
                  <input type="file" class="form-control" id="preface" name="preface" value="${book.prefacePath}">
                </div>
              </div>
            </div>
            <br>
            <div class="form-group">
              <label class="col-sm-2 control-label" for="file">上传对应的pdf文件</label>
              <div class="col-sm-3">
                <input type="file" class="form-control" id="file" name="pdfFile" value="${book.pdfFilePath}" required/>
              </div>
              <small class="text-muted">注意上传文件小于1M</small>
            </div>

            <br>
            <div class="row">
              <div class="form-group">
                <div class="col-sm-9 col-sm-offset-4">
                  <button type="submit" class="btn btn-primary" name="commitModify" value="Commit Modify">Commit Modify</button>
                </div>
              </div>
            </div>

          </form>
        </c:if>
      </div>
    </div>
  </div>
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
        pdfFile:{
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
