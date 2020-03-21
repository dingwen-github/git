import Vue from 'vue'
import { Button, Input, FormItem, Form, Message } from 'element-ui'
//  导入弹框提示组件 Message
Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
//  将Message全局挂在到Vue的原型对象上 在每一个组件上面都能使用
Vue.prototype.$message = Message
