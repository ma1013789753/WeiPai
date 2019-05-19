import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/Api/Share/getPage', 
    method: 'post',
    data:data
  })
}
//推荐
export function tuijian(data) {
  return request({ 
    url: '/Api/Share/tuiJian', 
    method: 'post',
    data:data
  })
}

// 添加
export function add(data) {
  return request({ 
    url: '/spare/add', 
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/spare/del', 
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getId(data) {
  return request({ 
    url: '/spare/getId', 
    method: 'post',
    data:data
  })
}



