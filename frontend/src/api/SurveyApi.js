import { instance } from '@/api/index'

const getCertainStateSurveys = (
  state,
  uid,
  pageNumber,
  callback,
  errorCallback,
) => {
  instance
    .get('/survey/state/' + state + '?uid=' + uid + '&pageNumber=' + pageNumber)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}
const getMysurvey = (state, uid, pageNumber, callback, errorCallback) => {
  instance
    .get('/my-survey/' + uid + '/state/' + state + '/' + pageNumber)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const getSurvey = (surveyId, callback, errorCallback) => {
  instance
    .get('/survey/' + surveyId)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const deleteSurvey = (sid, callback, errorCallback) => {
  instance
    .delete(`/survey/` + sid)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const getTemplateSurvey = (
  tid,
  start_date,
  end_date,
  callback,
  errorCallback,
) => {
  instance
    .get(
      '/compare/' + tid + '?start_date=' + start_date + '&end_date=' + end_date,
    )
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const makeSurvey = (payload, callback, errorCallback) => {
  instance
    .post('/survey', payload)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const loadSurveyResult = (payload, callback, errorCallback) => {
  instance
    .get(`/survey-result/${payload}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const alarmSurveyResult = (payload, callback, errorCallback) => {
  instance
    .post(`/survey-result/${payload.sid}/alarm/${payload.uid}`, payload.data)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const SurveyApi = {
  getCertainStateSurveys: (state, uid, pageNumber, callback, errorCallback) =>
    getCertainStateSurveys(state, uid, pageNumber, callback, errorCallback),
  getSurvey: (surveyId, callback, errorCallback) =>
    getSurvey(surveyId, callback, errorCallback),
  makeSurvey: (payload, callback, errorCallback) =>
    makeSurvey(payload, callback, errorCallback),
  getMysurvey: (state, uid, pageNumber, callback, errorCallback) =>
    getMysurvey(state, uid, pageNumber, callback, errorCallback),
  loadSurveyResult: (payload, callback, errorCallback) =>
    loadSurveyResult(payload, callback, errorCallback),
  alarmSurveyResult: (payload, callback, errorCallback) =>
    alarmSurveyResult(payload, callback, errorCallback),
  getTemplateSurvey: (tid, start_date, end_date, callback, errorCallback) =>
    getTemplateSurvey(tid, start_date, end_date, callback, errorCallback),
  deleteSurvey: (sid, callback, errorCallback) =>
    deleteSurvey(sid, callback, errorCallback),
}

export default SurveyApi
