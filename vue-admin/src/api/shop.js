import request from '@/utils/request'

// 列表
export function list(data) {
  return request({
    url: '/shop/getPage',
    method: 'post',
    data:data
  })
}

// 添加
export function add(data) {
  return request({
    url: '/shop/add',
    method: 'post',
    data:data
  })
}


// 删除
export function del(data) {
  return request({
    url: '/shop/del',
    method: 'post',
    data:data
  })
}

// 根据name获取job
export function getJob(data) {
  return request({
    url: '/job/getByName?jobName='+data,
    method: 'get',
  })
}



