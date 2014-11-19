<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${baseUrl}/static/bootstrap/bootstrap.min.css" rel="stylesheet"type="text/css" />
<link href="${baseUrl}/static/bootstrap/common.css" rel="stylesheet" type="text/css"/>
<title>COE</title>
</head>
<body >
      <table class="table" style="width: 100%;">
      		<tr>
      			<th>
      				按&nbsp;
      				<select style="width:100px;" name="noType" id="noType">
      					<option value="1">客户订单号</option>
      					<option value="2">顺丰跟踪号</option>
      				</select>	
      			</th>
      			<th>
      				<textarea type="text"  name="nos" id="nos" style="width:400px;height: 120px;" ></textarea>
      			</th>
      		</tr>
      </table>

	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.js"></script>
    <script type="text/javascript" src="${baseUrl}/static/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${baseUrl}/static/jquery/jquery.showMessage.js"></script>
</body>
</html>