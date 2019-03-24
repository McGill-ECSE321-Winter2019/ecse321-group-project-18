import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import internships from '@/components/internships'
import students from '@/components/students'
import courses from '@/components/courses'

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
      path: '/internships',
      name: 'internships',
      component: internships
    },
    {
      path: '/students',
      name: 'students',
      component: students
    },
    {
      path: '/courses',
      name: 'courses',
      component: courses
    }
  ]
})
