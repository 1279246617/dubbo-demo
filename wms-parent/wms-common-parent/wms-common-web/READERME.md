公共web层

使用swagger的步骤
1.在springmvc配置文件中加入<bean class="com.coe.fcs.common.web.swagger.MySwaggerConfig"></bean>
      更改swagger的相关配置也可在此类中更改swagger的相关配置
2.在Controller类上加@Api(description = "类的描述信息", value = "必须与@requestMapping中的路径一致")
       将方法加上@ApiOperation(value = "方法的描述信息", httpMethod = "与@requestMapping中的method属性一致")
       方法的参数加@ApiParam(value="参数的描述信息",name="参数名称,必须与该方法的参数名一致",required=true/false)
       或者加springmvc的注解@requestParam/@requestBody
3.将/fcs-web-user/src/main/webapp/static/3rd/swagger中所有内容copy到项目中，并将index.html中的
   url = "http://localhost:8080/fcs-web-user/api-docs"改为你的项目名称
4.打包部署，访问/fcs-web-user/static/3rd/swagger/index.html