import { instance } from '@/api/index'

const getSurveysByStatus = (data, callback, errorCallback) => {
  instance
    .get(`/main/${data.id}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}

const main = {
  getSurveysByStatus: (data, callback, errorCallback) =>
    getSurveysByStatus(data, callback, errorCallback),
}

export default main
