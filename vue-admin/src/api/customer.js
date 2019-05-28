import request from '@/utils/request'

// 列表
export function list(data) {
  return request({ 
    url: '/customer/getPage',
    method: 'post',
    data:data
  })
}

// 分享榜单
export function ranking(data) {
  return request({ 
    url: '/customer/ranking',
    method: 'post',
    data:data
  })
}

// 冻结
export function freeze(data) {
  return request({
    url: '/customer/freeze',
    method: 'post',
    data:data
  })
}
// 解冻
export function unfreeze(data) {
  return request({
    url: '/customer/unfreeze',
    method: 'post',
    data:data
  })
}

// 删除
export function del(data) {
  return request({ 
    url: '/customer/del',
    method: 'post',
    data:data
  })
}

// 根据id获取
export function getById(data) {
  return request({ 
    url: '/customer/getById',
    method: 'post',
    data:data
  })
}

// 根据id获取签到记录
export function getSignById(data,userId) {
  return request({
    url: '/customer/sign?userId=' + userId,
    method: 'post',
    data:data
  })
}

// 获取用户三方子账号
export function getUserAccount(userId,accType) {
  return request({
    url: '/customer/getAccount?userId=' + userId + '&accType='+ accType,
    method: 'post'
  })
}

// 分类获取三方子账号列表
export function getAccounts(data,accType,accState) {
  return request({
    url: '/customer/accounts?accType=' + accType + '&accState='+ accState,
    method: 'post',
    data:data
  })
}

// 封禁用户三方子账号
export function ban(data) {
  return request({
    url: '/customer/ban',
    method: 'post',
    data:data
  })
}

// 解封用户三方子账号
export function accunbanned(cid) {
  return request({
    url: '/customer/unbanned?cid='+cid,
    method: 'post'
  })
}

// 删除用户三方子账号
export function delAcc(cid) {
  return request({
    url: '/customer/delAcc?accId='+cid,
    method: 'post'
  })
}

//根据id获取三方子账号
export function getAccById(cid) {
  return request({
    url: '/customer/getAcc?accId='+cid,
    method: 'post'
  })
}

//审批拒绝三方子账号
export function rejectAccById(data) {
  return request({
    url: '/customer/rejectAcc',
    method: 'post',
    data:data
  })
}

//审批通过三方子账号
export function passAccById(cid) {
  return request({
    url: '/customer/passAcc?accId='+cid,
    method: 'post'
  })
}

//获取公众号标签列表
export function getTagList() {
  return request({
    url: '/customer/tagList',
    method: 'post'
  })
}

//新增公众号标签
export function addTag(data) {
  return request({
    url: '/customer/addTag',
    method: 'post',
    data:data
  })
}

//更新公众号标签
export function updateTag(data) {
  return request({
    url: '/customer/updateTag',
    method: 'post',
    data:data
  })
}

//删除公众号标签
export function delTag(tid) {
  return request({
    url: '/customer/delTag?tid='+tid,
    method: 'post'
  })
}

//后台充值
export function Recharge(data) {
  return request({
    url: '/customer/recharge',
    method: 'post',
    data:data
  })
}


