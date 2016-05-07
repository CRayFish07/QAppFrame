# QAppFrame
一个简单的App服务框架，基于SpringMVC4+，包含JSON格式报文结构，平台可配置的自动数据加密和客户端API发布等功能。开发者可以只关注请求接口和返回接口的定义
# 设计思路
  App请求入口在AppEntry中，该类的方法中解析URL然后将URL映射到具体业务类的具体方法中。
  URL格式为：@RequestMapping(value="/{transType}/{module}/{clazz}/{method}.mto" /*,method=RequestMethod.POST*/)
# 包结构说明
  plat.api.* ：该包里面是客户端API发布的代码，这个在开发过程中，用来发布API接口给客户端人员。
  plat.app.* ：该包是App服务的主框架代码。
  
