import { instance } from '@/api/index'

const getCertainStateSurveys = (state, uid, callback, errorCallback) => {
  instance
    .get('/survey/state/' + state + '?uid=' + uid)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const getSurvey = (surveyId, callback, errorCallback) => {
  instance
    .get('/survey/' + surveyId)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const makeSurvey = (payload, callback, errorCallback) => {
  instance
    .post('/survey', payload)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const SurveyApi = {
  getCertainStateSurveys: (state, uid, callback, errorCallback) =>
    getCertainStateSurveys(state, uid, callback, errorCallback),
  getSurvey: (surveyId, callback, errorCallback) =>
    getSurvey(surveyId, callback, errorCallback),
  makeSurvey: (payload, callback, errorCallback) =>
    makeSurvey(payload, callback, errorCallback),
}

export default SurveyApi
