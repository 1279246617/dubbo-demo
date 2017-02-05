/**    
* @Title: UserAction.java
* @Package com.coe.wms.web.sso
* @author yechao
* @date 2017年2月5日 下午3:26:11
* @version V1.0
* @Description: TODO   
*/
package com.coe.wms.web.sso;

import org.springframework.stereotype.Controller;

import com.coe.wms.facade.sso.service.UserFacade;
/**
 * @ClassName: UserAction
 * @author yechao
 * @date 2017年2月5日 下午3:26:11
 * @Description: TODO
 */
@Controller
public class UserAction {

	private UserFacade userFacade;

	public void create() {
		userFacade.create();
	}
}
