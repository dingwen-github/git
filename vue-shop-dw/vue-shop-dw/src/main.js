import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'

// 引入全局样式表
import './assets/global.css'

// 导入字体图标
import './assets/fonts/iconfont.css'

//  导入axios
import axios from 'axios'
//  将axios挂在到Vue的原型对象上，随处可用
Vue.prototype.$http = axios
//  配置请求的根路径
axios.defaults.baseURL = 'https://www.liulongbin.top:8888/api/private/v1/'
//  配置请求拦截器，添加token令牌获取访问数据的权限
axios.interceptors.request.use(config => {
  console.log(config)
  //  必须返回config
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
