import request from '@/utils/flowRequest'

export const processService = {
  start: function(params) {
    return request.post('/flow/rest/process-definition/' + params.processDefinitionId + '/start', params)
  }
}

