import request from '@/utils/request'

export const roleService = {
  list: function(params) {
    if (params.createDate) {
      params.createDate = ''
    }
    return request({
      url: '/role/list',
      method: 'get',
      params
    }, { indices: false })
  },
  edit: function(params) {
    return request({
      url: '/role/info',
      method: 'get',
      params
    })
  },
  delete: function(params) {
    return request({
      url: '/role/delete/' + params.roleCode,
      method: 'get'
    })
  },
  save: function(params) {
    return request({
      url: '/role/save',
      method: 'post',
      data: params
    })
  },
  listPerAndRouter: function(params) {
    return request({
      url: '/role/listPerAndRouter/' + params.roleCode,
      method: 'get'
    })
  },
  listAllRoles: function() {
    return request({
      url: '/role/all',
      method: 'get'
    })
  },
  getRoutes: function() {
    return request({
      url: '/router/list',
      method: 'get'
    })
  }
}

