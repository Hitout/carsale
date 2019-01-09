import request from '@/utils/request'

export function addOrder(orderVo) {
  console.log(orderVo)
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
