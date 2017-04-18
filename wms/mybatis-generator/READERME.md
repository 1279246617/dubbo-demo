1.修改生成的配置文件generator.xml

a.属性<jdbcConnection>是数据库连接的配置标签

b.<plugin type="org.mybatis.generator.plugins.CachePlugin">是redis缓存的插件根据项目需求确认是否需要该插件 

c.其他详细的配置见配置文件注解说明 

注意事项： 1.每次生成mapper的xml文件时并不会删除原来的mapper文件，故再次生成的时候需先手动删除