import request from '@/utils/request'

export function fetchOpt() {
  return request({
    url: '/order/seriesOpt',
    method: 'post'
  })
}
