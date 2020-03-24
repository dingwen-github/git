import store from '../store'

/**
 * Copyright (C), 2019/6/1, sunflow开发团队
 * 数据字典全局过滤器
 *
 * @author   jason
 * @fileName dictionary.js
 * @date     2019/6/2 11:16
 */
export default {
  install(Vue, options) {
    /**
     * dataCode 数据编码
     * catalogCode 字典编码
     */
    Vue.filter('dictionaryFilter', function(dataCode, catalogCode) {
      /* 如果value为空返回空串*/
      if (!dataCode || !catalogCode) return ''
      dataCode = dataCode.toString()
      var dicobj = store.getters.dictData[catalogCode]
      if (!dicobj) {
        return dataCode
      }
      var text = dicobj[dataCode]
      if (!text) {
        return dataCode
      }
      /* 返回取到的数据字典*/
      return text.dataValue
    })
  }
}
