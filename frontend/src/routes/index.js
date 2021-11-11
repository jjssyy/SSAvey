import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from '@/views/LoginPage.vue'
import SelectLoginPage from '@/views/SelectLoginPage.vue'
import MainPage from '@/views/MainPage.vue'
import SurveyPage from '@/views/SurveyPage.vue'
import SurveySetPage from '@/views/SurveySetPage.vue'
import SurveyExpected from '@/views/SurveyExpected.vue'
import SurveyProceeding from '@/views/SurveyProceeding.vue'
import SurveyCompleted from '@/views/SurveyCompleted.vue'
import MySurveyExpected from '@/views/MySurveyExpected.vue'
import MySurveyProceeding from '@/views/MySurveyProceeding.vue'
import MySurveyCompleted from '@/views/MySurveyCompleted.vue'
import SurveyAnswer from '@/views/SurveyAnswer.vue'
import SettingPage from '@/views/SettingPage.vue'
import Profile from '@/views/Profile.vue'
import Template from '@/components/SurveyEdit/Template.vue'
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
      path: '/survey',
      name: 'SurveyPage',
      component: SurveyPage,
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
      meta: { auth: true },
    },
    {
      path: '/survey/state/proceeding',
      name: 'SurveyProceeding',
      component: SurveyProceeding,
      meta: { auth: true },
    },
    {
      path: '/survey/state/completed',
      name: 'SurveyCompleted',
      component: SurveyCompleted,
      meta: { auth: true },
    },
    {
      path: '/my-survey/state/expected',
      name: 'MySurveyExpected',
      component: MySurveyExpected,
      meta: { auth: true },
    },
    {
      path: '/my-survey/state/proceeding',
      name: 'MySurveyProceeding',
      component: MySurveyProceeding,
      meta: { auth: true },
    },
    {
      path: '/my-survey/state/completed',
      name: 'MySurveyCompleted',
      component: MySurveyCompleted,
      meta: { auth: true },
    },
    {
      path: '/answer/survey',
      name: 'SurveyAnswer',
      component: SurveyAnswer,
      meta: { auth: true },
    },
    {
      path: '/setting',
      name: 'SettingPage',
      component: SettingPage,
      meta: { auth: true },
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      meta: { auth: true },
    },
    {
      path: '/templete',
      name: 'Templete',
      component: Template,
      meta: { auth: true },
    },
  ],
})
