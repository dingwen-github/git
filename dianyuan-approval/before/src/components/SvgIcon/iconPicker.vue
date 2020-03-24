<template>
  <el-popover
    v-model="popoverVisible"
    :disabled="disabledSelected"
    popper-class="picker-pop"
    placement="bottom-start"
    transition="el-zoom-in-top"
    trigger="click">
    <!-- 弹出框内容区 -->
    <el-scrollbar
      v-if="popoverVisible"
      :native="false"
      :noresize="false"
      class="hide-x"
      style="height: 100%">
      <!-- 图标项 -->
      <div
        v-for="item in icons"
        :key="item"
        :class="{ 'is-active-icon': isActive(item) }"
        class="iconPicker_trigger"
        @click="onClickSelected(item)">
        <span class="iconPicker_icon">
          <span class="iconPicker_ico-inner"><svg-icon :icon-class="item" /></span>
        </span>
      </div>
    </el-scrollbar>
    <!-- 页面显示内容区 -->
    <template slot="reference">
      <div class="iconPicker_trigger">
        <span class="iconPicker_icon">
          <span class="iconPicker_ico-inner"><svg-icon :icon-class="val" /></span>
        </span>
      </div>
    </template>
  </el-popover>
</template>

<script>
export default {
  name: 'IconPicker',
  // 设置绑定参数
  model: {
    prop: 'value',
    event: 'selected'
  },
  props: {
    disabled: { type: Boolean, default: false },
    // 接收绑定参数 - 图标类名
    value: {
      type: String,
      default: ''
    },
    // 选项数据，图标类名数组
    icons: {
      type: Array,
      default: () => (['user', 'tree', 'menu', 'eye', 'eye-open', 'example', 'form', 'link',
        'nested', 'password', 'table', 'role', 'jishu'])
    }
  },
  data() {
    return {
      // 弹出框显示状态
      popoverVisible: false,
      val: this.value
    }
  },
  computed: {
    disabledSelected() {
      if (this.disabled) return true
      return this.$parent.form ? this.$parent.form.disabled : false
    }
  },
  watch: { value: function(newval, oldval) {
    this.val = newval
  } },
  methods: {
    // 是否为当前已选项
    isActive(item) {
      return this.val === item
    },
    // 选中图标
    onClickSelected(item) {
      this.val = item
      this.$emit('selected', item)
      this.popoverVisible = false
    },
    // 清空选项
    onClickClear() {
      this.$emit('selected', 'menu')
    }
  }
}
</script>

<style>

.iconPicker_trigger{
  display: inline-block;
  box-sizing: border-box;
  height: 40px;
  width: 40px;
  padding: 4px;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  position: relative;
  cursor: pointer;
  margin-right: 5px;
}
.iconPicker_icon{
  position: relative;
  display: block;
  box-sizing: border-box;
  border: 1px solid #999;
  border-radius: 2px;
  width: 100%;
  height: 100%;
  text-align: center;
  background-color: #FFF;
}
  .iconPicker_ico-inner{
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
  }
  .el-scrollbar .iconPicker_ico-inner{
    line-height: 28px;
  }
  .is-active-icon{
    border: 1px solid #409EFF;
  }
  .picker-pop{
    height: 200px;
    width: 390px;
  }
.hide-x .el-scrollbar__wrap{overflow-x:hidden;}
</style>
