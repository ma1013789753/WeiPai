import request from '@/utils/request'

//提现申请数
export function withdrawCount() {
  return request({ 
    url: '/notice/getWithdrawCount',
    method: 'post'
  })
}

//原创申请数
export function originalShareCount() {
  return request({
    url: '/notice/getOriginalShareCount',
    method: 'post'
  })
}

//待审核微博/头条/微信/公众号数
export function platformAccCount(type) {
  return request({
    url: '/notice/getPlatformAccCount?type=' + type,
    method: 'post'
  })
}

//待处理意见反馈数
export function getPendingFeedback() {
  return request({
    url: '/notice/getPendingFeedback',
    method: 'post'
  })
}

//获取系统消息
export function systemMsg(data) {
  return request({
    url: '/notice/getSysMsg',
    method: 'post',
    data:data
  })
}

// 删除消息
export function delMsg(data) {
  return request({
    url: '/notice/delMsg',
    method: 'post',
    data:data
  })
}

//发布消息
export function addMsg(data) {
  return request({
    url: '/notice/addMsg',
    method: 'post',
    data:data
  })
}

//用户反馈
export function getFeedback(data) {
  return request({
    url: '/notice/getFeedback',
    method: 'post',
    data:data
  })
}

//处理用户反馈
export function handleFeedback(data) {
  return request({
    url: '/notice/handleFeedback',
    method: 'post',
    data:data
  })
}
