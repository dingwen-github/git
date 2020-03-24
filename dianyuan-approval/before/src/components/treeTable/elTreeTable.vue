<!--
element属性表格组件
@author jason
@version 1.0
### props:

**** `data: { type: Array, default: () => [] },` /* 列表数据，重新赋值可重新渲染列表*/

**** `selectedAll: { type: Boolean, default: false },` /* 是否默认全选*/

**** `expandAll: { type: Boolean, default: false },` /* 是否展开全部*/

**** `first_label: { type: String, default: '' },` /* 第一列属性描述*/

**** `first_column_width: { type: String, default: '250' },` /* 第一列宽度*/

**** `showCheckbox: { type: Boolean, default: false },` /* 是否打开选择功能*/

**** `defaultSelected: { type: Array, default: () => [] },` /* 默认选中值*/

**** `cascade: { type: Boolean, default: false },` /* 是否级联选择*/

**** `height: { type: Number, default: null },` /* 是否设置高度固定表头*/

**** `order_field: { type: String, default: '' },` /* 排序字段*/

**** `first_field: { type: String, default: '' },` /* 第一列属性名*/

**** `key_field: { type: String, default: 'id' }` /* 唯一key属性*/

### event:

**** `add_button_click`顶部新增按钮点击事件，需要隐藏可用.addicon{display:none}样式来实现

### api:

**** `rebuildTabledata` 重新渲染树形结构，添加或者编辑之后如果需要排序则调用该接口

**** `rebuildTabledata` 重新渲染树形结构，添加或者编辑之后如果需要排序则调用该接口

**** `addRow(pid,row)` 新增一行数据入参为新增对象的父id+新增对象

**** `deleteRow(id)` 删除一行数据

**** `findRow(id)` 查找一行数据，更新的时候可以通过该方法进行查找并更新

**** `onselected(row)` 节点选中事件

**** `getAllSelected` 获取所有选中节点的id

### solt:

**** `treeColumn` 树形列插槽 scope：data 原生element的item

**** `otherColumns` 除了树形列之外的其他列，直接用原生column即可
-->
<template>
  <el-table
    v-loading="tableLoading"
    :data="tableData"
    :height="height"
    :row-class-name="rowclassname"
    :tree-props="{ hasChildren: 'xxxxxx',children:'cccccc'}"
    row-key="id"
    style="width: 100%"
    @cell-dblclick="hoveExpanded"
  >
    <!--第一列-->
    <ElTreeTableColumn
      v-model="top.selected"
      :prop="first_field"
      :top-is-indeterminate="top.isIndeterminate"
      :label="first_label"
      :width="first_column_width"
      :show-checkbox="showCheckbox"
      :dynamic-checked-list="dynamicCheckedList"
      :cascade="cascade"
      @add_button_click="add_button_click()"
      @selected_all="changeSelectedAll"
      @onselected="onselected">
      <template slot-scope="item">
        <slot :data="item.data" name="treeColumn"/>
      </template>
    </ElTreeTableColumn>
    <!--其他列-->
    <slot name="otherColumns"/>
  </el-table>
</template>

<script>
import ElTreeTableColumn from '@/components/treeTable/elTreeTableColumn.vue'

