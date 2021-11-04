import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from '@/views/LoginPage.vue'
import SelectLoginPage from '@/views/SelectLoginPage.vue'
import MainPage from '@/views/MainPage.vue'
import SurveySetPage from '@/views/SurveySetPage.vue'
import SurveyExpected from '@/views/SurveyExpected.vue'
import SurveyProceeding from '@/views/SurveyProceeding.vue'
import SurveyCompleted from '@/views/SurveyCompleted.vue'
import SettingPage from '@/views/SettingPage.vue'
Vue.use(VueRouter)

export default new VueRouter({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginPage,
      meta: { auth: false },
    },
    {
      path: '/select',
      name: 'SelectLoginPage',
      component: SelectLoginPage,
      meta: { auth: true },
    },
    {
      path: '/main',
      name: 'MainPage',
      component: MainPage,
      meta: { auth: true },
    },
    {
      path: '/surveyset',
      name: 'SurveySetPage',
      component: SurveySetPage,
      meta: { auth: true },
    },
    {
      path: '/survey/state/expected',
      name: 'SurveyExpected',
      component: SurveyExpected,
    },
    {
      path: '/survey/state/proceeding',
      name: 'SurveyProceeding',
      component: SurveyProceeding,
    },
    {
      path: '/survey/state/completed',
      name: 'SurveyCompleted',
      component: SurveyCompleted,
    },
    {
      path: '/setting',
      name: 'SettingPage',
      component: SettingPage,
    },
  ],
})
