package com.coe.wms.dao.warehouse.shipway;

import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;

public interface IShipwayApiAccountDao {

	public ShipwayApiAccount getShipwayApiAccountByUserId(Long userId,String shipwayCode);

}
