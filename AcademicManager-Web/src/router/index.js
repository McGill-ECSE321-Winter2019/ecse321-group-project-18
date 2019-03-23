import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import termscreate from '@/components/termscreate'
import studentslist from '@/components/studentslist'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
