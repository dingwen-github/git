<!-- showCheckBox: true,是否开启选择功能
      editable: true,是否开启编辑功能
      defaultChecked: { checkedpermissions: ['16', '19'], checkedrouters: ['2', '3'] }默认选择框-->
<template>
  <div>
    <el-table
      v-loading="tableLoading"
      :data="tableData"
      :row-class-name="rowclassname"
      :tree-props="{ hasChildren: 'xxxxxx',children:'cccccc'}"
      row-key="id"
      style="width: 100%"
      @cell-dblclick="hoveExpanded"
      @click.native="closePermissionFormPop">
      <el-table-column
        prop="name"
        label="菜单"
        width="250"
      >
        <template slot="header" slot-scope="item">
          <span><el-checkbox v-if="showCheckBox" v-model="top.checked" :indeterminate="top.isIndeterminate" class="routerselectall" @change="checkedChange(top,0)"/>
            <span>{{ item.column.label }}</span><i v-if="editable" class="el-icon-plus routeradd" @click="addrouter({ id: '-1', name: '顶级' })"/>
          </span>
        </template>
        <template slot-scope="item">
          <span :style="indentWith(item.row)"/>
          <i :class="menuIconClass(item.row)" @click="hoveExpanded(item.row)"/>
          <el-tooltip :content="'地址：'+item.row.path+',组件：'+item.row.component" class="item" effect="light" placement="top-start">
            <el-checkbox
              v-if="menuCheckboxShow(item.row,1)"
              :indeterminate="item.row.isIndeterminate"
              v-model="item.row.checked"
              @change="checkedChange(item.row,1)">{{ item.row.name }}
            </el-checkbox>
            <span v-if="!menuCheckboxShow(item.row,1)" style="margin-left: 10px;">{{ item.row.name }}</span>
          </el-tooltip>
          <el-dropdown v-if="editable" :hide-on-click="false" size="mini" style="float:right" trigger="click">
            <span class="el-dropdown-link" style="font-size: 10px;color:#409EFF">
              操作<i class="el-icon-arrow-down el-icon--right"/>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-popover
                v-model="item.row.showDeletePop"
                placement="top"
                width="160">
                <p>确定删除？</p>
                <div v-loading="item.row.deleteRouterLoading" style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="item.row.showDeletePop = false">取消</el-button>
                  <el-button type="primary" size="mini" @click="deleteRouter(item.row)">确定</el-button>
                </div>
                <el-dropdown-item slot="reference">删除</el-dropdown-item>
              </el-popover>
              <el-dropdown-item @click.native="addrouter(item.row)">新增</el-dropdown-item>
              <el-dropdown-item @click.native="editrouter(item.row)">编辑</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
      <el-table-column
        prop="permissions"
        label="功能权限"
      >
        <template slot-scope="item" slot="header">
          <span><span>{{ item.column.label }}</span>
          <el-switch v-model="expandAll" class="expandswitch" @change="expandAllMenu(tableData)"/></span>
        </template>
        <template slot-scope="item">
          <el-checkbox
            v-if="menuCheckboxShow(item.row,2)"
            :indeterminate="item.row.isIndeterminate"
            v-model="item.row.checked"
            @change="checkedChange(item.row,2)">全选
          </el-checkbox>
          <span v-if="permissionShow(item.row)">
            <el-tag
              v-for="(tag, index) in item.row.permissions"
              :key="tag.id"
              :closable="editable"
              :disable-transitions="false"
              size="mini"
              @close="deletePermission(tag.id,item.row,index)">
              <el-tooltip :content="'地址：'+tag.url" class="item" effect="light" placement="top-start">
                <el-checkbox
                  v-if="menuCheckboxShow(item.row,3)"
                  v-model="tag.checked"
                  @change="checkedChange(item.row,3)">
                  {{ tag.name }}
                </el-checkbox>
                <span v-else>{{ tag.name }}</span>
              </el-tooltip>
            </el-tag>
          </span>
          <el-tag v-if="addPermissionShow(item.row)" size="mini" type="success" style="cursor: pointer" @click.native="showPermissionForm(item.row,$event)">+添加</el-tag>

        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="reuterFormVisible" title="新增菜单">
      <el-form v-loading="routerFormLoading" ref="routerForm" :model="routerForm" :rules="routerFormRules" size="mini">
        <input v-model="routerForm.id" type="hidden">
        <el-form-item :label-width="formLabelWidth" label="父级" prop="parentId">
          <el-input :disabled="true" v-model="routerForm.parentName" auto-complete="off"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="名称" prop="name">
          <el-input v-model="routerForm.name" auto-complete="off"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="排序" prop="orders">
          <el-input-number v-model="routerForm.orders" :min="1" :max="10000" label="序号"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="路由路径" prop="path">
          <el-input v-model="routerForm.path" auto-complete="off" placeholder="一级路由必须/开头，其他级不限"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="路由组件" prop="component">
          <el-input v-model="routerForm.component" auto-complete="off" placeholder="@view下组件路径不用/开头"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="图标" prop="icon">
          <IconPicker v-model="routerForm.icon"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reuterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="commitRouter()">确 定</el-button>
      </div>
    </el-dialog>

    <el-popover
      v-if="editable"
      ref="permissionFormPop"
      placement="right"
      width="600"
      @blur.native="closePermissionFormPop">
      <el-form
        v-loading="permissionFormLoading"
        ref="permissionFormEl"
        :inline="true"
        :rules="permissionFormRules"
        :model="permissionForm"
        class="demo-form-inline">
        <el-form-item label="名称" prop="name">
          <el-input v-model="permissionForm.name" placeholder="权限名称"/>
        </el-form-item>
        <el-form-item label="url" prop="url">
          <el-input v-model="permissionForm.url" placeholder="权限url"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="savePermission">确认</el-button>
        </el-form-item>
      </el-form>

    </el-popover>
  </div>
