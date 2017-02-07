package org.mybatis.generator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MyBatisGeneratorTest {

	@Test
    public void testGenerator() throws Exception {
    	   List<String> warnings = new ArrayList<String>();
           ConfigurationParser cp = new ConfigurationParser(warnings);
            //加载创建的配置文件初始化Configuration对象
           Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("generator.xml"));
          
           DefaultShellCallback shellCallback = new DefaultShellCallback(true);
           try {
   				MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
   				myBatisGenerator.generate(null);
   			} catch (Exception e) {
   				e.printStackTrace();
   			}
    }
    
}
