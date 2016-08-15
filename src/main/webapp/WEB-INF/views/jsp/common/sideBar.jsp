<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="sidebar-wrapper">
  <ul class="sidebar-nav">
    <li class="sidebar-brand">
      <a href="#">
        图书信息管理系统
      </a>
    </li>
    <li>
      <button type="button" class="btn btn-default">馆藏图书查询</button>
    </li>
    <li>
      <div class="btn-group">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          书籍信息管理 <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
          <li><a href="/books/create">录入书籍信息</a></li>
          <li><a href="/books/modify">修改书籍信息</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="/books/delete">删除书籍信息</a></li>
        </ul>
      </div>
    </li>
    <li>
      <div class="btn-group">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          会员信息管理 <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
          <li><a href="/users/query/">会员信息查询</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="/users/create/">创建新会员</a></li>
          <li><a href="/users/modify/">修改会员信息</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="/users/delete/">删除会员</a></li>
        </ul>
      </div>
    </li>
    <li>
      <div class="btn-group">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          借书/还书 <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
          <li><a href="#">借阅书籍信息录入</a></li>
          <li><a href="#">归还书籍信息录入</a></li>
        </ul>
      </div>
    </li>
    <li>
      <a href="#">关于</a>
    </li>
    <li>
      <a href="#">服务</a>
    </li>
    <li>
      <a href="#">联系</a>
    </li>
  </ul>
</div>
