package org.mybatis.generator.model;

public class VoModel {
//{0}包名 ,{4}实体bean类全名,{1}类名,{2}实体bean类名,{3}类主体
	public static String CLASS="package {0}; import java.io.Serializable; import {4}; \r\n public class {1} extends {2} implements Serializable  { {3} }";

    //public static String value="private String voStart;//创建时间  开始 \r\n private String voEnd;//创建时间  结束 \r\n private String pageNumber;//页码 \r\n private String pageSize;//页面数量 \r\n public String getVoStart() {\r\n return voStart; } \r\n public void setVoStart(String voStart) {\r\n 	this.voStart = voStart; }\r\n public String getVoEnd() { \r\n return voEnd;\r\n } \r\n public void setVoEnd(String voEnd) { \r\n this.voEnd = voEnd;\r\n } \r\n public String getPageNumber() { \r\n return pageNumber;\r\n } \r\n public void setPageNumber(String pageNumber) {\r\n this.pageNumber = pageNumber;\r\n } \r\n public String getPageSize() {\r\n 	return pageSize;\r\n }\r\n public void setPageSize(String pageSize) {\r\n this.pageSize = pageSize;\r\n}";


    public static String value="private String voStart;//创建时间  开始 \r\n private String voEnd;//创建时间  结束 \r\n private Integer page=1;//页码 \r\n private Integer limit=50;//页面数量 \r\n public String getVoStart() {\r\n return voStart; } \r\n public void setVoStart(String voStart) {\r\n 	this.voStart = voStart; }\r\n public String getVoEnd() { \r\n return voEnd;\r\n } \r\n public void setVoEnd(String voEnd) { \r\n this.voEnd = voEnd;\r\n } \r\n public Integer getPage() { \r\n return page;\r\n } \r\n public void setPage(Integer page) {\r\n this.page = page;\r\n } \r\n public Integer getLimit() {\r\n 	return limit;\r\n }\r\n public void setLimit(Integer limit) {\r\n this.limit = limit;\r\n}";

	
}
