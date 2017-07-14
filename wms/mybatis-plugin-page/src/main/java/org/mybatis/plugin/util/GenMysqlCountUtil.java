package org.mybatis.plugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenMysqlCountUtil {

	/** 结果集的子查询 */
	public static Pattern subquery = Pattern.compile("\\(\\s*select[^\\)]+\\)[\\s\\w]+,", Pattern.CASE_INSENSITIVE);
	/** 结果集最后一个子查询 */
	public static Pattern lastSubquery = Pattern.compile("\\(\\s*select[^\\)]+\\)[\\s\\w]+?(?!from)", Pattern.CASE_INSENSITIVE);
	/** 外层order by */
	public static Pattern pOrder = Pattern.compile("order\\s+by(\\s[\\w\\.]+(\\s*asc|\\s*desc)*)?(\\s*,\\s*[\\w\\.]+(\\s*asc|\\s*desc)*)*", Pattern.CASE_INSENSITIVE);
	/** 外层group by */
	public static Pattern pGroup = Pattern.compile("group\\s+by(\\s[\\w\\.]+)?(\\s*,\\s*[\\w\\.]+)*", Pattern.CASE_INSENSITIVE);
	/** 外层limit */
	public static Pattern pLimit = Pattern.compile("limit", Pattern.CASE_INSENSITIVE);
	/** sleect from */
	public static Pattern pSelectF = Pattern.compile("select[\\s\\S]+?from", Pattern.CASE_INSENSITIVE);
	/** count sum avg max min group_concat */
	public static Pattern pAggregate = Pattern.compile("max\\s*\\(|min\\s*\\(|count\\s*\\(|sum\\s*\\(|avg\\s*\\(|group_concat\\s*\\(",
			Pattern.CASE_INSENSITIVE);

	public static String genCountSql(String sql) {
		Matcher m = null;
		// 去掉结果集的子查询
		m = subquery.matcher(sql);
		while (m.find()) {
			sql = m.replaceAll("");
		}
		// 去掉结果集的最后一个子查询
		m = lastSubquery.matcher(sql);
		while (m.find()) {
			sql = m.replaceAll("");
		}

		int start = -1, end = -1;

		// 去掉最外层limit
		m = pLimit.matcher(sql);
		while (m.find()) {
			start = m.start();
			end = m.end();
		}
		if (start > sql.lastIndexOf(")")) {
			sql = sql.substring(0, start);
			start = -1;
			end = -1;
		}

		// 去掉最外层order by
		m = pOrder.matcher(sql);
		while (m.find()) {
			start = m.start();
			end = m.end();
		}
		if (start > sql.lastIndexOf(")")) {
			sql = sql.substring(0, start) + sql.substring(end, sql.length());
			start = -1;
			end = -1;
		}

		// 处理最外层group 转count (distinct)
		m = pGroup.matcher(sql);
		while (m.find()) {
			start = m.start();
			end = m.end();
		}
		String group = null;
		if (start > sql.lastIndexOf(")")) {
			group = sql.substring(start, end).replaceFirst("([Gg][Rr][Oo][Uu][Pp])\\s+([Bb][Yy])", "distinct(") + ")";
			sql = sql.substring(0, start) + sql.substring(end, sql.length());
			start = -1;
			end = -1;
		}

		// 获取select - from
		m = pSelectF.matcher(sql);
		if (!m.find()) {
			return null;
		}
		// 外层有group
		if (group != null) {
			return m.replaceFirst("select count(" + group + ") from");
		}
		// 外层无group by

		// 结果集有聚合函数
		if (pAggregate.matcher(m.group()).find()) {
			return "select 1";
		}

		return m.replaceFirst("select count(0) from");
	}
}
