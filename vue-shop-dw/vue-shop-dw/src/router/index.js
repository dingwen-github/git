import Vue from 'vue'
import VueRouter from 'vue-router'
// 导入路由组件
import Login from '../components/Login'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import Users from '../components/user/Users'
Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // 访问根目录也进入Login组件 重定向
  {
    path: '/',
    name: 'Login',
    redirect: '/login'
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/welcome',
    //  children配置子路由
    children: [
      {
        path: '/welcome',
        name: 'Welcome',
        component: Welcome
      },
      {
        path: '/users',
        name: 'Users',
        component: Users
      }
    ]
  }
]

const router = new VueRouter({
  routes
})
//  挂在路由导航守卫
router.beforeEach((to, from, next) => {
  //  to:将要访问的路径
  //  from:从哪一个路径跳转过来
  //  next: next()放行 next('/login'强制跳转)
  if (to.path === '/login') return next()
  //  token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})
export default router
