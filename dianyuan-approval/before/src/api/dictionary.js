import request from '@/utils/request'

export const dictionaryService = {
  list: function(params) {
    return request({
      url: '/dictionary/list',
      method: 'get',
      params
    }, { indices: false })
  },
  create: function(params) {
    return request({
      url: '/dictionary/create',
      method: 'post',
      data: params
    })
  },
  update: function(params) {
    return request({
      url: '/dictionary/update',
      method: 'post',
      data: params
    })
  },
  delete: function(id) {
    return request({
      url: '/dictionary/delete/' + id,
      method: 'post'
    })
  },
  listDetail: function(id) {
    return request({
      url: '/dictionary/listDetail/' + id,
      method: 'get'
    })
  },
  createDetail: function(params) {
    return request({
      url: '/dictionary/createDetail',
      method: 'post',
      data: params
    })
  },
  updateDetail: function(params) {
    return request({
      url: '/dictionary/updateDetail',
      method: 'post',
      data: params
    })
  },
  deleteDetail: function(params) {
    return request({
      url: '/dictionary/deleteDetail/' + params.catalogCode + '/' + params.dataCode,
      method: 'post'
    })
  },
  /**
   * 获取指定数据字典的数据
   * @param catalogCodes 字典编码(多个编码以','分隔)
   * @return 字典明细集合
   * @author cjy
   * @date 2019/5/26 21:05
   */
  storeDictionary: function(catalogCodes) {
    return request({
      url: '/dictionary/getDetailSetByCatalogcodes',
      method: 'get',
      params: { 'catalogCodes': catalogCodes }
    })
  },
  /**
   * 检查字典编码是否存在
   * @param catalogCode 字典编码
   * @return 是否存在
   * @author cjy
   * @date 2019/5/26 21:05
   */
  checkCatalogCodeExist: function(catalogCode) {
    return request({
      url: '/dictionary/hasCatalogcode/' + catalogCode,
      method: 'get'
    })
  }
}

