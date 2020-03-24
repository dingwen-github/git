import request from '@/utils/flowRequest'

export const ossService = {
  upload: function(file) {
    const param = new FormData()
    param.append('file', file)
    return request.post('/dept/put', param, { headers: { 'Content-Type': 'multipart/form-data' }})
  }
}

