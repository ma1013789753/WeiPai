import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/shareCLog/getPage',
    method: 'post',
    data:data
  })
}

// 通过
export function approve(data) {
  return request({ 
    url: '/shareCLog/approve',
    method: 'post',
    data:data
  })
}

// 失败
export function approveFail(data) {
  return request({ 
    url: '/shareCLog/approveFail',
    method: 'post',
    data:data
  })
}


