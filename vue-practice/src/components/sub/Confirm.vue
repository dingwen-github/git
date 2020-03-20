<!--可复用的子组件-->
<!--template中写html代码-->
<template>
    <div class="confirm-button">
      <h1>可复用的子组件</h1>
      <button @click="getButtonClick">{{text || '确认'}}</button>
    </div>
</template>
<!--script中写js-->
<script>
export default {
  name: 'confirm-button',
  // 父组件传给子组件的值
  props: ['text'],
  data () {
    return {
      msg: true
    }
  },
  methods: {
    // 点击按钮的时候向父组件传值
    getButtonClick () {
      // 向父组件传值
      this.$emit('message', this.msg)
    }
  }
}
</script>
