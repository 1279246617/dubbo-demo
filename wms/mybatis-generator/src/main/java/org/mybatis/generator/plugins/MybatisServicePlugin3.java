package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 生成service类
 */
public class MybatisServicePlugin3 extends PluginAdapter {

	private FullyQualifiedJavaType slf4jLogger;
	private FullyQualifiedJavaType slf4jLoggerFactory;
	private FullyQualifiedJavaType serviceType;
	private FullyQualifiedJavaType daoType;
	private FullyQualifiedJavaType interfaceType;
	private FullyQualifiedJavaType pojoType;
	private FullyQualifiedJavaType pojoCriteriaType;
	private FullyQualifiedJavaType pojoSubCriteriaType;
	private FullyQualifiedJavaType listType;
	private FullyQualifiedJavaType mapType;
	private FullyQualifiedJavaType stringUtilsType;
	private FullyQualifiedJavaType autowired;
	private FullyQualifiedJavaType service;
	private FullyQualifiedJavaType returnType;
	private FullyQualifiedJavaType pagerType;
	private String servicePack;
	private String serviceImplPack;
	private String project;
	private String pojoUrl;

	private List<Method> methods;
	/**
	 * 是否添加注解
	 */
	private boolean enableAnnotation = true;
	private boolean enableInsert = false;
	private boolean enableInsertSelective = false;
	private boolean enableDeleteByPrimaryKey = false;
	private boolean enableDeleteByExample = false;
	private boolean enableUpdateByExample = false;
	private boolean enableUpdateByExampleSelective = false;
	private boolean enableUpdateByPrimaryKey = false;
	private boolean enableUpdateByPrimaryKeySelective = false;

	public MybatisServicePlugin3() {
		super();
		// 默认是slf4j
		slf4jLogger = new FullyQualifiedJavaType("org.slf4j.Logger");
		slf4jLoggerFactory = new FullyQualifiedJavaType("org.slf4j.LoggerFactory");
		methods = new ArrayList<Method>();
	}

	@Override
	public boolean validate(List<String> warnings) {
		if (StringUtility.stringHasValue(properties.getProperty("enableAnnotation")))
			enableAnnotation = StringUtility.isTrue(properties.getProperty("enableAnnotation"));

		String enableInsert = properties.getProperty("enableInsert");

		String enableUpdateByExampleSelective = properties.getProperty("enableUpdateByExampleSelective");

		String enableInsertSelective = properties.getProperty("enableInsertSelective");

		String enableUpdateByPrimaryKey = properties.getProperty("enableUpdateByPrimaryKey");

		String enableDeleteByPrimaryKey = properties.getProperty("enableDeleteByPrimaryKey");

		String enableDeleteByExample = properties.getProperty("enableDeleteByExample");

		String enableUpdateByPrimaryKeySelective = properties.getProperty("enableUpdateByPrimaryKeySelective");

		String enableUpdateByExample = properties.getProperty("enableUpdateByExample");

		if (StringUtility.stringHasValue(enableInsert))
			this.enableInsert = StringUtility.isTrue(enableInsert);
		if (StringUtility.stringHasValue(enableUpdateByExampleSelective))
			this.enableUpdateByExampleSelective = StringUtility.isTrue(enableUpdateByExampleSelective);
		if (StringUtility.stringHasValue(enableInsertSelective))
			this.enableInsertSelective = StringUtility.isTrue(enableInsertSelective);
		if (StringUtility.stringHasValue(enableUpdateByPrimaryKey))
			this.enableUpdateByPrimaryKey = StringUtility.isTrue(enableUpdateByPrimaryKey);
		if (StringUtility.stringHasValue(enableDeleteByPrimaryKey))
			this.enableDeleteByPrimaryKey = StringUtility.isTrue(enableDeleteByPrimaryKey);
		if (StringUtility.stringHasValue(enableDeleteByExample))
			this.enableDeleteByExample = StringUtility.isTrue(enableDeleteByExample);
		if (StringUtility.stringHasValue(enableUpdateByPrimaryKeySelective))
			this.enableUpdateByPrimaryKeySelective = StringUtility.isTrue(enableUpdateByPrimaryKeySelective);
		if (StringUtility.stringHasValue(enableUpdateByExample))
			this.enableUpdateByExample = StringUtility.isTrue(enableUpdateByExample);

		servicePack = properties.getProperty("targetPackage");
		serviceImplPack = properties.getProperty("implementationPackage");
		project = properties.getProperty("targetProject");

		pojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();

		if (enableAnnotation) {
			autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
			service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
		}
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
		String table = introspectedTable.getBaseRecordType();
		String tableName = table.replaceAll(this.pojoUrl + ".", "");
		interfaceType = new FullyQualifiedJavaType(servicePack + "." + tableName + "Service");

		// mybatis
		daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

		// logger.info(toLowerCase(daoType.getShortName()));
		serviceType = new FullyQualifiedJavaType(serviceImplPack + "." + tableName + "ServiceImpl");

		pojoType = new FullyQualifiedJavaType(pojoUrl + "." + tableName);

		pojoCriteriaType = new FullyQualifiedJavaType(pojoUrl + "." + tableName + "Criteria");
		pojoSubCriteriaType=new FullyQualifiedJavaType(pojoUrl + "." + tableName + "Criteria.Criteria");
		listType = new FullyQualifiedJavaType("java.util.List");
		mapType  = new FullyQualifiedJavaType("java.util.Map");
		stringUtilsType =new FullyQualifiedJavaType("org.apache.commons.lang.StringUtils");
		pagerType=new FullyQualifiedJavaType("com.fpx.mybatis.plugin.model.Pager");
		Interface interface1 = new Interface(interfaceType);
		TopLevelClass topLevelClass = new TopLevelClass(serviceType);
		// 导入必要的类
		addImport(interface1, topLevelClass);

		// 接口
		addService(topLevelClass,interface1,introspectedTable, tableName, files);
		// 实现类
		addServiceImpl(topLevelClass,introspectedTable, tableName, files);
		addLogger(topLevelClass);

		return files;
	}

