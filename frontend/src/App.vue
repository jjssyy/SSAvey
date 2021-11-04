<template>
  <div>
    <div id="v-app" v-if="isLogin">
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
