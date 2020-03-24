<template>
  <el-table
    :data="tableData2"
    :row-class-name="tableRowClassName"
    style="width: 100%">
    <el-table-column
      prop="name"
      label="工具"
    />
    <el-table-column
      prop="url"
      label="地址"
    > <template slot-scope="scope">
      <a :href="scope.row.url" target="_blank">{{ scope.row.url }}</a>
    </template></el-table-column>
  </el-table>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>

<script>
export default {
  data() {
    return {
      tableData2: [{
        name: 'element',
        url: 'https://element.eleme.cn/2.6/#/zh-CN/component/installation'
      }, {
        name: 'element-admin',
        url: 'https://panjiachen.github.io/vue-element-admin-site/zh/guide/'
      }, {
        name: 'vue',
        url: 'https://cn.vuejs.org/ '
      }, {
        name: 'spring-boot',
        url: 'https://gitee.com/didispace/SpringBoot-Learning'
      }, {
        name: 'mybatis-plus',
        url: 'https://mp.baomidou.com/'
      }]
    }
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    }
  }
}
</script>
