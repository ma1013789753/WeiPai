import request from '@/utils/request'

// 提现列表
export function cashList(data,type) {
  return request({ 
    url: '/flow/getCashPage?type=' + type,
    method: 'post',
    data:data
  })
}

// 提现流水
export function cashFlow(data) {
  return request({
    url: '/flow/getCashFlow',
    method: 'post',
    data:data
  })
}

// 积分充值记录
export function pointsList(data) {
  return request({
    url: '/flow/getPointsList',
    method: 'post',
    data:data
  })
}

// 积分流水
export function pointsFlow(data) {
  return request({
    url: '/flow/getPointsFlow',
    method: 'post',
    data:data
  })
}




