<html>
<head>
    <!-- Bootstrap Core CSS -->
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- For check inputted form -->
    <script src="/static/bootstrap/js/jquery.js"></script>
    <script src="/static/bootstrap/js/jquery.validate.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>

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
                <h3 class="panel-title">Search Unit</h3>
            </div>
            <div class="panel-body">
                <form id="bookForm" method="post" class="form-horizontal" action="/books/query">

                    <div class="row">
                        <div class="form-group-inline">
                            <label class="col-sm-1 control-label" for="isbn">ISBN</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" id="isbn" name="isbn" placeholder="ISBN" >
                            </div>
                        </div>

                        <div class="form-group-inline">
                            <label class="col-sm-1 control-label" for="bookname">Book Name</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" id="bookname" name="bookname" placeholder="bookname">
                            </div>
                        </div>

                        <div class="form-group-inline">
                            <label class="col-sm-1 control-label" for="author">Author</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" id="author" name="author" placeholder="Author">
                            </div>
                        </div>
                    </div>

                    <div class="row">

                        <div class="form-group-inline">
                            <label class="col-sm-1 control-label">Price Range</label>
                            <div class="col-sm-1">
                                <input type="number" class="form-control" id="priceLowBound" name="priceLowBound" placeholder="price">
                            </div>
                            <div class="col-sm-1">
                                <input type="number" class="form-control" id="priceUpBound"  name="priceUpBound" placeholder="price">
                            </div>
                        </div>


                        <div class="form-group-inline">
                            <label class="col-sm-1 control-label" for="booktype">Book Type</label>
                            <div class="col-sm-2">
                                <select id="booktype" name="booktype" class="form-control">
                                    <option value="0">Computer Science</option>
                                    <option value="1">Machine Learning</option>
                                    <option value="2">Novel</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <br>

                    <div class="row">
                        <div class="form-group">
                            <div class="col-lg-6 col-lg-offset-3">
                                <button type="submit" class="btn btn-primary btn-block" name="searchBook" value="searchBook">Search</button>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

</body>
</html>
