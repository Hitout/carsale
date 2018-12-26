import request from '@/utils/request'
import Qs from 'qs'

export function loginByUsername(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/employee/login',
    method: 'post',
    transformRequest: [
      function(data) {
        return Qs.stringify(data)
      }],
    headers: {
      'deviceCode': 'A95ZEF1-47B5-AC90BF3'
    },
    data
  })
}

export function logout() {
  return request({
    url: '/employee/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/employee/info',
    method: 'get',
    params: { token }
  })
}
