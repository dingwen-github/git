<template>
  <div>
    <!--    面包屑导航区-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
<!--    卡片视图-->
    <el-card>
<!--      添加角色按钮区域-->
      <el-row>
        <el-col>
          <el-button type="primary" @click="roleAddDialogVisible = true">添加角色</el-button>
        </el-col>
      </el-row>
<!--      角色列表区域-->
      <el-table :data="rolesList" border stripe>
<!--        展开列-->
        <el-table-column type="expand">
<!--          使用作用于插槽-->
          <template slot-scope="scope">
            <el-row class="vcenter" :class="['bdbottom', i1 === 0 ? 'bdtop' : '']" v-for="(item1, i1) in scope.row.children" :key="item1.id">
<!--              渲染一级权限-->
              <el-col :span="5">
              <el-tag closable @close="removeRightById(scope.row,item1.id)"> {{ item1.authName }}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
<!--              渲染二三级权限-->
              <el-col :span="19">
                <el-row class="vcenter"  :class="[i2 === 0 ? '' : 'bdtop']" v-for="(item2, i2) in item1.children" :key="item2.id">
<!--                  二级权限-->
                  <el-col :span="6">
                    <el-tag type="success" closable @close="removeRightById(scope.row,item2.id)"> {{ item2.authName }}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
<!--                  三级权限-->
                  <el-col :span="18">
                    <el-tag v-for="(item3,i3) in item2.children" :key="item3.id" type="warning" closable
                    @close="removeRightById(scope.row,item3.id)">
                      {{ item3.authName }}
                      {{ i3 }}
                    </el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
<!--           <pre>-->
<!--              {{scope.row}}-->
<!--           </pre>-->
          </template>
        </el-table-column>
<!--        索引列-->
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="角色名称" prop="roleName"></el-table-column>
        <el-table-column label="角色描述" prop="roleDesc"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑" placement="top-start" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeRoleById(scope.row.id)">删除</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="分配权限" placement="top-start" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="showSetRightDialog(scope.row)">分配权限</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
<!--添加角色的对话框-->
    <el-dialog
      title="添加角色"
      :visible.sync="roleAddDialogVisible"
      width="50%"
      @close="roleAddDialogClosed">
<!--     内容主体区域-->
      <el-form :model="roleAddForm" :rules="roleAddFormRules"
                ref="roleAddFormRef" label-width="70px">
        <el-form-item label="名称" prop="roleName">
          <el-input v-model="roleAddForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="roleDesc">
          <el-input v-model="roleAddForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="roleAdd">确 定</el-button>
        <el-button @click="roleAddDialogVisible = false">取 消</el-button>
     </span>
    </el-dialog>
<!--    编辑角色信息的对话框-->
    <el-dialog
      title="修改角色信息"
      :visible.sync="editDialogVisible"
      width="50%">
      <el-form label-width="70px" v-model="editForm" :rules="editFormRules" ref="editFormRef">
        <el-form-item label="名称" prop="roleName">
          <el-input  v-model="editForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="roleDesc">
          <el-input v-model="editForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editRole">确 定</el-button>
            <el-button @click="editDialogVisible  = false">取 消</el-button>
      </span>
    </el-dialog>
<!--    分配权限的对话框-->
    <el-dialog
      title="分配权限"
      :visible.sync="setRightDialogVisible"
      width="50%"
      @close="setRightDialogClosed">
