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
