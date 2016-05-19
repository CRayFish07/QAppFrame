##QAppFrame
* 一个简单的App服务框架，基于SpringMVC4+，包含JSON格式报文结构，平台可配置的自动数据加密和客户端API发布等功能。开发者可以只关注请求接口和返回接口的定义，以及业务逻辑的处理。
* 集成App接口发布功能，可以为客户端自动生成接口对应的java/apple-bean代码。
* 将对应的发布接口的后缀`api`改为`gmt`则为服务调用的接口，统一为`POST`方式调用。

##设计思路
* App请求入口在AppEntry中，该类的方法中解析URL然后将URL映射到具体业务类的具体方法中。

##包结构说明
 * plat.api.* ：该包里面是客户端API发布的代码，这个在开发过程中，用来发布API接口给客户端人员。`生产环境禁用该功能`
 * plat.app.* ：该包是App服务的主框架代码。
 
##代码结构说明
* 应用入口AppEntry.java.
* API发布入口APIEntry.java.

##示例
* 客户端接口发布
 * 本地测试：`GET` http://localhost:8080/QAppFrame/api/list/APIExample.api
 * 样例展示：`GET` http://119.29.198.112:8080/QAppFrame/api/list/APIExample.api
* 交易请求和返回报文示例
 * 本地测试：`POST` http://localhost:8080/QAppFrame/worldtree/ulogin/UserLogin/login.gmt
 * 样例展示：`POST` http://119.29.198.112:8080/QAppFrame/worldtree/ulogin/UserLogin/login.gmt
 * `BODY-CONTENT` FF01{"head":{},"body":{"mobileNo":"18621596661","idType":"00"}}
 
##待改进的问题
* 暂未对映射做缓存。
* 暂未提供数据库功能（开发中）
  
  
