package com.coe.wms.web.symgmt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.facade.symgmt.service.ConfigService;

/**
 * 获取配置信息
* @ClassName: ConfigController  
* @author lqg  
* @date 2017年7月19日 下午5:12:34  
* @Description: TODO
 */
@RestController
@RequestMapping("/symgmt/config")
public class ConfigController extends AbstractController{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Reference
	private ConfigService configService;
	
	/**
	 * 根据仓库代码获取配置
	 * @param wareHouseCode
	 * @return
	 */
	@RequestMapping("getConfigByWareHouseCode/{wareHouseCode}")
	public Result getConfigByWareHouseCode(@PathVariable("wareHouseCode") String wareHouseCode){
		Map<String, String> configByIdentification = configService.getConfigByIdentification(wareHouseCode);
		return Result.success(configByIdentification);
	}
}
