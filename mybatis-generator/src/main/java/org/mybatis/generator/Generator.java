package org.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

	public static void main(String[] args) throws SQLException, InterruptedException, InvalidConfigurationException {
           List<String> warnings = new ArrayList<String>();
           boolean overwrite = true;
           File configFile = new File("generator.xml");
           ConfigurationParser cp = new ConfigurationParser(warnings);
           Configuration config;
		try {
			config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	           MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	           myBatisGenerator.generate(null);
		} catch (IOException | XMLParserException e) {
			e.printStackTrace();
		}
           

	}

}
