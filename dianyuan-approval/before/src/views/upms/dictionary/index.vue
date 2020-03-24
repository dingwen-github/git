<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.catalogCode"
        placeholder="字典编码"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"/>
      <el-input
        v-model="listQuery.catalogName"
        placeholder="字典名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"/>
      <DictionarySelect v-model="listQuery.catalogStruct" width="200px" placeholder="字典结构" catalogcode="CatalogStruct"/>
      <el-button v-waves type="primary" icon="el-icon-search" class="filter-item" @click="handleFilter">查 询</el-button>
      <el-button v-waves type="primary" icon="el-icon-refresh" class="filter-item" @click="resetForm">重 置</el-button>
      <el-button v-waves type="primary" icon="el-icon-edit" class="filter-item" @click="handleCreate">新 增</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="objList"
      border
      style="width: 100%">
      <el-table-column label="序号" type="index" align="center" width="95"/>
      <el-table-column label="字典编码" prop="catalogCode" align="center" show-overflow-tooltip/>
      <el-table-column label="字典名称" prop="catalogName" align="center" sortable show-overflow-tooltip/>
      <el-table-column label="字典描述" prop="catalogDesc" align="center" show-overflow-tooltip/>
      <el-table-column label="字典类型" prop="catalogType" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.catalogType | dictionaryFilter("CatalogType") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字典结构" prop="catalogStruct" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.catalogStruct | dictionaryFilter("CatalogStruct") }}</span>
        </template>
      </el-table-column>
      <el-table-column width="0" label="" align="center" fixed="right"/>
      <el-table-column width="250" label="操作" align="center" fixed="right">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button size="mini" type="primary" @click="handleDetail(row)">字典明细</el-button>
          <template v-if="row.catalogType == 'S'">
            <el-button size="mini" type="info" disabled>删除</el-button>
          </template>
          <template v-else>
            <el-button size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.current"
      :limit.sync="listQuery.size"
      @pagination="getList"/>

    <!--新增编辑表单-->
    <el-dialog :visible.sync="dialogFormVisible" :title="dialogFormTitle" >
      <el-form ref="dataForm" :model="dictionary" :rules="formRules" label-width="80px" label-position="left">
        <el-form-item label="字典编码" prop="catalogCode">
          <el-input v-model="dictionary.catalogCode" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="字典名称" prop="catalogName">
          <el-input v-model="dictionary.catalogName"/>
        </el-form-item>
        <el-form-item label="字典类型" prop="catalogType">
          <DictionarySelect v-model="dictionary.catalogType" catalogcode="CatalogType"/>
        </el-form-item>
        <el-form-item label="字典结构" prop="catalogStruct">
          <DictionarySelect v-model="dictionary.catalogStruct" catalogcode="CatalogStruct"/>
        </el-form-item>
        <el-form-item label="字典描述">
          <el-input
            v-model="dictionary.catalogDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="isEdit ? updateData() : createData()">确 定</el-button>
      </span>
    </el-dialog>

    <!--字典明细-->
    <el-dialog :visible.sync="dialogDetailVisible" title="字典明细" width="70%" @close="detaildialogclose">
      <el-form :model="dictionary" label-width="80px" label-position="left">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="字典编码">
              <el-input v-model="dictionary.catalogCode" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典名称">
              <el-input v-model="dictionary.catalogName" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="字典类型">
              <DictionarySelect v-model="dictionary.catalogType" catalogcode="CatalogType" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典结构">
              <DictionarySelect v-model="dictionary.catalogStruct" catalogcode="CatalogStruct" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="字典描述">
          <el-input
            v-model="dictionary.catalogDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            disabled
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <DictDetail ref="dictdetail" :dictionary="dictionary" :table-data="detailList"/>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { dictionaryService } from '@/api/dictionary'
import DictionarySelect from '@/components/dictionary/DictionarySelect.vue'
import DictDetail from './detail'
export default {
  name: 'Dictionary',
  components: { Pagination, DictionarySelect, DictDetail },
  directives: { waves },
  data: function() {
    return {
      objList: [],
      dictionary: {},
      listLoading: true,
      total: 0,
      dialogFormVisible: false,
      dialogDetailVisible: false,
      dialogFormTitle: '',
      listQuery: {
        current: 1,
        size: 20
      },
      isEdit: false, // 区分新增、编辑
      formRules: {
        catalogCode: [
          { validator: (rule, value, callback) => {
            if (value !== '' && !this.isEdit) {
              dictionaryService.checkCatalogCodeExist(value).then(function(res) {
                if (res.data) {
                  callback(new Error('字典编码已存在'))
                } else {
                  callback()
                }
              })
            } else {
              callback()
            }
          }, trigger: 'blur' },
          { required: true, message: '请输入编码', trigger: 'change' },
          { max: 32, message: '长度不得超过32个字符', trigger: 'change' }
        ],
        catalogName: [
          { required: true, message: '请输入名称', trigger: 'change' },
          { max: 64, message: '长度不得超过64个字符', trigger: 'change' }
        ],
        catalogType: [{ required: true, message: '请选择类型', trigger: 'change' }],
        catalogStruct: [{ required: true, message: '请选择结构', trigger: 'change' }],
        catalogDesc: [
          { max: 256, message: '长度不得超过256个字符', trigger: 'change' }
        ]
      },
      detailLoading: true,
      detailList: [],
      maxDataOrder: 0
    }
  },
  created() {
    var _this = this
    this.storeDictionary(['CatalogStruct', 'CatalogType']).then(function() {
      _this.getList()
    })
  },
  methods: {
    detaildialogclose: function() {
      this.detailLoading = true
      this.$refs.dictdetail.haverowOpenEdit = false
    },
    getList() {
      this.listLoading = true
      dictionaryService.list(this.listQuery).then(res => {
        this.objList = res.data.records
        this.total = res.data.total
        this.listLoading = false
      }, err => {
        console.log(err)
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    resetForm() {
      this.listQuery = {
        current: 1,
        size: 20
      }
    },
    handleCreate() {
      this.isEdit = false
      this.dialogFormTitle = '新增数据字典'
      this.dictionary = {
        catalogStruct: 'L',
        catalogType: 'U'
      }
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleEdit(item) {
      this.isEdit = true
      this.dialogFormTitle = '修改数据字典'
      this.dictionary = Object.assign({}, item)
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDetail(item) {
      this.dictionary = Object.assign({}, item)
      this.getDetailList()
    },
    handleDelete(item) {
      this.$confirm('请确认是否删除当前记录?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await dictionaryService.delete(item.catalogCode).then(res => {
            this.notifySuccess('删除')
            const index = this.objList.indexOf(item)
            this.objList.splice(index, 1)
          })
        })
        .catch(err => {
          console.error(err)
        })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          dictionaryService.create(this.dictionary).then(res => {
            this.getList()
            this.notifySuccess(this.dialogFormTitle)
            this.dialogFormVisible = false
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          dictionaryService.update(this.dictionary).then(res => {
            this.getList()
            this.notifySuccess(this.dialogFormTitle)
            this.dialogFormVisible = false
          })
        }
      })
    },
    getDetailList() {
      this.dialogDetailVisible = true
      dictionaryService.listDetail(this.dictionary.catalogCode).then(res => {
        let m = 0
        this.detailLoading = false
        this.detailList = res.data.map(v => {
          if (v.dataOrder > m) {
            m = v.dataOrder
          }
          this.$set(v, 'edit', false)
          return v
        })
        this.maxDataOrder = m
      }, err => {
        console.log(err)
        this.detailLoading = false
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
<style>
  .filter-container {
    padding-bottom: 10px;
  }

  .filter-item {
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 10px;
  }

  table {
    border-collapse: collapse;
  }
</style>
