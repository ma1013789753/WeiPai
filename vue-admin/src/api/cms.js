import request from '@/utils/request'

// 资讯列表
export function list(data) {
  return request({ 
    url: '/cms/getPage',
    method: 'post',
    data:data
  })
}

// 广告列表
export function adlist(data) {
  return request({
    url: '/cms/getAdPage',
    method: 'post',
    data:data
  })
}

// 删除资讯
export function del(data) {
  return request({ 
    url: '/cms/del',
    method: 'post',
    data:data
  })
}

// 根据id获取资讯
export function getById(data) {
  return request({ 
    url: '/cms/getById',
    method: 'post',
    data:data
  })
}

//获取资讯分类
export function getCategory(type) {
  return request({
    url: '/cms/getCategory?type=' + type,
    method: 'post'
  })
}

//更新资讯标签type=0正常更新，1禁用更新，2解禁更新
export function updateTag(data,type) {
  return request({
    url: '/cms/updateTag?type=' + type,
    method: 'post',
    data:data
  })
}

//添加资讯标签
export function addTag(data) {
  return request({
    url: '/cms/addTag',
    method: 'post',
    data:data
  })
}

//更新资讯
export function updateNews(data) {
  return request({
    url: '/cms/updateNews',
    method: 'post',
    data:data
  })
}


