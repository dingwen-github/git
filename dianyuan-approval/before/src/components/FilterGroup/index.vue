<template>
  <div id="filterGroup" class="filter">
    <div :class="{'opened':open,'big':open_btn_show}" class="filter-container">
      <!-- 筛选项 -->
      <el-form id="formBox" ref="filterForm" :inline="true">
        <el-form-item
          v-for="(item,index) in filterList"
          :span="item.span?item.span:8"
          :prop="item.prop"
          :label="item.label"
          :key="index"
        >
          <!--输入框表单类型-->
          <el-input
            v-if="item.type ==='text'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
            @keyup.enter.native="getFilterData"/>
          <!--文本域表单类型-->
          <el-input
            v-if="item.type === 'textarea'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
            type="textarea"
            @keyup.enter.native="getFilterData"/>
          <!--checkbox表单类型-->
          <el-checkbox-group
            v-if="item.type === 'checkbox'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请选择'+item.label">
            <el-checkbox v-for="option in item.data" :label="option.id" :key="option.id">{{ option.name }}</el-checkbox>
          </el-checkbox-group>
          <!--radio表单类型-->
          <el-radio-group
            v-if="item.type === 'radio'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请选择'">
            <el-radio v-for="option in item.data" :label="option.id" :key="option.id">{{ option.name }}</el-radio>
          </el-radio-group>
          <!--下拉选择类型-->
          <el-select
            v-if="item.type === 'select'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请选择'+item.label"
            value-key="id">
            <el-option
              v-for="option in item.data"
              :key="option.id"
              :label="option.name"
              :value="option.id"/>
          </el-select>

          <el-date-picker
            v-if="item.type === 'datepicker'"
            v-model="filterData[item.prop]"
            :placeholder="item.placeholder?item.placeholder:'请选择'+item.label"
            type="date"/>

          <el-date-picker
            v-if="item.type === 'daterange'"
            v-model="dateValue"
            :picker-options="pickerOptions"
            type="daterange"
            range-separator="至 "
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="dateRangeChange"/>

        </el-form-item>

        <el-form-item >
          <el-button type="primary" @click="getFilterData()">查 询</el-button>
          <el-button type="primary" @click="resetForm()">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
// css
import '@/components/filterGroup/style.scss'

export default {
  // 筛选项
  name: 'FilterGroup',
  props: {
    // 筛选项配置 外部传入
    filterList: {
      type: Array,
      default: () => []
    }

  },
  data() {
    return {
      // 布局状态
      menuType: 1,
      contentWidthType: '流式',
      isCollapse: false, // 导航是否折叠
      open: false,
      filterData: {},
      start_time: '',
      end_time: '',
      dateValue: '',
      open_btn_show: true,
      pickerOptions: {
        dateValue: [this.start_time, this.end_time]
      }
    }
  },
  computed: {
  },
  watch: {
  },
  created: function() {
    // 匹配显示
  },
  mounted: function() {
  },
  methods: {
    // 传递筛选数据
    getFilterData() {
      const obj = this.filterData
      for (const key in obj) {
        if (obj[key] === '' || obj[key] === null) {
          delete obj[key]
        }
      }
      this.$emit('getFilterData', obj)
    },
    resetForm() {
      this.filterData = {}
      //        this.getFilterData();
    },
    dateRangeChange(data) {
      if (data) {
        this.start_time = data[0]
        this.end_time = data[1]
      }
      Object.assign(
        this.filterData,
        this.start_time,
        this.end_time)
    }
  }
}
</script>
