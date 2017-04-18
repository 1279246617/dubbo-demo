插件主要实现基于Mybatis动态数据源选择方法
项目采用jar形式，坐标如下：

<groupId>mybatis.plugin.rw</groupId>
<artifactId>mybatis-plugin-rw</artifactId>
<version>1.0.1</version>

集成步骤：
1、在你的项目中引入本插件的maven地址

2、在Mybatis主配置文件中入引插件，代码如下：
<plugins>
	<plugin interceptor="org.mybatis.plugin.rw.DynamicPlugin">
	</plugin>
</plugins>



//读写分离方案选择

1.aop+AbstractRoutingDataSource,注解选择数据源

2.mybatis Plugin ,即本插件:在MyBatis创建Statement对象前通过拦截器选择真正的数据源，在拦截器中根据方法名称不同（select、update、insert、delete）选择数据源。