<!--      树形控件-->
      <el-tree :data="rightsList" :props="treeProps"
               show-checkbox node-key="id"
               default-expand-all
               :default-checked-keys="defKeys"
               ref="treeRef"
      >
      </el-tree>
      <span slot="footer" class="dialog-footer">
    <el-button @click="setRightDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="allotRight">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
    //  所有角色列表数据
      rolesList: [],
      //  显示与隐藏添加角色对话框
      roleAddDialogVisible: false,
      //  添加角色数据表单对象
      roleAddForm: {
        roleName: '',
        roleDesc: ''
      },
      //  添加角色表单的数据验证对象
      roleAddFormRules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 20, message: '角色名称的长度在 3 到 10 个字符之间', trigger: 'blur' }
        ],
        roleDesc: [
          { required: false, message: '请输入角色描述', trigger: 'blur' },
          { min: 3, max: 30, message: '角色描述的长度在 3 到 30 个字符之间', trigger: 'blur' }
        ]
      },
      //  显示与隐藏编辑角色信息对话框
      editDialogVisible: false,
      //  编辑角色表单的数据对象
      editForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      //  修改角色表单的规则验证对象
      editFormRules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 20, message: '角色名称的长度在 3 到 10 个字符之间', trigger: 'blur' }
        ],
        roleDesc: [
          { required: false, message: '请输入角色描述', trigger: 'blur' },
          { min: 3, max: 30, message: '角色描述的长度在 3 到 30 个字符之间', trigger: 'blur' }
        ]
      },
      //  控制分配权限对话框的显示与隐藏
      setRightDialogVisible: false,
      //  所有权限的数据
      rightsList: [],
      // 树形控件的属性绑定对象
      treeProps: {
        label: 'authName',
        children: 'children'
      },
      // 默认选中的节点Id值数组
      defKeys: [],
      //  当前选中的角色Id值
      roleId: ''
    }
  },
  created () {
  //  发起请求获取所有角色数据列表
    this.getRolesList()
  },
  methods: {
    //  获取所有角色列表
    async getRolesList () {
      const { data: res } = await this.$http.get('/roles')
      if (res.meta.status !== 200) return this.$message.error('获取角色列表失败')
      //  this.$message.success('获取角色列表成功')
      this.rolesList = res.data
      console.log(this.rolesList)
    },
    //  添加角色对话框关闭事件
    roleAddDialogClosed () {
      this.$refs.roleAddFormRef.resetFields()
    },
    //  添加角色
    async roleAdd () {
      const { data: res } = await this.$http.post('roles', this.roleAddForm)
      if (res.meta.status !== 201) return this.$message.error('角色添加失败')
      this.$message.success('角色添加成功')
      //  关闭对话框
      this.roleAddDialogVisible = false
      //  刷新角色列表
      this.getRolesList()
    },
    // 显示编辑角色信息对话框
    async showEditDialog (id) {
      const { data: res } = await this.$http.get('roles/' + id)
      if (res.meta.status !== 200) return 0
      this.editDialogVisible = true
      this.editForm = res.data
    },
    //  添加角色
    async addRole () {
      const { data: res } = await this.$http.post('roles' + this.roleAddForm)
      if (res.meta.status !== 200) return this.$message.error('添加失败')
      this.$message.success('添加成功')
      this.addDialogVisible = false
      this.getRolesList()
    },
    //  修改角色
    async editRole () {
      const { data: res } = await this.$http.put('roles/' + this.editForm.roleId, this.editForm.roleName, this.editForm.roleDesc)
      if (res.meta.status !== 200) return this.$message.error('修改失败')
      this.$message.success('修改成功')
      this.editDialogVisible = false
      this.getRolesList()
    },
    //  删除角色
    //  根据Id删除指定的用户信息
    async removeRoleById (id) {
      //  弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      //  如果用户确认删除则返回字符串 confirm 取消则cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消了删除')
      }
      //  发起删除请求
      const { data: res } = await this.$http.delete('roles/' + id)
      if (res.meta.status !== 200) return this.$message.error('删除失败')
      this.$message.info('已删除')
      //  刷新角色列表
      this.getRolesList()
    },
    //  根据id删除权限
    async removeRightById (role, rightId) {
      // 弹框提示用户是否删除
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      //  如果用户确认删除则返回字符串 confirm 取消则cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消了删除')
      }
      //  发起删除请求
      const { data: res } = await this.$http.delete(`roles/${role.id}/rights/${rightId}`)
      if (res.meta.status !== 200) return this.$message.error('删除失败')
      this.$message.info('已删除')
      role.children = res.data
    },
    //  展示分配权限的对话框
    async showSetRightDialog (role) {
      //  当前分配角色的Id
      this.roleId = role.id
      //  获取所有权限的数据
      const { data: res } = await this.$http.get('rights/tree')
      if (res.meta.status !== 200) return this.$message.error('获取权限数据失败')
      this.rightsList = res.data
      console.log(this.rightsList)
      //  获取所有三级节点的Id
      this.getLeafKeys(role, this.defKeys)
      this.setRightDialogVisible = true
    },
    //  通过递归的形式，获取角色下所有三级权限的id
    getLeafKeys (node, arr) {
      if (!node.children) return arr.push(node.id)
      // 还不是三级节点需要调用递归拿到所有的三级节点
      node.children.forEach(item => {
        this.getLeafKeys(item, arr)
      })
    },
    //  监听分配权限的对话框的关闭事件
    setRightDialogClosed () {
      this.defKeys = []
    },
    // 点击按钮为角色分配权限
    async allotRight () {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      // console.log(keys)
      const idStr = keys.join(',')
      const { data: res } = await this.$http.post(`roles/${this.roleId}/rights`, { rids: idStr })
      if (res.meta.status !== 200) {
        return this.$message.error('分配失败')
      }
      this.$message.success('角色分配成功')
      this.getRolesList()
    }
  }
}
</script>
<style lang="less" scoped>
  .el-tag {
    margin: 7px;
  }
  .bdtop {
    border-top: 1px solid #eee;
  }
  .bdbottom {
    border-bottom: 1px solid #eee;
  }
  .vcenter {
    display: flex;
    justify-content: center;
  }
</style>
