<template>
  <div>
    <BaseCrud
      :api-service="apiService"
      :grid-config="configData.columns"
      :grid-btn-config="configData.gridBtnConfig"
      :grid-data="listData"
      :form-config="configData.formConfig"
      :filter-config="configData.filterConfig"
      :form-data="configData.formModel"
      :grid-edit-width="250"
      :is-async="true"
      form-title="角色"
      @handlePower="handlePower"
    />

    <!--crud按钮触发的表单弹窗-->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="角色代码">
          <el-input v-model="role.roleCode" placeholder="角色代码" readonly="true"/>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input
            v-model="role.roleDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            readonly="true"
            type="textarea"
            placeholder="Role Description"
          />
        </el-form-item>
        <el-form-item label="菜单1">
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
import treetable from '@/views/upms/menu/treeTable/index'
import { USER_CONFIG } from './columns/roleColumns'
import { roleService } from '@/api/upms/roleInfo'

export default {
  name: 'Role',
  components: {
    BaseCrud, treetable
  },
  data() {
    return {
      showCheckBox: true,
      editable: false,
      defaultChecked: { checkedpermissions: [], checkedrouters: [] },
      listData: [],
      role: {},
      configData: USER_CONFIG,
      apiService: roleService,
      dialogVisible: false,
      dialogTitle: '权限管理'
    }
  },
  computed: {
  },
  mounted: function() {
    this.listData = []
  },
  created() {
    // Mock: get all routes and roles list from server
  },
  methods: {
    async handlePower(item) {
      this.role = {}
      this.defaultChecked = { checkedpermissions: [], checkedrouters: [] }
      this.dynamicCheckedList = { checkedpermissions: [], checkedrouters: [] }
      this.role = item ? Object.assign({}, item) : Object.assign({}, this.configData.formModel)
      const res = await this.apiService.listPerAndRouter(item)
      // TODO 等待修改
      this.defaultChecked.checkedpermissions = res.data.rolePermissions
      this.defaultChecked.checkedrouters = res.data.roleRouters
      this.dialogVisible = true
    },
    async confirmRole() {
      this.role.routers = this.$refs.treetable1.dynamicCheckedList.checkedrouters.join(',')
      this.role.permissions = this.$refs.treetable1.dynamicCheckedList.checkedpermissions.join(',')
      const res = await this.apiService.save(this.role)
      this.dialogVisible = false
      this.$notify({
        title: res.data,
        dangerouslyUseHTMLString: true,
        message: `
            <div>Role Key: ${this.role.roleCode}</div>
            <div>Role Nmae: ${this.role.roleName}</div>
            <div>Description: ${this.role.roleDesc}</div>
          `,
        type: 'success'
      })
    }
  }
}
</script>
