import request from '@/utils/request'

// 登录
export function login(username, password) {
  return request({ url: '/auth/login', method: 'post', data: { username, password }})
}

// 登录验证
export function logincheck(platform, code,) {
  return request({ url: '/auth/logincheck', method: 'post', data: { platform, code,}})
}

// 获取用户信息
export function getInfo(token) {
  return request({ url: '/sysUser/info', method: 'get' })
}

// 登出
export function logout() {
  return request({ url: '/auth/logout', method: 'post' })
}
