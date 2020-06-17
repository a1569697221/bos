import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/user/delete',
    method: 'get',
    params: { id }
  })
}

export function update(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function list(data) {
  return request({
    url: '/user/list',
    method: 'post',
    data
  })
}
