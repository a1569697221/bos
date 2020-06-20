import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/log/byId',
    method: 'post',
    data
  })
}
