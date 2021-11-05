import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    uid: '',
    isSignUp: null,
    email: '',
    user: {},
  },
  // 값 가져오기
  getters: {
    getUid(state) {
      return state.uid
    },
    getIsSignUp(state) {
      return state.isSignUp
    },
    getUser(state) {
      return state.user
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
    setUser(state, user) {
      state.user = user
    },
  },
  actions: {},
  plugins: [createPersistedState()],
})
