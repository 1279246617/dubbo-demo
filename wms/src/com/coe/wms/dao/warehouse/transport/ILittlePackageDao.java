package com.coe.wms.dao.warehouse.transport;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.transport.LittlePackage;
import com.coe.wms.util.Pagination;

public interface ILittlePackageDao {

	public long saveLittlePackage(LittlePackage littlePackage);

	public LittlePackage getLittlePackageById(Long LittlePackageId);

	public int deleteLittlePackageById(Long LittlePackageId);

	public List<LittlePackage> findLittlePackage(LittlePackage LittlePackage, Map<String, String> moreParam, Pagination page);

	public Long countLittlePackage(LittlePackage LittlePackage, Map<String, String> moreParam);

	public int updateLittlePackageSeatCode(LittlePackage LittlePackage);

	public int updateLittlePackageCallback(LittlePackage LittlePackage);

	public int updateLittlePackageStatus(LittlePackage LittlePackage);
	
	public int updateLittlePackageStatus(Long bigPackageId,String newStatus);

	public int receivedLittlePackage(LittlePackage LittlePackage);

	public List<Long> findCallbackUnSuccessPackageId();
}
