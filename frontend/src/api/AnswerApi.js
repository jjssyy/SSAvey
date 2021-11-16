import { instance } from '@/api/index'

const checkAssignedSurveyUser = (data, callback, errorCallback) => {
  instance
    .get(`/answer/check/${data.id}/${data.sid}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const saveSurveyResponse = (payload, callback, errorCallback) => {
  instance
    .post(`/answer/${payload.id}/${payload.result.sid}`, payload.result)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const loadSurveyResponse = (payload, callback, errorCallback) => {
  instance
    .get(`/answer/${payload.id}/${payload.sid}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const AnswerApi = {
  checkAssignedSurveyUser: (data, callback, errorCallback) =>
    checkAssignedSurveyUser(data, callback, errorCallback),
  saveSurveyResponse: (payload, callback, errorCallback) =>
    saveSurveyResponse(payload, callback, errorCallback),
  loadSurveyResponse: (payload, callback, errorCallback) =>
    loadSurveyResponse(payload, callback, errorCallback),
}
export default AnswerApi
