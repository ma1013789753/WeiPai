import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/shareLog/getPage',
    method: 'post',
    data:data
  })
}

// 通过
export function approve(data) {
  return request({ 
    url: '/shareLog/approve',
    method: 'post',
    data:data
  })
}

// 失败
export function approveFail(data) {
  return request({ 
    url: '/shareLog/approveFail',
    method: 'post',
    data:data
  })
}


