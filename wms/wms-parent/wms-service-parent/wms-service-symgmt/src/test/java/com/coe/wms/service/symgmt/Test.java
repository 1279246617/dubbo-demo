package com.coe.wms.service.symgmt;

import java.util.ArrayList;
import java.util.List;

import com.coe.wms.facade.symgmt.entity.Config;
import com.coe.wms.facade.symgmt.entity.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test {
public static void main(String[] args) {
	List<Config> configList=new ArrayList<Config>();
	Config config=new Config();
	config.setId(0l);
	
	
	Config config1=new Config();
	config1.setId(1l);
	
	
	
	Config config2=new Config();
	config2.setId(2l);
	
	configList.add(config);
	configList.add(config1);
	configList.add(config2);
	
	
	Gson gson=new Gson();
	String configListStr=gson.toJson(configList);
	
	List<Config> list = gson.fromJson(configListStr, new TypeToken<List<Config>>() { }.getType());
    
	for (Config config3 : list) {
		System.out.println(gson.toJson(config3));
	}
	
}
}
