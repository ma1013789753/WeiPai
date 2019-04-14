import request from '@/utils/request'

// 列表
export function list(data) {
  return request({
    url: '/${entity}/getPage',
    method: 'post',
    data:data
  })
}


// 添加
export function add(data) {
  return request({
    url: '/${entity}/add',
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({
    url: '/${entity}/del',
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getId(data) {
  return request({
    url: '/${entity}/getId',
    method: 'post',
    data:data
  })
}



