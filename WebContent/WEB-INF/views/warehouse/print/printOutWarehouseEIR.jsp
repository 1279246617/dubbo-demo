<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<!-- print-page-a4 控制A4宽度, 不控制高度 -->
<body class="print-page-a4" style="font-size:12px;">
			<div class='a4-for-packagelist change-page'>
					<div style="height:17mm;weight:100%;">
						<div style="width:38%;height:15mm;" class="pull-left">
							仓库名称:${warehouse.warehouseName}
						</div>
						<div class="pull-left" style="width:62%;height:14mm;font-size: 7mm;font-weight: bold;margin-top: 1mm;">
							出库日期:${timeNow}
						</div>					
													
					</div>
			</div>
</body>
</html>