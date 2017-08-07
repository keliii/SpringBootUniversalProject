# SpringBootUniversalProject
SpringBoot的通用配置工程

项目位置:<br>[https://github.com/keliii/SpringBootUniversalProject.git][]

[https://github.com/keliii/SpringBootUniversalProject.git]:https://github.com/keliii/SpringBootUniversalProject.git

*在配置了这个工程后的一个月，当我再次打开玩意，郁闷的发现这些配置都忘的七七八八了,当时写的shiro配置也是那么的陌生，于是就有了这个...*

<font color="#f00">强调一点!!这个工程只是用来归类配置，并没有通过真实项目检测（等哪天用上了我在把这句给去掉）</font>

### 配置项
- SpringBoot
- Mybatis - JPA - Druid
- Redis
- Shiro
- AMQ
  
#### 打包

* idea打jar包时的配置
<br><p>Project Structure -> Artifacts -> add Jar - from modules with dependencies -> 配置MainClass</p>

* 命令：<code>mvn clean package</code>
* idea操作：Build -> Build Artifacts
* 使用war包时重写configure方法
<pre><code>public class UniversalApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UniversalApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(UniversalApplication.class, args);
	}
}</code></pre>




#### 执行
* 设置配置文件环境
<br><code>java -jar universal.jar --spring.profiles.active=dev</code>
* 配置端口
<br><code>java -jar universal.jar --server.port=8080</code>



- - -

### DAO层

+ application*.yml

数据库方面用的是mysql，同时使用了SpringBoot Data JPA和Mybatis

先说说jpa，仅仅是添加了spring-boot-starter-data-jpa依赖，再加上点数据库的配置就能使用，不得不说springboot做的真是好。
jpa的配置简单，反倒是连接池的配置多了点，项目中使用的druid。(druid有大批的拥护者，其实我并不清楚它的优秀，我只是想使用它的sql监控能力；就像fastjson，都说它解析快，我也一直用它，但实际上我并不知道它到底快不快。）

##### 配置druid监控统计功能
+ com.keliii.common.filter.DruidStatFilter
+ com.keliii.common.servlet.DruidStatViewServlet
##### mybatis配置（主要是mapper文件位置，还有启用事务）
<pre><code>@Configuration
@MapperScan({"com.keliii.news.mapper",
		"com.keliii.user.mapper"})
@EnableTransactionManagement
public class MybatisConfig {
}</code></pre>

还有没有说清楚的直接看源码，也可以自己[Google][]
网上说druid是为了解除jdbc的限制，能定制一些特有功能，但我还没有去了解。

[Google]:http://www.google.com.hk

### redis缓存

配置
+ application*.yml
+ com.keliii.common.config.RedisConfig

redis的使用
+ com.keliii.common.service.RedisService
+ com.keliii.news.service.NewsService

通过annotation的方式使用能在尽量不干扰原代码逻辑的情况下增加缓存。

### AMQ配置（略过吧，没什么好说的）

### shiro
这是第一次正式使用，给我的感觉是小巧灵活；
定义了用户，角色，权限的关系模型，通过自定义身份校验doGetAuthenticationInfo，权限校验doGetAuthorizationInfo来定制用户访问权限；
其实还有一个doCredentialsMatch，定义缓存方式，这里也可以增加一些安全性逻辑（如：防频繁尝试密码）。

配置
+ com.keliii.user.shiro.*

拓展
+ com.keliii.user.init.PermissionInit

shiro权限将自动匹配controller
在方法上使用@Permission(isPublic = true)设置该URL为开放URL

运行时修改ControllerUrl内容时，应将state置为>0来锁定配置，以免下次启动时因代码配置而覆盖

@Permission是自定义的annotation，主要是用来设置开放访问的url，以及通过url参数来自定义shiro权限匹配url（其实是当前的逻辑不能处理含有URL params而产生的折中方法，会继续改进）

