import request from '@/utils/request'

// 列表
export function list() {
  return request({ 
    url: '/job/getList',
    method: 'get'
  })
}

// 添加
export function add(data) {
  return request({ 
    url: '/job/add',
    method: 'post',
    data:data
  })
}

// run
export function run(data) {
  return request({
    url: '/job/run',
    method: 'post',
    data:data
  })
}

// pause
export function pause(data) {
  return request({
    url: '/job/pause',
    method: 'post',
    data:data
  })
}

// resume
export function resume(data) {
  return request({
    url: '/job/resume',
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/job/delete',
    method: 'post',
    data:data
  })
}

// 根据name获取job
export function getJob(jobName) {
  return request({ 
    url: '/job/getByName',
    method: 'post',
    data:{jobName}
  })
}



