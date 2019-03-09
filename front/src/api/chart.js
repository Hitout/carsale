import request from '@/utils/request'

export function fetchEmpChart(date) {
  return request({
    url: '/chart/getEmpChart',
    method: 'get',
    params: { date }
  })
}

export function fetchEmpSalesChart(id, date) {
  return request({
    url: '/chart/getEmpSalesChart',
    method: 'get',
    params: { id, date }
  })
}

export function fetchIndexChart() {
  return request({
    url: '/chart/getIndexChart',
    method: 'get'
  })
}

export function fetchSaleNum() {
  return request({
    url: '/chart/getSaleNum',
    method: 'get'
  })
}

export function fetchSalesChart(start, end) {
  return request({
    url: '/chart/getSalesChart',
    method: 'get',
    params: { start, end }
  })
}

export function fetchIndexSales(id) {
  return request({
    url: '/chart/getIndexSales',
    method: 'get',
    params: { id }
  })
}
