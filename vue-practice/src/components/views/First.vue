<!--
template写HTML模板
一个组件里面只能有一个最外层div
-->
<template>
  <div class="first-app">
    {{msg}}
<!--    使用子组件-->
<!--    传给子组件的值-->
    <confirm text="注册" @message="getMessage"></confirm>
    <p><router-link to="/second">去第二个页面</router-link></p>
  </div>
</template>
<!--script写js-->
<script>
// 引入子组件
import Confirm from '../sub/Confirm'
export default {
  name: 'First',
  components: {
    Confirm
  },
  data () {
    return {
      msg: 'Welcome to FirstApp'
    }
  },
  methods: {
    // 获取子组件传给父组件的值
    getMessage (val) {
      alert('子组件传给父组件的值是：' + val)
    }
  }
}
</script>
<!--style里面写样式-->
<style>
  div.first-app{
    height: 400px;
    background-color: red;
  }
</style>
