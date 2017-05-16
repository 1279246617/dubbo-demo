package org.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class PaginationPlugin extends PluginAdapter {

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// modify by yepeng, 集成分页插件 - change to extends QueryParam
		// add field, getter, setter for limit clause
		// addLimit(topLevelClass, introspectedTable, "page");
		// addLimit(topLevelClass, introspectedTable, "limit");
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
		isNotNullElement.addAttribute(new Attribute("test", "limit>0")); //$NON-NLS-1$ //$NON-NLS-2$
		isNotNullElement.addElement(new TextElement("limit ${start} , ${limit}"));
		element.addElement(isNotNullElement);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	/**
	 * This plugin is always valid - no properties are required
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

}
