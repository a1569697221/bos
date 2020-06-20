import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/menu/add',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/menu/delete',
    method: 'get',
    params: { id }
  })
}

export function update(data) {
  return request({
    url: '/menu/update',
    method: 'post',
    data
  })
}

export function list(data) {
  return request({
    url: '/menu/list',
    method: 'post',
    data
  })
}
