import request from '@/utils/request'

export function add(params) {
  return request({
    url: '/task/add',
    method: 'post',
    params
  })
}

// 列表
export function list(data) {
  return request({ 
    url: '/task/getPage',
    method: 'post',
    data:data
  })
}

export function getAccount(params) {
  return request({
    url: '/task/getAccount',
    method: 'post',
    params
  })
}

// 根据id获取
export function getId(data) {
  return request({ 
    url: '/task/getId', 
    method: 'post',
    data:data
  })
}

