import request from '@/utils/flowRequest'

export const modelService = {
  list: function(params) {
    if (!params) {
      params = {}
      params.name = ''
    }
    return request({
      url: '/flow/process-api/repository/models',
      method: 'get',
      params
    }, { indices: false })
  },
  setEditorSrc: function(xml, filename, modelId) {
    const param = new FormData()
    param.append('file', new Blob([xml], { type: 'text/xml' }), filename)
    return request.put('/flow/process-api/repository/models/' + modelId + '/source', param, { headers: { 'Content-Type': 'multipart/form-data' }})
  },
  getEditorSrc: function(modelId) {
    return request({
      url: '/flow/process-api/repository/models/' + modelId + '/source',
      method: 'get'
    })
  },
  save: function(params) {
    if (params.id) {
      return request.put('/flow/process-api/repository/models/' + params.id, params)
    } else {
      return request.post('/flow/process-api/repository/models', params)
    }
  },
  delete: function(id) {
    return request.delete('/flow/process-api/repository/models/' + id)
  }
}

