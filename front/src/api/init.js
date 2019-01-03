import request from '@/utils/request'

export function fetchSeries() {
  return request({
    url: '/init/seriesOpt',
    method: 'get'
  })
}

export function fetchBrand() {
  return request({
    url: '/init/brandOpt',
    method: 'get'
  })
}
