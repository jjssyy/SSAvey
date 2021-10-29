import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from '@/views/LoginPage.vue'
import SelectLoginPage from '@/views/SelectLoginPage.vue'
import SurveyEditPage from '@/views/SurveyEditPage.vue'

Vue.use(VueRouter)

export default new VueRouter({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginPage,
    },
    {
      path: '/select',
      name: 'Select',
      component: SelectLoginPage,
    },
    {
      path: '/surveyedit',
      name: 'SurveyEdit',
      component: SurveyEditPage,
    },
  ],
})
