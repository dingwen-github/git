import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { constructorRouters } from '@/utils/routerFactory'
import { constantRouterMap } from '@/router'
const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    /* 用于注册router*/
    addRouters: [],
    /* 用于渲染菜单*/
    routers: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_ADDROUTERS: (state, addRouters) => {
      state.addRouters = addRouters
      state.routers = constantRouterMap.concat(addRouters)
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          setToken(data.token)
          commit('SET_TOKEN', data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const data = response.data
          if (data !== null) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
            var routers = constructorRouters(data.addRouters)
            routers.push({ path: '*', redirect: '/404', hidden: true })
            commit('SET_ADDROUTERS', routers)
          } else {
            reject('请重新登录')
          }
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        if (process.env.CAS_ENABLE) {
          var caswin = window.open('http://localhost:8080/cas/logout?service=http%3A%2F%2Flocalhost%2Fcallback%3Fclient_name%3DupmsClient')
          caswin.close()
          window.location.href = process.env.BASE_API + '/logout'
        } else {
          logout(state.token).then(() => {
            commit('SET_TOKEN', '')
            commit('SET_ROLES', [])
            removeToken()
            resolve()
          }).catch(error => {
            reject(error)
          })
        }
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
