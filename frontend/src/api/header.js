import { instance } from '@/api/index.js'

const usercount = (payload, callback, errorCallback) => {
  instance
    .get(`/main/count/${payload}`)
    .then(res => callback(res))
    .catch(err => errorCallback(err))
}
const headerInfo = {
  usercount: (data, callback, errorCallback) =>
    usercount(data, callback, errorCallback),
}

export default headerInfo
