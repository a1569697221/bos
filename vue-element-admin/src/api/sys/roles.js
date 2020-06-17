import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/role/add',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/role/delete',
    method: 'get',
    params: { id }
  })
}

export function update(data) {
  return request({
    url: '/role/update',
    method: 'post',
    data
  })
}

export function list(data) {
  return request({
    url: '/role/list',
    method: 'post',
    data
  })
}
