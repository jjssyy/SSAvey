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
            {{ region }}
            <v-select
              v-if="position == '교육생'"
              v-model="region"
              :items="regions"
              label="지역"
              dense
              solo
            ></v-select>
            <v-btn class="signin-btn" type="submit" rounded color="white" light>
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
    regions: ['서울', '대전', '광주', '구미', '부울경'],
    position: '',
    generation: '',
    region: '',
  }),
}
</script>

<style scoped></style>
