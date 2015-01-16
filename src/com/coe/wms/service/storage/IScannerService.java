package com.coe.wms.service.storage;

import com.coe.scanner.pojo.Response;

public interface IScannerService {

	public Response getOrderId(String content, Long userIdOfOperator);

	public Response getOnShelfInfo(String content, Long userIdOfOperator);

	public Response getOutShelfInfo(String content, Long userIdOfOperator);

	public Response getBatchNo(String content, Long userIdOfOperator);

	public Response onShelf(String content, Long userIdOfOperator);

	public Response outShelf(String content, Long userIdOfOperator);

	public Response getCoeTrackingNo(String content, Long userIdOfOperator);

	public Response bindingOrder(String content, Long userIdOfOperator);

	public Response unBindingOrder(String content, Long userIdOfOperator);

	public Response bindingOrderFinsh(String content, Long userIdOfOperator);
}
