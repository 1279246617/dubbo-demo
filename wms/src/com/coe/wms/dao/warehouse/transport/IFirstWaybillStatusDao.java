package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;

public interface IFirstWaybillStatusDao {

	public FirstWaybillStatus findFirstWaybillStatusByCode(String code);

	public List<FirstWaybillStatus> findAllFirstWaybillStatus();
}
