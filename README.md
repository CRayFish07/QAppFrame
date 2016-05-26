##QAppFrame
* 一个简单的App服务框架，基于SpringMVC4+，包含JSON格式报文结构，平台可配置的自动`数据加密`和`客户端API发布`等功能。开发者可以只关注请求接口和返回接口的定义，以及业务逻辑的处理。
* 集成MyBatis
* 集成`App接口发布`功能，可以为客户端`自动生成接口`对应的java/apple-bean代码。
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
 * 本地测试：`POST` http://localhost:8080/QAppFrame/wdt/rlogin/UserLogin/login.gmt
 * 样例展示：`POST` http://119.29.198.112:8080/QAppFrame/worldtree/ulogin/UserLogin/login.gmt
 * `BODY-CONTENT` FF01{"head":{},"body":{"mobileNo":"18621596661","idType":"00"}}

##发布TIPS
* 建议将`FieldDefiner.class`和`APIDefiner.class`注解改成源码级别。
 
##待新增的功能
* 对映射做缓存。
* 增加redis会话支持。
* 按照设备和客户号两个维度对客户操作轨迹进行记录的策略（只是日志打印，数据分析和展示剥离到另外系统）。
* 对接口做版本历史追溯。
  
