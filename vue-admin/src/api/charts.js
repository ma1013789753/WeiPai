import request from '@/utils/request'

//平台最近一年注册用户
export function getRegister() {
  return request({ 
    url: '/charts/getRegisterReport',
    method: 'post'
  })
}

//平台最近一年任务发布量
export function getShare() {
  return request({
    url: '/charts/getShareReport',
    method: 'post'
  })
}

//平台最近一年任务转发量
export function getForward() {
  return request({
    url: '/charts/getForwardReport',
    method: 'post'
  })
}



