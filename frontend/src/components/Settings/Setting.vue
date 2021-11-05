<template>
  <div class="myinfo">
    <div class="profile-img">
      <div id="header-img">
        <div>
          <img
            src="@/assets/profileDefault.jpg"
            alt=""
            width="10%"
            height="10%"
            style="border-radius: 50%;"
          />
        </div>
      </div>
      <h2 class="nickname">
        {{ user.name }} 님
        <span @click="Update">
          <i class="fas fa-pen fa-sm"></i>
        </span>
      </h2>
    </div>
    <div class="myinfo-contents">
      <div class="userinfo">
        <div class="content">
          <h1>이메일</h1>
          <input v-model="user.email" :disabled="true" />
        </div>
        <div class="content">
          <h1>직책</h1>
          <input v-model="user.position" :disabled="is_modify" />
        </div>
        <div class="content">
          <h1>기수</h1>
          <input v-model="user.generation" :disabled="is_modify" />
        </div>
        <div class="content">
          <h1>지역</h1>
          <input v-model="user.area" :disabled="is_modify" />
        </div>
        <div class="content">
          <h1>반</h1>
          <input v-model="user.group" :disabled="is_modify" />
        </div>
        <div class="content">
          <h1>팀</h1>
          <input v-model="user.team" :disabled="is_modify" />
        </div>
        <div class="content">
          <h1>팀 내 역할</h1>
          <input v-model="user.team_roll" :disabled="is_modify" />
        </div>
        <div>
          <p>
            <span>
              회원 탈퇴
            </span>
          </p>
        </div>
        <div class="button" v-if="!is_modify">
          <button>취소</button>
          <button @click="modify" :disabled="!isValid">
            완료
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserApi from '@/api/UserApi'
export default {
  data() {
    return {
      user: {},
      is_modify: true,
    }
  },
  methods: {
    Update() {
      this.is_modify = false
    },
    modify() {
      UserApi.updateUser(
        {
          uid: this.user.uid,
          email: this.user.email,
          name: this.user.name,
          position: this.user.position,
          generation: this.user.generation,
          area: this.user.area,
          group: this.user.group,
          team: this.user.team,
          team_roll: this.user.team_roll,
          template: this.user.template,
          mySurvey: this.user.mySurvey,
          survey: this.user.survey,
        },
        res => {
          this.is_modify = !this.is_modify
          alert(res.data.message)
        },
      )
    },
  },
  computed: {
    isValid() {
      return (
        this.user.position &&
        this.user.generation &&
        this.user.area &&
        this.user.team &&
        this.user.team_roll
      )
    },
  },
  created() {
    this.user = this.$store.state.user
  },
}
</script>

<style scoped src="./../../css/setting/profile.css"></style>
