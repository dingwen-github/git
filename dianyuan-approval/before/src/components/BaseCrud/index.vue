<template>
  <div class="crud">
    <!--crud头部，包含可操作按钮-->
    <filterGroup :filter-list="filterConfig" :filter-data="formModel" v-on="{getFilterData:filterData}"/>
    <el-row class="crud-header">
      <el-button v-if="gridBtnConfig.create" type="primary" style="float: right" @click="createOrUpdate(null)">新增
      </el-button>
    </el-row>
    <!--crud主体内容区，展示表格内容-->
    <el-table
      v-loading="listLoading"
      :data="objList"
      border
      style="width: 98%;">
      <el-table-column align="center" type="index" label="序号" width="95"/>
      <el-table-column
        v-for="(item,index) in gridConfig"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width?item.width:''"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <Cell
            v-if="item.render"
            :row="scope.row"
            :column="item"
            :index="scope.$index"
            :render="item.render"/>
          <span v-else>{{ scope.row[item.prop] }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="!hideEditArea" :width="gridEditWidth?gridEditWidth:200" fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button v-if="gridBtnConfig.update" size="mini" type="primary" @click="createOrUpdate(scope.row)">修改</el-button>
          <el-button v-if="gridBtnConfig.delete" size="mini" type="danger" @click="handleDelete(scope)">删除</el-button>
          <el-button v-if="gridBtnConfig.view" size="mini" type="primary" @click="handleView(scope)">查看</el-button>
          <!--扩展按钮-->
          <span v-if="gridBtnConfig.expands && gridBtnConfig.expands.length>0">
            <el-button
              v-for="(item,index) in gridBtnConfig.expands"
              :key="index"
              :type="item.type?item.type:'primary'"
              size="mini"
              @click="handleEmit(item.emitName, scope.row)">
              {{ item.name }}
            </el-button>
          </span>
        </template>
      </el-table-column>

    </el-table>

    <!--crud的分页组件-->
    <div class="crud-pagination">
      <!--如果不是异步请求展示数据，需要隐藏分页-->
      <el-pagination
        v-show="totalRows>0"
        v-if="isAsync"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="currentPageSize"
        :total="totalRows"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
    </div>

    <!--crud按钮触发的表单弹窗-->
    <BaseDialogForm
      ref="dialogForm"
      :title="dialogTitle"
      :config="formConfig"
      :form-data="formModel"
      @submit="dialogSubmit"/>
    <!--信息查看-->
    <BaseDialogForm
      ref="dialogView"
      :title="dialogTitle"
      :config="formConfig"
      :form-data="formModel"
      @submit="dialogSubmit"/>
  </div>
</template>

<script>
import BaseDialogForm from '@/components/BaseDialogForm/index.vue'
import filterGroup from '@/components/FilterGroup/index.vue'
import Cell from './expand'
import moment from 'moment'
export default {
  name: 'BaseCrud',
  components: {
    BaseDialogForm,
    Cell,
    filterGroup
  },
  props: {
    formTitle: { type: String, default: '' },
    formConfig: { type: Array, default: () => [] },
    filterConfig: { type: Array, default: () => [] },
    formData: { type: Object, default: () => {} },
    gridConfig: { type: Array, default: () => [] },
    gridBtnConfig: { type: Object, default: () => {} },
    gridData: { type: Array, default: () => [] },
    apiService: { type: Object, default: () => {} },
    isAsync: { type: Boolean, default: false },
    gridEditWidth: { type: Number, default: 27 },
    hideEditArea: { type: Boolean, default: () => false }}, /* [    // 表单标题，例如用户、角色
    'formTitle',
    // 表单配置
    'formConfig',
    // 表单的model数据
    'filterConfig',
    // 查询栏的配置
    'formData',
    // 表格配置
    'gridConfig',
    // 表格按钮配置
    'gridBtnConfig',
    // 表格死数据
    'gridData',
    // 数据接口
    'apiService',
    // 判断是否是异步数据
    'isAsync',
    //  表格编辑区域宽度
    'gridEditWidth',
    //  是否隐藏表格操作
    'hideEditArea'
    // 表单标题，例如用户、角色
  ]*/
  data() {
    return {
      // 新增修改模态框title
      dialogTitle: '',
      // 展示的表格数据，数据来源可能是父组件传递的固定数据，可能是接口请求数据
      objList: [],
      // 当前页码
      currentPage: 1,
      // 每页显示数量
      currentPageSize: 10,
      // 列表数据总数
      totalRows: 0,
      // 表单数据
      formModel: {},
      //  表格加载状态
      listLoading: false,

      filter_data: {}
      // 筛选项
    }
  },
  watch: {
    // 防止表格预置数据不成功，涉及生命周期问题
    gridData() {
      this.objList = this.gridData
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    // 获取列表数据
    datefmt(input, fmtstring) {
      moment(input).format(fmtstring)
    },
    getData() {
      this.listLoading = true
      let params = {
        pageNo: this.currentPage,
        pageSize: this.currentPageSize
      }
      params = Object.assign(params, this.filter_data)
      this.apiService.list(params).then(res => {
        this.objList = res.data.records
        this.totalRows = res.data.total
        this.listLoading = false
      }, err => {
        console.log(err)
        this.listLoading = false
      })
    },
    filterData: function(obj) {
      this.objList = []
      this.filter_data = JSON.parse(JSON.stringify(obj))
      this.currentPage = 1
      // 刷新数据
      this.getData()
    },
    createOrUpdate(item) {
      this.$refs.dialogForm.resetForm()
      // 新增时，模态框数据需要拷贝基础定义的数据模型，修改时，直接拷贝当前行数据
      this.formModel = item ? Object.assign({}, item) : Object.assign({}, this.formData)
      this.dialogTitle = (item ? '修改' : '新增') + this.formTitle
      this.$refs.dialogForm.showDialog()
    },
    handleView(item) {
      this.$refs.dialogView.resetForm()
      // 新增时，模态框数据需要拷贝基础定义的数据模型，修改时，直接拷贝当前行数据
      this.formModel = item ? Object.assign({}, item) : Object.assign({}, this.formData)
      this.dialogTitle = '查看' + this.formTitle
      this.$refs.dialogView.showDialog()
    },
    handleDelete({ $index, row }) {
      this.$confirm('请确认是否删除当前记录?', 'Warning', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          await this.apiService.delete(row).then(res => {
            this.objList.splice($index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
        })
        .catch(err => { console.error(err) })
    },
    // 处理相应父组件的事件方法
    handleEmit(emitName, row) {
      this.$emit(emitName, row)
    },
    handleCurrentChange(page) {
      this.currentPage = page
      this.getData()
    },
    handleSizeChange(size) {
      this.currentPageSize = size
      this.getData()
    },
    // 模态框数据提交
    dialogSubmit(data) {
      this.apiService.save(data).then(res => {
        this.getData()
        this.$message.success(this.dialogTitle + '成功！')
      })
    },
    remove(data) {
      //  处理删除逻辑
    },
    view(data) {
      // 处理查看详情逻辑
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .crud {
    align: center;
    .crud-header {
      margin: 5px 20px;
      line-height: 40px;
    }

    .crud-pagination {
      text-align: right;
      margin-top: 10px;
    }
  }
</style>
