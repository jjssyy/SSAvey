<template>
  <div class="container-set">
    <p class="container-title">설문 공유 설정</p>
    <!-- 검색 -->
    <section class="container-items">
      <div class="item">
        <div class="container-subtitle">
          <p>내가 만든 설문지를</p>
          <p>
            함께 보고 수정/배포 할 수 있는 권한을
          </p>
          <p>줄 회원을 설정하세요.</p>
        </div>
        <div class="search-box">
          <input
            id="search"
            type="search"
            placeholder="이름"
            autocomplete="off"
            v-model="inputSearch"
            @keyup.enter="merberUidSearch"
          />
          <label for="search">
            <i class="fa fa-search" @click="merberUidSearch"></i>
          </label>
        </div>
      </div>
      <div class="item user-list-box">
        <div class="user-list">
          <div
            class="user-item"
            v-for="(element, idx) in searchUsers"
            :key="idx"
            @click="plusSearchTarget(idx)"
          >
            <p>{{ element['name'] }}</p>
            <p v-if="element.hasOwnProperty('generation')">
              {{ element['generation'] + '기' }}/{{ element['area'] }}/{{
                element['group']
              }}
            </p>
            <p>{{ element['position'] }}</p>
          </div>
        </div>
      </div>
    </section>
    <div class="temp"></div>
    <section class="line">
      <hr />
    </section>
    <section>
      <div class="tag-box">
        <ul class="tag-items">
          <li v-for="(element, idx) in targets" :key="idx">
            {{ element }}
            <i class="fas fa-times" @click="cancelTarget(idx)"></i>
          </li>
        </ul>
      </div>
    </section>
    <div class="bottom-btn">
      <button @click="prevSet" class="prev-btn">
        <i class="fas fa-chevron-left fa-lg"></i>
      </button>
      <button @click="nextSet" class="next-btn">
        <i class="fas fa-chevron-right fa-lg"></i>
      </button>
    </div>
  </div>
</template>

<script>
import UserApi from '@/api/UserApi'
export default {
  data() {
    return {
      searchUsers: [],
      targets: [],
      inputSearch: null,
    }
  },
  methods: {
    merberUidSearch() {
      if (this.inputSearch) {
        let payload = `name=${this.inputSearch}`
        UserApi.searchMember(
          payload,
          res => {
            console.log(res)
            if (res.data['검색 결과'].length == 0) {
              console.log('검색결과가 없습니다.')
            } else {
              this.searchUsers = res.data['검색 결과']
            }
          },
          err => {
            console.log(err)
          },
        )
      } else {
        let payload = ''
        UserApi.searchMember(
          payload,
          res => {
            console.log(res)
            if (res.data['검색 결과'].length == 0) {
              console.log('검색결과가 없습니다.')
            } else {
              this.searchUsers = res.data['검색 결과']
            }
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    plusSearchTarget(idx) {
      let tempStr = ''
      let shareTemp = []
      shareTemp.push(this.searchUsers[idx]['uid'])
      this.$store.state.surveySet.survey.share.push(shareTemp)
      if (this.searchUsers[idx]['generation']) {
        tempStr += `${this.searchUsers[idx]['generation']}기/${this.searchUsers[idx]['area']}/${this.searchUsers[idx]['group']} ${this.searchUsers[idx]['name']}`
      } else {
        tempStr += `${this.searchUsers[idx]['position']} ${this.searchUsers[idx]['name']}`
      }
      this.targets.push(tempStr)
    },
    cancelTarget(idx) {
      this.targets.splice(idx, 1)
      this.$store.state.surveySet.survey.share.splice(idx, 1)
    },
    nextSet() {
      this.$emit('nextSet')
    },
    prevSet() {
      this.$emit('prevSet')
    },
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set-two.css"></style>