	/**
	 * 添加接口
	 * 
	 * @param tableName
	 * @param files
	 */
	protected void addService(TopLevelClass topLevelClass,Interface interface1,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {

		interface1.setVisibility(JavaVisibility.PUBLIC);
		
		//添加缺省方法
		Method method = addEntity(topLevelClass,introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);
	
		method = deleteEntity(introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);

		method = modifyEntity(topLevelClass,introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);
		
		method = getEntity(introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);
		
		method = getEntitys(introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);
		
		method= getPagerEntitys(introspectedTable, tableName);
		method.removeAllBodyLines();
		interface1.addMethod(method);

		if (enableDeleteByPrimaryKey) {
			method = getOtherInteger("removeByPrimaryKey","deleteByPrimaryKey", introspectedTable, tableName, 2);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableUpdateByPrimaryKeySelective) {
			method = getOtherInteger("saveByPrimaryKeySelective","updateByPrimaryKeySelective", introspectedTable, tableName, 1);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableUpdateByPrimaryKey) {
			method = getOtherInteger("saveByPrimaryKey","updateByPrimaryKey", introspectedTable, tableName, 1);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableDeleteByExample) {
			method = getOtherInteger("removeByCriteria", "deleteByCriteria",introspectedTable, tableName, 3);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableUpdateByExampleSelective) {
			method = getOtherInteger("saveByCriteriaSelective", "updateByCriteriaSelective",introspectedTable, tableName, 4);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableUpdateByExample) {
			method = getOtherInteger("saveByCriteria","updateByCriteria", introspectedTable, tableName, 4);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableInsert) {
			method = getOtherInsertboolean("create","insert", introspectedTable, tableName);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}
		if (enableInsertSelective) {
			method = getOtherInsertboolean("createSelective", "insertSelective",introspectedTable, tableName);
			method.removeAllBodyLines();
			interface1.addMethod(method);
		}

		GeneratedJavaFile file = new GeneratedJavaFile(interface1, project,
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
		files.add(file);
	}

	private Method getPagerEntitys(IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("getPager"+tableName);
		method.setReturnType(new FullyQualifiedJavaType("Pager<" + tableName + ">"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, Object>"), "params"));
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addBodyLine("Pager<"+tableName+"> pager = new Pager<"+tableName+">();");
		method.addBodyLine("int page = 1;");
		method.addBodyLine("int limit = 10;");
		method.addBodyLine("if (isNotEmpty(params, \"page\")) {");
		method.addBodyLine("page = Integer.valueOf(params.get(\"page\").toString());");
		method.addBodyLine("}");
		method.addBodyLine("if (isNotEmpty(params, \"limit\")) {");
		method.addBodyLine("limit = Integer.valueOf(params.get(\"limit\").toString());");
		method.addBodyLine("}");
		
		method.addBodyLine(pojoCriteriaType.getShortName()+" criteria = new "+pojoCriteriaType.getShortName()+"();");
		method.addBodyLine(pojoSubCriteriaType.getShortName()+" cri = criteria.createCriteria();");
		method.addBodyLine("criteria.setLimitStart((page - 1) * limit);");
		method.addBodyLine("criteria.setLimitEnd(limit);");
		method.addBodyLine("setCriteria(cri, params);");
		method.addBodyLine("pager.setData("+getDaoShort()+"selectByCondition(criteria));");
		method.addBodyLine("pager.setTotal("+getDaoShort()+"countByCondition(criteria));");
		method.addBodyLine("pager.setLimit(limit);");
		method.addBodyLine("pager.setPage(page);");
		method.addBodyLine("return pager;");
		return method;
	}

	//基础方法-获取实体列表
	private Method getEntitys(IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("get"+tableName+"s");
		method.setReturnType(new FullyQualifiedJavaType("List<" + tableName + ">"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String,Object>"), "params"));
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addBodyLine(pojoCriteriaType.getShortName()+" criteria=new "+pojoCriteriaType.getShortName()+"();");
		method.addBodyLine("Criteria cri = criteria.createCriteria();");
		method.addBodyLine("setCriteria(cri, params);");
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		if (introspectedTable.hasBLOBColumns()) {
			sb.append("selectByConditionWithoutBLOBs");
		} else {
			sb.append("selectByCondition");
		}
		sb.append("(");
		sb.append("criteria");
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	//基础方法-查询
	private Method getEntity(IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("get"+tableName);
		method.setReturnType(pojoType);
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
			method.addParameter(new Parameter(type, "key"));
		} else {
			for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
				FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
				method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
			}
		}
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		sb.append("selectByPrimaryKey");
		sb.append("(");
		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	//基础方法-修改
	private Method modifyEntity(TopLevelClass topLevelClass,IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("modify"+tableName);
		method.setReturnType(pojoType);
		method.addParameter(new Parameter(pojoType, "record"));
		method.setVisibility(JavaVisibility.PUBLIC);
		//更新时间
		List<IntrospectedColumn> introspectedColumnsList=introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			// modify by yepeng 20160821 - update updateTime
//			if(introspectedColumn.isJDBCDateColumn()||introspectedColumn.isJDBCTimeColumn()){
				if("updateTime".equals(introspectedColumn.getJavaProperty())){
					topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Date"));
					method.addBodyLine("record.setUpdateTime(new Date());");
				}
//			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("if(this.");
		sb.append(getDaoShort());
		if (introspectedTable.hasBLOBColumns()) {
			sb.append("updateByPrimaryKeySelectiveWithoutBLOBs");
		} else {
			sb.append("updateByPrimaryKeySelective");
		}
		sb.append("(record)==1)");
		method.addBodyLine(sb.toString());
		method.addBodyLine("\treturn record;");
		method.addBodyLine("return null;");
		return method;
	}
	
	//基础方法-删除
	private Method deleteEntity(IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("delete"+tableName);
		method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		String params = addParams(introspectedTable, method, 2);
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		if (introspectedTable.hasBLOBColumns()){
			sb.append("deleteByPrimaryKeyWithoutBLOBs");
		} else {
			sb.append("deleteByPrimaryKey");
		}
		sb.append("(");
		sb.append(params);
		sb.append(")==1;");
		method.addBodyLine(sb.toString());
		return method;
	}
	//基础方法--新增
	private Method addEntity(TopLevelClass topLevelClass,IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("add"+tableName);
		method.setReturnType(pojoType);
		method.addParameter(new Parameter(pojoType, "record"));
		method.setVisibility(JavaVisibility.PUBLIC);
		List<IntrospectedColumn> introspectedColumnsList=introspectedTable.getAllColumns();
		 for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			 // modify by yepeng 20160821 - add createTime
//			 if(introspectedColumn.isJDBCDateColumn()||introspectedColumn.isJDBCTimeColumn()){
				 if("createTime".equals(introspectedColumn.getJavaProperty())){
					 topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Date"));
					 method.addBodyLine("record.setCreateTime(new Date());");
				 }
//			 }
		 }
		StringBuilder sb = new StringBuilder();
		sb.append("if(this.");
		sb.append(getDaoShort());
		// modify by yepeng 20160821 - 调用 mapper.insert to mapper.insertSelective
		sb.append("insertSelective");
//		sb.append("insert");
		sb.append("(");
		sb.append("record");
		sb.append(")==1)");
		method.addBodyLine(sb.toString());
		method.addBodyLine("\treturn record; ");
		method.addBodyLine("return null;");
		return method;
	}
	
	//map元素非空判断
	private Method isNotEmpty(TopLevelClass topLevelClass,IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("isNotEmpty");
		method.setReturnType(new FullyQualifiedJavaType("boolean"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, Object>"), "params"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "key"));
		method.setVisibility(JavaVisibility.PRIVATE);
		method.addBodyLine("if (params.get(key) != null) {");
		method.addBodyLine("return StringUtils.isNotEmpty(params.get(key).toString());");
		method.addBodyLine("}");
		method.addBodyLine("return false;");
		return method;
	}
	
	//查询条件方法
	private Method setCriteria(TopLevelClass topLevelClass,IntrospectedTable introspectedTable,
			String tableName) {
		Method method = new Method();
		method.setName("setCriteria");
		method.setReturnType(new FullyQualifiedJavaType("void"));
		method.addParameter(new Parameter(pojoSubCriteriaType, "criteria"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String,Object>"), "params"));
		method.setVisibility(JavaVisibility.PRIVATE);
		List<IntrospectedColumn> introspectedColumnsList=introspectedTable.getAllColumns();
		 for (IntrospectedColumn introspectedColumn : introspectedColumnsList) {
			 String javaProperty=introspectedColumn.getJavaProperty();
			 if(introspectedColumn.isStringColumn()){
				 method.addBodyLine("if(isNotEmpty(params, \""+javaProperty+"\")) {");
				 method.addBodyLine("criteria.and"+toUpperCase(javaProperty)+"EqualTo(params.get(\""+javaProperty+"\").toString());");
				 method.addBodyLine("}");
			 }
			 // TODO yepeng 待修改 - map参数查询方法
			 if((introspectedColumn.isJDBCDateColumn()||introspectedColumn.isJDBCTimeColumn())&&"createTime".equals(introspectedColumn.getJavaProperty())){
				 topLevelClass.addImportedType(new FullyQualifiedJavaType("com.yolipai.server.common.utils.DateUtils"));
				 method.addBodyLine("String startTime = params.getOrDefault(\"startTime\", \"\").toString();");
				 method.addBodyLine("String endTime = params.getOrDefault(\"endTime\", \"\").toString();");
				 method.addBodyLine("if(StringUtils.isNotEmpty(startTime))");
				 method.addBodyLine("\tcriteria.andCreateTimeGreaterThan(DateUtils.StringToTime(startTime));");
				 method.addBodyLine("if(StringUtils.isNotEmpty(endTime))");
				 method.addBodyLine("\tcriteria.andCreateTimeLessThan(DateUtils.StringToTime(endTime));");
			 }
         }
		return method;
	}

	/**
	 * 添加实现类
	 * 
	 * @param introspectedTable
	 * @param tableName
	 * @param files
	 */
	protected void addServiceImpl(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		// 设置实现的接口
		topLevelClass.addSuperInterface(interfaceType);
		//添加注解
		if (enableAnnotation) {
			topLevelClass.addAnnotation("@Service(\"" + tableName.substring(0,1).toLowerCase() + tableName.substring(1) + "Service\")");
			topLevelClass.addImportedType(service);
		}
		// 添加引用dao
		addField(topLevelClass, tableName);
		// 添加基础方法
		topLevelClass.addMethod(addEntity(topLevelClass,introspectedTable, tableName));
		topLevelClass.addMethod(deleteEntity(introspectedTable, tableName));
		topLevelClass.addMethod(modifyEntity(topLevelClass,introspectedTable, tableName));
		topLevelClass.addMethod(getEntity(introspectedTable, tableName));
		topLevelClass.addMethod(isNotEmpty(topLevelClass,introspectedTable, tableName));
		topLevelClass.addMethod(setCriteria(topLevelClass,introspectedTable, tableName));
		topLevelClass.addMethod(getEntitys(introspectedTable, tableName));
		topLevelClass.addMethod(getPagerEntitys(introspectedTable, tableName));
		/**
		 * type 的意义 pojo 1 ;key 2 ;example 3 ;pojo+example 4
		 */
		if (enableDeleteByPrimaryKey) {
			topLevelClass.addMethod(getOtherInteger("removeByPrimaryKey", "deleteByPrimaryKey",introspectedTable, tableName, 2));
		}
		if (enableUpdateByPrimaryKeySelective) {
			topLevelClass.addMethod(getOtherInteger("saveByPrimaryKeySelective", "updateByPrimaryKeySelective",introspectedTable, tableName, 1));

		}
		if (enableUpdateByPrimaryKey) {
			topLevelClass.addMethod(getOtherInteger("saveByPrimaryKey", "updateByPrimaryKey",introspectedTable, tableName, 1));
		}
		if (enableDeleteByExample) {
			topLevelClass.addMethod(getOtherInteger("removeByCriteria", "deleteByCriteria",introspectedTable, tableName, 3));
		}
		if (enableUpdateByExampleSelective) {
			topLevelClass.addMethod(getOtherInteger("saveByCriteriaSelective", "updateByCriteriaSelective",introspectedTable, tableName, 4));
		}
		if (enableUpdateByExample) {
			topLevelClass.addMethod(getOtherInteger("saveByCriteria", "updateByCriteria",introspectedTable, tableName, 4));
		}
		if (enableInsert) {
			topLevelClass.addMethod(getOtherInsertboolean("create", "insert",introspectedTable, tableName));
		}
		if (enableInsertSelective) {
			topLevelClass.addMethod(getOtherInsertboolean("createSelective","insertSelective", introspectedTable, tableName));
		}
		// 生成文件
		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project,
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
		files.add(file);
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
	 * 添加方法
	 * 
	 */
	protected Method selectByPrimaryKey(IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName("selectByPrimaryKey");
		method.setReturnType(pojoType);
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
			method.addParameter(new Parameter(type, "key"));
		} else {
			for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
				FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
				method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
			}
		}
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		sb.append("selectByPrimaryKey");
		sb.append("(");
		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	/**
	 * 添加方法
	 * 
	 */
	protected Method countByExample(IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName("countByCriteria");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(pojoCriteriaType, "condition"));
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("int count = this.");
		sb.append(getDaoShort());
		sb.append("countByCriteria");
		sb.append("(");
		sb.append("condition");
		sb.append(");");
		method.addBodyLine(sb.toString());
		method.addBodyLine("logger.debug(\"count: {}\", count);");
		method.addBodyLine("return count;");
		return method;
	}

	/**
	 * 添加方法
	 * 
	 */
	protected Method selectByExample(IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName("findByCriteria");
		method.setReturnType(new FullyQualifiedJavaType("List<" + tableName + ">"));
		method.addParameter(new Parameter(pojoCriteriaType, "condition"));
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		if (introspectedTable.hasBLOBColumns()) {
			sb.append("selectByConditionWithoutBLOBs");
		} else {
			sb.append("selectByCondition");
		}
		sb.append("(");
		sb.append("condition");
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	/**
	 * 添加方法
	 * 
	 */
	protected Method getOtherInteger(String methodName,String daoName ,IntrospectedTable introspectedTable, String tableName, int type) {
		Method method = new Method();
		method.setName(methodName);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		String params = addParams(introspectedTable, method, type);
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return this.");
		sb.append(getDaoShort());
		if (introspectedTable.hasBLOBColumns()
				&& (!"saveByPrimaryKeySelective".equals(methodName) && !"removeByPrimaryKey".equals(methodName)
						&& !"removeByCriteria".equals(methodName) && !"saveByCriteriaSelective".equals(methodName))) {
			sb.append(daoName + "WithoutBLOBs");
		} else {
			sb.append(daoName);
		}
		sb.append("(");
		sb.append(params);
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	/**
	 * 添加方法
	 * 
	 */
	protected Method getOtherInsertboolean(String methodName,String daoName, IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName(methodName);
		method.setReturnType(returnType);
		method.addParameter(new Parameter(pojoType, "record"));
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		if (returnType==null) {
			sb.append("this.");
		} else {
			sb.append("return this.");
		}
		sb.append(getDaoShort());
		sb.append(daoName);
		sb.append("(");
		sb.append("record");
		sb.append(");");
		method.addBodyLine(sb.toString());
		return method;
	}

	/**
	 * type 的意义 pojo 1 key 2 example 3 pojo+example 4
	 */
	protected String addParams(IntrospectedTable introspectedTable, Method method, int type1) {
		switch (type1) {
		case 1:
			method.addParameter(new Parameter(pojoType, "record"));
			return "record";
		case 2:
			if (introspectedTable.getRules().generatePrimaryKeyClass()) {
				FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
				method.addParameter(new Parameter(type, "key"));
			} else {
				for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
					FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
					method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
				}
			}
			StringBuffer sb = new StringBuffer();
			for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
				sb.append(introspectedColumn.getJavaProperty());
				sb.append(",");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		case 3:
			method.addParameter(new Parameter(pojoCriteriaType, "condition"));
			return "condition";
		case 4:

			method.addParameter(0, new Parameter(pojoType, "record"));
			method.addParameter(1, new Parameter(pojoCriteriaType, "condition"));
//			if(method.getName().equals("updateByExampleSelective")||method.getName().equals("updateByExample")){
//				return "record, example.getCondition()";
//			}
			return "record, condition";
		default:
			break;
		}
		return null;
	}

	protected void addComment(JavaElement field, String comment) {
		StringBuilder sb = new StringBuilder();
		field.addJavaDocLine("/**");
		sb.append(" * ");
		comment = comment.replaceAll(OutputUtilities.lineSeparator, "<br>"+OutputUtilities.lineSeparator+"\t * ");
		sb.append(comment);
		field.addJavaDocLine(sb.toString());
		field.addJavaDocLine(" */");
	}

	/**
	 * 添加字段
	 * 
	 * @param topLevelClass
	 */
	protected void addField(TopLevelClass topLevelClass) {
		// 添加 success
		Field field = new Field();
		field.setName("success"); // 设置变量名
		field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance()); // 类型
		field.setVisibility(JavaVisibility.PRIVATE);
		addComment(field, "执行结果");
		topLevelClass.addField(field);
		// 设置结果
		field = new Field();
		field.setName("message"); // 设置变量名
		field.setType(FullyQualifiedJavaType.getStringInstance()); // 类型
		field.setVisibility(JavaVisibility.PRIVATE);
		addComment(field, "消息结果");
		topLevelClass.addField(field);
	}

	/**
	 * 添加方法
	 * 
	 */
	protected void addMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("setSuccess");
		method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "success"));
		method.addBodyLine("this.success = success;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		method.setName("isSuccess");
		method.addBodyLine("return success;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("setMessage");
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "message"));
		method.addBodyLine("this.message = message;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.setName("getMessage");
		method.addBodyLine("return message;");
		topLevelClass.addMethod(method);
	}

	/**
	 * 添加方法
	 * 
	 */
	protected void addMethod(TopLevelClass topLevelClass, String tableName) {
		Method method2 = new Method();
		for (int i = 0; i < methods.size(); i++) {
			Method method = new Method();
			method2 = methods.get(i);
			method = method2;
			method.removeAllBodyLines();
			method.removeAnnotation();
			StringBuilder sb = new StringBuilder();
			sb.append("return this.");
			sb.append(getDaoShort());
			sb.append(method.getName());
			sb.append("(");
			List<Parameter> list = method.getParameters();
			for (int j = 0; j < list.size(); j++) {
				sb.append(list.get(j).getName());
				sb.append(",");
			}
			sb.setLength(sb.length() - 1);
			sb.append(");");
			method.addBodyLine(sb.toString());
			topLevelClass.addMethod(method);
		}
		methods.clear();
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

	/**
	 * 导入需要的类
	 */
	private void addImport(Interface interfaces, TopLevelClass topLevelClass) {
		interfaces.addImportedType(pojoType);
		interfaces.addImportedType(listType);
		interfaces.addImportedType(mapType);
		interfaces.addImportedType(pagerType);
		topLevelClass.addImportedType(daoType);
		topLevelClass.addImportedType(interfaceType);
		topLevelClass.addImportedType(pojoType);
		topLevelClass.addImportedType(pojoCriteriaType);
		topLevelClass.addImportedType(pojoSubCriteriaType);
		topLevelClass.addImportedType(listType);
		topLevelClass.addImportedType(mapType);
		topLevelClass.addImportedType(slf4jLogger);
		topLevelClass.addImportedType(slf4jLoggerFactory);
		topLevelClass.addImportedType(stringUtilsType);
		topLevelClass.addImportedType(pagerType);
		if (enableAnnotation) {
			topLevelClass.addImportedType(service);
			topLevelClass.addImportedType(autowired);
		}
	}

	/**
	 * 导入logger
	 */
	private void addLogger(TopLevelClass topLevelClass) {
		Field field = new Field();
		field.setFinal(true);
		field.setInitializationString("LoggerFactory.getLogger(" + topLevelClass.getType().getShortName() + ".class)"); // 设置值
		field.setName("logger"); // 设置变量名
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("Logger")); // 类型
		field.setVisibility(JavaVisibility.PRIVATE);
		topLevelClass.addField(field);
	}

	private String getDaoShort() {
		return toLowerCase(daoType.getShortName()) + ".";
	}
	public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		returnType = method.getReturnType();
		return true;
	}
}
