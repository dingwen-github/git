import request from '@/utils/flowRequest'
export const tasksService = {
  list: function(params) {
    return request({
      url: '/flow/rest/task',
      method: 'get',
      params
    })
  },
  count: function(params) {
    return request({
      url: '/flow/rest/task/count',
      method: 'get',
      params
    })
  },
  complete: function(id, params) {
    return request.post('/flow/rest//task/' + id + '/complete', params)
  }
}

