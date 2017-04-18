插件主要实现基于Mybatis分页和查询指定数目数据的方法
项目采用jar形式，坐标如下：
<groupId>mybatis.plugin.page</groupId>
<artifactId>mybatis-plugin-page</artifactId>
<version>1.0.4</version>

集成步骤：
1、在你的项目中引入本插件的maven地址

2、在Mybatis主配置文件中入引插件，代码如下：
<plugins>
	<plugin interceptor="org.mybatis.plugin.PagePlugin">
		<!-- 表示数据方言，目前仅支持MySQL(MYSQL)、Oracle(ORACLE) -->
		<property name="dialect" value="ORACLE" />
		<!-- 表示拦截的statement 以下配置会拦截 id="listPageXXX"、id="XXXlistPage" -->
		<property name="pageSqlId" value=".*listPage.*" />
	</plugin>
</plugins>

3、查询条件类继承QueryParam

4、Service实现类继承AbstractMapperSupport，重写setMapper方法，代码如下
public void setMapper(){
	super.mapper = this.xxxMaper;
}
5、给查询条件QueryParam设定分页参数
6、调用supper.queryPagerModel查询分页对象