export default {
  name: 'ElTreeTable',
  components: { ElTreeTableColumn },
  props: {
    data: { type: Array, default: () => [] }, /* 列表数据，重新赋值可重新渲染列表*/
    selectedAll: { type: Boolean, default: false }, /* 是否默认全选*/
    expandAll: { type: Boolean, default: false }, /* 是否展开全部*/
    first_label: { type: String, default: '' }, /* 第一列属性描述*/
    first_column_width: { type: String, default: '250' }, /* 第一列宽度*/
    showCheckbox: { type: Boolean, default: false }, /* 是否打开选择功能*/
    defaultSelected: { type: Array, default: () => [] }, /* 默认选中值*/
    cascade: { type: Boolean, default: false }, /* 是否级联选择*/
    height: { type: Number, default: null }, /* 是否设置高度固定表头*/
    order_field: { type: String, default: '' }, /* 排序字段*/
    first_field: { type: String, default: '' }, /* 第一列属性名*/
    key_field: { type: String, default: 'id' } /* 唯一key属性*/
  },
  data() {
    return {
      tableLoading: false,
      tableData: [],
      top: {
        id: '-1',
        name: '顶级',
        path: '/',
        children: [],
        selected: this.selectedAll,
        expanded: true,
        show: true,
        isIndeterminate: false
      }
    }
  },
  computed: {
    dynamicCheckedList: function() {
      const dynamicCheckedList = []
      var _this = this
      Array.from(this.tableData).forEach(function(row) {
        if (row.selected) {
          dynamicCheckedList.push(row[_this.key_field])
        }
      })
      if (dynamicCheckedList.length > 0) {
        dynamicCheckedList.push('-1')
      }
      return dynamicCheckedList
    }
  },
  watch: {
    data: function(newdata, olddata) {
      this.top.children = JSON.parse(JSON.stringify(this.data))
      this.rebuildTabledata()
    },
    defaultSelected: {
      immediate: true, deep: true, handler(val) {
        if (val.indexOf('-1') > -1) {
          this.top.selected = true
        }
        var _this = this
        this.tableData.some(function(row, index) {
          if (val.indexOf(row[_this.key_field]) > -1) {
            row.selected = true
          } else {
            row.selected = false
          }
        })
      }
    }
  },
  created: function() {
    if (this.data && this.data.length > 0) {
      this.top.children = JSON.parse(JSON.stringify(this.data))
      this.rebuildTabledata()
    }
  },
  methods: {
    rebuildTabledata: function() {
      this.tableData = this.treeToArray(this.top.children, this.expandAll, this.top)
    },
    rowclassname: function(row) {
      const show = (row.row.parent ? (row.row.parent.expanded && row.row.parent.show) : true)
      /* 父元素不展示时子元素也应当隐藏*/
      row.row.show = show
      return show ? 'rowshow' : 'rowhide'
    },
    hoveExpanded: function(menu, column, cell, event) {
      if (column === undefined || column.property === 'name') {
        menu.expanded = !menu.expanded
      }
    },
    sortArr: function(a, b) {
      return a[this.order_field] - b[this.order_field]
    },
    treeToArray: function(data, expandAll, parent = null, level = null) {
      let tmp = []
      var _this = this
      if (this.order_field !== '') {
        data.sort(_this.sortArr)
      }
      Array.from(data).forEach(function(record) {
        if (record.expanded === undefined) {
          /* 确保新属性也是响应式的*/
          _this.$set(record, 'expanded', expandAll)
        }
        /* 用于计算首行缩进*/
        let _level = 1
        if (level !== undefined && level !== null) {
          _level = level + 1
        }
        _this.$set(record, 'level', _level)
        // 如果有父元素
        if (parent) {
          _this.$set(record, 'parent', parent)
        }
        if (!record.selected) {
          _this.$set(record, 'selected', _this.selectedAll)
        }
        /* 给record添加函数判断其选中状态*/
        if (_this.defaultSelected.indexOf(record[_this.key_field]) > -1) {
          record.selected = true
        } else {
          record.selected = false
        }
        tmp.push(record)
        /* 给record添加函数判断其选中状态*/
        if (record.children && record.children.length > 0) {
          const children = _this.treeToArray(record.children, expandAll, record, _level)
          tmp = tmp.concat(children)
        }
      })
      return tmp
    },
    addRow: function(pid, node) {
      node.show = true
      if (pid === '-1') {
        node.parent = this.top
        node.level = 1
        this.tableData.push(node)
        this.top.children.push(node)
        return
      }
      var _this = this
      this.tableData.some(function(item, index) {
        if (item[_this.key_field] === pid) {
          if (!item.children) {
            item.children = []
          }
          node.parent = item
          node.level = item.level + 1
          if (!item.expanded) {
            _this.$set(item, 'expanded', true)
          }

          item.children.push(node)
          _this.tableData.splice(index + item.children.length, 0, node)
          return
        }
      })
    },
    deleteRow: function(id) {
      const _this = this

      _this.tableData.some(function(item, index) {
        if (item[_this.key_field] === id) {
          _this.tableData.splice(index, 1)
          if (item.parent.children) {
            item.parent.children.some(function(son, index) {
              if (son[_this.key_field] === item[_this.key_field]) {
                item.parent.children.splice(index, 1)
              }
            })
          }
        }
      })
    },
    findRow: function(id) {
      var result = null
      const _this = this
      this.tableData.some(function(item, index) {
        if (item[_this.key_field] === id) {
          result = item
          return
        }
      })
      return result
    },
    changeSelected: function(row) {
      row.selected = !row.selected
    },
    changeSelectedAll: function(selectedAll) {
      this.top.isIndeterminate = false
      this.tableData.some(function(item, index) {
        item.selected = selectedAll
      })
    },
    onselected: function(row) {
      this.$emit('onselected', row)
    },
    getAllSelected: function() {
      return this.dynamicCheckedList
    },
    add_button_click: function() {
      this.$emit('add_button_click')
    }
  }
}
</script>

<style scoped>
  .rowshow{
    animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s
  }
  .rowhide{
    display:none
  }
</style>
