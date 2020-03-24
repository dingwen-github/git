import { dictionaryService } from '@/api/dictionary'

/**
 * Copyright (C), 2019/6/1, sunflow开发团队
 * 数据字典全局函数
 *
 * @author   cjy
 * @fileName dictionary.js
 * @date     2019/6/1 11:16
 */
export default {
  install(Vue, options) {
    /**
     * 获取指定数据字典的数据
     * 加载页面需要的所有字典明细集合（先查找前端缓存，若无数据再向后台请求）
     * @param catalogCodes 字典编码数组
     * @author cjy
     * @date 2019/5/26 21:05
     */
    Vue.prototype.storeDictionary = function(catalogCodes) {
      return new Promise((resolve, reject) => {
        const _this = this
        const dictData = this.$store.getters.dictData
        let requestCodes = ''
        // 是否全部已缓存
        var allStored = true
        for (const item of catalogCodes) {
          if (!dictData[item]) {
            requestCodes += item + ','
            allStored = false
          }
        }
        if (allStored) {
          resolve()
        } else {
          requestCodes = requestCodes.substring(0, requestCodes.length - 1)
          dictionaryService.storeDictionary(requestCodes).then(res => {
            const codes = requestCodes.split(',')
            codes.some(function(item, index) {
              _this.$store.dispatch('storeDictionary', { code: item, datas: res.data[item] })
            })
            resolve()
          })
        }
      })
    }
  }
}
