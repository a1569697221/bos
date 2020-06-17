import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
import notFound from '../views/404'
import serviceError from '../views/500'

Vue.use(Router)
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },{
      path: '/error/404',
      name: 'notFound',
      component: notFound
    },
    ,{
      path: '/error/500',
      name: 'serviceError',
      component: serviceError
    },
    {
      path: '/',
      redirect: '/login'
    }
  ]
})
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