</template>

<script>
import { saveRouter, listRouter, deleteRouter, savePermission, deletePermission, updateRouter } from '@/api/upms/router'
import IconPicker from '@/components/SvgIcon/iconPicker'
export default {
  name: 'TreeTable',
  components: { IconPicker },
  props: {

    showCheckBox: { type: Boolean, default: () => true },
    editable: { type: Boolean, default: () => true },
    defaultChecked: {
      type: Object, default: function() {
        return { checkedpermissions: [], checkedrouters: [] }
      }
    }
  },
  data() {
    return {
      permissionFormLoading: false,
      expandAll: false,
      tableLoading: false,
      routerFormLoading: false,
      formLabelWidth: '80px',
      reuterFormVisible: false,
      routerForm: { id: '', name: '', path: '', component: '', title: '', parentId: '', parentName: '', icon: '', orders: 0 },
      top: {
        id: '-1',
        name: '顶级',
        path: '/',
        children: [],
        checked: false,
        expanded: true,
        show: true,
        isIndeterminate: false
      },
      tableData: [],
      permissionForm: { parentRouter: '', name: '', url: '' },
      routerFormRules: {
        name: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        icon: [
          { required: true, message: '请选择菜单图标', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        component: [
          { required: true, message: '请输入组件名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }],
        path: [
          { validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入路由地址'))
            } else if (this.routerForm.parentId === '-1' && value.indexOf('/') !== 0) {
              callback(new Error('顶级菜单必须以/开头'))
            } else {
              let flag = false
              this.tableData.some(item => {
                if (item.parentId === this.routerForm.parentId && item.path === this.routerForm.path && this.routerForm.id !== item.id) {
                  callback(new Error('同级目录不允许有相同地址'))
                  flag = true
                }
              })
              if (!flag) {
                callback()
              }
            }
          }, trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }],
        orders: [{ required: true, message: '请输入序号', trigger: 'change' }] },
      permissionFormRules: {
        name: [
          { required: true, message: '请输入权限名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ], url: [
          { required: true, message: '请输入权限地址', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dynamicCheckedList: function() {
      const dynamicCheckedList = { checkedrouters: [], checkedpermissions: [] }
      Array.from(this.tableData).forEach(function(menu) {
        if (menu.checked) {
          dynamicCheckedList.checkedrouters.push(menu.id)
        }
        if (menu.permissions && menu.permissions.length > 0) {
          Array.from(menu.permissions).forEach(function(permission) {
            if (permission.checked) {
              dynamicCheckedList.checkedpermissions.push(permission.id)
            }
          })
        }
      })
      if (dynamicCheckedList.checkedrouters > 0) {
        dynamicCheckedList.checkedrouters.push('-1')
      }
      return dynamicCheckedList
    }
  },
  watch: {
    defaultChecked: { immediate: true, deep: true, handler(val) {
      if (this.defaultChecked.checkedrouters.indexOf('-1') > -1) {
        this.top.checked = true
      }
      this.tableData = this.treeToArray(this.top.children, this.expandAll, this.top)
      this.recursiveChecked(this.top)
    } }},
  created: function() {
    /* 加载菜单数据*/
    this.tableLoading = true
    listRouter().then(response => {
      this.top.children = response.data
      this.top.checked
      this.tableData = this.treeToArray(this.top.children, this.expandAll, this.top)
      this.recursiveChecked(this.top)
      this.tableLoading = false
    }).catch(error => {
      this.tableLoading = false
      console.error(error)
    })
  },

  methods: {
    showPermissionForm: function(menu, event) {
      this.permissionForm.parentRouter = menu
      const pop = this.$refs.permissionFormPop
      pop.updatePopper()
      pop.referenceElm = event.target
      pop.showPopper = true
      this.$nextTick(() => {
        pop.popperJS._reference = event.target
        pop.popperJS.state.updateBound()
      })
      event.stopPropagation()
    },
    closePermissionFormPop: function() {
      if (this.editable) {
        const pop = this.$refs.permissionFormPop
        pop.showPopper = false
      }
    },
    sortArr: function(a, b) {
      return a.orders - b.orders
    },
    treeToArray: function(data, expandAll, parent = null, level = null) {
      let tmp = []
      var _this = this
      data.sort(_this.sortArr)
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
        _this.$set(record, 'permissionFormLoading', false)
        tmp.push(record)
        /* 给record添加函数判断其选中状态*/
        if (_this.defaultChecked.checkedrouters.indexOf(record.id) > -1) {
          record.checked = true
        } else {
          record.checked = false
        }
        if (record.permissions && record.permissions.length > 0) {
          Array.from(record.permissions).forEach(function(permission) {
            if (_this.defaultChecked.checkedpermissions.indexOf(permission.id) > -1) {
              permission.checked = true
            } else {
              permission.checked = false
            }
          })
        }
        if (record.children && record.children.length > 0) {
          const children = _this.treeToArray(record.children, expandAll, record, _level)
          tmp = tmp.concat(children)
        }
      })
      return tmp
    },
    recursiveChecked: function(menu) {
      this.recursiveCheckedByChild(menu)
      if (menu.parent) {
        this.recursiveChecked(menu.parent)
      }
    },
    recursiveCheckedByChild: function(menu) {
      /* menu是父菜单*/
      let checkednum = 0
      let uncheckednum = 0
      let checkgrouplength = 0
      let isIndeterminatenum = 0
      menu.isIndeterminate = false
      if (menu.children && menu.children.length > 0) {
        checkgrouplength = menu.children.length
        menu.children.some(function(item) {
          if (item.checked) {
            checkednum = checkednum + 1
          } else {
            uncheckednum = uncheckednum + 1
          }
          if (item.isIndeterminate) {
            isIndeterminatenum = isIndeterminatenum + 1
          }
        })
      } else {
        /* menu是叶子菜单*/
        if (menu.permissions && menu.permissions.length > 0) {
          checkgrouplength = menu.permissions.length
          menu.permissions.some(function(permission) {
            if (permission.checked) {
              checkednum = checkednum + 1
            } else {
              uncheckednum = uncheckednum + 1
            }
          })
        } else {
          return menu.checked
        }
      }
      if (checkednum === checkgrouplength) {
        /* 全选叶子*/
        if (isIndeterminatenum > 0) {
          menu.isIndeterminate = true
        }
        menu.checked = true
        return true
      } else if (uncheckednum === checkgrouplength) {
        /* 全取消叶子*/
        menu.checked = false
        return false
      } else {
        /* 半选叶子*/
        menu.checked = true
        menu.isIndeterminate = true
        return true
      }
    },
    checkAll: function(menu) {
      var _this = this
      if (menu.children && menu.children.length > 0) {
        menu.children.some(function(item) {
          item.checked = menu.checked
          _this.checkAll(item)
        })
      } else {
        /* menu是叶子菜单*/
        if (menu.permissions && menu.permissions.length > 0) {
          menu.permissions.some(function(permission) {
            permission.checked = menu.checked
          })
        }
      }
    },
    menuIconClass: function(menu) {
      /* 有子元素才展示*/
      if (menu.children && menu.children.length > 0) {
        if (menu.expanded) {
          return 'el-icon-arrow-down'
        } else {
          return 'el-icon-arrow-right'
        }
      } else {
        return 'el-icon-star-off'
      }
    },
    hoveExpanded: function(menu, column, cell, event) {
      if (column === undefined || column.property === 'name') {
        menu.expanded = !menu.expanded
      }
    },
    rowclassname: function(row) {
      const show = (row.row.parent ? (row.row.parent.expanded && row.row.parent.show) : true)
      /* 父元素不展示时子元素也应当隐藏*/
      row.row.show = show
      return show ? 'rowshow' : 'rowhide'
    },
    indentWith: function(menu) {
      return {
        display: 'inline-block',
        width: menu.level * 18 + 'px'
      }
    },
    addPermissionShow: function(menu) {
      if (this.editable && (!menu.children || menu.children.length === 0)) {
        return true
      } else {
        return false
      }
    },
    permissionShow: function(menu) {
      if ((!menu.children || menu.children.length === 0)) {
        return true
      } else {
        return false
      }
    },
    menuCheckboxShow: function(menu, type) {
      if (!this.showCheckBox) {
        return false
      }
      const boo = menu.children && menu.children.length > 0
      if (type === 1) {
        return !boo
      } else if (type === 2) {
        return boo
      }
      return true
    },
    deletePermission: function(id, menu, index) {
      deletePermission(id).then(response => {
        menu.permissions.splice(index, 1)
      }).catch(error => {
        this.$notify.info({
          title: '消息',
          message: error
        })
      })
    },
    checkedChange: function(menu, type) {
      /* 权限变化维护父代状态向上传递*/
      if (type !== 3) {
        this.checkAll(menu)
      }
      this.recursiveChecked(menu)
    },
    resetRouterForm: function() {
      this.routerForm = { id: '', name: '', path: '', component: '', title: '', parentId: '', parentName: '', icon: '' }
    },
    addrouter: function(parent) {
      this.resetRouterForm()
      this.routerForm.parentName = parent.name
      this.routerForm.parentId = parent.id
      this.reuterFormVisible = true
      if (parent.id === '-1') {
        this.routerForm.path = '/'
      }

      this.$nextTick(() => {
        this.$refs['routerForm'].clearValidate()
      })
    },
    editrouter: function(menu) {
      const copyMenu = Object.assign({}, menu)
      let parent = copyMenu.parent
      if (!parent) {
        parent = { id: '-1', name: '顶级' }
      }
      this.routerForm.name = copyMenu.name
      this.routerForm.id = copyMenu.id
      this.routerForm.path = copyMenu.path
      this.routerForm.component = copyMenu.component
      this.routerForm.orders = copyMenu.orders
      this.routerForm.icon = copyMenu.icon
      this.routerForm.parentName = parent.name
      this.routerForm.parentId = parent.id
      this.reuterFormVisible = true
      this.$nextTick(() => {
        this.$refs['routerForm'].clearValidate()
      })
    },
    commitRouter: function() {
      var _this = this
      this.$refs['routerForm'].validate((valid) => {
        if (valid) {
          this.routerFormLoading = true
          if (this.routerForm.id === '') {
            saveRouter(this.routerForm).then(response => {
              const data = response.data
              this.routerForm.id = data
              this.routerFormLoading = false
              this.reuterFormVisible = false
              /* 把数据显示到列表中*/
              this.addNode2child(this.routerForm.parentId, this.tableData, Object.assign({}, this.routerForm))
              this.tableData = this.treeToArray(this.top.children, this.expandAll, this.top)
            }).catch(error => {
              console.error(error)
              this.routerFormLoading = false
            })
          } else {
            updateRouter(this.routerForm).then(response => {
              this.routerFormLoading = false
              this.reuterFormVisible = false
              /* 更新数据*/
              this.tableData.some(function(item) {
                if (item.id === _this.routerForm.id) {
                  item.name = _this.routerForm.name
                  item.path = _this.routerForm.path
                  item.component = _this.routerForm.component
                  item.icon = _this.routerForm.icon
                  item.orders = _this.routerForm.orders
                }
              })
              this.tableData = this.treeToArray(this.top.children, this.expandAll, this.top)
            }).catch(error => {
              console.error(error)
              this.routerFormLoading = false
            })
          }
        } else {
          this.$message({
            message: '警告哦，请按照提示补全表单',
            type: 'warning'
          })
          return false
        }
      })
    },
    deleteRouter: function(menu) {
      const _this = this
      if (menu.children && menu.children.length > 0) {
        this.$message({
          message: '警告哦，该目录存在子菜单不允许删除',
          type: 'warning'
        })
        menu.showDeletePop = false
        return
      }
      menu.deleteRouterLoading = true
      deleteRouter(menu.id).then(response => {
        /* 从父级删除*/
        menu.parent.children.some(function(item, index) {
          if (item.id === menu.id) {
            menu.parent.children.splice(index, 1)
          }
        })
        /* 从列表删除*/
        _this.tableData.some(function(item, index) {
          if (item.id === menu.id) {
            _this.tableData.splice(index, 1)
          }
        })
        menu.showDeletePop = false
        menu.deleteRouterLoading = false
      }).catch(error => {
        menu.deleteRouterLoading = false
        this.$notify.info({
          title: '消息',
          message: error
        })
        console.log(error)
      })
    },
    addNode2child: function(pid, toplist, node) {
      node.show = true
      if (pid === '-1') {
        node.parent = this.top
        node.level = 1
        toplist.push(node)
        node.parent.children.push(node)
        return
      }
      toplist.some(function(item, index) {
        if (item.id === pid) {
          if (!item.children) {
            item.children = []
          }
          node.parent = item
          node.level = item.level + 1
          item.expanded = true
          item.children.push(node)
          toplist.splice(index + item.children.length, 0, node)
          return
        }
      })
    },
    savePermission: function() {
      const pop = this.$refs.permissionFormPop
      const menu = this.permissionForm.parentRouter
      this.permissionForm.parentRouter = menu.id
      this.$refs['permissionFormEl'].validate((valid) => {
        if (valid) {
          savePermission(this.permissionForm).then(response => {
            if (!menu.permissions) {
              this.$set(menu, 'permissions', [])
            }
            this.permissionForm.id = response.data
            const newp = Object.assign({}, this.permissionForm, { checked: false })
            this.$set(menu.permissions, menu.permissions.length, newp)
            this.permissionForm = {}
            menu.permissionFormLoading = false
            pop.showPopper = false
            pop.popperJS.state.updateBound()
          }).catch(error => {
            this.$notify.info({
              title: '消息',
              message: '保存失败'
            })
            pop.popperJS.state.updateBound()
            console.error(error)
          })
        } else {
          this.$message({
            message: '警告哦，请补全表单',
            type: 'warning'
          })
          return false
        }
      })
    },
    expandAllMenu: function(menuList) {
      var _this = this
      menuList.some(function(item) {
        item.expanded = _this.expandAll
        if (item.children && item.children.length > 0) {
          _this.expandAllMenu(item.children)
        }
      })
    }
  }
}
</script>

<style>
  .hidden {
    display: none;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  label {
    font-weight: 200;
  }

  .routeradd {
    cursor: pointer;
    float: right;
    margin-right: 15px;
    color: #409EFF;
  }

  .routerselectall {
    margin-right: 10px;
  }

  .el-table th div.expandswitch {
    display: inline;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .rowshow{
   animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s
  }
  .rowhide{
    display:none
  }
</style>
