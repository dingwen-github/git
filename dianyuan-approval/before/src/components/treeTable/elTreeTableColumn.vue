<template>
  <el-table-column
    :prop="prop"
    :label="label"
    :width="width"
  >
    <template slot="header" slot-scope="item">
      <span><el-checkbox v-if="showCheckbox" v-model="i_selectedAll" :indeterminate="topIsIndeterminate" class="topcheckbox" @change="selectedAllChange"/>
        <span>{{ item.column.label }}</span>
        <i class="el-icon-plus addicon" @click="add_button_click"/>
      </span>
    </template>
    <template slot-scope="item">
      <!--宽度-->
      <span :style="indentWith(item.row)"/>
      <el-checkbox
        v-if="showCheckbox"
        :indeterminate="item.row.isIndeterminate"
        v-model="item.row.selected"
        @change="checkedChange(item.row)"
      />
      <!--展开下级切换功能-->
      <i :class="menuIconClass(item.row)" @click="hoveExpanded(item.row)"/>
      <slot :data="item">{{ item.row.name }}</slot>
    </template>
  </el-table-column>
</template>

<script>
export default {
  name: 'ElTreeTableColumn',
  model: {
    prop: 'selectedAll',
    event: 'selected_all'
  },
  props: {
    indent: { type: Number, default: 18 },
    selectedAll: { type: Boolean, default: false },
    label: { type: String, default: '' },
    prop: { type: String, default: '' },
    dynamicCheckedList: { type: Array, default: () => [] },
    width: { type: String, default: '250' },
    showCheckbox: { type: Boolean, default: false },
    topIsIndeterminate: { type: Boolean, default: false },
    cascade: { type: Boolean, default: false }
  },
  data() {
    return { i_selectedAll: this.selectedAll }
  },
  watch: {
    selectedAll: function(newval, oldval) {
      this.i_selectedAll = newval
    }
  },
  methods: {
    indentWith: function(item) {
      return {
        display: 'inline-block',
        width: item.level * this.indent + 'px'
      }
    },
    menuIconClass: function(item) {
      /* 有子元素才展示*/
      if (item.children && item.children.length > 0) {
        if (item.expanded) {
          return 'el-icon-arrow-down'
        } else {
          return 'el-icon-arrow-right'
        }
      } else {
        return 'el-icon-star-off'
      }
    },
    selectedAllChange: function(v) {
      if (this.cascade) {
        this.$emit('selected_all', v)
      }
    },
    hoveExpanded: function(row) {
      row.expanded = !row.expanded
    },
    checkedChange: function(row) {
      this.$emit('onselected', row)
      if (this.cascade) {
        this.checkAllchild(row)
        this.recursiveChecked(row)
      }
    },
    checkAllchild: function(row) {
      var _this = this
      if (row.children && row.children.length > 0) {
        row.children.some(function(item) {
          item.selected = row.selected
          _this.checkAllchild(item)
        })
      }
    },
    recursiveChecked: function(row) {
      this.recursiveCheckedByChild(row)
      if (row.parent) {
        this.recursiveChecked(row.parent)
      }
    },
    recursiveCheckedByChild: function(row) {
      /* menu是父菜单*/
      let checkednum = 0
      let uncheckednum = 0
      let checkgrouplength = 0
      let isIndeterminatenum = 0
      if (row.children && row.children.length > 0) {
        checkgrouplength = row.children.length
        row.children.some(function(item) {
          if (item.selected) {
            checkednum = checkednum + 1
          } else {
            uncheckednum = uncheckednum + 1
          }
          if (item.isIndeterminate) {
            isIndeterminatenum = isIndeterminatenum + 1
          }
        })
      } else {
        row.isIndeterminate = false
        return
      }
      row.isIndeterminate = false
      /* 选中的和子长度相等*/
      if (checkednum === checkgrouplength) {
        /* 全选叶子*/
        if (isIndeterminatenum > 0) {
          row.isIndeterminate = true
        }
        row.selected = true
        return true
      } else if (uncheckednum === checkgrouplength) {
        /* 全取消叶子*/
        row.isIndeterminate = false
        row.selected = false
        return false
      } else {
        /* 半选叶子*/
        row.selected = true
        row.isIndeterminate = true
        return true
      }
    },
    add_button_click: function() {
      this.$emit('add_button_click')
    }
  }
}
</script>

<style scoped>
  .el-checkbox {
    margin-right: 5px;
  }
  .addicon{
    cursor: pointer;
    float: right;
    margin-right: 15px;
    color: #409EFF;}
</style>
