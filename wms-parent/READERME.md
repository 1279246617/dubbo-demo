聚合子工程

在父级工程pom统一添加jar,插件依赖


编码约定

一:类的命名,参数封装,传递

名词

PO数据持久对象 :entity包下,按表名转驼峰命名(如:com.coe.wms.facade.user.entity.User)

QueryVO查询条件对象 :vo包下,按实体类名+VO(如:com.coe.wms.facade.user.vo.UserQueryVO)

DTO数据传输对象:dto包下,按实体类名+DTO(如:com.coe.wms.facacde.user.dto.UserDTO)

1.web层action方法接收参数,使用Page类封装分页参数,其他参数尽量使用基础数据类型接收,统一当参数大于等于10个,使用QueryVo对象

2.web层完成参数非空,格式校验,trim,不作业务处理

3.web层response数据统一使用Result类封装,除有特定数据格式要求的action方法(如:对外接口)

4.对外接口,数据封装统一使用DTO对象,不可使用entity,避免暴露数据结构




