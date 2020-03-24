<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.loginName"
        placeholder="登录名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"/>
      <el-input
        v-model="listQuery.name"
        placeholder="名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreateOrUpdate()">
        新增
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="id" prop="id" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" width="110px" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" class="user-avatar">
        </template>
      </el-table-column>
      <el-table-column label="登录名" width="110px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loginName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="密码" width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.pwd }}</span>
        </template>
      </el-table-column>
      <el-table-column label="简介" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.introduction }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleCreateOrUpdate(row)">
            编辑
          </el-button>
          <el-button type="primary" size="mini" @click="handlePermission(row)">
            授权
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.current"
      :limit.sync="listQuery.size"
      @pagination="getList"/>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
      >
        <input type="password" style="position: fixed; bottom: -1000px;">
        <el-form-item label="头像" prop="avatar">
          <!-- <pan-thumb :image="temp.avatar" />-->
          <avatar-upload v-model="temp.avatar"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="temp.name" placeholder="姓名"/>
        </el-form-item>
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="temp.loginName" placeholder="登录名"/>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input v-model="temp.pwd" show-password placeholder="密码"/>
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input v-model="temp.introduction" placeholder="简介"/>
        </el-form-item>
        <DeptPicker v-model="temp.dept"/>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="createData()">
          提交
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="selectRoleDig" title="角色配置">
      <div slot="title" class="header-title">
        <el-select
          v-model="selectRoles"
          size="large"
          multiple
          placeholder="下方提供菜单实时预览"
          style="width: 95%"
          @change="viewMenu">
          <el-option
            v-for="item in roles"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
      </div>
      <el-container>
        <el-header style="height: 12px;color: #409EFF">
          <span>菜单预览</span>
        </el-header>
        <el-main>
          <el-tree :data="treedata" :props="defaultProps" default-expand-all/>
        </el-main>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button @click="selectRoleDig = false">
          取消
        </el-button>
        <el-button type="primary" @click="savePermissions">
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  saveUser,
  deleteUser,
  saveUserRole,
  listUserRoles,
  loadViewMenuData,
  checkLoginNameUnique,
  loadUserDept
} from '@/api/upms/userInfo'
import { roleService } from '@/api/upms/roleInfo'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination'
import DeptPicker from '@/views/upms/dept/deptPicker.vue'
import AvatarUpload from '@/views/upms/userInfo/avatar-upload.vue'

export default {
  name: 'ComplexTable',
  components: { Pagination, DeptPicker, AvatarUpload },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      treedata: [],
      selectRoles: [],
      optUserId: '',
      roles: [],
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 10,
        name: undefined,
        loginName: undefined,
        sort: '+id'
      },
      temp: {
        id: undefined,
        name: 1,
        loginName: '',
        pwd: '',
        introduction: '',
        dept: [],
        oldLoginName: '',
        avatar: 'https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      selectRoleDig: false,
      textMap: {
        update: '编辑',
        create: '新增'
      },
      pvData: [],
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        loginName: [{
          validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入登录名'))
            } else {
              checkLoginNameUnique({ loginName: value, id: this.temp.id }).then(function(res) {
                if (res.data) {
                  callback()
                } else {
                  callback(new Error('用户名已存在'))
                }
              })
            }
          }, trigger: 'blur'
        }, { required: true, message: '请输入登录名', trigger: 'blur' }],
        pwd: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listUser(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: '',
        dept: [],
        avatar: 'https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191'
      }
    },
    handleCreateOrUpdate(row) {
      this.resetTemp()
      if (row) {
        this.temp = Object.assign({}, row)
        this.temp.oldLoginName = row.loginName
        var _this = this
        loadUserDept(row.id).then(function(res) {
          _this.temp.dept = res.data
          _this.dialogStatus = 'update'
          _this.dialogFormVisible = true
          _this.$nextTick(() => {
            _this.$refs['dataForm'].clearValidate()
          })
        })
      } else {
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 封装userdept
          var userdeptlist = []
          var userId = this.temp.id
          this.temp.dept.some(function(item, index) {
            const deptId = item.id
            item.posts.some(function(p, index) {
              const userdept = { userId: userId, deptId: deptId, postId: p }
              userdeptlist.push(userdept)
            })
          })
          saveUser({ oldLoginName: this.temp.oldLoginName, userInfo: JSON.stringify(this.temp), userDept: JSON.stringify(userdeptlist) }).then((res) => {
            if (this.temp.id) {
              this.updateData()
            } else {
              this.temp.id = res.data
              this.list.unshift(Object.assign({}, this.temp))
            }
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    updateData() {
      for (const v of this.list) {
        if (v.id === this.temp.id) {
          const index = this.list.indexOf(v)
          this.list.splice(index, 1, this.temp)
          break
        }
      }
    },
    handlePermission: function(row) {
      const _this = this
      this.optUserId = row.id
      _this.selectRoles.splice(0, _this.selectRoles.length)
      listUserRoles({ userId: row.id }).then(res => {
        res.data.some(function(item) {
          _this.selectRoles.push(item.roleCode)
        })
        _this.viewMenu()
      })
      if (this.roles.length > 0) {
        this.selectRoleDig = true
        return
      }
      roleService.listAllRoles().then(
        (res) => {
          res.data.some(function(item) {
            const r = { value: item.roleCode, label: item.name }
            _this.roles.push(r)
          })
          this.selectRoleDig = true
        })
    },
    savePermissions: function() {
      const _this = this
      const userroles = []
      this.roles.some(function(item) {
        if (_this.selectRoles.indexOf(item.value) > -1) {
          userroles.push({ userId: _this.optUserId, roleCode: item.value })
        }
      })
      saveUserRole({ userId: _this.optUserId, userRoles: JSON.stringify(userroles) }).then(res => {
        this.selectRoleDig = false
      })
    },
    handleDelete(row) {
      var params = { loginName: row.loginName, id: row.id }
      deleteUser(params).then((res) => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      })
    },
    viewMenu: function() {
      loadViewMenuData({ roles: JSON.stringify(this.selectRoles) }).then(res => {
        this.treedata = res.data
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

  .user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 10px;
  }
</style>
