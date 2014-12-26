package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.Shipway;

public interface IShipwayDao {

	public Shipway findShipwayByCode(String code);

	public List<Shipway> findAllShipway();
}
