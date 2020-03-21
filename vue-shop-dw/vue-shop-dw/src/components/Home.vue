<template>
<!--  主页-->
  <el-container class="home-container">
<!--    头部区域-->
    <el-header>
      <div>
        <img src="../assets/logo.png" alt="headImage">
        <span>电商后台管理系统</span>
      </div>
      <el-button @click="logout" type="info">退出</el-button>
    </el-header>
<!--    页面主题区域-->
    <el-container>
<!--      侧边栏-->
      <el-aside :width="isCollapse ? '64px' : '200px'">
<!--        折叠展开按钮-->
        <div class="toggle-button" @click="toggleCollapse">|||</div>
<!--        菜单区域-->
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409EEf" unique-opened :collapse="isCollapse"
          :collapse-transition="false"
          router
          :default-active="activePath">
<!--         一级菜单-->
<!--          v-for:尽量指定唯一却的key-->
          <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
<!--            一级菜单模板-->
            <template slot="title">
<!--              图标-->
              <i :class="iconObj[item.id]"></i>
<!--              文本-->
              <span>{{item.authName}}</span>
            </template>
<!--            二级菜单-->
              <el-menu-item :index="'/'+ch.path" v-for="ch in item.children" :key="ch.id"
              @click="saveNavState('/'+ch.path)">
                <template slot="title">
                  <!--              图标-->
                  <i class="el-icon-menu"></i>
                  <!--              文本-->
                  <span>{{ ch.authName }}</span>
                </template>
              </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
<!--      内容区域-->
      <el-main>
<!--        子路由的路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      //  左侧菜单数据
      menuList: [],
      //  一级菜单图标
      iconObj: {
        125: 'iconfont icon-user',
        103: 'iconfont icon-tijikongjian',
        101: 'iconfont icon-shangpin',
        102: 'iconfont icon-danju',
        145: 'iconfont icon-baobiao'
      },
      //  是否折叠
      isCollapse: false,
      //  激活的链接地址
      activePath: ''
    }
  },
  //  组件创建完成后加载菜单列表
  created () {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  methods: {
    //  退出登录
    logout () {
      //  清空sessionStorage
      window.sessionStorage.clear()
      //  编程式导航到登录页
      this.$router.push('/login')
    },
    //  获取所有的菜单
    async getMenuList () {
      //  为了简化Promise，使用async和await
      //  解构赋值
      const { data: res } = await this.$http.get('menus')
      // console.log(res)
      if (res.meta.status !== 200) return this.$message.error(res.meta.msg)
      this.menuList = res.data
    },
    //  点击按钮展开侧边栏的折叠与展开
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    //  保存链接的激活状态
    saveNavState (activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
</script>
<!--scoped: 样式只在当前组件中生效-->
<style lang="less" scoped>
  .el-header {
    background-color: #373d41;
    display: flex;
    /*左右对齐*/
    justify-content: space-between;
    padding-left: 0px;
    /*上下居中*/
    align-items: center;
    color: #fff;
    font-size: 20px ;
    /*给右侧文本嵌套一点div,实现垂直居中对齐*/
    > div {
      display: flex;
      align-items: center;
    }
  }
  .el-aside {
    background-color: #333744;
  }
  .el-main {
    background-color: #EAEDF1;
  }
  .home-container {
    height: 100%;
  }
  .iconfont {
    margin-right: 10px;
  }
  .el-menu {
    border-right: none;
  }

  .toggle-button {
    background-color: #4A5064;
    color: #fff;
    font-size: 10px;
    line-height: 24px;
    text-align: center;
    letter-spacing:0.2em;
    cursor: pointer;
  }
</style>
