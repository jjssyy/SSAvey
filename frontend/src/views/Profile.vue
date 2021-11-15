<template>
  <v-app>
    <v-card class="mx-auto mt-5" color="#3396f4" dark width="600">
      <v-card-title>
        <v-list-item class="grow">
          <v-list-item-avatar color="grey darken-3">
            <v-img
              class="elevation-6"
              alt=""
              src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
            ></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>
              <h2>{{ user.name }} 님</h2>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-card-title>
      <hr />
      <v-card-text class="text-h5 font-weight-bold">
        <div class="myinfo-contents">
          <div class="userinfo">
            <div class="content">
              <h4>이메일</h4>
              <input v-model="user.email" :disabled="true" />
            </div>
            <div class="content">
              <h4>직책</h4>
              <v-select
                v-model="user.position"
                :items="positions"
                :label="user.position"
                required
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.position" :disabled="is_modify" /> -->
            </div>
            <div class="content">
              <h4>기수</h4>
              <v-select
                v-if="user.position == '교육생'"
                v-model="user.generation"
                :items="generations"
                :label="user.generation"
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.generation" :disabled="is_modify" /> -->
            </div>
            <div class="content">
              <h4>지역</h4>
              <v-select
                v-if="
                  user.position == '교육생' &&
                    (user.generation == '5' || user.generation == '5기')
                "
                v-model="user.area"
                :items="regions"
                :label="user.area"
                기
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <v-select
                v-if="
                  user.position == '교육생' &&
                    (user.generation == '6' || user.generation == '6기')
                "
                v-model="user.area"
                :items="regions2"
                :label="user.area"
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.area" :disabled="is_modify" /> -->
            </div>
            <div class="content">
              <h4>반</h4>
              <v-select
                v-if="user.position == '교육생'"
                v-model="user.group"
                :items="clss"
                :label="user.group"
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.group" :disabled="is_modify" /> -->
            </div>
            <div class="content">
              <h4>팀</h4>
              <v-select
                v-if="user.position == '교육생'"
                v-model="user.team"
                :items="teams"
                :label="user.team"
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.team" :disabled="is_modify" /> -->
            </div>
            <div class="content">
              <h4>팀 내 역할</h4>
              <v-select
                v-if="user.position == '교육생'"
                v-model="user.team_role"
                :items="roles"
                :label="user.team_roll"
                dense
                solo
                :disabled="is_modify"
              ></v-select>
              <!-- <input v-model="user.team_roll" :disabled="is_modify" /> -->
            </div>
          </div>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-row align="center" justify="space-between">
          <v-btn
            v-if="is_modify"
            @click="Update"
            light
            class="mx-5 my-5"
            color="white"
          >
            <i class="fas fa-edit fa-sm"></i>수정
          </v-btn>
          <v-btn
            v-if="!is_modify"
            @click="modify"
            light
            class="mx-5 my-5"
            color="white"
            ><i class="fas fa-pen fa-sm"></i>완료</v-btn
          >
          <v-btn
            v-if="!is_modify"
            @click="back"
            light
            class="mx-5 my-5"
            color="warning"
            ><i class="fas fa-window-close fa-sm"></i>취소</v-btn
          >
        </v-row>
      </v-card-actions>
    </v-card>
  </v-app>
</template>
<script>
import UserApi from '@/api/UserApi'
export default {
  data() {
    return {
      user: {},
      is_modify: true,
      offset: true,
      positions: ['교육생', '컨설턴트', '교육프로', '교육코치'],
      generations: ['5기', '6기'],
      roles: ['없음', '팀장', '팀원'],
      regions: ['서울', '대전', '광주', '구미'],
      regions2: ['서울', '대전', '광주', '구미', '부울경'],
      clss: [
        '1반',
        '2반',
        '3반',
        '4반',
        '5반',
        '6반',
        '7반',
        '8반',
        '9반',
        '10반',
        '기업연계',
      ],
      teams: [
        '없음',
        '1팀',
        '2팀',
        '3팀',
        '4팀',
        '5팀',
        '6팀',
        '7팀',
        '8팀',
        '9팀',
        '10팀',
      ],
    }
  },
  methods: {
    Update() {
      this.is_modify = false
    },
    back() {
      this.is_modify = !this.is_modify
    },
    modify() {
      UserApi.updateUser(
        {
          uid: this.user.uid,
          email: this.user.email,
          name: this.user.name,
          position: this.user.position,
          generation: this.user.generation.charAt(0, 1) * 1,
          area: this.user.area,
          group: this.user.group,
          team: this.user.team,
          team_roll: this.user.team_roll,
          template: this.user.template,
          mySurvey: this.user.mySurvey,
          survey: this.user.survey,
        },
        res => {
          alert(res.data.message)
          this.$store.commit('setUser', this.user)
          this.is_modify = !this.is_modify
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

<style scoped>
.content {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
