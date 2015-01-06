package com.coe.wms.service.storage;

import com.coe.scanner.pojo.Response;

public interface IScannerService {

	public Response login(String loginName, String password);

	public Response getOrderId(String content, String loginName, String password);

	public Response getBatchNo(String content, String loginName, String password);

	public Response onShelf(String content, String loginName, String password);

	public Response outShelf(String content, String loginName, String password);
}
