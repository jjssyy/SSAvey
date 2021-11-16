<template>
  <v-app style="display: flex; justify-content: center">
    <section class="section-container">
      <v-row class="signin">
        <v-col cols="12" sm="8" class="left">
          <img src="@/assets/bigLogo.png" alt="" />
        </v-col>
        <v-col cols="12" sm="4" class="right">
          <h2>직책/기수/지역 선택</h2>
          <div class="text-center">
            {{ position }}
            <v-select
              v-model="position"
              :items="positions"
              label="직책"
              required
              dense
              solo
            ></v-select>
            <div v-if="position == '교육생'">
              {{ generation }}
              <v-select
                v-if="position == '교육생'"
                v-model="generation"
                :items="generations"
                label="기수"
                dense
                solo
              ></v-select>
              {{ region }}
              <v-select
                v-if="position == '교육생' && generation == '5기'"
                v-model="region"
                :items="regions"
                label="지역"
                dense
                solo
              ></v-select>
              {{ cls }}
              <v-select
                v-if="position == '교육생'"
                v-model="cls"
                :items="clss"
                label="반"
                dense
                solo
              ></v-select>
              {{ team }}
              <v-select
                v-if="position == '교육생'"
                v-model="team"
                :items="teams"
                label="팀"
                dense
                solo
              ></v-select>
              <v-select
                v-if="position == '교육생' && generation == '6기'"
                v-model="region"
                :items="regions2"
                label="지역"
                dense
                solo
              ></v-select>
              {{ role }}
              <v-select
                v-if="position == '교육생'"
                v-model="role"
                :items="roles"
                label="역할"
                dense
                solo
              ></v-select>
            </div>
            {{ name }}
            <v-text-field
              v-model="name"
              :counter="4"
              :rules="nameRules"
              label="Name"
              required
              outlined
              dark
              filled
              dense
              @keyup.enter="Enter"
            ></v-text-field>
            <v-btn
              v-if="name.length < 3"
              class="signin-btn"
              rounded
              color="white"
              disabled
              light
            >
              Enter
            </v-btn>
            <v-btn
              v-if="name.length >= 3"
              class="signin-btn"
              type="submit"
              @click="Enter"
              rounded
              color="white"
              light
            >
              Enter
            </v-btn>
          </div>
        </v-col>
      </v-row>
    </section>
  </v-app>
</template>

<script>
import UserApi from '@/api/UserApi'
export default {
  data: () => ({
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
    position: '',
    generation: '',
    region: '',
    cls: '',
    team: '',
    name: '',
    role: '',
    nameRules: [
      v => !!v || 'Name is required',
      v => v.length <= 4 || 'Name must be less than 4 characters',
    ],
  }),
  methods: {
    Enter: function() {
      if (this.name.length >= 3) {
        console.log(this.position, this.generation, this.region, this.name)

        UserApi.updateUser(
          {
            uid: this.$store.state.uid,
            email: this.$store.state.email,
            name: this.name,
            position: this.position,
            generation: this.generation.charAt(0, 1) * 1,
            area: this.region,
            group: this.cls,
            team: this.team,
            team_roll: this.role,
            template: [],
            mySurvey: [],
            survey: [],
            answer_survey: [],
          },
          // eslint-disable-next-line no-unused-vars
          res => {
            this.$store.commit('setIsSignUp', true)
            this.$router.push({ name: 'MainPage' })
          },
        )
      } else {
        alert('name은 3글자 이상 작성해주세요')
      }
    },
  },
}
</script>

<style scoped></style>
