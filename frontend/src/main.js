import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index'
import vuetify from './plugins/vuetify'
// import vueDragAndDropList from 'vue-drag-and-drop-list'
import './css/login/styles.scss'
import store from './store/index'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  vuetify,
  router,
  store,
  mode: 'history',
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
