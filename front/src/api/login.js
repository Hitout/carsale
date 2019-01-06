import request from '@/utils/request'

export function loginByUsername(employeeId, password) {
  return request({
    url: '/user/login',
    method: 'post',
    params: { employeeId, password }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  console.log('获取用户信息')
  return request({
    url: '/user/info',
    method: 'post',
    params: { token }
  })
}
