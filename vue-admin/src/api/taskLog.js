import request from '@/utils/request'


// 列表
export function list(data) {
  return request({ 
    url: '/taskLog/getPage',
    method: 'post',
    data:data
  })
}

