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
<%@include file="../menu.jsp" %>
<div class="panel">
<div class="panel-body ">
<div class="row col-md-8">
<table class="table table-bordered">
<tr>
	<th>包名</th>
	<th>游戏名</th>
	<th>当前流程</th>
	<th></th>
</tr>
<c:forEach items="${list }" var="task">
<tr>
<td>${task.businessKey }</td>
<td>${task.gameName }</td>
<td>${task.taskName }</td>
<td>
<button onclick="doSubmit('${task.businessKey }', 1)">审核通过</button>&nbsp;&nbsp;
<button onclick="doSubmit('${task.businessKey }', 0)">审核拒绝</button>&nbsp;&nbsp;
<a href="/package/modify/index?packageNo=${task.businessKey }">修改</a>&nbsp;&nbsp;
<a href="/show?key=${task.businessKey }">视图</a>
</td>
</tr>
</c:forEach>
</table>
</div>
</div>
</div>
</body>
<script type="application/javascript">
function doSubmit(key, state) {
	var param = {
        "packageNo": key,
        "state": state
    };
	$.ajax({
        type: 'post',
        url: '${pageContext.request.contextPath}' + '/package/complete',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(param),
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
