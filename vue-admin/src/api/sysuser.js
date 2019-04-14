import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/sysUser/getPage', 
    method: 'post',
    data:data
  })
}

// 添加
export function add(data) {
  return request({ 
    url: '/sysUser/add', 
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/sysUser/del', 
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getId(data) {
  return request({ 
    url: '/sysUser/getId', 
    method: 'post',
    data:data
  })
}

// 重置密码
export function reset(data) {
  return request({ 
    url: '/sysUser/reset', 
    method: 'post',
    data:data
  })
}


