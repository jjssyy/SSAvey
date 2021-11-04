import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    uid: '',
    isSignUp: null,
    email: '',
  },
  // 값 가져오기
  getters: {
    getUid(state) {
      return state.uid
    },
    getIsSignUp(state) {
      return state.isSignUp
    },
  },
  // 값 넣기
  mutations: {
    setUid(state, uid) {
      state.uid = uid
    },
    setEmail(state, email) {
      state.email = email
    },
    setIsSignUp(state, isSignUp) {
      state.isSignUp = isSignUp
    },
  },
  actions: {},
})
