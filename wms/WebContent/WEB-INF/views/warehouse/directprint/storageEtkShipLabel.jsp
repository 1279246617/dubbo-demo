<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${baseUrl}/static/css/print/common.css" rel="stylesheet" type="text/css" />
<div class="label-etk-105-156 change-page mt_1mm"  id="etk">
		<div class="pull-left label-top-1">
	    	<!-- 第一行内容 -->
	    	<div class="etk">
			    e特快 
	    	</div>
	    	<div class="receiver"><b>收件人联</b></div>
	    	<div class="tracking-no-top1">${map.etkTrackingNo}</div>
	    	<!-- 第二行内容 -->
	    	<table style="height:10mm;margin-left:1mm;width: 100%">
	    		<tbody>
	    		<tr>
	    			<td style="width:50%;"><img class="etkpng" src="${baseUrl}/static/img/print/etk.png"></td>
	    			<td style="width:50%;">
	    					<div class="zone" style="margin-left: 6mm;">
	    						<div style="font-size: 41px;margin-top: -8px;">${map.shipwayExtra1}</div>
	    					</div>
	    			</td>
	    		</tr>
	    	</tbody>
	    </table>
    	<!-- 寄件人信息 居左-->
    	<div style="float: left;margin-left: 1mm;line-height: 4mm;font-size: 12px;">
    		<span style="line-height:3mm;">寄件人姓名及地址</span><br>
    		<span style="width:32mm;">${map.customerNo}</span>
    		<span style="margin-left:7mm;">852-27555000</span>
    		<br>
    		<span style="">Flat G &amp; H, 6/F, Blk 1,</span>
    		<br>
    		<span style="">Kwai Tak Industrial Centre,</span>
    		<br>
    		<span style="">No 15-33 Kwai Tak St, Kwai Chung</span>
    		<br>
    		<span style="">HK&nbsp;&nbsp;NT</span><span style="font-weight: bold;margin-left: 10mm;"><b>邮政编码</b>${map.receiver.postalCode}</span>
    		<br>
    	</div>
    	<!-- 收件人信息 巨右 -->
    	<div style="float: right;width:42mm;line-height: 4mm;font-size: 12px;">
    		<span style="line-height:3mm;">收件人姓名及地址</span>
    		<br>
    		<div style="float:left; overflow: hidden;width:16mm;height:3.5mm;">${map.receiverName}</div>
    		<div style="float:right;overflow: hidden;width:24mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
    		<br>
    		<div style="float: left;width:40mm;height:4mm;overflow:hidden;">
    			${map.receiver.addressLine1}
    		</div>	
    		<div style="width:15mm;float: left;">${map.receiver.city}</div>
    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.countryName}</div>
    		<br>
    		<div style="width:15mm;float: left;">总重</div>
    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.totalWeight}公斤</div>
    	</div>	
	 </div>
     
   <div class="pull-left label-top-2">
    	<div class="etk">e特快</div>
      	<div class="receiver" style="margin-top: -10mm;line-height:5.2mm;"><b>投递局联</b></div>
      	<br>
      	<!-- 收件人姓名及地址  居左-->
    	<div style="float: left;width:54mm;margin-left: 1mm;margin-top: 1mm;line-height: 3.9mm;font-size: 12px;">
    		<span style="line-height:5px;">收件人信息</span>
    		<div style="float:right; width: 35mm; overflow:hidden">${map.customerReferenceNo}</div>
    		<br>
    		<div style="float:left; overflow: hidden;width:20mm;height:3.5mm;">${map.receiverName}</div>
    		<div style="float:right;overflow: hidden;width:34mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
    		<div style="float:left;overflow: hidden;width:52mm;height:7mm;">
    				${map.receiver.addressLine1} ${map.receiver.addressLine2}
    		</div>
	    	<br>
	    	<div style="width:25mm;float: left;">${map.receiver.city}</div>
    		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.stateOrProvince}</div>
    		<br>
    		<div style="width:20mm;float: left;">${map.receiver.countryName}</div>
    		<div style="float: right;text-align: left;">邮政编码&nbsp;&nbsp;${map.receiver.postalCode}</div>
    		<br>
    		<div style="width:15mm;float: left;">运单号码</div>
    		<div style="float: right;text-align: left;">${map.etkTrackingNo}</div>
    		<br>
    		<div style="text-align: left;">
    			<img  style="margin-top:1mm;" src="data:image/png;base64,<c:out value="${map.trackingNoBarcodeData2}"/>">
            </div>
    	</div>
    	<div style="float: right;width:42mm;margin-right: -1mm;line-height: 3.9mm;font-size: 12px;">
    		<span>总重</span>
    		<div style="float: right;margin-right: 10mm;text-align: left;">
    			${map.totalWeight}公斤
    		</div>
    		<br>
    		<span>收件人簽名</span><br>
    		<div style="border-bottom:1px solid #000;margin-right: 5mm;height:16mm;"></div>
    		<span>日期</span><br>
    		<div style="border-bottom:1px solid #000;margin-right: 5mm;margin-left: 7mm;height:4mm;"></div>
   		<span>時間</span>
   		<div style="border-bottom:1px solid #000;margin-right: 5mm;margin-left: 7mm;height:4mm;"></div>
   	</div>
    </div>
    <div class="pull-left label-top-3">
   	<div class="etk">e特快</div>
     	<div class="receiver" style="line-height:5.2mm;"><b>海关联</b></div>
     	<br>
     	<!-- 寄件人信息 居左-->
   	<div style="float: left;line-height: 3.2mm;font-size: 12px;margin-left: 1mm;">
   		<span style="line-height:3mm;">寄件人姓名及地址</span>
   		<br>
   		<span style="width:32mm;">${map.customerNo}</span>
   		<br>
   		<span style="">Flat G &amp; H, 6/F, Blk 1,</span>
   		<br>
   		<span style="">Kwai Tak Industrial Centre,</span>
   		<br>
   		<span style="">No 15-33 Kwai Tak St</span>
   		<br>
   		<span style=""> Kwai Chung &nbsp;HK&nbsp;&nbsp;NT</span>
   		<br>
   		<span style="width:15mm;float: left;">运单号码</span>
   		<span style=";text-align: left;">${map.etkTrackingNo}</span>
   	</div>
 	
   	<!-- 收件人信息 巨右 -->
   	<div style="float: right;width:48mm;margin-right: 1mm;line-height: 3.2mm;font-size: 12px;">
   		<span style="line-height:3mm;">收件人姓名及地址</span>
   		<br>
   		<div style="float:left; overflow: hidden;width:16mm;height:3.5mm;">${map.receiverName}</div>
   		<div style="float:right;overflow: hidden;width:31mm;height:3.5mm;">${map.phoneOrMobileNumber}</div>
   		<span style="">${map.receiver.addressLine1} ${map.receiver.addressLine2}</span><br>
   		<div style="width:15mm;float: left;">${map.receiver.city}</div>
   		<div style="float: right;margin-right: 10mm;text-align: left;">${map.receiver.stateOrProvince}</div>
   		<br>
   		<div style="width:15mm;float: left;">${map.receiver.countryName}</div>
   		<div style="float: right;text-align: left; margin-right: 5mm">邮政编码&nbsp;&nbsp;${map.receiver.postalCode}</div>
   		<div style="width:15mm;float: left;">总重</div>
   		<div style="float: right;margin-right: 10mm;text-align: left;">${map.totalWeight}公斤</div>
   	</div>	
   	
   	<table style="float: left;border:2px solid #000;width:97mm;margin-left: 0.5mm;font-size: 12;line-height: 3mm;" rules="all">
   			<tbody><tr style="height:3mm">
   				<td colspan="5" style="text-align: center">货物信息</td>
   			</tr>
			<tr style="height:3mm">
				<td>货品详细描述</td>
				<td>重量</td>
				<td>单价</td>
				<td>件数</td>
				<td>海关申报值</td>
			</tr>
			<c:forEach var="customs"  items="${map.etkCustoms}">
				<tr style="height:3.2mm">
					<td style="width:130px;overflow: hidden;height:10px;"><c:out value="${customs.skuName}"/></td>
					<td><c:out value="${customs.customsWeight}"/></td>
					<td><c:out value="${customs.customsValue}"/></td>
					<td><c:out value="${customs.quantity}"/></td>
					<td><c:out value="${customs.totalvalue}"/></td>
				</tr>  
			</c:forEach>
			<tr style="height:4.5mm">
   				<td colspan="3" style="text-align: center">海关申报总值</td>
   				<td colspan="1" style="text-align: center">${map.currency}</td>
   				<td colspan="1" style="text-align: center">${map.totalPrice}</td>
   			</tr>
   		</tbody>
   	</table>
    </div> 
</div>	 
