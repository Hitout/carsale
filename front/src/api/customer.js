import request from '@/utils/request'

export function addCustomer(customer) {
  return request({
    url: '/customer/addCustomer',
    method: 'get',
    params: customer
  })
}

export function fetchList(query) {
  // console.log(query)
  return request({
    url: '/customer/getList',
    method: 'get',
    params: query
  })
}

export function updateCustomer(customer) {
  // console.log(customer)
  return request({
    url: '/customer/update',
    method: 'post',
    params: customer
  })
}
