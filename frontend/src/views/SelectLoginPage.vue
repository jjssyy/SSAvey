<template>
  <div style="display: flex; justify-content: center;">
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
            {{ generation }}
            <v-select
              v-if="position == '교육생'"
              v-model="generation"
              :items="generations"
              label="기수"
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
            {{ region }}
            <v-select
              v-if="position == '교육생' && generation == '5기'"
              v-model="region"
              :items="regions"
              label="지역"
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
  </div>
</template>

<script>
export default {
  data: () => ({
    positions: ['교육생', '컨설턴트', '교육프로', '교육코치'],
    generations: ['5기', '6기'],
    roles: ['팀장', '팀원'],
    regions: ['서울', '대전', '광주', '구미'],
    regions2: ['서울', '대전', '광주', '구미', '부울경'],
    position: '',
    generation: '',
    region: '',
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
      }
    },
  },
}
</script>

<style scoped></style>
