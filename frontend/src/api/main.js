import { instance } from '@/util/index'

// 특정 일기 불러오기 API
function fetchDiary(uid, data) {
  return instance.get(`/diary/${uid}/${data['date']}`)
}

// 학습 노트 데이터를 생성하는 API
function createPost(postData) {
  return posts.post('/', postData)
}

export { fetchDiary, createPost }
