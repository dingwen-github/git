<template>
  <div>
    Home组件
    <el-button type="info" @click="logout">退出</el-button>
  </div>
</template>

<script>
export default {
  methods: {
    //  退出登录
    logout () {
      //  清空sessionStorage
      window.sessionStorage.clear()
      //  编程式导航到登录页
      this.$router.push('/login')
    }
  }
}
</script>
<!--scoped: 样式只在当前组件中生效-->
<style lang="less" scoped></style>
