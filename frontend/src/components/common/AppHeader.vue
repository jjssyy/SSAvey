<template>
  <div>
    <div class="top-menu" :class="{ 'open-top-menu': isOpenMenu }">
      <div class="menu-icon-box" @click="openMenu">
        <i id="top-menu" class="menu-icon fas fa-bars"></i>
      </div>
      <div class="big-logo-box">
        <img src="@/assets/bigLogo.png" alt="" />
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
        <img class="logo" src="@/assets/bigLogo.png" alt="" />
      </div>
      <div class="items profile">
        <img src="@/assets/profileDefault.jpg" alt="" />
        <p class="medium-text">민싸피</p>
        <p class="small-text">미완료 설문 {{ count }}개</p>
      </div>
      <div class="items">
        <div @click="moveMain">
          <i class="fas fa-home icon-1"></i>
          <p>메인</p>
        </div>
      </div>
      <div class="items">
        <div id="div-1" @click="openUl1">
          <i class="fas fa-marker icon-1"></i>
          <p>설문 만들기</p>
          <i
            class="fas fa-caret-right icon-2"
            :class="{ 'change-degree': isOpenIcon2One }"
          ></i>
        </div>
        <ul id="ul-1" :class="{ 'open-list-1': isOpenUl1 }">
          <li @click="moveSurveySet">설문 만들기</li>
          <li>내 탬플릿 보기</li>
        </ul>
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
          <li>예정</li>
          <li>잰행중</li>
          <li>완료</li>
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
          <li>예정</li>
          <li>잰행중</li>
          <li>완료</li>
        </ul>
      </div>
      <div class="items">
        <div @click="moveSetting">
          <i class="fas fa-cog icon-1"></i>
          <p>설정</p>
        </div>
      </div>
      <div class="items">
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
    moveSurveySet() {
      // this.$router.push('/surveyset')
    },
    moveSetting() {
      this.$router.push('/setting')
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
    const res = await headerInfo('spdnq7u4bpbu5pu4f3g4ehss3c')
    this.count = res.data.data
  },
}
</script>

<style scoped src="./../../css/menu/menu.css"></style>
