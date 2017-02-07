package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * mybatis自动生成junit的Mapper测试插件
 * @author cai
 *
 */
public class MybatisMapperJunitPlugin extends PluginAdapter{
	
	private FullyQualifiedJavaType junit;  //juit测试类
	private FullyQualifiedJavaType assertType; //断言
	private FullyQualifiedJavaType autowired;  //spring自动注入
	private FullyQualifiedJavaType daoType;    //Mapper
	private FullyQualifiedJavaType pojoType;   //Entity实体
	private FullyQualifiedJavaType testType;   //测试例子
	private FullyQualifiedJavaType pojoCriteriaType;  
	private FullyQualifiedJavaType listType;   //java.util.List
	
	private String targetPackage;
	private String superTestCase;
	private String project;
	private String testPojoUrl;
	private boolean enableAnnotation = true; //注解
	

	/**
	 * This plugin is always valid - no properties are required 
	 */
	@Override
	public boolean validate(List<String> warnings) {
		targetPackage = properties.getProperty("targetPackage");   //生成的package
		project = properties.getProperty("targetProject");
		testPojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();
		superTestCase=properties.getProperty("superTestCase");
		if (enableAnnotation) {
			autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
		}
		return true;
	}
	
	//初始化
	public MybatisMapperJunitPlugin() {
		super();
		junit=new FullyQualifiedJavaType("org.junit.Test");
		assertType=new FullyQualifiedJavaType("static org.junit.Assert.assertEquals");
	}
	
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
		String table = introspectedTable.getBaseRecordType();
		String tableName = table.replaceAll(this.testPojoUrl + ".", "");
		// mybatis
		daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());


		testType = new FullyQualifiedJavaType(targetPackage + "." + tableName+"MapperTest");
		String pojoPackage=targetPackage+".entity."+tableName;
		pojoType=  new FullyQualifiedJavaType(pojoPackage);
		String pojo = targetPackage+".criteria."+tableName;
		pojoCriteriaType = new FullyQualifiedJavaType(pojo + "Criteria");
		listType=new FullyQualifiedJavaType("java.util.List");
		TopLevelClass topLevelClass = new TopLevelClass(testType);
		// 导入必要的类
		addImport(topLevelClass);

		// 实现类
		addTestPojo(topLevelClass,introspectedTable, tableName, files);

		return files;
	}

	private void addTestPojo(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable, String tableName,
			List<GeneratedJavaFile> files) {
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(superTestCase);
		// 添加引用dao
		addField(topLevelClass, tableName);
		// 添加基础方法
		topLevelClass.addMethod(addCRUD(topLevelClass,introspectedTable, tableName));
	
		// 生成文件
		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project,
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
		files.add(file);
		
	}
	
	//CRUD测试方法
	private Method addCRUD(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName("crudTest");
		method.setReturnType(new FullyQualifiedJavaType("void"));
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addAnnotation("@Test");
		StringBuilder sb = new StringBuilder();
		method.addBodyLine(pojoType.getShortName()+" record=new "+pojoType.getShortName()+"();");
		List<IntrospectedColumn> introspectedColumnsList=introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			String property=introspectedColumn.getJavaProperty();
			if(introspectedColumn.isStringColumn()&&!property.toUpperCase().equals("ID")){
				method.addBodyLine("record.set"+toUpperCase(property)+"(\""+property+"\");");
			}
		}
		method.addBodyLine("//vaild insertRecord Test");
		method.addBodyLine("assertEquals(1,"+getDaoShort()+"insert(record));");
		method.addBodyLine("//vaild updateRecord Test");
		for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			String property=introspectedColumn.getJavaProperty();
			if(introspectedColumn.isStringColumn()&&!property.toUpperCase().equals("ID")){
				method.addBodyLine("record.set"+toUpperCase(property)+"(\""+toUpperCase(property)+"\");");
			}
			if(introspectedColumn.isJDBCDateColumn()||introspectedColumn.isJDBCTimeColumn()){
				topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Date"));
				method.addBodyLine("record.set"+toUpperCase(property)+"(new Date());");
			}
		}
		method.addBodyLine(getDaoShort()+"updateByPrimaryKeySelective(record);");
		for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			String property=introspectedColumn.getJavaProperty();
			if(introspectedColumn.isStringColumn()&&!property.toUpperCase().equals("ID")){
				method.addBodyLine("assertEquals(\""+toUpperCase(property)+"\","+"record.get"+toUpperCase(property)+"());");
			}
		}
		method.addBodyLine("//vaild selectRecord Test" );
		method.addBodyLine("assertEquals(record.getId(),"+getDaoShort()+"selectByPrimaryKey(record.getId()).getId());");
		method.addBodyLine("//vaild selectByConditon Test");
		method.addBodyLine(pojoCriteriaType.getShortName()+" criteria = new "+pojoCriteriaType.getShortName()+"();");
		method.addBodyLine("criteria.createCriteria().andIdEqualTo(record.getId());");
		method.addBodyLine("List<"+pojoType.getShortName()+"> "+pojoType.getShortName()+"s ="+getDaoShort()+"selectByConditionList(criteria);");
		method.addBodyLine("assertEquals(1,"+pojoType.getShortName()+"s.size());");
		method.addBodyLine("//vaild countByCondition Test");
		method.addBodyLine("int connt="+getDaoShort()+"countByCondition(criteria);");
		method.addBodyLine("assertEquals(1, connt);");
		method.addBodyLine("//vaild page Test");
		method.addBodyLine("criteria.setPage(1);");
		method.addBodyLine("criteria.setLimit(10);");
		method.addBodyLine(pojoType.getShortName()+"s ="+getDaoShort()+"selectByConditionList(criteria);");
		method.addBodyLine("assertEquals(1,"+pojoType.getShortName()+"s.size());");
		method.addBodyLine("//vaild deleteRecord Test");
		method.addBodyLine("assertEquals(1,"+getDaoShort()+"deleteByPrimaryKey(record.getId()));");
		return method;
	}

	private void addImport(TopLevelClass topLevelClass) {
		topLevelClass.addImportedType(assertType);
		topLevelClass.addImportedType(daoType);
		topLevelClass.addImportedType(pojoType);
		topLevelClass.addImportedType(junit);
		topLevelClass.addImportedType(superTestCase);
		topLevelClass.addImportedType(pojoCriteriaType);
		topLevelClass.addImportedType(listType);
		if (enableAnnotation) {
			topLevelClass.addImportedType(autowired);
		}
	}
	
	/**
	 * 添加字段
	 * 
	 * @param topLevelClass
	 */
	protected void addField(TopLevelClass topLevelClass, String tableName) {
		// 添加 dao
		Field field = new Field();
		field.setName(toLowerCase(daoType.getShortName())); // 设置变量名
		topLevelClass.addImportedType(daoType);
		field.setType(daoType); // 类型
		field.setVisibility(JavaVisibility.PRIVATE);
		if (enableAnnotation) {
			field.addAnnotation("@Autowired");
		}
		topLevelClass.addField(field);
	}
	
	/**
	 * BaseUsers to baseUsers
	 * 
	 * @param tableName
	 * @return
	 */
	protected String toLowerCase(String tableName) {
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}
	
	/**
	 * baseUsers to BaseUsers
	 * 
	 * @param tableName
	 * @return
	 */
	protected String toUpperCase(String tableName) {
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}
	
	private String getDaoShort() {
		return toLowerCase(daoType.getShortName()) + ".";
	}
	

}
