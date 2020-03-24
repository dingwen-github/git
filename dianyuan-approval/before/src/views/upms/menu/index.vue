<template>
  <div class="app-container">
    <treetable ref="treetable1" :show-check-box="showCheckBox" :editable="editable" :default-checked="defaultChecked"/>
  </div>
</template>

<script>
import treetable from '@/views/upms/menu/treeTable/index'

export default {
  name: 'TreeTableDemo',
  components: { treetable },
  data() {
    return {
      showCheckBox: false,
      editable: true,
      defaultChecked: { checkedpermissions: ['16', '19'], checkedrouters: ['2', '3'] },
      dynamicCheckedList: { checkedpermissions: [], checkedrouters: [] }
    }
  }, methods: {
    getselected: function() {
      this.dynamicCheckedList = this.$refs.treetable1.dynamicCheckedList
    }
  }
}
</script>
