import request from '@/utils/request'


// 列表
export function list(data) {
  return request({ 
    url: '/taskLog/getPage',
    method: 'post',
    data:data
  })
}

// 列表
export function approve(data) {
  return request({ 
    url: '/taskLog/approve',
    method: 'post',
    data:data
  })
}
// 列表
export function approveFail(data) {
  return request({ 
    url: '/taskLog/approveFail',
    method: 'post',
    data:data
  })
}

