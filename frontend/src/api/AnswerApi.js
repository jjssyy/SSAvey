import { instance } from '@/api/index'

const checkAssignedSurveyUser = (data, callback, errorCallback) => {
  instance
    .get(`/answer/check/${data.id}/${data.sid}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const AnswerApi = {
  checkAssignedSurveyUser: (data, callback, errorCallback) =>
    checkAssignedSurveyUser(data, callback, errorCallback),
}
export default AnswerApi
