package com.coe.wms.facade.symgmt.entity.vo; import java.io.Serializable; import com.coe.wms.facade.symgmt.entity.Server; 
 public class ServerVo extends Server implements Serializable  { private String voStart;//创建时间  开始 
 private String voEnd;//创建时间  结束 
 private Integer page=1;//页码 
 private Integer limit=50;//页面数量 
 public String getVoStart() {
 return voStart; } 
 public void setVoStart(String voStart) {
 	this.voStart = voStart; }
 public String getVoEnd() { 
 return voEnd;
 } 
 public void setVoEnd(String voEnd) { 
 this.voEnd = voEnd;
 } 
 public Integer getPage() { 
 return page;
 } 
 public void setPage(Integer page) {
 this.page = page;
 } 
 public Integer getLimit() {
 	return limit;
 }
 public void setLimit(Integer limit) {
 this.limit = limit;
} }