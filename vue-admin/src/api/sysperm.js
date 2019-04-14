import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/sysPerm/getPage', 
    method: 'post',
    data:data
  })
}

// 列表
export function getMenuVo(data) {
  return request({ 
    url: '/sysPerm/getMenuVo', 
    method: 'post',
    data:data
  })
}


// 添加
export function edit(data) {
  return request({ 
    url: '/sysPerm/edit', 
    method: 'post',
    data:data
  })
}



