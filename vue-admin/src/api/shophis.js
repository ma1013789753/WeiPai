import request from '@/utils/request'

// 列表
export function list(data) {
  return request({
    url: '/shop-his/getPage',
    method: 'post',
    data:data
  })
}

// 添加
export function approve(data) {
  return request({
    url: '/shop-his/approve',
    method: 'post',
    data:data
  })
}


// 删除
export function fail(data) {
  return request({
    url: '/shop-his/fail',
    method: 'post',
    data:data
  })
}




