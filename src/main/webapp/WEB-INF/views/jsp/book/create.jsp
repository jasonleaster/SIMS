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

<%@include file="../common/sideBar.jsp"%>

<div id="page-content-wrapper">

    <div class="container-fluid">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">新图书录入</h3>
            </div>
            <div class="panel-body">
                <form id="bookForm" method="post" class="form-horizontal" action="/books/create">
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="isbn">ISBN</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="isbn" name="isbn" placeholder="ISBN" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="bookname">书籍名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="bookname" name="bookname" placeholder="bookname" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="author">作者</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="author" name="author" placeholder="Author" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="price">价格</label>
                        <div class="col-sm-5">
                            <input type="number" class="form-control" id="price" name="price" placeholder="price" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="publisher">出版社</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="publisher" name="publisher" placeholder="Publisher" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">发行日期</label>
                        <div class="input-group date" data-provide="datepicker" data-date-format="yyyy-mm-dd">
                            <input type="text" name="publisheddate" class="form-control">
                            <div class="input-group-addon">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="booktype">书籍类型</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="booktype" name="booktype" placeholder="booktype" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="codeinlib">馆藏编码</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="codeinlib" name="codeinlib" placeholder="codeinlib" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="locationinlib">馆藏地址</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="locationinlib" name="locationinlib" placeholder="LocationInLib" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="description">书籍描述</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="description" name="description" placeholder="description" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-4">
                            <button type="submit" class="btn btn-primary" name="bookCreate" value="CreateBook">Create This Book</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="col-lg-6">
            <h3>书籍封面</h3>
            <img src="D:/pic3.jpg" class="img-thumbnail" alt="Cinque Terre" width="304" height="236"/>
            <div class = "caption">
                <div class="form-group">
                    <label for="exampleInputFile">上传书籍封面</label>
                    <input type="file" class="form-control-file" id="exampleInputFile"/>
                    <small class="text-muted">注意上传文件小于1M</small>
                </div>
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
