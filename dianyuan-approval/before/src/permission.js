import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { Message } from 'element-ui'
import { setToken, getToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress configuration

const whiteList = ['login', 'oauth2'] // 不重定向白名单
if (process.env.CAS_ENABLE) {
  router.beforeEach((to, from, next) => {
    NProgress.start()
    if (to.name === 'cas') {
      store.commit('SET_TOKEN', to.params.token)
      setToken(to.params.token)
      next({ path: '/' })
      NProgress.done()
      return
    }
    if (getToken()) {
      /* 加载用户角色信息*/
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
          next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    } else {
      window.location.href = process.env.BASE_API + '/user/cas/login' // 否则全部重定向到登录页
      NProgress.done()
    }
  })
} else {
  router.beforeEach((to, from, next) => {
    NProgress.start()
    if (getToken()) {
      /* 已经有token登录页面直接到主页*/
      if (to.path === '/login') {
        next({ path: '/' })
        NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
      } else {
        if (store.getters.roles.length === 0) {
          store.dispatch('GetInfo').then(res => { // 拉取用户信息
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          }).catch((err) => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err || 'Verification failed, please login again')
              next({ path: '/' })
            })
          })
        } else {
          next()
        }
      }
    } else {
      if (whiteList.indexOf(to.name) !== -1) {
        next()
      } else {
        next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
        NProgress.done()
      }
    }
  })
}

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
