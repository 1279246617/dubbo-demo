<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />

<body class="print-page-label">
			<div class="label-100-150 change-page mt_1mm">
				<div style="height:10mm;width:100%; border-bottom:0px solid  #000;text-align: center" >
						<div style="width: 38mm;float: left;margin-top: 0.5mm;margin-left:25mm; font-size: 5mm;;">
							${map.outWarehouseRecord.coeTrackingNo}
						</div>
				</div>
				<div style="height:23mm;width:96mm; border-bottom:0px solid  #000;">
						 <div style="width:4mm;line-height:3.5mm;font-weight: bold;" class="pull-left">
						 		<font size="3.5">F<br>R</font><br>/<br>寄件人
						 </div>
						 
						 <div style="width:90mm;line-height:3.5mm;"  class="pull-left">
						 		COE香港仓
						 </div>
				</div>
				
				<div style="height:23mm;width:96mm; border-bottom:2px solid  #000;font-size: 12px;">
					<div style="margin-left: 2mm;" >
							<table style="line-height: 3mm;width:80mm;" class="pull-left">
								<tr>	
									<td >ShipDate/發貨日:${map.shipdate}</td>
									<td>WT/實重: ${map.totalWeight}kg</td>
								</tr>
								<tr>	
									<td >Origin ID/始發地:HK</td>
									<td>DWT/體積重: ${map.totalWeight}kg</td>
								</tr>
								<tr>	
									<td >SVC/服務:HKG</td>
									<td>Payment/付費方式:PP</td>
								</tr>
								<tr>	
									<td >&nbsp;</td>
									<td>CC Charge/到付款:0</td>
								</tr>
								<tr>	
									<td >&nbsp;</td>
									<td>DC Charge/代付款:0</td>
								</tr>
							</table>
							
							<div style="widht:15mm;text-align: left" class="pull-left">
								品名:
							</div>
					</div>
				</div>
		</div>
</body>
</html>
