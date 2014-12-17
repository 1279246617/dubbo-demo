package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.LittlePackageOnShelf;
import com.coe.wms.util.Pagination;

public interface ILittlePackageOnShelfDao {
	
	public long saveLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf);

	public List<LittlePackageOnShelf> findLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf, Map<String, String> moreParam, Pagination page);

	public Long countLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf, Map<String, String> moreParam);

	public int updateLittlePackageOnShelf(LittlePackageOnShelf littlePackageOnShelf);
	
	public String findSeatCodeForOnShelf(String businessType);
}
