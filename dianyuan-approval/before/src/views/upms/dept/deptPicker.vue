<template><div>
  <ElTreeTable
    ref="table"
    :data="tableData"
    :selected-all="selectedAll"
    :first_field="'name'"
    :first_label="'部门名称'"
    :first_column_width="'250'"
    :show-checkbox="showCheckbox"
    :default-selected="defaultSelected"
    :cascade="false"
    :height="250"
    class="deptPicker"
    @onselected="onselected"
  >
    <template slot="treeColumn" slot-scope="item">
      <!--这里可以自定义树形列里面显示的内容-->
      {{ item.data.row.name }}
      <!--<el-input/>-->
    </template>
    <template slot="otherColumns">
      <el-table-column
        prop="description"
        label="岗位"
      >
        <template slot-scope="item">
          <el-select v-model="item.row.posts" multiple placeholder="选择岗位" style="width: 80%" @change="postchange(item.row)">
            <el-option
              v-for="item in postOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
      </template></el-table-column>
  </template></ElTreeTable>
</div>
</template>

<script>
import ElTreeTable from '@/components/treeTable/elTreeTable.vue'
import { deptService } from '@/api/upms/dept'
export default {
  name: 'DeptPicker',
  components: { ElTreeTable },
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: { type: Array, default: () => {} }
  },
  data() {
    return {
      postOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      defaultSelected: [],
      showCheckbox: true,
      selectedAll: false,
      dept: {},
      tableData: [],
      val: this.value
    }
  },
  watch: {
    value: function() {
      this.initdefaultSelected()
      this.initselectedpost(this.tableData)
      this.tableData = JSON.parse(JSON.stringify(this.tableData))
    } },
  created: function() {
    var _this = this
    deptService.list().then(function(res) {
      /* 初始化默认选中*/
      _this.initselectedpost(res.data)
      _this.tableData = res.data
      _this.initdefaultSelected()
    })
  },
  methods: {
    initdefaultSelected: function() {
      var _this = this
      _this.defaultSelected = []
      if (_this.value) {
        _this.value.some(function(item, index) {
          _this.defaultSelected.push(item.id)
        })
      }
    },
    initselectedpost: function(data) {
      var _this = this
      data.some(function(row) {
        _this.$set(row, 'posts', [])
        if (_this.value) {
          _this.value.some(function(item, index) {
            if (row.id === item.id) {
              _this.$set(row, 'posts', item.posts)
            }
          })
        }
        if (row.children && row.children.length > 0) {
          _this.initselectedpost(row.children)
        }
      })
    },
    copyObj: function(item) {
      var obj = {}
      obj.id = item.id
      obj.name = item.name
      obj.description = item.description
      obj.pid = item.pid
      return obj
    },
    postchange: function(row) {
      this.value.some(function(item, index) {
        if (item.id === row.id) {
          item.posts = row.posts
        }
      })
    },
    onselected: function(row) {
      var _this = this
      if (row.selected) {
        var obj = { id: row.id, posts: [] }
        this.value.push(obj)
      } else {
        row.posts = []
        this.value.some(function(item, index) {
          if (item.id === row.id) {
            item.posts = []
            _this.value.splice(index, 1)
          }
        })
      }

      this.$emit('onselected', row)
    }
  }

}
</script>

<style>
.deptPicker .addicon,.deptPicker .topcheckbox{
  display: none;
}
</style>
