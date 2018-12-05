<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/public/bootstrap-3.3.7/css/bootstrap.css" />
<script type="text/javascript" src="/public/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<%@include file="menu.jsp" %>
<div class="panel">
<div class="panel-body ">
<div class="row col-md-8">
<h2>流程任务列表</h2>
<table class="table table-bordered">
<tr>
	<th>流程名称</th>
	<th>BusinessKey</th>
	<th>当前流程</th>
	<th></th>
</tr>
<c:forEach items="${list }" var="task">
<tr>
<td>${task.processName }</td>
<td>${task.businessKey }</td>
<td>${task.taskName }</td>
<td><a href="/show?key=${task.businessKey }">视图</a></td>
</tr>
</c:forEach>
</table>
</div>
</div>
</div>

</body>
<script type="application/javascript">
</script>
</html>
