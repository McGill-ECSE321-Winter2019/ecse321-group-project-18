import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import termscreate from '@/components/termscreate'
import studentslist from '@/components/studentslist'
import Login from '@/components/Login'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/terms/create',
      name: 'termscreate',
      component: termscreate
    },
    {
      path: '/students/list',
      name: 'studentslist',
      component: studentslist
    }
  ]
})
