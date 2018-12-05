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
	<div class="panel-body col-md-3">
		<div class="form-group">
		    <label for="packageNo">包名：</label>
		    <input type="text" class="form-control" id="packageNo" value="${packageNo }">
		</div>
		<div class="form-group">
		    <label for="gameName">游戏：</label>
		    <input type="text" class="form-control" id="gameName" value="${gameName }">
		</div>
		<button type="submit" class="btn btn-default" onclick="doSubmit()">提交</button>
	</div>
</div>
<script type="application/javascript">
function doSubmit() {
	var param = {
        "packageNo":$("#packageNo").val(),
        "gameName": $("#gameName").val()
    };
	$.ajax({
        type: 'post',
        url: '${pageContext.request.contextPath}' + '/package/complete',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(param),
        success: function (data, status) {
        	alert("成功");
        },
        error: function (xhr, status, error) {
        	alert("失败");
        }
    });
}
</script>
</body>
</html>
