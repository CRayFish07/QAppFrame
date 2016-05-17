<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<style type="text/css">
#main_table
{
	MARGIN-LEFT: 36px;
}

.msgType {
	padding: 3px;
	font-family: verdana, arial, sans-serif;
	font-size: 13px;
	font-weight: bold;
}

.reqType {
	margin-top: 20px;
	margin-bottom: 5px;
}

table {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table th {
	background-color: #c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table tr {
	background-color: #d4e3e5;
}

table td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>
	<title>${moduleName}</title>
</head>

<body>
	<div id="main_table">
		<table border="1">
			<tr><th>接口名称</th><th>接口地址</th></tr>
			<c:if test="${apiList != null }">
				<c:forEach var="apiItem" items="${apiList}"	varStatus="status">
				<tr>
				<td>
					<a href="${hostUrl}/${apiItem.apiUrl}">${apiItem.apiName}</a>
				</td>
				<td>
					${hostUrl}/${apiItem.apiUrl}
				</td>
				</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>
