import request from '@/utils/request'

// 列表
export function list(data,type) {
  return request({ 
    url: '/Api/Share/getPage?type='+type,
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

// 查看互推
export function getShare(data) {
  return request({
    url: '/Api/Share/getShare',
    method: 'post',
    data:data
  })
}

// 获取互推标签
export function getShareTag() {
  return request({
    url: '/Api/Share/getShareTag',
    method: 'post'
  })
}

//更新互推标签
export function updateTag(data) {
  return request({
    url: '/Api/Share/updateTag',
    method: 'post',
    data:data
  })
}

//添加互推标签
export function addTag(data) {
  return request({
    url: '/Api/Share/addTag',
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



