* [基础组件](#基础组件)
  * [组件说明](#组件说明)
  * [业务组件包拆分说明](#业务组件包拆分说明)
  * [业务系统架构图](#业务系统架构图)
  * [QuickStart Guide](#quickstart-guide)
    * [在Maven Project 中使用](#在maven-project-中使用)
    * [Basic Usage（基于SpringBoot编写）](#basic-usage基于springboot编写)
    * [集成Nacos Discovery 与 Nacos Config](#集成nacos-discovery-与-nacos-config)
# 基础组件
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
#### 业务组件包拆分说明

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/model.png)

- Tripartie Service：其他业务服务
- Entity：业务对象实体（PO、VO、DTO）服务常量定义（CONTSTANT）
- Service：具体业务逻辑处理实现
- Api：业务对外提供服务API定义
- Web：业务服务提供者

#### 业务系统架构图

![image](https://github.com/lanrenspace/stagging-basic-cloud/blob/master/design/projectArchitecture.png)

#### QuickStart Guide

##### 在Maven Project 中使用

1. 在具体业务实现服务`pom.xml` 文件中添加依赖：

   ```xml
   <dependencyManagement>
      <dependencies>
         <dependency>
            <groupId>com.basic.cloud</groupId>
            <artifactId>stagging-basic-cloud</artifactId>
            <version>${last version}</version>
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
         <version>${last version}</version>
      </dependency>
   </dependencies>
   
   <dependencyManagement>
      <dependencies>
         <dependency>
            <groupId>com.basic.cloud</groupId>
            <artifactId>stagging-basic-cloud</artifactId>
            <version>${last version}</version>
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
           <version>${last version}</version>
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
         <version>${last version}</version>
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

   
