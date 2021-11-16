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
    selectedTemplate: {},
    surveySet: {
      isWriting: false,
      isClkUpdate: false,
      survey: {},
    },
    surveyDragThing: [],
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
    getSelectedTemplate(state) {
      return state.selectedTemplate
    },
    getSurveySet(state) {
      return state.surveySet
    },
    getSurveyDragThing(state) {
      return state.surveyDragThing
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
    setSelectedTemplate(state, template) {
      state.selectedTemplate = template
    },
    setSurveySet(state, payload) {
      state.surveySet = payload
    },
    setIsClkUpdate(state, payload) {
      state.surveySet.isClkUpdate = payload
    },
    setSurveyDragThing(state, payload) {
      state.surveyDragThing.push(payload)
    },
    resetSurveyDragThing(state, payload) {
      state.surveyDragThing = payload
    },
    logout(state) {
      state.uid = null
      state.isSignUp = null
      state.email = null
      state.user = {}
      state.selectedTemplate = {}
      state.surveySet = {
        isWriting: false,
        isClkUpdate: false,
        survey: {},
      }
      state.surveyDragThing = []
    },
  },
  actions: {},
  plugins: [createPersistedState()],
})
