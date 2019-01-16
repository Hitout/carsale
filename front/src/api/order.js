import request from '@/utils/request'

export function addOrder(orderVo) {
  return request({
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    url: '/order/addOrder',
    method: 'post',
    data: JSON.stringify(orderVo)
  })
}

export function fetchList(query) {
  return request({
    url: '/order/getList',
    method: 'get',
    params: query
  })
}

export function updateStatus(data) {
  return request({
    url: '/order/update',
    method: 'post',
    params: data
  })
}

export function updateDetail(data) {
  return request({
    url: '/order/updateDetail',
    method: 'post',
    params: data
  })
}

export function deleteDetail(data) {
  return request({
    url: '/order/deleteDetail',
    method: 'post',
    params: data
  })
}

export function fetchDetailsList(query) {
  return request({
    url: '/order/getDetailsList',
    method: 'get',
    params: query
  })
}
