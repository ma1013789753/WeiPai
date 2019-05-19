import { login, logincheck, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken,getOauth, setOauth, removeOauth } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    nickname: '',
    avatar: '',
    uid: '',
    user: {},
    roles: [],
    menus: [], // 菜单权限
    buttons: [], // 安装权限
    authtype: '', //第三方验证类型
    oauth: getOauth()
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INFO: (state, user) => {
      state.nickname = user.nickname
      state.avatar = user.avatar
      state.uid = user.uid
      state.user = user
    },
    SET_ROLES: (state, roles) => {
      state.roles.push(roles)
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    },
    SET_BUTTONS: (state, buttons) => {
      state.buttons = buttons
    },
    SET_AUTHTYPE: (state, authtype) => {
      state.authtype = authtype
    },
    SET_OAUTH: (state, oauth) => {
      state.oauth = oauth
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(res => {
          setToken(res.data)
          commit('SET_TOKEN', res.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 第三方授权验证
    LoginCheck({ commit }, platform) {
      const codevalue = platform.code
      return new Promise((resolve, reject) => {
        logincheck( platform.type,codevalue).then(res => {
          setOauth(res.data)
          commit('SET_OAUTH', res.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(res => {
          const data = res.data
          if (data.roles) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_MENUS', data.menus)
          commit('SET_BUTTONS', data.buttons)
          // 设置用户信息
          commit('SET_INFO', data)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_INFO', '')
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_OAUTH', '')
          removeToken()
          removeOauth()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_OAUTH', '')
        removeToken()
        removeOauth()
        resolve()
      })
    }
  }
}

export default user
