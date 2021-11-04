<template>
  <div class="userinfo">
    <div class="profile-img"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {},
    }
  },
  created() {
    this.user = this.$$store.user
  },
}
</script>

<style></style>
