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
<h2>合同待财务审核列表</h2>
<table class="table table-bordered">
<tr>
	<th>流程名称</th>
	<th>合同编号</th>
	<th>当前流程</th>
	<th></th>
</tr>
<c:forEach items="${taskList }" var="task">
<tr>
<td>${task.processName }</td>
<td>${task.businessKey }</td>
<td>${task.taskName }</td>
<td><button onclick="doSubmit('${task.businessKey }')">审核</button>&nbsp;&nbsp;<a href="/show?key=${task.businessKey }">视图</a></td>
</tr>
</c:forEach>
</table>
</div>
</div>
</div>
</body>
<script type="application/javascript">
function doSubmit(key) {
	$.ajax({
        type: 'get',
        url: '${pageContext.request.contextPath}' + '/conrtact/audit/finance',
        data: { "contractNo": key },
        success: function (data, status) {
        	alert("成功");
        	window.location.reload(); 
        },
        error: function (xhr, status, error) {
        	alert("失败");
        }
    });
}
</script>
</html>
