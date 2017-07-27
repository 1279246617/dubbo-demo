package org.mybatis.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mybatis.generator.model.VoModel;
/**
 * 此类用于生成vo
* @ClassName: VoGenerate  
* @author lqg  
* @date 2017年7月11日 下午4:59:11  
* @Description: TODO
 */
public class VoGenerate {

	static String url = "jdbc:mysql://192.168.80.12:3306/ctcs?useUnicode=true&amp;characterEncoding=UTF-8";
	static String user = "kwikto";
	static String password = "gcb123456";
	static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("longtext", "String");
		map.put("bigint", "Long");
		map.put("datetime", "java.util.Date");
		map.put("char", "String");
		map.put("int", "Integer");
		map.put("varchar", "String");
		map.put("float", "Float");
		map.put("double", "Double");
		map.put("tinyint", "Integer");
	}

	public static void main(String[] args) throws Exception {

		
		/*
		<!-- s_server -->
        <!-- u_admin -->
        <!-- u_customer -->
        <!-- u_operator -->
        <!-- w_warehouse -->
        <!-- u_admin_warehouse_role -->
        <!-- u_menu -->
        <!-- u_role -->
        <!-- u_role_permission -->
		*/
		
		
		//表名对应的类名
		Map<String, String> tableMap=new HashMap<String, String>();
		tableMap.put("s_config", "Config");
//		tableMap.put("u_admin", "Admin");
//		tableMap.put("u_customer", "Customer");
//		tableMap.put("u_operator", "Operator");
//		tableMap.put("w_warehouse", "Warehouse");
//		tableMap.put("u_admin_warehouse_role", "AdminWarehouseRole");
//		tableMap.put("u_menu", "Menu");
//		tableMap.put("u_role", "Role");
//		tableMap.put("u_role_permission", "rolePermission");
		
		
		
		String packageVo="com.coe.wms.facade.symgmt.entity.vo";//包名
		
		String packageEnt="com.coe.wms.facade.symgmt.entity";//实体所在类包名
		
		//父路径
		String fileParentPath="E:/wmskaifa/mybatis-generator/src/main/java"; 
		//路径
		String path=fileParentPath+File.separator+"com/coe/wms/facade/symgmt/entity/vo";
		
		
		File file=new File(path);
		
		
		if(!file.exists()){
			file.mkdir();
		}
		
		
		Set<Entry<String, String>> entrySet = tableMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
		      //获取类名
			String beanName = entry.getValue();
			//Vo名字
			String voName=beanName+"Vo";
			//实体bean 名字
			String entName=beanName;
			String classVal=VoModel.CLASS;
			String bodyVal=VoModel.value;
			String voStr = classVal=classVal.replace("{0}", packageVo).replace("{4}", packageEnt+"."+entName).replace("{1}", voName).replace("{2}", entName).replace("{3}", bodyVal);
			//写入文件中
			FileOutputStream fileOutputStream=new FileOutputStream(path+File.separator+voName+".java");
			fileOutputStream.write(voStr.getBytes("utf-8"));
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		
		
	}
}
