<template>
<<<<<<< HEAD
  <v-app>
    <header>
      <!-- url 변경 -->
      <router-link to="/login">로그인</router-link> |
      <router-link to="/select">메인</router-link> |
      <router-link to="/surveyedit">설문작성</router-link>
    </header>
    <!-- url변경될때 pagecomponent가 뜨는 영역 -->
    <router-view></router-view>
  </v-app>
=======
  <div>
    <div v-if="isLogin">
      <nav>
        <AppHeader @openMenu="openMenu"></AppHeader>
      </nav>
      <main class="component" :class="{ 'open-menu-component': isOpenMenu }">
        <router-view></router-view>
      </main>
    </div>
    <div v-else>
      <nav>
        <router-link to="/login">login</router-link> |
        <router-link to="/select">select</router-link>
      </nav>
      <v-app>
        <router-view></router-view>
      </v-app>
    </div>
  </div>
>>>>>>> 2c71696deb2e24afcd0d3005cf92d484fd1f64ee
</template>

<script>
import '@/css/common/default.css'
import AppHeader from '@/components/common/AppHeader.vue'
export default {
  components: {
    AppHeader,
  },
  data() {
    return {
      isLogin: true,
      isOpenMenu: false,
    }
  },
  methods: {
    openMenu(check) {
      this.isOpenMenu = check
    },
  },
}
</script>

<style scoped src="./css/menu/app.css"></style>
