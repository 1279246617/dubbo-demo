package com.coe.wms.web.symgmt;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//classpath*:spring/spring-servlet.xml
/**
 * 
* @ClassName: BaseWebTest  
* @author lqg  
* @date 2017年7月27日 下午4:44:04  
* @Description: TODO
* 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-servlet.xml" })
public class BaseWebTest {

}
