package com.coe.wms.web.symgmt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.facade.symgmt.entity.Warehouse;
import com.coe.wms.facade.symgmt.service.WarehouseService;

/**
 * 获取仓库信息配置
* @ClassName: WareHouseController  
* @author lqg  
* @date 2017年7月19日 下午5:13:58  
* @Description: TODO
 */
@RestController
@RequestMapping("/symgmt/wareHouse")
public class WareHouseController extends AbstractController{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Reference
	private WarehouseService warehouseService;
	
	/**
	 * 获取所有父级菜单
	 * @return
	 */
	@RequestMapping("getAllParentWarehouse")
	public Result getAllParentWarehouse(){
		List<Warehouse> allParentWarehouse = warehouseService.getAllParentWarehouse();
	    return	Result.success(allParentWarehouse);
	}
	
	
}
