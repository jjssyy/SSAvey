<template>
  <div>
    <div class="top-menu" :class="{ 'open-top-menu': isOpenMenu }">
      <div class="menu-icon-box" @click="openMenu">
        <i id="top-menu" class="menu-icon fas fa-bars"></i>
      </div>
      <div class="big-logo-box">
        <img src="@/assets/bigLogo.png" alt="" @click="moveMain" />
      </div>
    </div>
    <div
      id="left-menu"
      class="left-menu"
      :class="{ 'open-left-menu': isOpenMenu }"
      @mouseenter="openMenu"
      @mouseleave="openMenu"
    >
      <div class="items big-logo-box">
        <img class="logo" src="@/assets/bigLogo.png" alt="" @click="moveMain" />
      </div>
      <div class="items profile">
        <img src="@/assets/profileDefault.jpg" alt="" />
        <p class="medium-text">{{ user.name }}</p>
        <p class="small-text">미완료 설문 {{ count }}개</p>
      </div>
      <div class="items">
        <div @click="moveMain">
          <i class="fas fa-home icon-1"></i>
          <p>메인</p>
        </div>
      </div>
      <div class="items" @click="moveSurvey">
        <div id="div-1" @click="openUl1">
          <i class="fas fa-marker icon-1"></i>
          <p>설문 만들기</p>
        </div>
      </div>
      <div class="items">
        <div id="div-2" @click="openUl2">
          <i class="fas fa-file icon-1"></i>
          <p>설문 목록</p>
          <i
            class="fas fa-caret-right icon-2"
            :class="{ 'change-degree': isOpenIcon2Two }"
          ></i>
        </div>
        <ul id="ul-2" :class="{ 'open-list-2': isOpenUl2 }">
          <li @click="moveExpected">예정</li>
          <li @click="moveProceeding">진행중</li>
          <li @click="moveCompleted">완료</li>
        </ul>
      </div>
      <div class="items">
        <div id="div-3" @click="openUl3">
          <i class="fas fa-chart-bar icon-1"></i>
          <p>내 설문 보기</p>
          <i
            class="fas fa-caret-right icon-2"
            :class="{ 'change-degree': isOpenIcon2Thr }"
          ></i>
        </div>
        <ul id="ul-3" :class="{ 'open-list-3': isOpenUl3 }">
          <li @click="moveMyExpected">예정</li>
          <li @click="moveMyProceeding">진행중</li>
          <li @click="moveMyCompleted">완료</li>
        </ul>
      </div>
      <div class="items">
        <div @click="moveSetting">
          <i class="fas fa-cog icon-1"></i>
          <p>설정</p>
        </div>
      </div>
      <div class="items" @click="logout">
        <div>
          <i class="fas fa-sign-out-alt icon-1"></i>
          <p>로그아웃</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { headerInfo } from '@/api/header.js'
import UserApi from '@/api/UserApi'
export default {
  data() {
    return {
      isOpenMenu: false,
      prevOpenMenuId: null,
      isOpenUl1: false,
      isOpenUl2: false,
      isOpenUl3: false,
      isOpenIcon2One: false,
      isOpenIcon2Two: false,
      isOpenIcon2Thr: false,
      windowWidth: window.innerWidth,
      count: null,
      user: {},
    }
  },
  methods: {
    openMenu(e) {
      if (e.target.id == 'top-menu' && this.isOpenMenu == true) {
        this.isOpenMenu = false
      } else if (e.target.id == 'top-menu' && this.isOpenMenu == false) {
        this.isOpenMenu = true
      } else if (
        e.target.id == 'left-menu' &&
        this.isOpenMenu == true &&
        this.prevOpenMenuId == 'top-menu'
      ) {
        this.isOpenMenu = true
      } else if (e.target.id == 'left-menu' && this.isOpenMenu == false) {
        this.isOpenMenu = true
      } else if (e.target.id == 'left-menu' && this.isOpenMenu == true) {
        this.isOpenMenu = false
      }
      this.$emit('openMenu', this.isOpenMenu)
      if (this.windowWidth >= 768 && this.windowWidth < 1200) {
        this.isOpenUl1 = false
        this.isOpenUl2 = false
        this.isOpenUl3 = false
        this.isOpenIcon2One = false
        this.isOpenIcon2Two = false
        this.isOpenIcon2Thr = false
      }
      this.prevOpenMenuId = e.target.id
    },
    openUl1() {
      this.isOpenUl1 = !this.isOpenUl1
      this.isOpenIcon2One = !this.isOpenIcon2One
    },
    openUl2() {
      this.isOpenUl2 = !this.isOpenUl2
      this.isOpenIcon2Two = !this.isOpenIcon2Two
    },
    openUl3() {
      this.isOpenUl3 = !this.isOpenUl3
      this.isOpenIcon2Thr = !this.isOpenIcon2Thr
    },
    widthResize() {
      this.windowWidth = window.innerWidth
    },
    moveMain() {
      this.$router.push('/main')
    },
    moveSurvey() {
      this.$router.push('/survey')
    },
    moveSetting() {
      this.$router.push('/profile')
    },
    moveExpected() {
      this.$router.push('/survey/state/expected')
    },
    moveProceeding() {
      this.$router.push('/survey/state/proceeding')
    },
    moveCompleted() {
      this.$router.push('/survey/state/completed')
    },
    moveMyExpected() {
      this.$router.push('/my-survey/state/expected')
    },
    moveMyProceeding() {
      this.$router.push('/my-survey/state/proceeding')
    },
    moveMyCompleted() {
      this.$router.push('/my-survey/state/completed')
    },
    moveTemplete() {
      this.$router.push('/templete')
    },
    logout() {
      this.$store.commit('logout')
      this.$router.push('/')
    },
  },

  watch: {
    windowWidth(newWidth) {
      if (newWidth >= 768 && newWidth < 1200) {
        this.isOpenUl1 = false
        this.isOpenUl2 = false
        this.isOpenUl3 = false
        this.isOpenIcon2One = false
        this.isOpenIcon2Two = false
        this.isOpenIcon2Thr = false
      }
    },
  },
  async created() {
    window.addEventListener('resize', this.widthResize)
    const resp = this.$store.state.uid
    const res = await headerInfo(resp)
    this.count = res.data.data
    UserApi.userInfo(
      {
        uid: resp,
      },
      res => {
        this.$store.commit('setUser', res.data.user)
        this.user = res.data.user
      },
      err => {
        console.log(err)
        this.$swal({
          icon: 'warning',
          title: '올바르지 않은 ID입니다.',
          text: '다시 로그인해 주세요.',
          // target: '.container-set',
          position: 'center-center',
          showConfirmButton: false,
          timer: 1500,
        })
        setTimeout(() => {
          this.$router.push({ name: 'Login' })
        }, 1000)
      },
    )
  },
}
</script>

<style scoped src="./../../css/menu/menu.css"></style>
