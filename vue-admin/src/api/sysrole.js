import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/sysRole/getPage', 
    method: 'post',
    data:data
  })
}

// 列表
export function allRole(data) {
  return request({ 
    url: '/sysRole/getAll', 
    method: 'post',
    data:data
  })
}

// 添加
export function add(data) {
  return request({ 
    url: '/sysRole/add', 
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/sysRole/del', 
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getId(data) {
  return request({ 
    url: '/sysRole/getId', 
    method: 'post',
    data:data
  })
}

// 保存权限
export function setRole(data) {
  return request({ 
    url: '/sysRole/setRole', 
    method: 'post',
    data:data
  })
}


