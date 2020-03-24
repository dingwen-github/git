<template>
  <div class="dashboard-container">
    <el-container>
      <el-container>
        <el-aside width="400px">
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :size="activity.size"
              :timestamp="activity.timestamp"
              placement="top">
              <el-card>
                <h4>{{ activity.timestamp }}</h4>
                <p>{{ activity.content }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-aside>
        <el-main>
          <el-card class="box-card">项目介绍
          采用<span style="color: #E6A23C">Spring、MyBatis-plus、Shiro,element-admin</span>框架，开发的一套权限系统，使用门槛低，拿来即用。
          项目提供了代码生成器，后端代码一键生成,前端采用vue组件的开发模式，只要你会使用vue前端没有任何限制，开发自由度很高。
          项目目前完成的模块:<span style="color: #409EFF">菜单管理，角色管理，用户管理，部门管理</span>。而且交互方式更加现代化，独创的后台管理配置交互模式，给开发提供一个良好的配置体验。
          项目权限控制支持三种认证方式，<span style="color: #67C23A">cas单点登录，普通用户名密码登录，oauth2认证（支持优雅的扩展更多的oauth2）</span>。目前的功能足够用来做一个简单的业务系统。后期打算接入redis缓存技术。开发接入activiti工作流引擎
            <p>Git地址：
              <a href="https://gitee.com/cangjingge/sunFlow/stargazers"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/star.svg?theme=white"
                alt="star"></a>
              <a href="https://gitee.com/cangjingge/sunFlow/members"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/fork.svg?theme=white"
                alt="fork"></a>
            </p>
          </el-card>
          <el-card class="box-card">项目目前做了升级element最新版本兼容。后面打算在element稳定之后升级新版本element-admin,而且项目中所用到的技术，后期都会在合适的时候进行版本升级，与时俱进
            <p>Git地址：
              <a href="https://gitee.com/cangjingge/sunFlow/stargazers"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/star.svg?theme=white"
                alt="star"></a>
              <a href="https://gitee.com/cangjingge/sunFlow/members"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/fork.svg?theme=white"
                alt="fork"></a>
            </p>
          </el-card>
          <el-card class="box-card">开发人员：
            <el-tooltip class="item" effect="light" content="项目架构搭建，权限控制，用户部门菜单路由管理" placement="top-start">
              <el-button type="primary">jason</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="角色管理，oauth2功能，数据字典" placement="top-start">
              <el-button type="success">jwy</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="数据字典" placement="top-start">
              <el-button type="primary">cjy</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="工作流模块搭建" placement="top-start">
              <el-button type="success">hfz</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="还在打酱油" placement="top-start">
              <el-button type="primary">yyf</el-button>
            </el-tooltip>
            <p>Git地址：
              <a href="https://gitee.com/cangjingge/sunFlow/stargazers"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/star.svg?theme=white"
                alt="star"></a>
              <a href="https://gitee.com/cangjingge/sunFlow/members"><img
                src="https://gitee.com/cangjingge/sunFlow/badge/fork.svg?theme=white"
                alt="fork"> </a>
            </p>
          </el-card>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      activities: [{
        content: '项目正式启动',
        timestamp: '2019-04-03 20:37',
        size: 'large',
        type: 'primary',
        icon: 'el-icon-more'
      },
      {
        content: '用户角色菜单部门开发完成',
        timestamp: '2019-05-01 20:00',
        color: '#0bbd87'
      },
      {
        content: '支持三种主流授权方式',
        timestamp: '2018-05-20 20:46',
        size: 'large'
      },
      {
        content: '数据字典功能开发完成',
        timestamp: '2018-05-29 20:46'
      },
      {
        content: '完成工作流技术集成',
        timestamp: '2018-06-9 20:38'
      },
      {
        content: '引入redis缓存技术',
        timestamp: '2018-06-16 22:38'
      },
      {
        content: 'validate模块开发完成',
        timestamp: '2018-07-8 20:12'
      },
      {
        content: '加入对象存储',
        timestamp: '2018-07-14 20:12'
      }]
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }
    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
