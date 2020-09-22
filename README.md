[![GitHub release](https://img.shields.io/badge/release-2.0.1-28a745.svg)](https://github.com/0nebean/com.alibaba.druid-0nebean.custom/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

![](https://img.shields.io/badge/belong_to-chemical--el-yellowgreen.svg)
![](https://img.shields.io/badge/support-onebean--data-red.svg)
![](https://img.shields.io/badge/dependency-spring--15.20-blue.svg)
![](https://img.shields.io/badge/runing_on_Kubernetes-10.96.0.1-green.svg)  

![](https://img.shields.io/badge/middleware-mysql-lightgrey.svg)
![](https://img.shields.io/badge/middleware-apollo-lightgrey.svg)
![](https://img.shields.io/badge/middleware-eureka-lightgrey.svg)
![](https://img.shields.io/badge/middleware-rabbitMQ-lightgrey.svg)
![](https://img.shields.io/badge/middleware-redis-lightgrey.svg)  


[Introduction](https://0nebean.github.io/Magnesium/)
---
- Magnesium 是一个以管理API和DevOps工具链 为核心功能的数据中台。


- 框架特性
  - 基于[`Silicon`](https://0nebean.github.io/Silicon/)，[`VUE`](https://cn.vuejs.org/) 框架开发
  - 数据中台能力，以服务和应用的概念解耦项目和API之间的关联
  - API网关能力，提供oauth授权码，单设备登陆，单点登陆等丰富的API鉴权模式，满足常见业务场景
  - 独立的user-auth体系，针对平台上接入的每个应用单独分表，提供独立却不臃肿的用户体系,省去重复开发
  - 基于Kubernetes的完整devops工具链，提供基于openresty的Kubernetes流量路由组件，一键部署，版本热切换
  - 图形化的nginx配置工具，配置域名和证书信息无需离开中台的页面
  - 为[`SaaS系统 Sodium`](https://0nebean.github.io/Sodium/)租户信息管理下发提供一站式的入口(选装功能,下一版本将已移除)

Documentation
---
[Magnesium Documentation](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/md/magnesium_2.x.md)

控制台预览：
![](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/img/62.png)
![](https://github.com/0nebean/chemical-el/blob/master/src/main/resources/img/61.png)
