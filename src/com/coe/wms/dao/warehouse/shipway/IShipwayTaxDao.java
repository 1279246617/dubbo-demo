package com.coe.wms.dao.warehouse.shipway;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.shipway.ShipwayTax;
import com.coe.wms.util.Pagination;

public interface IShipwayTaxDao {

	public List<ShipwayTax> findShipwayTax(ShipwayTax shipwayTax, Map<String, String> moreParam, Pagination page);

	public Long countShipwayTax(ShipwayTax shipwayTax, Map<String, String> moreParam);

	public ShipwayTax getShipwayTaxById(Long id);

	public ShipwayTax findShipwayTaxByReferenceNo(Long customerId, String barcode);

	public long addShipwayTax(ShipwayTax shipwayTax);

	public int deleteShipwayTaxById(Long id);

	public int deleteShipwayTaxByIds(String ids);

	public int updateShipwayTax(ShipwayTax shipwayTax);
}
