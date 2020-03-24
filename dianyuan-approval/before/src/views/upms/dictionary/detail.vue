<template>
  <div class="app-container">
    <el-form ref="dicform" :model="formmodel" :inline="true">
      <ElTreeTable
        ref="table"
        :data="tableData"
        :selected-all="selectedAll"
        :first_field="'dataCode'"
        :first_label="'数据编码'"
        :first_column_width="'200'"
        :show-checkbox="showCheckbox"
        :default-selected="defaultSelected"
        :key_field="'dataCode'"
        :order_field="'dataOrder'"
        @add_button_click="handleAddOrEdit({dataCode:'-1'})">
        <template slot="treeColumn" slot-scope="item">
          <template v-if="item.data.row.edit && item.data.row.isNew">
            <el-form-item
              :rules="[{ required: true, message: '请输入编码', trigger: 'blur' },
                       { max: 32, message: '长度不得超过32个字符', trigger: 'change' }]"
              prop="dataCode"
              size="mini">
              <el-input v-model="item.data.row.dataCode" class="edit-input" size="small" style="width: 80px"/>
            </el-form-item>
          </template>
          <span v-else>{{ item.data.row.dataCode }}</span>
        </template>
        <template slot="otherColumns">
          <el-table-column prop="dataCode" label="数据数值">
            <template slot-scope="{row}">
              <template v-if="row.edit">
                <el-form-item
                  :rules="[{ required: true, message: '请输入值', trigger: 'blur' },
                           { max: 32, message: '长度不得超过32个字符', trigger: 'change' }]"
                  prop="dataValue">
                <el-input v-model="row.dataValue" class="edit-input" size="small"/></el-form-item>
              </template>
              <span v-else>{{ row.dataValue }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="dataOrder" label="数据排序">
            <template slot-scope="{row}">
              <template v-if="row.edit">
                <el-form-item
                  :rules="[{ required: true, message: '请输排序', trigger: 'blur' }]"
                  prop="dataOrder"
                  style="width: 30px">
                  <el-input-number v-model="row.dataOrder" style="width: 90px" size="mini"/>
                </el-form-item>
              </template>
              <span v-else>{{ row.dataOrder }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="dataValue2" label="扩展值">
            <template slot-scope="{row}">
              <template v-if="row.edit">
                <el-form-item
                  :rules="{ max: 64, message: '长度不得超过64个字符', trigger: 'change' }"
                  prop="dataValue2">
                <el-input v-model="row.dataValue2" class="edit-input" size="small"/></el-form-item>
              </template>
              <span v-else>{{ row.dataValue2 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="dataDesc" label="数据描述">
            <template slot-scope="{row}">
              <template v-if="row.edit">
                <el-form-item
                  :rules="{ max: 256, message: '长度不得超过256个字符', trigger: 'change' }"
                  prop="dataDesc">
                <el-input v-model="row.dataDesc" class="edit-input" size="small"/></el-form-item>
              </template>
              <span v-else>{{ row.dataDesc }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="opt" label="操作" width="150">
            <template slot-scope="{row}">
              <span v-if="row.edit !== true">
                <el-button
                  v-if="dictionary.catalogStruct ==='T'"
                  size="mini"
                  type="success"
                  circle
                  @click="handleAddOrEdit(row)"><i class="el-icon-plus"/></el-button>
                <el-button size="mini" type="success" circle style="margin-left: 0px" @click="handleEdit(row)"><i
                  class="el-icon-edit"/></el-button>
                <el-popover
                  v-model="row.showDeletePop"
                  placement="top"
                  width="160">
                  <p>确定删除？</p>
                  <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="row.showDeletePop = false">取消</el-button>
                    <el-button type="primary" size="mini" @click="deleteItem(row)">确定</el-button>
                  </div>
                  <el-button slot="reference" size="mini" type="danger" circle @click="row.showDeletePop = true"><i class="el-icon-delete"/></el-button>
                </el-popover>
              </span>
              <span :visible.sync="row.edit">
                <el-button v-if="row.edit" size="mini" type="success" circle @click="handleDetailCreateOrUpdate(row)"><i
                  class="el-icon-check"/></el-button>
                <el-button v-if="row.edit" size="mini" type="danger" circle @click="deleteRow(row)"><i
                  class="el-icon-close"/></el-button>
              </span>
            </template>
          </el-table-column>
        </template>
      </ElTreeTable>
    </el-form>
  </div>
</template>
<script>
import waves from '@/directive/waves'
import ElTreeTable from '@/components/treeTable/elTreeTable.vue'
import { dictionaryService } from '@/api/dictionary'

export default {
  name: 'DictDetail',
  components: { ElTreeTable },
  directives: { waves },
  props: {
    dictionary: {
      type: Object,
      default: () => {
      }
    },
    tableData: {
      type: Array, default: () => []
    }
  },
  data() {
    return {
      defaultSelected: [],
      showCheckbox: false,
      selectedAll: false,
      dictdetail: {},
      maxDataOrder: 0,
      dictionaryDetail: {},
      formmodel: {},
      haverowOpenEdit: false
    }
  },
  activated: function() {
    this.haverowOpenEdit = false
  },
  mounted: function() {
    this.init()
  },
  updated: function() {
    this.init()
  },
  methods: {
    init() {
      let m = 0
      this.tableData.map(v => {
        if (v.dataOrder > m) {
          m = v.dataOrder
        }
        this.$set(v, 'edit', false)
        return v
      })
      this.maxDataOrder = m
    },
    getList() {
      dictionaryService.listDetail(this.dictionary.catalogCode).then(res => {
        let m = 0
        this.tableData = res.data.map(v => {
          if (v.dataOrder > m) {
            m = v.dataOrder
          }
          this.$set(v, 'edit', false)
          return v
        })
        this.maxDataOrder = m
      }, err => {
        console.log(err)
      })
    },
    handleAddOrEdit(parent) {
      var _this = this
      if (this.haverowOpenEdit) {
        this.$refs['dicform'].validate()
        this.$notify({
          title: '注意',
          message: '有内容正在编辑',
          type: 'error',
          duration: 2000
        })
        return
      }
      this.haverowOpenEdit = true
      var dictionaryDetail = {
        edit: true,
        catalogCode: this.dictionary.catalogCode,
        dataParent: parent.dataCode,
        dataOrder: this.maxDataOrder + 1,
        isNew: true
      }
      this.formmodel = dictionaryDetail
      _this.$refs.table.addRow(parent.dataCode, dictionaryDetail)
      this.$nextTick(() => {
        this.$refs['dicform'].clearValidate()
      })
    },
    handleEdit(item) {
      this.formmodel = item
      if (this.haverowOpenEdit) {
        this.$refs['dicform'].validate()
        this.$notify({
          title: '注意',
          message: '有内容正在编辑',
          type: 'error',
          duration: 2000
        })
        return
      }
      this.haverowOpenEdit = true
      /* this.dictionaryDetail = item*/
      this.$set(item, 'edit', true)
    },
    handleDetailCreateOrUpdate(item) {
      this.$refs['dicform'].validate((valid) => {
        if (valid) {
          const obj = this.copyObj(item)
          if (item.isNew) {
            dictionaryService.createDetail(obj).then(res => {
              item.edit = false
              item.isNew = false
              this.haverowOpenEdit = false
              this.maxDataOrder = this.maxDataOrder + 1
              this.notifySuccess('新增')
              this.formmodel = {}
              this.$refs['dicform'].clearValidate()
            })
          } else {
            dictionaryService.updateDetail(obj).then(res => {
              item.edit = false
              this.haverowOpenEdit = false
              this.notifySuccess('修改')
              this.formmodel = {}
              this.$refs['dicform'].clearValidate()
            })
          }
        } else {
          return
        }
      })
    },
    copyObj: function(item) {
      var obj = {}
      obj.catalogCode = item.catalogCode
      obj.dataCode = item.dataCode
      obj.dataValue = item.dataValue
      obj.dataValue2 = item.dataValue2
      obj.dataParent = item.dataParent
      obj.dataOrder = item.dataOrder
      obj.dataDesc = item.dataDesc
      return obj
    },
    deleteRow: function(row) {
      if (row.isNew) {
        // 如果是新增的，直接删除此行
        this.$refs.table.deleteRow(row.dataCode)
      } else {
        // 如果编辑此行，则取消编辑状态
        this.$set(row, 'edit', false)
      }
      this.haverowOpenEdit = false
    },
    deleteItem: function(item) {
      var _this = this
      dictionaryService.deleteDetail(item).then(function(res) {
        item.showDeletePop = false
        _this.$refs.table.deleteRow(item.dataCode)
      })
    },
    notifySuccess(msg) {
      this.$notify({
        title: '成功',
        message: msg + '成功',
        type: 'success',
        duration: 2000
      })
    }
  }
}
</script>

<style scoped>

</style>
