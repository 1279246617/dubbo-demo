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

	public Map<String, String> saveMultipartFile(Map<String, MultipartFile> fileMap, Long userIdOfCustomer, Long warehouseId, String uploadDir) throws ServiceException;

	public Map<String, Object> validateImportInWarehouseOrder(String filePathAndName) throws ServiceException;

	public Map<String, Object> executeImportInWarehouseOrder(List<Map<String, String>> mapList, Long userIdOfCustomer, Long warehouseId, Long userIdOfOperator) throws ServiceException;

	public Map<String, Object> validateImportOutWarehouseOrder(String filePathAndName) throws ServiceException;

	public Map<String, Object> executeImportOutWarehouseOrder(List<Map<String, String>> mapList, Long userIdOfCustomer, Long warehouseId, Long userIdOfOperator) throws ServiceException;
}
