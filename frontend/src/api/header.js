import { instance } from '@/api/index.js'

function headerInfo(uid) {
  return instance
    .get(`/main/count/${uid}`)
    .then(res => {
      return res
    })
    .catch(() => {
      return 'error'
    })
}

export { headerInfo }
