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
  * [集成Nacos Discovery 与 Nacos Config](#集成nacos-discovery-与-nacos-config)
  * [集成Gateway网关组件](#集成gateway网关)
  * [集成OpenFeign组件](#集成openfeign)
  * [文件上传](#文件上传)
  * [登录认证、续签、退出](#登录认证退出)
* [组件使用说明](#组件使用说明)
  * [basic\-gateway（网关组件）](#basic-gateway网关组件)
* [数据库设计说明](#数据库设计说明)
  * [ER图](#er图)
  * [数据实体描述](#数据实体描述)
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


#### 组件使用说明

##### basic-gateway（网关组件）

*所有服务请求的统一出入口，黑名单拦截、令牌解析、接口文档、权限控制、限流等在此组件处理；*

**组件属性配置**

| name                                 | description                                                 | option                                                       |
| ------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| spring.application.name              | 服务名称                                                    | default: basic.gateway.service                               |
| gate.ignore.swagger.path             | Swaager2服务调用默认接口                                    | default: /v2/api-docs                                        |
| gate.ignore.swagger.service          | Swaager2接口文档忽略的服务名称，多个服务以```/```分割       | default: basic.uaa.service                                   |
| uaa.auth.jwt.signingKey              | jwt 签名                                                    | 需要与UAA服务配置的 ```uaa.auth.jwt.signingKey``` 参数值一致 |
| gate.ignore.authentication.startWith | 不需要网关签权的url配置，多个请求头以英文逗号进行分割       | default: /oauth,/open                                        |
| gate.internal.call.startWith         | 内部调用不需要网关鉴权url配置，多个请求头以英文逗号进行分割 | default: /feign                                              |

**黑名单配置：**

接口添加：```/anonymousInfo/add```

接口描述：```${GATEWAY_PATH}```/doc.html#/basic.uum.service/black-ips-controller/addUsingPOST

手动添加：在数据表```authority_black_ip```中新增一条数据即可

数据实体描述：




#### 数据库设计说明

##### ER图

- **basic-file**

  ![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/er/file_er.png)

- **basic-uaa**

- **basic-uums**

##### 数据实体描述

- **basic-file**

  主文件信息表

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

  文件分片表

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

  

- **basic-uaa**

- **basic-uums**
