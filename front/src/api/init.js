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

export function fetchStore(seriesId) {
  return request({
    url: '/init/storeOpt',
    method: 'get',
    params: { seriesId }
  })
}

export function fetchCustomer(idCard) {
  return request({
    url: '/init/getCustomer',
    method: 'get',
    params: { idCard }
  })
}
