<template>
  <el-select v-model="mValue" :placeholder="placeholder" :style="mStyle" :disabled="disabled" clearable class="filter-item" @click.native="refresh">
    <el-option
      v-for="option in options"
      :key="option.dataCode"
      :label="option.dataValue"
      :value="option.dataCode"/>
  </el-select>
</template>

<style>
</style>

<script>
export default{
  name: 'DictionarySelect',
  props: {
    value: { type: String, default: '' },
    catalogcode: { type: String, default: '' },
    placeholder: { type: String, default: undefined },
    width: { type: String, default: undefined },
    disabled: { type: Boolean, default: false }
  },
  data: function() {
    let mStyle = {}
    if (this.width != null) {
      mStyle = {
        width: this.width
      }
    }
    return {
      options: this.$store.getters.dictData[this.catalogcode],
      mStyle
    }
  },
  computed: {
    mValue: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  methods: {
    refresh: function() {
      this.options = this.$store.getters.dictData[this.catalogcode]
    }
  }
}
</script>
