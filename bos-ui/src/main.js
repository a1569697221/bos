// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import api from './http/index'

Vue.config.productionTip = false
Vue.use(api) //使用自己的插件
Vue.use(ElementUI) // 使用ElementUI

new Vue({
  el: '#app',
  router,
 render: h => h(App)
})
