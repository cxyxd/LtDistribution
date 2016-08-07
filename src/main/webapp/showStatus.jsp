<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'showStatus.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link href="css/bootstrap.min.css" rel="stylesheet">


<style type="text/css">
td {
	text-align: center; /*设置水平居中*/
	vertical-align: middle; /*设置垂直居中*/
}
</style>

</head>

<body>

	<table class="table table-bordered"
		style="width: 70%;margin-left: 15%;margin-top: -25;">
		<button class="btn btn-danger" style="    margin-left: 80%;    margin-top: 5%;">全部更新</button>
		<caption>
			最新的代码是${version}版 <br>
		</caption>

		<thead>
			<trclass="active">
			<th>编号</th>
			<th>ip</th>
			<th>地址</th>
			<th>版本号</th>
			<th>操作</th>
			</tr>
		</thead>
		<tbody>

			<c:out value="${list.size}" />
			<c:forEach var="recoreds" items="${listabc}" varStatus="status">
				<tr class="active">
					<td><c:out value="${status.count}" /></td>
					<td><c:out value="${recoreds.ip}" /></td>
					<td><c:out value="${recoreds.tomcat}" /></td>
					<td>123</td>
					<td><button class="btn btn-primary">更新</button></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</body>

<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
</html>
