package com.coe.wms.service.storage;

import com.coe.scanner.pojo.Response;

public interface IScannerService {

	public Response getOrderId(String content, Long userIdOfOperator );

	public Response getBatchNo(String content, Long userIdOfOperator );

	public Response onShelf(String content, Long userIdOfOperator );

	public Response outShelf(String content, Long userIdOfOperator );
}
