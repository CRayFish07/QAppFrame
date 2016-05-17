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
	<title>${apiEntity.name}</title>
</head>

<body>
	<div id="main_table">
		<table border="1">
			<c:if test="${apiEntity.apiVersion != null}">
				<tr><td><span class="msgType">版本</span></td><td>${apiEntity.apiVersion}</td></tr>
			</c:if>
			<c:if test="${apiEntity.name != null}">
				<tr><td><span class="msgType">名称</span></td><td>${apiEntity.name}</td></tr>
			</c:if>
			<c:if test="${apiEntity.desc != null}">
				<tr><td><span class="msgType">描述</span></td><td>${apiEntity.desc}</td></tr>
			</c:if>
			<c:if test="${apiEntity.beanList != null }">
				<tr>
				<td><span class="msgType">JavaBean</span></td>
				<td>
				<c:forEach var="beanItem" items="${apiEntity.beanList}"	varStatus="status">
					<a href="${hostURL}/api/download/java/${beanItem.clazzName}.java?fullpath=${beanItem.fullPath}">${beanItem.clazzName}.java</a><span>&nbsp;&nbsp;</span>
				</c:forEach>
				</td>
				<tr>
				<td><span class="msgType">AppleBean</span></td>
				<td>
				<c:forEach var="beanItem" items="${apiEntity.beanList}"	varStatus="status">
					<a href="${hostURL}/api/download/ios/${beanItem.clazzName}.h?fullpath=${beanItem.fullPath}">${beanItem.clazzName}.h</a><span>&nbsp;&nbsp;</span>
				</c:forEach>
				</td>
				</tr>
			</c:if>
		</table>
		<div class="reqType">
			<span class="msgType">请求报文:v${apiEntity.reqBeanInfo.beanVersion}</span>
		</div>
		<table border="1">
			<tr>
				<th>字段名称</th>
				<th>字段中文</th>
				<th>字段类型</th>
				<th>是否必填</th>
				<th>字段说明</th>
			</tr>
			<c:forEach var="item" items="${apiEntity.reqBeanInfo.fieldList}"
				varStatus="status">
				<tr>
					<td>${item.fdName}</td>
					<td>${item.fdChName}</td>
					<td>${item.type}</td>
					<c:if test="${item.required == true}">
						<td><span>YES</span></td>
					</c:if>
					<c:if test="${item.required == false}">
						<td>N/A</td>
					</c:if>
					<td>${item.desc}</td>
				</tr>
				<c:if test="${item.type == 'List'}">
					<tr>
						<td>列表开始</td>
					</tr>
					<c:forEach var="item1" items="${item.beanInfo.fieldList}"
						varStatus="status">
						<tr>
							<td>${item1.fdName}</td>
							<td>${item1.fdChName}</td>
							<td>${item1.type}</td>
							<c:if test="${item1.required == true}">
								<td>YES</td>
							</c:if>
							<c:if test="${item1.required == false}">
								<td>N/A</td>
							</c:if>
							<td>${item1.desc}</td>
						</tr>
					</c:forEach>
					<tr>
						<td>列表结束</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<div class="reqType" >
			<span class="msgType">返回报文:v${apiEntity.rspBeanInfo.beanVersion}</span>
		</div>
		<table border="1">
			<tr>
				<th>字段名称</th>
				<th>字段中文</th>
				<th>字段类型</th>
				<th>是否必填</th>
				<th>字段说明</th>
			</tr>
			<c:forEach var="item" items="${apiEntity.rspBeanInfo.fieldList}"
				varStatus="status">
				<tr>
					<td>${item.fdName}</td>
					<td>${item.fdChName}</td>
					<td>${item.type}</td>
					<c:if test="${item.required == true}">
						<td>YES</td>
					</c:if>
					<c:if test="${item.required == false}">
						<td>N/A</td>
					</c:if>
					<td>${item.desc}</td>
				</tr>
				<c:if test="${item.type == 'List'}">
					<tr>
						<td>列表开始</td>
					</tr>
					<c:forEach var="item1" items="${item.beanInfo.fieldList}" varStatus="status">
						<tr>
							<td>${item1.fdName}</td>
							<td>${item1.fdChName}</td>
							<td>${item1.type}</td>
							<c:if test="${item1.required == true}">
								<td>YES</td>
							</c:if>
							<c:if test="${item1.required == false}">
								<td>N/A</td>
							</c:if>
							<td>${item1.desc}</td>
						</tr>
					</c:forEach>
					<tr>
						<td>列表结束</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		
		<div class="reqType" >
			<span class="msgType">更新记录</span>
		</div>
		<table>
			<tr><th>变更类型</th><th>变更内容</th></tr>
			<c:if test="${apiEntity.reqBeanInfo.beanUpdate != null}">
			<c:forEach var="updateItem" items="${apiEntity.reqBeanInfo.beanUpdate}" varStatus="status">
			<tr>
				<td>_request</td>
				<td>${updateItem}</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${apiEntity.rspBeanInfo.beanUpdate != null}">
			<c:forEach var="updateItem" items="${apiEntity.rspBeanInfo.beanUpdate}" varStatus="status">
			<tr>
				<td>RESPONSE</td>
				<td>${updateItem}</td>
			</tr>
			</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>
