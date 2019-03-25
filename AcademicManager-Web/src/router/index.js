import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import internships from '@/components/internships'
import students from '@/components/students'
import courses from '@/components/courses'
import developer from '@/components/developer-only'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
    },
    {
      path: '/developer-only',
      name: 'developer-only',
      component: developer
    }
  ]
})
