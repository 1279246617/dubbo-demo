package com.coe.wms.common.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


/**
 * 用于将swagger集成到springmvc中的配置类
* @ClassName: MySwaggerConfig  
* @author wanyipeng  
* @date 2017年3月17日 下午2:01:23  
* @Description: TODO
 */
@Configuration
@EnableWebMvc // 如果没加这个会报错
@EnableSwagger // 上面三个注释都是必要的，无添加便会出现莫名奇妙的错误
public class MySwaggerConfig {

	private SpringSwaggerConfig springSwaggerConfig;

	/**
	 * Required to autowire SpringSwaggerConfig
	 */
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	/**
	 * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
	 * framework - allowing for multiple swagger groups i.e. same code base
	 * multiple swagger resource listings.
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("wmsms系统", // 应用名称
				"内部人员开发文档", // 文档名称
				"本API为了解决前端与后台连接问题", // 概述
				"邮箱", // 联系作者
				"无许可证", // 许可证明
				null);// url地址
		return apiInfo;
	}
}
