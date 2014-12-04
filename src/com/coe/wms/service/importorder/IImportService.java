package com.coe.wms.service.importorder;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.coe.wms.exception.ServiceException;

/**
 * 
 * @author Administrator
 * 
 */
public interface IImportService {

	static final Logger logger = Logger.getLogger(IImportService.class);

	public Map<String, Object> executeImportInWarehouseOrder(Map<String, MultipartFile> fileMap, String userLoginName, Long warehouseId) throws ServiceException;
}
