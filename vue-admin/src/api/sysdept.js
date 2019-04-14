import request from '@/utils/request'


//所有部门
export function all(data) {
  return request({ 
    url: '/sysDept/all', 
    method: 'post',
    data:data
  })
}

// 列表
export function list(data) {
  return request({ 
    url: '/sysDept/getPage', 
    method: 'post',
    data:data
  })
}

// 添加
export function add(data) {
  return request({ 
    url: '/sysDept/add', 
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/sysDept/del', 
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getId(data) {
  return request({ 
    url: '/sysDept/getId', 
    method: 'post',
    data:data
  })
}


