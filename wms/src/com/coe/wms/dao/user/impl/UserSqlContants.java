/**
 *
 * 2015年1月29日
 */
package com.coe.wms.dao.user.impl;

/**
 * @author yechao
 *
 */
public class UserSqlContants {
	
	
	public String  getfindUserByTokenSql(){
		String sql = "select id,parent_id,login_name,user_name,secret_key,token,opposite_secret_key,opposite_token,opposite_service_url,default_warehouse_id from u_user where token = ?";
		return sql;
	}

}


