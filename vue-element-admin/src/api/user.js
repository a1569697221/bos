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

export function login(data) {
  return request({
    // 指定好要提交后台的URL
    url: '/user/login',
    // 提交的方式
    method: 'post',
    // 参数
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
