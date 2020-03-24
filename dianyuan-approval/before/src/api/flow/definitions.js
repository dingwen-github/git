import request from '@/utils/flowRequest'
export const definitionsService = {
  list: function(params) {
    if (!params) {
      params = {}
      params.name = ''
    }
    return request({
      url: '/flow/rest/process-definition',
      method: 'get',
      params
    })
  },
  count: function(params) {
    return request({
      url: '/flow/rest/process-definition/count',
      method: 'get',
      params
    })
  },
  getBPMNXML: function(id) {
    return request({
      url: '/flow/rest//process-definition/' + id + '/xml',
      method: 'get'
    })
  }

}

