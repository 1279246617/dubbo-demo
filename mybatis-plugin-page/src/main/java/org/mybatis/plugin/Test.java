package org.mybatis.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.plugin.util.GenMysqlCountUtil;

public class Test {

	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		String sql = GenMysqlCountUtil.genCountSql(sql());
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);

		System.out.println(sql);
	}

	public static String sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("(SELECT dict_name FROM s_dict_type WHERE id = u.dict_sex) AS sex, ");
		sb.append("u.id AS id, ");
		sb.append("u.user_code AS userCode, ");
		sb.append("u.user_name AS userName,");
		sb.append("u.dict_is_emp AS isEmp, ");
		sb.append("(SELECT dict_name FROM s_dict_type WHERE id = u.dict_status) AS posStatus, ");
		sb.append("u.site_code AS siteCode, ");
		sb.append("(SELECT dict_name FROM s_dict_type WHERE id = u.dict_dept) AS dept, ");
		sb.append("(SELECT dict_name FROM s_dict_type WHERE id = u.dict_position) AS pos, ");
		sb.append("GROUP_CONCAT(sg.group_name) AS groupName, ");
		sb.append("u.create_operator AS createOperator, ");
		sb.append("u.create_time AS createTime, ");
		sb.append("u.update_operator AS updateOperator, ");
		sb.append("u.update_time AS updateTime, ");
		sb.append("(SELECT dict_status FROM s_dict_type WHERE id = u.dict_is_enable_right) AS isEnableRight ");
		sb.append("FROM ");
		sb.append("u_user u ");
		sb.append("LEFT JOIN s_user_group ug ON u.user_code = ug.user_code ");
		sb.append("LEFT JOIN s_group sg  ON ug.group_code = sg.group_code ");
		sb.append("left join ( ");
		sb.append("select id from table ");
		sb.append(")b  ");
		sb.append("on a.id = b.id ");
		sb.append("		WHERE 1=1 ");
		sb.append("GROUP BY u.id order by u.name,ug.id limit 1,200");

		return sb.toString();
	}

}
