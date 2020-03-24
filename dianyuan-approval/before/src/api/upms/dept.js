import request from '@/utils/request'

export const deptService = {
  list: function(params) {
    if (!params) {
      params = {}
      params.name = ''
    }
    return request({
      url: '/dept/list',
      method: 'get',
      params
    }, { indices: false })
  },
  edit: function(params) {
    return request({
      url: '/dept/info',
      method: 'get',
      params
    })
  },
  delete: function(params) {
    return request({
      url: '/dept/delete/' + params.roleCode,
      method: 'get'
    })
  },
  save: function(params) {
    return request({
      url: '/dept/save',
      method: 'post',
      data: params
    })
  }
}

