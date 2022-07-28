# 基础组件
* [组件说明](#组件说明)
* [基础依赖服务启动说明（jar）](#基础依赖服务启动说明nacos注册中心需自行部署)
* [基础依赖服务启动说明（Docker）](#基础依赖服务启动说明docker方式)
* [业务组件包拆分说明](#业务组件包拆分说明)
* [业务系统架构图](#业务系统架构图)
* [中间件安装配置](#中间件安装配置)
* [接口文档使用](#接口文档使用)
* [QuickStart Guide](#quickstart-guide)
  * [在Maven Project 中使用](#在maven-project-中使用)
  * [Basic Usage](#basic-usage基于springboot编写)
  * [集成Nacos Discovery 与 Nacos Config 组件](#集成nacos-discovery-与-nacos-config)
  * [集成Gateway网关组件](#集成gateway网关)
  * [集成OpenFeign组件](#集成openfeign)
  * [集成SpringBootAdmin服务](#集成springbootadmin服务)
  * [文件上传](#文件上传)
  * [登录认证、续签、退出](#登录认证退出)
  * [分布式接口幂等性使用](#分布式接口幂等性使用)
* [Q&amp;A](#qa)
  * [携带授权令牌请求接口资源后如何获取当前请求用户信息?](#携带授权令牌请求接口资源后如何获取当前请求用户信息)
  * [请求参数非空校验？参数格式校验？](#请求参数非空校验参数格式校验)
  * [各种Model之间如何快速相互转换？（Dto to Entity、Entity to Vo等）](#各种model之间如何快速相互转换dto-to-entityentity-to-vo等)
  * [需要获取服务中指定Spring管理的Bean对象？](#需要获取服务中指定spring管理的bean对象)
  * [需要获取当前服务运行环境？](#需要获取当前服务运行环境)
  * [需要使用Redis进行缓存操作？](#需要使用redis进行缓存操作)
  * [Rest接口全局响应返回格式用哪个？](#rest接口全局响应返回格式用哪个)
  * [Rest接口全局响应返回分页格式用哪个？](#rest接口全局响应返回分页格式用哪个)
  * [系统提供的内置异常有哪些？](#系统提供的内置异常有哪些)
  * [系统内置异常编码定义有哪些？](#系统内置异常编码定义有哪些)
  * [内置的分页接口请求对象？](#内置的分页接口请求对象)
* [组件使用说明](#组件使用说明)
  * [basic\-gateway（网关组件）](#basic-gateway网关组件)
    * [组件属性配置](#组件属性配置)
    * [黑名单配置](#黑名单配置)
    * [接口资源白名单配置](#接口资源白名单配置)
* [数据库设计说明](#数据库设计说明)
  * [ER图](#er图)
    * [basic\-file（文件服务）](#basic-file)
    * [basic\-uaa（授权服务）](#basic-uaa)
    * [basic\-uums（用户中心服务）](#basic-uums)
  * [数据实体描述](#数据实体描述)
    * [basic\-file（文件服务）](#basic-file-1)
    * [basic\-uaa（授权服务）](#basic-uaa-1)
    * [basic\-uums（用户中心服务）](#basic-uums-1)
#### 组件说明
| 组件名称          | 说明                              | 进度          |
|:--------------|:--------------------------------|-------------|
| basic-common  | 业务基础组件                          | development |
| basic-abs-web | Spring Boot Admin Server 服务     |    development         |
| basic-file    | 业务组件：文件服务                       |    development                    |
| basic-gateway | SpringCloud GateWay 网关服务        |               development                    |
| basic-uaa     | 业务组件：SpringSecurity OAuth2 认证服务 |      development                                        |
| basic-uums    | 业务组件：用户服务中心                     |     development                                                    |
| basic-tenant  | 业务组件：租户服务中心                     |     development                                                               |
#### 基础依赖服务启动说明(Nacos注册中心需自行部署)

1. Step 1

   ```shell
   git clone https://github.com/lanrenspace/stagging-basic-cloud.git
   ```

2. Step 2

   ```shell
   mvn clean install
   ```

3. Step 3

   - 启动用户中心服务

      ```shell
      cd ~/basic-uums/basic-uums-web/target
      java -jar basic-uums-web-1.0.0.jar
      ```

   - 启动授权服务

      ```shell
      cd ~/basic-uaa/basic-uua-auth/target
      java -jar basic-uaa-auth-1.0.0.jar
      ```

   - 启动网关服务

      ```shell
      cd ~/basic-gateway/target
      java -jar basic-gateway-1.0.0.jar
      ```

4. Step 4

   其他服务按需启动即可
   
#### 基础依赖服务启动说明(Docker方式)

使用相应服务下的Dockerfile配置文件

```shell
docker build --rm -t ImageTagName --build-arg JAR_FILE=service path
```

```shell
docker run -itd --name ContainerName ImageTagName
```
   
#### 业务组件包拆分说明

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/model.png)

- Tripartie Service：其他业务服务
- Entity：业务对象实体（PO、VO、DTO）服务常量定义（CONTSTANT）
- Service：具体业务逻辑处理实现
- Api：业务对外提供服务API定义
- Web：业务服务提供者

#### 业务系统架构图

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/projectArchitecture.png)

#### 中间件安装配置

1. 安装MySql（使用版本8.0.25）

   创建配置及数据挂载目录

   ```shell
   ~# mkdir /home/mysql/conf
   ~# mkdir /home/mysql/data
   ~# mkdir /home/mysql/logs
   ```

   Docker 命令

   ```shell
   docker run --name mysql \
       -v /home/mysql/conf/my.cnf:/etc/mysql/my.cnf \
       -v /home/mysql/data:/var/lib/mysql \
       -v /home/mysql/logs:/var/log/mysql \
       -p 3306:3306 \
       -e MYSQL_ROOT_PASSWORD=mysql_root_password \
       -d mysql:8.0.29
   ```

   修改权限

   ```shell
   ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'custom_mysql_root_password';
   FLUSH PRIVILEGES;
   ```

2. 安装Redis（使用版本6.2）

   创建数据挂载目录及配置文件

   ```shell
   ~# mkdir /home/redis/data
   ~# mkdir /home/redis/conf
   ~# touch /home/redis/conf/redis.conf
   ```

   Docker 命令

   ```shell
   docker run -p 6379:6379 --name redis \
       -v /home/redis/data:/data \
       -v /home/redis/conf/redis.conf:/etc/redis/redis.conf \
       -d redis:6.2 redis-server /etc/redis/redis.conf
   ```

   添加Redis密码及数据持久化

   ```shell
   ~# vim /home/redis/conf/redis.conf
   
   # 添加或修改配置
   requirepass custom_redis_password
   appendonly yes
   ```

3. 安装Nacos服务（使用版本1.2.0）

   Docker 命令

   ```shell
   docker  run \
       --name nacos -d \
       -p 8848:8848 \
       --privileged=true \
       --restart=always \
       -e JVM_XMS=256m \
       -e JVM_XMX=256m \
       -e MODE=standalone \
       -e PREFER_HOST_MODE=hostname \
       nacos/nacos-server:1.2.0
   ```

4. 安装FastDfs

   创建数据挂载目录

   ```shell
   ~# mkdir /home/fastdfs
   ```

   Docker 命令

   ```shell
   docker run --name fastdfs --privileged=true --net=host \
            -e IP=tms.jxncyy.com -e WEB_PORT=8888 \
            -v /home/fastdfs:/var/local/fdfs \
            -d --restart=always \
            registry.cn-beijing.aliyuncs.com/itstyle/fastdfs:1.0
   ```


####  接口文档使用

*项目默认集成```Swagger2```,项目接口代码编写遵循Swagger2语法，文档在线访问地址：```${对外网关服务地址}/doc.html```;*

**系统Feign接口，在文档中应该有所标识，遵循命名注释如下：**

```java
@Api(value = "Feign:业务描述")
@RestController
@RequestMapping("/feign/xxxx")
public class AnonymousInfoController {
    // ......
}
```

**注意：Feign类接口在开发环境（dev、test）下能够允许外部调用，但在预生产（sit）与生产环境（prod）下不允许外部调用**


#### QuickStart Guide

##### 环境信息定义

| 定义         | 描述                      |
| ------------ | ------------------------- |
| LAST_VERSION | 基础服务最后文档版本      |
| GATEWAY      | GateWay网关服务           |
| AUTH         | 认证授权代理前缀 eg：/uaa |

**注意：之后文档中出现的${`定义`}值均为变量，具体值选择以上表格中的信息.**


##### 在Maven Project 中使用

1. 在具体业务实现服务`pom.xml` 文件中添加依赖：

   ```xml
   <dependencyManagement>
      <dependencies>
         <dependency>
            <groupId>com.basic.cloud</groupId>
            <artifactId>stagging-basic-cloud</artifactId>
            <version>${LAST_VERSION}</version>
            <type>pom</type>
            <scope>import</scope>
         </dependency>
      </dependencies>
   </dependencyManagement>
   ```

2. 按需引入所需组件：

   ```xml
   <dependencies>
      <dependency>
         <groupId>com.basic.cloud</groupId>
         <artifactId>basic-common</artifactId>
         <version>${LAST_VERSION}</version>
      </dependency>
   </dependencies>
   
   <dependencyManagement>
      <dependencies>
         <dependency>
            <groupId>com.basic.cloud</groupId>
            <artifactId>stagging-basic-cloud</artifactId>
            <version>${LAST_VERSION}</version>
            <type>pom</type>
            <scope>import</scope>
         </dependency>
      </dependencies>
   </dependencyManagement>
   ```


##### Basic Usage（基于SpringBoot编写）

1. 增删改查操作基础项目结构搭建

   - 引入 `basic-common` 组件

     ```xml
     <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>2.1.6.RELEASE</version>
     </parent>
     <!--
     ......
     -->
     <dependencies>
        <dependency>
           <groupId>com.basic.cloud</groupId>
           <artifactId>basic-common</artifactId>
           <version>${LAST_VERSION}</version>
        </dependency>
     </dependencies>
     ```

   - 新建 `yml` 或 `properties` 配置文件

     ```yaml
     server:
       port: 7000
     spring:
       application:
         name: example
       datasource:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://xxx.xxx.x.xxx:xxxx/xxxx?useUnicode=true&characterEncoding=UTF-8&useSSL=false
         username: xxxx
         password: xxxxxx
     ```

   - 新建 `Entity(po)、Mapper、Service、Controller` 文件

     - Entity：entity业务对象继承 ```BisDataEntity``` 类

       ```java
       @Data
       @TableName("example_info")
       public class ExampleInfo extends BisDataEntity<ExampleInfo> {
       
           /**
            * 主键ID
            */
           @TableId
           private Long id;
       
           /**
            * 名称
            */
           private String name;
       }
       ```

     - Mapper：mapper 接口继承 ```BaseBeanMapper``` 接口

       ```java
       public interface ExampleInfoMapper extends BaseBeanMapper<ExampleInfo> {
       }
       ```

     - Service：service 接口继承 ```IBaseBeanService``` 接口，service实现基础 ```BaseBeanServiceImpl``` 类

       ```java
       public interface ExampleInfoService extends IBaseBeanService<ExampleInfo> {
       }
       ```

     - Controller

       ```java
       @RestController
       @RequestMapping("/example")
       public class ExampleInfoController {
       }
       ```

   - 新建启动类

     ```java
     @MapperScan("org.example.mapper")
     @SpringBootApplication
     public class Application {
     
         public static void main(String[] args) {
             SpringApplication.run(Application.class, args);
         }
     }
     ```

2. 新增、修改、删除接口实现

   在controller中引入业务service并添加接口

   ```java
   @RestController
   @RequestMapping("/example")
   public class ExampleInfoController {
       
       private final ExampleInfoService exampleInfoService;
       
       public ExampleInfoController(ExampleInfoService exampleInfoService) {
           this.exampleInfoService = exampleInfoService;
       }
       
       @GetMapping("/save")
       public ResultData save(@RequestBody ExampleInfo exampleInfo) {
           // ......
           // save(entity) 方法当没有主键ID时，默认为insert操作，当有主键ID时默认为update操作
           return ResultData.ok(this.exampleInfoService.save(exampleInfo));
       }
       
       @DeleteMapping("/delete/{id}")
       public ResultData delete(@PathVariable Long id) {
           // ......
           // 通过业务service执行的默认delete操作为逻辑删除
           // 物理删除需在mapper.xml 中自定义delete sql 进行操作
           return ResultData.ok(this.exampleInfoService.removeById(id));
       }
   }
   ```

3. 查询接口实现

   ```java
   @RestController
   @RequestMapping("/example")
   public class ExampleInfoController {
       
       private final ExampleInfoService exampleInfoService;
       
       public ExampleInfoController(ExampleInfoService exampleInfoService) {
           this.exampleInfoService = exampleInfoService;
       }
       
       @GetMapping("/list")
       public ResultData list() {
           // ......
           // 数据列表查询
           return ResultData.ok(this.exampleInfoService.list());
       }
   
       @GetMapping("/page")
       public ResultData page(@RequestBody PageDTO pageDTO) {
           // ......
           // 分页列表查询
           return ResultData.ok(this.exampleInfoService.page(new Page<>(pageDTO.getPageNumber(), pageDTO.getPageSize())));
       }
   }
   ```


##### 集成Nacos Discovery 与 Nacos Config

1. 引入组件 ```basic-nacos-import```

   ```xml
   <!--
   ......
   -->
   <dependencies>
      <dependency>
         <groupId>com.basic.cloud</groupId>
         <artifactId>basic-nacos-import</artifactId>
         <version>${LAST_VERSION}</version>
      </dependency>
   </dependencies>
   ```

2. 新建 ``bootstrap.yml`` 配置文件

   ```yaml
   spring:
     application:
     # 指定注册服务名
       name: example.service
     profiles:
     # 指定config文件使用环境
       active: dev
     cloud:
       nacos:
         # 配置nacos注册中心地址信息
         discovery:
           server-addr: xxxxx
           namespace: xxx
         # 配置nacos配置中心地址信息
         config:
           server-addr: xxxxx
           namespace: xxxx
           group: xxxx
           file-extension: xxx
   
   ```

##### 集成Gateway网关

在网关服务配置文件中添加如下配置：

```yaml
# ......
spring:
  cloud:
    gateway:
      routes:
        - id: example.service # 服务名
          uri: lb://example.service # 服务uri
          predicates: # 断言
            - Path=/example/**
          filters:
            - StripPrefix=1
```

##### 集成OpenFeign

*具体业务服务需要对其他服务提供接口服务时，在Api包中进行集成；*

**引入组件：**

```xml
<!--
......
-->
<dependencies>
   <dependency>
      <groupId>com.basic.cloud</groupId>
      <artifactId>basic-feign-import</artifactId>
      <version>${LAST_VERSION}</version>
   </dependency>
</dependencies>
```

**编写接口：为实现规范化 Feign path的定义必须以```/feign/```开头**

```java
@FeignClient(value = "${api.feign.client.file}", path = "/feign/fileInfo", contextId = "fileInfoFeignClient")
public interface FileInfoFeignClient {
    // ......
}
```

- value：指定服务提供者
- path：feign接口路径
- contextId：SpringBean注册名（必填、唯一），建议与接口名一致

**注意：组件默认扫描Feign注册路径为```com.basic.cloud```包及其子包，如需扫描其他包则在服务调用发使用```@EnableFeignClients```注解进行自定义配置即可;**


##### 集成SpringBootAdmin服务

1. 启动**basic-abs-web**服务

2. 在需要集成的服务中引入依赖

   ```xml
   <!--
   ......
   -->
   <dependencies>
      <dependency>
         <groupId>com.basic.cloud</groupId>
         <artifactId>basic-abs-import</artifactId>
         <version>${LAST_VERSION}</version>
      </dependency>
   </dependencies>
   ```

3. 添加配置

   ```yaml
   spring:
     boot:
       admin:
         client:
           url: http://localhost:8100
           username: username
           password: password
   management:
     endpoints:
       web:
         exposure:
           include: '*'
     endpoint:
       health:
         show-details: ALWAYS
   ```

   参数说明：

   spring.boot.admin.client.url：配置basic-abs-web服务地址

   spring.boot.admin.client.username：配置basic-abs-web服务登录账号

   spring.boot.admin.client.password：配置basic-abs-web服务登录密码

   management：监控端点配置，详情参考：https://codecentric.github.io/spring-boot-admin/current/#spring-boot-admin-client


##### 文件上传

在业务服务中上传文件附件使用

1. 引入文件服务Api

   ```xml
   <!--
   ......
   -->
   <dependencies>
      <dependency>
         <groupId>com.basic.cloud</groupId>
         <artifactId>basic-file-api</artifactId>
         <version>${LAST_VERSION}</version>
      </dependency>
   </dependencies>
   ```

2. 调用上传

   ```java
   //.... 需自行注入
   private final FileInfoFeignClient fileInfoFeignClient;
   
   ```

##### 登录认证、退出

**基础服务使用 ```OAuth2 + JWT``` 进行实现，登录认证授权遵循OAuth2协议.**

1. 登录

   - password模式（服务默认实现）

     请求地址：```${GATEWAY}/${AUTH}```/oauth/token?username=loginName&password=loginPassword&client_id=clientId&client_secret=clientSecret&grant_type=password

     请求方式：POST/GET

     参数说明：

     | 参数          | 描述                      |
     | ------------- | ------------------------- |
     | username      | 登录账号                  |
     | password      | 登录账号密码              |
     | client_id     | 授权客户端ID              |
     | client_secret | 授权客户端密匙            |
     | grant_type    | 授权模式 固定值：password |

     响应示例：

     ```json
     {
         "additionalInformation": {
             "userAccount": "登录账号",
             "userId": "账号唯一ID"
         },
         // token过期时间
         "expiration": 1658560965062,
         "expired": false, // token是否过期
         "expiresIn": 7199, // token有效时长
         "refreshToken": {
             "expiration": 1658812965060, // refreshToken 过期时间
             "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" // refreshToken
         },
         "scope": [
             "all"
         ], // 授权范围
         "tokenType": "bearer", // token 类型
         "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" // token
     }
     ```

     **注意：在登录成功后，客户端应对获取到的token值与refreshToken值进行本地缓存；在后续对其他需授权的业务接口进行方式时，在请求头中添加参数：```Authorization``` 参数值：```${tokenType} + 英文空格 + ${tokenValue}```**

2. 续登

   请求地址：```${GATEWAY}/${AUTH}```/oauth/token?client_id=clientId&client_secret=clientSecret&grant_type=refresh_token&refresh_token=refreshTokenValue

   请求方式：POST

   参数说明：

   | 参数          | 描述                             |
   | ------------- | -------------------------------- |
   | client_id     | 授权客户端ID                     |
   | client_secret | 授权客户端密匙                   |
   | grant_type    | 授权模式 固定值：refresh_token   |
   | refresh_token | 登录时获得的```refreshToken```值 |

   响应示例：

   ```json
   {
       "additionalInformation": {
           "userAccount": "账号",
           "userId": "唯一ID"
       },
       "expiration": 1658561750302,
       "expired": false,
       "expiresIn": 7199,
       "refreshToken": {
           "expiration": 1658813750300,
           "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
       },
       "scope": [
           "all"
       ],
       "tokenType": "bearer",
       "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
   }
   ```

   **响应结果与登录接口响应结果完全一致**

3. 退出

   1. 清除客户端本地缓存的```tokenValue``` 与 ```refreshTokenValue```

   2. 调用登出接口（非必须）

      请求地址：```${GATEWAY}/${AUTH}```/oauth/token/remove?accessToken=accessTokenValue

      请求方式：DELETE

      参数说明：

      | 参数        | 描述                         |
      | ----------- | ---------------------------- |
      | accessToken | 登录时获得的```tokenValue``` |


##### 分布式接口幂等性使用

**组件默认基于Redis进行实现，业务可实现```com.basic.cloud.common.idempotent.IdempotentManager```接口进行自定义实现**

实现原理：

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/redis_idem.png)

使用方式：

在需要幂等的方法上添加注解```com.basic.cloud.common.annotion.Idempotent```,通过SPEL指定幂等key的生成规则；

示例：

```java
@Idempotent(key = "#exampleDTO.name")
@PostMapping("/save")
public ResultData save(@RequestBody ExampleDTO exampleDTO) throws InterruptedException {
	//......
    return ResultData.ok();
}
```

```@Idempotent```注解参数说明：

key：指定幂等控制的key（必须具有唯一性的）

maxLockMilli：加锁最长时间（单位毫秒），默认值10秒

generator：指定key生成器实现，可自行实现```com.basic.cloud.common.base.IdInjectionStrategy```接口

idempotentManager：指定幂等管理器BeanName, 多个管理器情况下使用 @Primary 的管理器, 默认的是 Redis方式，可自行实现```com.basic.cloud.common.idempotent.IdempotentManager```接口

组件属性配置：

| name                         | description | option         |
| ---------------------------- | ----------- | -------------- |
| platform.idem.redisKeyPrefix | redis前缀   | default：idem: |


#### Q&A

*```basic-common```包默认集成hutool工具包，常用工具详见：https://www.hutool.cn/docs*



##### 携带授权令牌请求接口资源后如何获取当前请求用户信息?

```java
com.basic.cloud.common.bean.UserDetail userDetail = com.basic.cloud.common.utils.UserUtil.getUser();
```

<!--UserDetail 类中包含当前请求用户大部分信息数据（用户基本信息、角色、组织机构等）-->



##### 请求参数非空校验？参数格式校验？

```java
public ResultData<Boolean> add(@Validated @RequestBody BlackIpsDTO blackIpsDTO) {
    // ......
}
```

<!--使用org.springframework.validation.annotation.Validated 注解开启校验-->

<!--项目默认使用Spring Validation进行校验,具体注解用法请参考Spring Validation相关文档-->



##### 各种Model之间如何快速相互转换？（Dto to Entity、Entity to Vo等）

```java
// DTO
@MappingData
public class BlackIpsDTO implements Serializable {

    /**
     * ip
     */
    @MappingField
    private String ip;

    /**
     * 禁用时间
     */
    @MappingField(targetClass = Date.class)
    private Long disabledDate;

    /**
     * 截止时间
     */
    @MappingField(targetClass = Date.class)
    private Long deadlineDate;
}

// convert
BlackIpsDTO source = new BlackIpsDTO();
BlackIps target = new BlackIps();
ModelMapper.map(target, source);
```

<!--1.在转换与被转换其中一个class上添加com.basic.cloud.common.annotion.MappingData注解,在转换与被转换property上添加com.basic.cloud.common.annotion.MappingField注解.-->

执行转换：

- 单个对象转换

  ```java
  com.basic.cloud.common.transfer.ModelMapper.map(target, source);
  // OR
  com.basic.cloud.common.transfer.ModelMapper.mapFrom(targetClass,source)
  ```

  

- 多个对象转换

  ```java
  com.basic.cloud.common.transfer.ModelMapper.mapFromCollection(targetClass,sourceCollection);
  // OR
  com.basic.cloud.common.transfer.ModelMapper.mapFromCollection(targetClass,StremSource);
  ```

  

- 转换是自定义额外属性

  ```java
  com.basic.cloud.common.transfer.ModelMapper.map(target, source,Custom MappingFunctional);
  // OR
  com.basic.cloud.common.transfer.ModelMapper.mapFromCollection(targetClass,sourceCollection,Custom MappingFunctional);
  ```



注解参数说明：

```java
com.basic.cloud.common.annotion.MappingData
// targetClass : 目标class
// filter: 自定义class类型过滤

com.basic.cloud.common.annotion.MappingField
// name: 指定target property名称
// targetClass: 指定target property class类型
// postFilter: 指定target property 类型过滤
```



##### 需要获取服务中指定Spring管理的Bean对象？

```java
com.basic.cloud.common.utils.AppContextHelper.getBean(beanName or beanClass);
```



##### 需要获取当前服务运行环境？

```java
com.basic.cloud.common.utils.AppContextHelper.getActiveProfile();
```



##### 需要使用Redis进行缓存操作？

```java
com.basic.cloud.common.utils.RedisUtil.exists(key);
```

RedisUtil中提供的常用方法包括：set、get、expire、exists、delete、increasing、decr、hmget、hmset等；



##### Rest接口全局响应返回格式用哪个？

```java
com.basic.cloud.common.vo.ResultData<T>
```

ResultData中包含多个常用静态方法如：ok()、error()等；



##### Rest接口全局响应返回分页格式用哪个？

```java
com.basic.cloud.common.vo.ResultPage<T>
```



##### 系统提供的内置异常有哪些？

```java
// 数据处理异常
com.basic.cloud.common.exceptions.DataException
// 服务处理异常
com.basic.cloud.common.exceptions.ServiceException
// 三方服务调用异常
com.basic.cloud.common.exceptions.TripartiteServiceException
```



##### 系统内置异常编码定义有哪些？

```java
// class
com.basic.cloud.common.enums.SysErrorTypeEnum
```

- -1：系统异常！
- 1000：数据异常！
- 4416：网关异常！
- 4098：网关超时！
- 4356：服务未找到！
- 8193：无效token！

tip：可以在自己的业务实现中去实现 ```com.basic.cloud.common.base.ErrorType``` 接口，这样使用 ```com.basic.cloud.common.vo.ResultData<T>``` 时会更加方便；



##### 内置的分页接口请求对象？

```java
com.basic.cloud.common.dto.PageDTO
```

tip：在分页查看接口中，具体业务可以进行继承扩展；



#### 组件使用说明

##### basic-gateway（网关组件）

*所有服务请求的统一出入口，黑名单拦截、令牌解析、接口文档、权限控制、限流等在此组件处理；*

###### **组件属性配置**

| name                                 | description                                                 | option                                                       |
| ------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| spring.application.name              | 服务名称                                                    | default: basic.gateway.service                               |
| gate.ignore.swagger.path             | Swaager2服务调用默认接口                                    | default: /v2/api-docs                                        |
| gate.ignore.swagger.service          | Swaager2接口文档忽略的服务名称，多个服务以```/```分割       | default: basic.uaa.service                                   |
| uaa.auth.jwt.signingKey              | jwt 签名                                                    | 需要与UAA服务配置的 ```uaa.auth.jwt.signingKey``` 参数值一致 |
| gate.ignore.authentication.startWith | 不需要网关签权的url配置，多个请求头以英文逗号进行分割       | default: /oauth,/open                                        |
| gate.internal.call.startWith         | 内部调用不需要网关鉴权url配置，多个请求头以英文逗号进行分割 | default: /feign                                              |

###### **黑名单配置**

接口添加：```/blackIps/add```

接口描述：```${GATEWAY_PATH}```/doc.html#/basic.uum.service/black-ips-controller/addUsingPOST

接口删除：```/blackIps/remove```

接口描述：```${GATEWAY_PATH}```/doc.html#/basic.uum.service/black-ips-controller/removeUsingDELETE

查看是否存在：```/blackIps/exits```

接口描述：```${GATEWAY_PATH}```/doc.html#/basic.uum.service/black-ips-controller/exitsUsingGET

手动添加：在数据表```authority_black_ip```中新增一条数据即可

数据实体描述：[请求黑名单IP信息表](#authority_black_ip)


###### **接口资源白名单配置**

在接口白名单配置中的资源信息将不受认证拦截；

详细配置信息参考：[接口资源白名单数据实体描述](#uum_anonymous_info)



#### 数据库设计说明

##### ER图

###### basic-file

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/er/file_er.png)

###### basic-uaa

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/er/uaa_er.png)

###### basic-uums

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/er/uums_er.png)


##### 数据实体描述

###### basic-file

bis_file_info（主文件信息表）

| 字段名称       | 类型     | 约束 | 描述         |
| -------------- | -------- | ---- | ------------ |
| id             | bigint   | PK   | 主键ID       |
| file_name      | varchar  |      | 文件名称     |
| original_name  | varchar  |      | 原名         |
| storage_path   | varchar  |      | 存储路径     |
| storage_server | varchar  |      | 存储服务     |
| size           | bigint   |      | 文件大小     |
| category       | varchar  |      | 业务目录     |
| group_name     | varchar  |      | 分组名称     |
| path           | varchar  |      | 完整路径     |
| remark         | varchar  |      | 文件备注     |
| sort           | tinyint  |      | 排序         |
| tenant_code    | varchar  |      | 租户编码     |
| create_by      | bigint   |      | 创建用户     |
| create_name    | varchar  |      | 创建用户名称 |
| create_time    | datetime |      | 创建日期     |
| update_by      | bigint   |      | 编辑用户     |
| update_name    | varchar  |      | 更新时间     |
| update_time    | datetime |      | 更新时间     |
| del_flag       | tinyint  |      | 是否逻辑删除 |

bis_file_sharding（文件分片表）

| 字段名称    | 类型     | 约束 | 描述         |
| ----------- | -------- | ---- | ------------ |
| id          | bigint   | PK   | 主键ID       |
| shard_key   | varchar  |      | 分片编码     |
| file_id     | bigint   |      | 文件ID       |
| shard_index | int      |      | 第几个分片   |
| total       | int      |      | 总分片       |
| tenant_code | varchar  |      | 租户编码     |
| create_by   | bigint   |      | 创建用户     |
| create_name | varchar  |      | 创建用户名称 |
| create_time | datetime |      | 创建日期     |
| update_by   | bigint   |      | 编辑用户     |
| update_name | varchar  |      | 更新时间     |
| update_time | datetime |      | 更新时间     |
| del_flag    | tinyint  |      | 是否逻辑删除 |



###### basic-uaa

oauth_client_details（三方应用客户端信息）

| 字段名称                | 类型    | 约束 | 描述                                                         |
| ----------------------- | ------- | ---- | ------------------------------------------------------------ |
| client_id               | varchar | PK   | 主键,必须唯一,不能为空.<br />用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成).<br />对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appId,与client_id是同一个概念. |
| resource_ids            | varchar |      | 客户端所能访问的资源id集合,多个资源时用英文逗号分隔,eg: basic.uum.service,basic.file.service |
| client_secret           | varchar |      | 用于指定客户端(client)的访问密匙; 在注册时必须填写(也可由服务端自动生成).<br />对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appId,与client_secret是同一个概念 |
| scope                   | varchar |      | 指定客户端申请的权限范围,可选值包括*read*,*write*,*trust*;若有多个权限范围用英文逗号分隔,eg: read,write |
| authorized_grant_types  | varchar |      | 指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用英文逗号分隔,eg: password,refresh_token |
| web_server_redirect_uri | varchar |      | 客户端的重定向URI,可为空, 当grant_type为`authorization_code`或`implicit`时使用，详情可参考OAuth流程中的redirect_uri参数说明 |
| authorities             | varchar |      | 指定客户端所拥有的固定角色权限值，可选, 若有多个权限值,用英文逗号分隔 eg:ROLE_UNITY,ROLE_USER |
| access_token_validity   | int     |      | 设定客户端的access_token的有效时间值(单位:秒)                |
| refresh_token_validity  | int     |      | 设定客户端的refresh_token的有效时间值(单位:秒)，一般情况下refresh_token的有效时长要大于access_token的有效时长 |
| additional_information  | varchar |      | 预留字段，可定义自定义参数：json格式数据                     |
| autoapprove             | varchar |      | 适用于grant_type="authorization_code"的情况，用户是否自动Approval操作，可选值包括 true,false, read,write |


oauth_access_token（授权access_token信息）

| 字段名称          | 类型      | 约束 | 描述                                                         |
| ----------------- | --------- | ---- | ------------------------------------------------------------ |
| token_id          | varchar   |      | 该字段的值是将access_token的值通过MD5加密后存储的            |
| token             | blob      |      | 存储将OAuth2AccessToken对象序列化后的二进制数据，是真实的AccessToken数据值 |
| authentication_id | varchar   |      | 该字段具有唯一性, 其值是根据当前的username,client_id与scope通过MD5加密生成的 |
| user_name         | varchar   |      | 登录时的用户名, 若客户端没有用户名,如客户端没有用户名，则该值等于client_id |
| client_id         | varchar   |      | client_id的值                                                |
| authentication    | blob      |      | 存储将OAuth2Authentication对象序列化后的二进制数据           |
| refresh_token     | varchar   |      | 该字段的值是将refresh_token的值通过MD5加密后存储的，主要用来续签时使用 |
| create_time       | timestamp |      | 数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段) |

oauth_refresh_token（续签access_token的信息）

| 字段名称       | 类型      | 约束 | 描述                                                         |
| -------------- | --------- | ---- | ------------------------------------------------------------ |
| token_id       | varchar   |      | 该字段的值是将refresh_token的值通过MD5加密后存储的           |
| token          | blob      |      | 存储将OAuth2RefreshToken对象序列化后的二进制数据             |
| authentication | blob      |      | 存储将OAuth2Authentication对象序列化后的二进制数据           |
| create_time    | timestamp |      | 数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段) |

oauth_code（授权码信息）

| 字段名称       | 类型      | 约束 | 描述                                                         |
| -------------- | --------- | ---- | ------------------------------------------------------------ |
| code           | varchar   |      | 存储服务端系统生成的code的值(未加密)                         |
| authentication | blob      |      | 存储将AuthorizationRequestHolder对象序列化后的二进制数据     |
| create_time    | timestamp |      | 数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段) |


###### basic-uums

<a id="authority_black_ip">authority_black_ip（请求黑名单IP信息）</a>

| 字段名称      | 类型     | 约束 | 描述                      |
| ------------- | -------- | ---- | ------------------------- |
| id            | bigint   | PK   | 主键ID                    |
| ip            | varchar  |      | ip                        |
| disabled_date | datetime |      | 禁用时间                  |
| deadline_date | datetime |      | 截止时间 为null则永久禁用 |
| tenant_code   | varchar  |      | 租户编码                  |
| create_by     | bigint   |      | 创建用户                  |
| create_name   | varchar  |      | 创建用户名称              |
| create_time   | datetime |      | 创建日期                  |
| update_by     | bigint   |      | 编辑用户                  |
| update_name   | varchar  |      | 更新时间                  |
| update_time   | datetime |      | 更新时间                  |
| del_flag      | tinyint  |      | 是否逻辑删除              |


uum_user_info（用户主信息）

| 字段名称             | 类型     | 约束 | 描述                 |
| -------------------- | -------- | ---- | -------------------- |
| id                   | bigint   | PK   | 主键ID               |
| account              | varchar  |      | 账号                 |
| name                 | varchar  |      | 名称                 |
| password             | varchar  |      | 密码                 |
| slat                 | varchar  |      | 密码盐值             |
| user_no              | varchar  |      | 用户编号/工号        |
| mobile               | varchar  |      | 电话                 |
| email                | varchar  |      | 邮箱                 |
| wx_open_id           | varchar  |      | WX Open Id           |
| status               | tinyint  |      | 状态                 |
| user_image           | varchar  |      | 用户头像             |
| user_image_thumbnail | varchar  |      | 用户头像缩略图       |
| type                 | tinyint  |      | 用户类型             |
| nick_name            | varchar  |      | 昵称                 |
| third_no             | varchar  |      | 三方编码（公司编码） |
| sex                  | tinyint  |      | 性别                 |
| tenant_code          | varchar  |      | 租户编码             |
| create_by            | bigint   |      | 创建用户             |
| create_name          | varchar  |      | 创建用户名称         |
| create_time          | datetime |      | 创建日期             |
| update_by            | bigint   |      | 编辑用户             |
| update_name          | varchar  |      | 更新时间             |
| update_time          | datetime |      | 更新时间             |
| del_flag             | tinyint  |      | 是否逻辑删除         |

uum_user_ext（用户扩展信息表）

| 字段名称              | 类型     | 约束 | 描述             |
| --------------------- | -------- | ---- | ---------------- |
| user_id               | bigint   | PK   | 用户ID           |
| user_password_changed | datetime |      | 初始密码改变日期 |
| user_enabled_date     | datetime |      | 账号启用日期     |
| user_disabled_date    | datetime |      | 失效时间         |
| user_account_locked   | tinyint  |      | 是否锁定         |
| last_login_date       | datetime |      | 最后登录日期     |
| login_fails           | tinyint  |      | 登录失败次数     |
| remark                | varchar  |      | 备注信息         |
| user_description      | varchar  |      | 用户描述         |
| tenant_code           | varchar  |      | 租户编码         |
| create_by             | bigint   |      | 创建用户         |
| create_name           | varchar  |      | 创建用户名称     |
| create_time           | datetime |      | 创建日期         |
| update_by             | bigint   |      | 编辑用户         |
| update_name           | varchar  |      | 更新时间         |
| update_time           | datetime |      | 更新时间         |
| del_flag              | tinyint  |      | 是否逻辑删除     |

uum_unit_info（组织机构信息）

| 字段名称        | 类型     | 约束 | 描述                                    |
| --------------- | -------- | ---- | --------------------------------------- |
| id              | bigint   | PK   | 主键ID                                  |
| unit_code       | varchar  |      | 机构编码                                |
| unit_name       | varchar  |      | 机构名称                                |
| parent_unit_id  | bigint   |      | 上级组织机构编码                        |
| unit_type       | tinyint  |      | 组织机构类型，1组织机构，2部门，3分公司 |
| order_num       | int      |      | 排序号，越小越好                        |
| valid           | tinyint  |      | 是否有效                                |
| unit_full_name  | varchar  |      | 机构全路径名称                          |
| sub_tenant_code | varchar  |      | 下级租户编号，下级机构编码              |
| enabled_date    | datetime |      | 启用日期                                |
| tree_path       | varchar  |      | 树结构处理，树路径                      |
| admin_account   | varchar  |      | 组织管理员账号                          |
| tenant_code     | varchar  |      | 租户编码                                |
| create_by       | bigint   |      | 创建用户                                |
| create_name     | varchar  |      | 创建用户名称                            |
| create_time     | datetime |      | 创建日期                                |
| update_by       | bigint   |      | 编辑用户                                |
| update_name     | varchar  |      | 更新时间                                |
| update_time     | datetime |      | 更新时间                                |
| del_flag        | tinyint  |      | 是否逻辑删除                            |

uum_user_unit（用户机构信息）

| 字段名称    | 类型     | 约束 | 描述                   |
| ----------- | -------- | ---- | ---------------------- |
| id          | bigint   | PK   | 主键ID                 |
| user_id     | bigint   |      | 用户Id                 |
| unit_id     | bigint   |      | 机构部门ID             |
| main        | tinyint  |      | 是否主部门             |
| valid       | tinyint  |      | 是否有效               |
| order_num   | int      |      | 多部门情况时的先后排序 |
| tenant_code | varchar  |      | 租户编码               |
| create_by   | bigint   |      | 创建用户               |
| create_name | varchar  |      | 创建用户名称           |
| create_time | datetime |      | 创建日期               |
| update_by   | bigint   |      | 编辑用户               |
| update_name | varchar  |      | 更新时间               |
| update_time | datetime |      | 更新时间               |
| del_flag    | tinyint  |      | 是否逻辑删除           |

uum_role_info（角色信息）

| 字段名称         | 类型     | 约束 | 描述                          |
| ---------------- | -------- | ---- | ----------------------------- |
| id               | bigint   | PK   | 主键ID                        |
| role_name        | varchar  |      | 角色名称                      |
| role_code        | varchar  |      | 角色编码                      |
| role_description | varchar  |      | 角色描述                      |
| common           | tinyint  |      | 是否公共角色                  |
| role_type        | tinyint  |      | 角色类型,1管理角色，2业务角色 |
| update_table     | tinyint  |      | 是否可维护                    |
| user_numbers     | int      |      | 角色用户数                    |
| tenant_code      | varchar  |      | 租户编码                      |
| create_by        | bigint   |      | 创建用户                      |
| create_name      | varchar  |      | 创建用户名称                  |
| create_time      | datetime |      | 创建日期                      |
| update_by        | bigint   |      | 编辑用户                      |
| update_name      | varchar  |      | 更新时间                      |
| update_time      | datetime |      | 更新时间                      |
| del_flag         | tinyint  |      | 是否逻辑删除                  |

uum_user_group_role（用户或组织角色关联信息）

| 字段名称        | 类型     | 约束 | 描述                                                         |
| --------------- | -------- | ---- | ------------------------------------------------------------ |
| id              | bigint   | PK   | 主键ID                                                       |
| role_id         | bigint   |      | 角色ID                                                       |
| user_group_id   | bigint   |      | 当关联以用户为单位时，这里放的是用户ID<br />当以组织部门为单位时，这里放的是组织部门ID |
| user_group_type | tinyint  |      | 关联类型，1用户为单位，2组织部门为单位                       |
| tenant_code     | varchar  |      | 租户编码                                                     |
| create_by       | bigint   |      | 创建用户                                                     |
| create_name     | varchar  |      | 创建用户名称                                                 |
| create_time     | datetime |      | 创建日期                                                     |
| update_by       | bigint   |      | 编辑用户                                                     |
| update_name     | varchar  |      | 更新时间                                                     |
| update_time     | datetime |      | 更新时间                                                     |
| del_flag        | tinyint  |      | 是否逻辑删除                                                 |

uum_menu_info（菜单信息）

| 字段名称       | 类型     | 约束 | 描述                               |
| -------------- | -------- | ---- | ---------------------------------- |
| id             | bigint   | PK   | 主键ID                             |
| menu_name      | varchar  |      | 菜单名称                           |
| parent_menu_id | bigint   |      | 父级菜单ID                         |
| menu_type      | tinyint  |      | 菜单类型 1业务菜单,0管理菜单       |
| page_point     | tinyint  |      | 是否页面资源  boolean              |
| menu_url       | varchar  |      | 菜单url                            |
| menu_open_mode | tinyint  |      | 打开模式  1.工作区打开 2新窗口打开 |
| menu_image_url | varchar  |      | 菜单图标样式                       |
| order_num      | int      |      | 排序                               |
| menu_chief     | tinyint  |      | 是否菜单叶子节点  boolean          |
| product_id     | bigint   |      | 产品ID                             |
| loading        | tinyint  |      | 是否加载业务菜单 boolean           |
| tenant_code    | varchar  |      | 租户编码                           |
| create_by      | bigint   |      | 创建用户                           |
| create_name    | varchar  |      | 创建用户名称                       |
| create_time    | datetime |      | 创建日期                           |
| update_by      | bigint   |      | 编辑用户                           |
| update_name    | varchar  |      | 更新时间                           |
| update_time    | datetime |      | 更新时间                           |
| del_flag       | tinyint  |      | 是否逻辑删除                       |

uum_role_menu （角色菜单信息）

| 字段名称    | 类型     | 约束 | 描述                          |
| ----------- | -------- | ---- | ----------------------------- |
| id          | bigint   | PK   | 主键ID                        |
| role_id     | bigint   |      | 角色ID                        |
| menu_id     | bigint   |      | 菜单ID                        |
| semi_select | tinyint  |      | 是否半选 boolean 适应前端组件 |
| tenant_code | varchar  |      | 租户编码                      |
| create_by   | bigint   |      | 创建用户                      |
| create_name | varchar  |      | 创建用户名称                  |
| create_time | datetime |      | 创建日期                      |
| update_by   | bigint   |      | 编辑用户                      |
| update_name | varchar  |      | 更新时间                      |
| update_time | datetime |      | 更新时间                      |
| del_flag    | tinyint  |      | 是否逻辑删除                  |

uum_resource_info（服务资源信息）

| 字段名称    | 类型     | 约束 | 描述         |
| ----------- | -------- | ---- | ------------ |
| id          | bigint   | PK   | 主键ID       |
| app_id      | varchar  |      | 服务名称     |
| name        | varchar  |      | 资源名称     |
| url         | varchar  |      | 资源url      |
| method      | varchar  |      | httpMethod   |
| description | varchar  |      | 资源描述     |
| product_id  | bigint   |      | 产品ID       |
| tenant_code | varchar  |      | 租户编码     |
| create_by   | bigint   |      | 创建用户     |
| create_name | varchar  |      | 创建用户名称 |
| create_time | datetime |      | 创建日期     |
| update_by   | bigint   |      | 编辑用户     |
| update_name | varchar  |      | 更新时间     |
| update_time | datetime |      | 更新时间     |
| del_flag    | tinyint  |      | 是否逻辑删除 |

uum_resource_authority（资源授权信息）

| 字段名称       | 类型     | 约束 | 描述                           |
| -------------- | -------- | ---- | ------------------------------ |
| id             | bigint   | PK   | 主键ID                         |
| role_id        | bigint   |      | 角色ID                         |
| resource_id    | bigint   |      | 资源ID                         |
| resource_type  | tinyint  |      | 资源类型                       |
| resource_opt   | tinyint  |      | 资源操作类型,1可以使用,2可分配 |
| authority_desc | varchar  |      | 描述                           |
| tenant_code    | varchar  |      | 租户编码                       |
| create_by      | bigint   |      | 创建用户                       |
| create_name    | varchar  |      | 创建用户名称                   |
| create_time    | datetime |      | 创建日期                       |
| update_by      | bigint   |      | 编辑用户                       |
| update_name    | varchar  |      | 更新时间                       |
| update_time    | datetime |      | 更新时间                       |
| del_flag       | tinyint  |      | 是否逻辑删除                   |

<a id="uum_anonymous_info">uum_anonymous_info（白名单资源信息）</a>

| 字段名称    | 类型     | 约束 | 描述                  |
| ----------- | -------- | ---- | --------------------- |
| id          | bigint   | PK   | 主键ID                |
| app_id      | varchar  |      | 应用服务ID            |
| url         | varchar  |      | 请求服务URL           |
| http_method | varchar  |      | 请求方式：POST、GET等 |
| description | varchar  |      | 描述                  |
| tenant_code | varchar  |      | 租户编码              |
| create_by   | bigint   |      | 创建用户              |
| create_name | varchar  |      | 创建用户名称          |
| create_time | datetime |      | 创建日期              |
| update_by   | bigint   |      | 编辑用户              |
| update_name | varchar  |      | 更新时间              |
| update_time | datetime |      | 更新时间              |
| del_flag    | tinyint  |      | 是否逻辑删除          |
