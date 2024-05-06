import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query,
    paramsSerializer: {
      serialize: (params) => Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function detailOrder(id) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: { id }
  })
}

export function shipOrder(data) {
  return request({
    url: '/order/ship',
    method: 'post',
    data
  })
}

export function refundOrder(data) {
  return request({
    url: '/order/refund',
    method: 'post',
    data
  })
}

export function payOrder(data) {
  return request({
    url: '/order/pay',
    method: 'post',
    data
  })
}

export function deleteOrder(data) {
  return request({
    url: '/order/delete',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/order/reply',
    method: 'post',
    data
  })
}

export function listChannel(id) {
  return request({
    url: '/order/channel',
    method: 'get'
  })
}

export function deliveryOrder(data) {
  return request({
    url: '/order/delivery',
    method: 'post',
    data
  })
}

export function receiveOrder(orderSn) {
  return request({
    url: '/order/receive/' + orderSn,
    method: 'get'
  })
}

export function orderExpress(orderSn) {
  return request({
    url: '/order/express/' + orderSn,
    method: 'get'
  })
}