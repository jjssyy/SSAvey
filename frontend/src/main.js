import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index'
import vuetify from './plugins/vuetify'
import VueSweetalert2 from 'vue-sweetalert2'
import './css/common/sweetalert2.min.css'
import './css/login/styles.scss'
import store from './store/index'
import VueApexCharts from 'vue-apexcharts'

Vue.config.productionTip = false
Vue.use('apexchart', VueApexCharts)
Vue.use(VueSweetalert2)
new Vue({
  render: h => h(App),
  vuetify,
  router,
  store,
  // vueDragAndDropList,
}).$mount('#app')

router.beforeEach((to, from, next) => {
  if (to.meta.auth && store.state.uid == '') {
    next('/login')
    return
  } else if (!to.meta.auth && store.state.uid != '') {
    router.push('/main')
  } else {
    next()
  }
})
