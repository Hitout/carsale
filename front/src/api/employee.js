import request from '@/utils/request'

export function addEmployee(employee) {
  console.log(employee)
  return request({
    url: '/employee/addEmployee',
    method: 'post',
    params: employee
  })
}

export function fetchList(query) {
  console.log(query)
  return request({
    url: '/employee/getList',
    method: 'get',
    params: query
  })
}

export function updateEmployee(employee) {
  console.log(employee)
  return request({
    url: '/employee/update',
    method: 'post',
    params: employee
  })
}
