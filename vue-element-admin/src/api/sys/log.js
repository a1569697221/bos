import request from '@/utils/request'

export function del(id) {
  return request({
    url: '/log/del',
    method: 'get',
    params: { id }
  })
}

export function list(data) {
  return request({
    url: '/log/list',
    method: 'post',
    data
  })
}  

export function byId(data) {
  return request({
    url: '/log/byId',
    method: 'post',
    data
  })
}
