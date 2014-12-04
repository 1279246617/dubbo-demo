package com.coe.wms.service.importorder;

import java.util.List;
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

	public Map<String, String> saveMultipartFile(Map<String, MultipartFile> fileMap, String userLoginName, Long warehouseId, String uploadDir) throws ServiceException;

	public Map<String, Object> validateImportInWarehouseOrder(String filePathAndName) throws ServiceException;

	public Map<String, Object> executeImportInWarehouseOrder(List<Map<String, String>> mapList, String userLoginName, Long warehouseId) throws ServiceException;
}
