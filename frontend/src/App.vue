<template>
  <div>
    <header>
      <!-- url 변경 -->
      <router-link to="/login">로그인</router-link> |
      <router-link to="/main">메인</router-link>
    </header>
    <!-- url변경될때 pagecomponent가 뜨는 영역 -->
    <router-view></router-view>
  </div>
</template>

<script>
import '@/css/common/default.css'
export default {}
</script>

<style></style>
