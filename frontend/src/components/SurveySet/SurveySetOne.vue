<template>
  <div class="container-set">
    <p class="container-title">설문대상자 선택하기</p>
    <!-- 선택 -->
    <div class="content-menu">
      <button
        class="content-menu-item"
        @click="clkSelectMenu"
        :class="{ 'btn-active': isClkSelectMenu }"
      >
        선택
      </button>
      <button
        class="content-menu-item"
        @click="clkSearchMenu"
        :class="{ 'btn-active': isClkSearchMenu }"
      >
        검색
      </button>
    </div>
    <section class="container-items scroll-y" v-show="isClkSelectMenu">
      <div class="item">
        <p class="container-subtitle">직책</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="position"
            v-for="(element, idx) in positions"
            :key="idx"
            @click="selectPosition(element)"
          >
            {{ element }}
          </button>
        </ul>
      </div>
      <div class="item" v-if="isOpenStudent">
        <p class="container-subtitle">기수</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="generation"
            v-for="(element, idx) in generations"
            :key="idx"
            @click="selectGeneration(element)"
          >
            {{ element == '전체' ? element : element + '기' }}
          </button>
        </ul>
      </div>
      <div class="item" v-if="isOpenStudent">
        <p class="container-subtitle">지역</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="area"
            v-for="(element, idx) in areas"
            :key="idx"
            @click="selectArea(element)"
          >
            {{ element }}
          </button>
        </ul>
      </div>
      <div class="item" v-if="isOpenStudent">
        <p class="container-subtitle">분반</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="class"
            v-for="(element, idx) in classes"
            :key="idx"
            @click="selectClass(element)"
          >
            {{ element }}
          </button>
        </ul>
      </div>
      <div class="item" v-if="isOpenStudent">
        <p class="container-subtitle">팀</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="team"
            v-for="(element, idx) in teams"
            :key="idx"
            @click="selectTeam(element)"
          >
            {{ element }}
          </button>
        </ul>
      </div>
      <div class="item" v-if="isOpenStudent">
        <p class="container-subtitle">역할</p>
        <ul class="select-box" @click="colorEvent">
          <button
            id="teamroll"
            v-for="(element, idx) in Team_rolls"
            :key="idx"
            @click="selectTeamRoll(element)"
          >
            {{ element }}
          </button>
        </ul>
      </div>
    </section>

    <!-- 검색 -->
    <section class="container-items" v-show="isClkSearchMenu">
      <div class="item">
        <p class="container-subtitle">대상자 검색</p>
        <div class="user-filter">
          <span v-if="target['position']">{{ target['position'] }}</span>
          <span v-if="target['generation']">{{ target['generation'] }}</span>
          <span v-if="target['area']">{{ target['area'] }}</span>
          <span v-if="target['class']">{{ target['class'] }}</span>
          <span v-if="target['team_roll']">{{ target['team_roll'] }}</span>
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
          <div class="user-none-item" v-if="isNoneUser">
            <p>검색결과가 없습니다.</p>
          </div>
        </div>
      </div>
    </section>
    <div class="temp" v-show="isClkSearchMenu"></div>
    <div class="item-plus" v-show="isClkSelectMenu">
      <div class="item-plus-box" @click="plusTarget('select')">
        <i class="fas fa-plus"></i>
        <button>추가</button>
      </div>
    </div>
    <section class="line">
      <hr />
    </section>
    <section class="tag-container">
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
      <button class="update-btn" @click="moveSurvey">수정하기</button>
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
      positions: ['전체', '컨설턴트', '교육생', '교육프로', '실습코치'],
      generations: ['전체', 5, 6],
      areas: ['전체', '서울', '대전', '광주', '구미', '부울경'],
      classes: ['전체', '1반', '2반', '3반', '4반', '5반', '6반'],
      teams: ['전체', '1팀', '2팀', '3팀', '4팀', '5팀', '6팀'],
      Team_rolls: ['전체', '팀장', '팀원'],
      searchUsers: [],
      target: {
        position: null,
        generation: null,
        area: null,
        class: null,
        team: null,
        team_roll: null,
      },
      targetTags: {},
      targets: [],
      inputSearch: null,
      isOpenStudent: false,
      isClkSelectMenu: true,
      isClkSearchMenu: false,
      Clkposition: null,
      Clkgeneration: null,
      Clkarea: null,
      Clkclass: null,
      Clkteam: null,
      Clkteamroll: null,
      scrollHeightOne: null,
      isNoneUser: false,
    }
  },
  methods: {
    clkSelectMenu() {
      this.isClkSelectMenu = true
      this.isClkSearchMenu = false
    },
    clkSearchMenu() {
      this.isClkSelectMenu = false
      this.isClkSearchMenu = true
    },
    selectPosition(element) {
      this.target['position'] = element
      if (element === '교육생') {
        this.isOpenStudent = true
      } else {
        this.isOpenStudent = false
        this.target['generation'] = null
        this.target['area'] = null
        this.target['class'] = null
        this.target['team'] = null
        this.target['team_roll'] = null
      }
    },
    selectGeneration(element) {
      this.target['generation'] = element
    },
    selectArea(element) {
      this.target['area'] = element
    },
    selectClass(element) {
      this.target['class'] = element
    },
    selectTeam(element) {
      this.target['team'] = element
    },
    selectTeamRoll(element) {
      this.target['team_roll'] = element
    },
    colorEvent(e) {
      let itemIds = [
        'position',
        'generation',
        'area',
        'class',
        'team',
        'teamroll',
      ]
      if (e.target.tagName == 'BUTTON') {
        for (let itemId of itemIds) {
          if (e.target.id == itemId) {
            if (this[`Clk${itemId}`]) {
              this[`Clk${itemId}`].classList.remove('btn-active')
            }
            e.target.classList.add('btn-active')
            this[`Clk${itemId}`] = e.target
            break
          }
        }
      }
    },
    async plusTarget(check) {
      let tempStr = ''
      if (check === 'select' && this.target['position']) {
        let payload = ''
        for (let key in this.target) {
          if (this.target[key] && this.target[key] != '전체') {
            payload += `${key}=${this.target[key]}&`
          }
        }
        console.log(payload)
        UserApi.searchMember(
          payload.substring(0, payload.length - 1),
          res => {
            if (res.data['검색 결과'].length == 0) {
              console.log('검색결과가 없습니다.')
              this.$swal({
                title: '검색결과가 없습니다.',
                icon: 'warning',
                target: '.container-set',
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: '확인',
              })
            } else {
              let targetTemp = []
              for (let user of res.data['검색 결과']) {
                targetTemp.push(user.uid)
              }
              this.$store.state.surveySet.survey.target.push(targetTemp)
              this.$store.state.surveySet.survey.incomplete.push(targetTemp)
              for (let property in this.target) {
                if (this.target[property]) {
                  tempStr += `${this.target[property]}/`
                }
              }
              this.targets.push(tempStr.substr(0, tempStr.length - 1))
              this.initTarget()
            }
          },
          err => {
            console.log(err)
          },
        )
      } else {
        console.log('설문대상자 선택해줄래?')
      }
    },
    merberUidSearch() {
      if (this.inputSearch) {
        let payload = `name=${this.inputSearch}&`
        for (let key in this.target) {
          if (this.target[key] && this.target[key] != '전체') {
            payload += `${key}=${this.target[key]}&`
          }
        }
        UserApi.searchMember(
          payload.substring(0, payload.length - 1),
          res => {
            console.log(res)
            if (res.data['검색 결과'].length == 0) {
              console.log('검색결과가 없습니다.')
              this.searchUsers = null
              this.isNoneUser = true
            } else {
              this.searchUsers = res.data['검색 결과']
              this.isNoneUser = false
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
              this.searchUsers = null
              this.isNoneUser = true
            } else {
              this.searchUsers = res.data['검색 결과']
              this.isNoneUser = false
            }
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    plusSearchTarget(idx) {
      let targetTemp = []
      targetTemp.push(this.searchUsers[idx]['uid'])
      this.$store.state.surveySet.survey.target.push(targetTemp)
      this.$store.state.surveySet.survey.incomplete.push(targetTemp)

      let tempStr = ''
      if (this.searchUsers[idx]['generation']) {
        tempStr += `${this.searchUsers[idx]['generation']}기/${this.searchUsers[idx]['area']}${this.searchUsers[idx]['group']}/${this.searchUsers[idx]['name']}`
      } else {
        tempStr += `${this.searchUsers[idx]['position']} ${this.searchUsers[idx]['name']}`
      }
      this.targets.push(tempStr)
    },
    cancelTarget(idx) {
      this.targets.splice(idx, 1)
      this.$store.state.surveySet.survey.target.splice(idx, 1)
      this.$store.state.surveySet.survey.incomplete.splice(idx, 1)
    },
    initTarget() {
      this.target['position'] = null
      this.isOpenStudent = false
      this.target['generation'] = null
      this.target['area'] = null
      this.target['class'] = null
      this.target['team'] = null
      this.target['team_roll'] = null
      let itemIds = ['position', 'generation', 'area', 'team', 'class']
      for (let itemId of itemIds) {
        if (this[`Clk${itemId}`]) {
          this[`Clk${itemId}`].classList.remove('btn-active')
          this[`Clk${itemId}`] = null
        }
      }
    },
    nextSet() {
      this.$emit('nextSet')
    },
    moveSurvey() {
      this.$store.commit('setIsClkUpdate', true)
      this.$router.push('/survey')
    },
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set-one.css"></style>
