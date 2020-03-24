<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.roleCode" :placeholder="$t('role.roleCode')" style="width: 120px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.roleName" :placeholder="$t('role.roleName')" style="width: 120px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.roleType" :placeholder="$t('role.roleType')" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in roleTypeDict" :key="item.id" :label="item.name" :value="item.name" />
      </el-select>
      <el-date-picker
        v-model="listQuery.createDate"
        class="filter-item"
        type="daterange"
        range-separator="至 "
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        @change="dateRangeChange"/>
      <el-button type="primary" icon="el-icon-search" class="filter-item" @click="handleFilter">查 询</el-button>
      <el-button type="primary" icon="el-icon-edit" class="filter-item" @click="handleAddOrEdit">新  增</el-button>
      <el-button type="primary" class="filter-item" icon="el-icon-refresh" @click="resetForm">重 置</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="objList"
      border
      style="width: 100%;">
      <el-table-column align="center" type="index" label="序号" width="95"/>
      <el-table-column :label="$t('role.roleCode')" prop="id" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.roleCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.roleName')" prop="id" align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.createDate')" prop="createDate" sortable align="center" width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.createDate }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.roleType')" prop="roleType" align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.roleType | roleTypeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.roleDesc')" prop="roleDesc" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.roleDesc }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.isValid')" prop="isValid" sortable align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isValid | statusFilter">
            {{ scope.row.isValid | isValidFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column width="250" fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleAddOrEdit(scope.row)">修改</el-button>
          <el-button size="mini" type="primary" @click="handlePower(scope.row)">编辑权限</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.pageNo"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"/>

    <!--crud按钮触发的表单弹窗-->
    <el-dialog :visible.sync="dialogFormVisible" :title="dialogFormTitle">
      <el-form ref="configForm" :model="role" label-width="80px" label-position="left">
        <el-form-item :label="$t('role.roleCode')">
          <el-input v-model="role.roleCode" :placeholder="$t('role.roleCode')"/>
        </el-form-item>
        <el-form-item :label="$t('role.roleName')">
          <el-input v-model="role.roleName" :placeholder="$t('role.roleName')"/>
        </el-form-item>
        <el-form-item :label="$t('role.roleType')">
          <el-select v-model="role.roleType" :placeholder="$t('role.roleType')">
            <el-option
              v-for="option in roleTypeDict"
              :key="option.id"
              :label="option.name"
              :value="option.id"/>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('role.isValid')">
          <el-radio-group
            v-model="role.isValid">
            <el-radio v-for="option in validFlag" :label="option.id" :key="option.id">{{ option.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('role.roleDesc')">
          <el-input
            v-model="role.roleDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="Role Description"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>

    <!--crud按钮触发的表单弹窗-->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="角色代码">
          <el-input v-model="role.roleCode" :readonly="true" placeholder="角色代码"/>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input
            v-model="role.roleDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            :readonly="true"
            type="textarea"
            placeholder="Role Description"
          />
        </el-form-item>
        <el-form-item label="菜单">
          <treetable ref="treetable1" :show-check-box="showCheckBox" :editable="editable" :default-checked="defaultChecked"/>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">
          取消
        </el-button>
        <el-button type="primary" @click="confirmRole">
          确认
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import BaseCrud from '@/components/BaseCrud/index.vue'
import Treetable from '@/views/upms/menu/treeTable/index'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { roleService } from '@/api/upms/roleInfo'
const roleTypeDicts = [{ name: '全局通用', id: 'G' }, { name: '系统固有', id: 'F' }, { name: '系统公用', id: 'P' }]
const validFlag = [{ id: 'T', name: '有效' }, { id: 'F', name: '注销' }]
export default {
  name: 'Role',
  components: {
    BaseCrud,
    Treetable,
    Pagination
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        T: 'success',
        F: 'danger'
      }
      return statusMap[status]
    },
    isValidFilter(status) {
      const statusMap = {
        T: '正常',
        F: '注销'
      }
      return statusMap[status]
    },
    roleTypeFilter(key) {
      let val = key
      roleTypeDicts.forEach(function(value) {
        if (value.id === key) {
          val = value.name
        }
      })
      return val
    }
  },
  data: function() {
    return {
      showCheckBox: true,
      editable: false,
      defaultChecked: { checkedpermissions: [], checkedrouters: [] },
      objList: [],
      role: {},
      dialogVisible: false,
      dialogFormVisible: false,
      dialogTitle: '权限管理',
      dialogFormTitle: '权限管理',
      listLoading: true,
      total: 0,
      listQuery: {
        pageNo: 1,
        pageSize: 20,
        roleType: undefined,
        roleCode: undefined,
        roleName: undefined,
        createDate: undefined,
        start_time: '',
        end_time: ''
      },
      roleTypeDict: roleTypeDicts,
      validFlag: validFlag
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      roleService.list(this.listQuery).then(res => {
        this.objList = res.data.records
        this.total = res.data.total
        this.listQuery.createDate = [this.listQuery.start_time, this.listQuery.end_time]
        this.listLoading = false
      }, err => {
        console.log(err)
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNo = 1
      this.getList()
    },
    resetForm() {
      this.listQuery = {}
    },
    dateRangeChange(data) {
      if (data) {
        this.listQuery.start_time = data[0]
        this.listQuery.end_time = data[1]
      }
    },
    handleAddOrEdit(item) {
      this.dialogFormTitle = item ? '修改角色' : '新增角色'
      this.role = item ? Object.assign({}, item) : Object.assign({}, this.listQuery)
      this.dialogFormVisible = true
    },
    submitForm() {
      this.$refs.configForm.validate((valid) => {
        if (valid) {
          // 让父组件接收到响应数据
          roleService.save(this.role).then(res => {
            this.getList()
            this.$message.success(this.dialogFormTitle + '成功！')
          })
          // 关闭模态框
          this.dialogFormVisible = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('请确认是否删除当前记录?', 'Warning', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await roleService.delete(row).then(res => {
            this.objList.splice($index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
        })
        .catch(err => { console.error(err) })
    },

    async handlePower(item) {
      this.role = {}
      this.defaultChecked = { checkedpermissions: [], checkedrouters: [] }
      this.dynamicCheckedList = { checkedpermissions: [], checkedrouters: [] }
      this.role = item ? Object.assign({}, item) : Object.assign({}, this.listQuery)
      const res = await roleService.listPerAndRouter(item)
      // TODO 等待修改
      this.defaultChecked.checkedpermissions = res.data.rolePermissions
      this.defaultChecked.checkedrouters = res.data.roleRouters
      this.dialogVisible = true
    },
    async confirmRole() {
      this.role.routers = this.$refs.treetable1.dynamicCheckedList.checkedrouters.join(',')
      this.role.permissions = this.$refs.treetable1.dynamicCheckedList.checkedpermissions.join(',')
      const res = await roleService.save(this.role)
      this.dialogVisible = false
      this.$notify({
        title: res.data,
        dangerouslyUseHTMLString: true,
        message: '操作成功',
        type: 'success'
      })
    }
  }
}
</script>
<style>
  .filter-container {
    padding-bottom: 10px;
  }
  .filter-item {
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 10px;
  }
</style>
