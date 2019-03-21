import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Students from '@/components/Students'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/students',
      name: 'Students',
      component: Students
    }
  ]
})
