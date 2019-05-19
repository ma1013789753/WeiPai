import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/customer/getPage',
    method: 'post',
    data:data
  })
}

// 分享榜单
export function ranking(data) {
  return request({ 
    url: '/customer/ranking',
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/customer/del',
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getById(data) {
  return request({ 
    url: '/customer/getById',
    method: 'post',
    data:data
  })
}



