# sunFlow

#### 介绍

sunFlow基于是基于camunda开发的工作流web系统。设计理念是易用，简单，美观，高效。 尤其注重对开发友好；
项目设计之初就考虑了前后端分离的vue开发模式。角色和路由之间的绑定跟角色和数据权限之间是不耦合的。前端开发只需要开发路由组件，
后端开发只需要开发 数据api接口。从菜单配置界面上分前端做的是左边的部分，后端是开发右边的部分，当然如果你不用工作流**只用后台权限管理本框架也是一个绝佳的选择**。[wiki](https://gitee.com/cangjingge/sunFlow/wikis/%E5%AE%89%E8%A3%85%E6%95%99%E7%A8%8B?sort_id=1438493)
#### 软件架构
软件架构说明：目前项目分三个模块，camunda（工作流相关服务），upms-server(后台权限管理模块)，webApp(前端模块)

系统为了降低上手成本，和自由度，各个模块之间依赖尽量降低，而且对组件依赖也很低，有些都是可选的，比如redis组件你就可以选择不用，而且仅仅通过一个spring注解就可以控制，具体看[wiki](https://gitee.com/cangjingge/sunFlow/wikis/%E5%AE%89%E8%A3%85%E6%95%99%E7%A8%8B?sort_id=1438493)

| 模块名   |   描述 |  技术栈  |
| -- | -- | -- |
|  camunda  |  工作流restful服务端，使用restful的原因是，以后流程可以实现在多个业务系统之间优雅的流转  |  camunda-7.1,spring-boot 2.0，mysql  |
|  upms-server  |  后端管理框架，有很多舒服的组件，注解式日志，注解式入参校验等等，让后端开发过程更加愉悦  |  spring-boot 2.0，shiro,mybatis,redis，mysql  |
|   webApp |  前端模块  |  vue2.0,element-admin,bpmn-js(已做汉化)  |
|   文件服务器 |  采用minio自行安装即可  |  minio  |

vue代码规范：格式为eslint

#### 系统截图
登录页面
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/login.png)
绘制流程
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/flow.png)
首页
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/home.png)
菜单管理
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/menu.png)
角色权限
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/roleauth.png)
用户管理
![](https://gitee.com/cangjingge/sunFlow/raw/master/doc/img/user.png)
#### 安装教程
1. 执行doc下面的sql脚本到mysql数据库中，配置项目的数据链接
2. 后端启动后台管理服务Application#main
3. 后端启动工作流restful服务FlowApplication#main（ **可选** ）
4. 后端启动minio文件服务器,doc下有server和client客户端（ **可选** ）
5. 前端运行cd webapp  ; npm install  ; npm run dev
#### 开发进度
目前权限架构已经完成：菜单配置，用户管理，角色配置，部门管理；工作流引擎已经集成，下一步需要抽象出一种工作流的开发模式出来
#### 开发模式
后端提供api数据接口按照已有模板格式开发即可，是spring mvc模式
前端通过vue单文件组件进行开发。所有组件放在views文件夹下
#### 联系方式
本人在上班时间不方便回复，大家有问题可以留言，提issues也行。邮箱地址zhangsen@protonmail.com;目前阶段，
如果只用后台管理功能可以使用，如果想用工作流引擎，还需要自己看camunda的官方文档中的restful api文档进行开发。
等后面做几个例子给大家