<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="部门名称"
        style="width: 200px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"/>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>

    </div>
    <ElTreeTable
      ref="table"
      :data="tableData"
      :selected-all="selectedAll"
      :first_field="'name'"
      :first_label="'部门名称'"
      :first_column_width="'250'"
      :show-checkbox="showCheckbox"
      :default-selected="defaultSelected"
      :order_field="'orders'"
      @add_button_click="handleAddOrEdit({name:'顶级部门',id:'-1'})">
      <template slot="treeColumn" slot-scope="item">
        <!--这里可以自定义树形列里面显示的内容-->
        {{ item.data.row.name }}
        <el-dropdown :hide-on-click="false" size="mini" style="float:right" trigger="click">
          <span class="el-dropdown-link" style="font-size: 10px;color:#409EFF">
            操作<i class="el-icon-arrow-down el-icon--right"/>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-popover
              v-model="item.data.row.showDeletePop"
              placement="top"
              width="160">
              <p>确定删除？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="item.data.row.showDeletePop = false">取消</el-button>
                <el-button type="primary" size="mini" @click="deleteItem(item.data.row)">确定</el-button>
              </div>
              <el-dropdown-item slot="reference">删除</el-dropdown-item>
            </el-popover>
            <el-dropdown-item @click.native="handleAddOrEdit(item.data.row)">新增</el-dropdown-item>
            <el-dropdown-item @click.native="handleAddOrEdit(item.data.row.parent,item.data.row)">编辑</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!--<el-input/>-->
      </template>
      <template slot="otherColumns">
        <el-table-column
          prop="orders"
          label="序号"
        />
        <el-table-column
          prop="description"
          label="描述"
        />
        <el-table-column
          prop="createTime"
          label="创建时间"
        />
      </template>
    </ElTreeTable>
    <el-dialog :visible.sync="dialogFormVisible" :title="dialogFormTitle">
      <el-form ref="dialogForm" :model="dept" :rules="rules" label-width="80px" label-position="left">
        <el-form-item label="父级部门">
          <el-input v-model="dept.parentName" placeholder="父部门" disabled=""/>
        </el-form-item>
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="dept.name" placeholder="部门名称"/>
        </el-form-item>
        <el-form-item label="排序" prop="orders">
          <el-input-number v-model="dept.orders" :min="1" :max="10000" label="序号"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="dept.description"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="dept Description"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ElTreeTable from '@/components/treeTable/elTreeTable.vue'
import { deptService } from '@/api/upms/dept'

export default {
  name: 'Dept',
  components: { ElTreeTable },
  data() {
    return {
      listQuery: { name: '' },
      dialogFormVisible: false,
      dialogFormTitle: '新增部门',
      defaultSelected: [],
      showCheckbox: false,
      selectedAll: false,
      dept: {},
      rules: {
        name: [{ required: true, message: '请输入部门名称', trigger: 'change' }],
        orders: [{ required: true, message: '请输入部门名称', trigger: 'change' }],
        description: [{ required: true, message: '请输入部门名称', trigger: 'change' }]
      },
      tableData: []
    }
  },
  created: function() {
    this.getList()
  },
  methods: {
    handleFilter() {
      this.getList()
    },
    getList: function() {
      var _this = this
      deptService.list(this.listQuery).then(function(res) {
        _this.tableData = res.data
      })
    },
    handleAddOrEdit(parent, item) {
      this.dialogFormTitle = item ? '修改部门' : '新增部门'
      this.dept = item ? this.copyObj(item) : {}
      this.dept.pid = parent.id
      this.dept.parentName = parent.name
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dialogForm'].clearValidate()
      })
    },
    copyObj: function(item) {
      var obj = {}
      obj.id = item.id
      obj.name = item.name
      obj.description = item.description
      obj.orders = item.orders
      obj.pid = item.pid
      return obj
    },
    submitForm: function() {
      var _this = this
      this.$refs['dialogForm'].validate((valid) => {
        if (valid) {
          deptService.save(this.dept).then(function(res) {
            if (_this.dept.id) {
              var findobj = _this.$refs.table.findRow(_this.dept.id)
              findobj.name = _this.dept.name
              findobj.description = _this.dept.description
              findobj.orders = _this.dept.orders
            } else {
              _this.dept.id = res.data
              _this.$refs.table.addRow(_this.dept.pid, Object.assign({}, _this.dept))
            }
            // 重新渲染进行排序
            _this.$refs.table.rebuildTabledata()
            _this.dialogFormVisible = false
          })
        }
      })
    },
    deleteItem: function(item) {
      var _this = this
      deptService.delete(item).then(function(res) {
        item.showDeletePop = false
        _this.$refs.table.deleteRow(item.id)
      })
    }
  }

}
</script>

<style scoped>

</style>
