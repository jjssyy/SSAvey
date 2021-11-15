<template>
  <v-app style="background-color: #eef2f7">
    <v-carousel
      cycle
      height="400"
      hide-delimiter-background
      show-arrows-on-hover
    >
      <v-carousel-item v-for="(image, i) in images" :key="i">
        <!-- <v-sheet :color="colors[i]" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <div class="text-h2">{{ slide }} Slide</div>
          </v-row>
        </v-sheet> -->

        <img
          :src="image.url"
          style="height: 100%; object-fit: contain;"
          :alt="image.des"
          margin:auto
        />
      </v-carousel-item>
    </v-carousel>
    <v-container class="lighten-5" style="min-width: 100%">
      <v-row>
        <v-col
          style="min-height: 300px"
          cols="12"
          sm="6"
          class="container-item"
        >
          <div style="border-bottom: 2px solid">
            <h2>
              진행 중인 설문 <i @click="gotoProceeding" class="fas fa-plus"></i>
            </h2>
          </div>
          <div
            class="d-flex justify-space-between"
            style="margin: 3px 5px"
            v-if="proceedingSurvey.length <= 0"
          >
            진행 중인 설문이 없습니다.
          </div>
          <div
            class="d-flex justify-space-between"
            v-for="(survey, index) in proceedingSurvey"
            :key="index"
            style="
              padding: 5px;
              margin: 3px 5px;
              align-items: center;
              border: 1px solid;
              border-radius: 10px;
            "
          >
            <v-btn fab small class="mx-2" style="font-weight: bold">진행</v-btn>
            <v-row class="d-flex" style="align-items: center">
              <v-col cols="12" sm="6">
                <h4>{{ survey.title }}</h4>
              </v-col>
              <v-col cols="12" sm="6">
                <h5>
                  {{
                    survey.start_date.substring(0, 10) +
                      ' ' +
                      survey.start_date.substring(11, 16)
                  }}
                </h5>
                <h5>
                  ~
                  {{
                    survey.end_date.substring(0, 10) +
                      ' ' +
                      survey.end_date.substring(11, 16)
                  }}
                </h5>
              </v-col>
            </v-row>
          </div>
        </v-col>
        <v-col
          style="min-height: 300px"
          cols="12"
          sm="6"
          class="container-item"
        >
          <div style="border-bottom: 2px solid">
            <h2>
              진행 예정 설문 <i @click="gotoExpected" class="fas fa-plus"></i>
            </h2>
          </div>
          <div
            class="d-flex justify-space-between"
            style="margin: 3px 5px"
            v-if="expectedSurvey.length <= 0"
          >
            진행 예정인 설문이 없습니다.
          </div>
          <div
            class="d-flex justify-space-between"
            v-for="(survey, index) in expectedSurvey"
            :key="index"
            style="
            padding: 5px; 
            margin: 3px 5px; 
            align-items: center; 
            border: 1px solid; 
            border-radius: 10px;
            "
          >
            <v-btn fab small class="mx-2" style="font-weight: bold">예정</v-btn>
            <v-row class="d-flex" style="align-items: center">
              <v-col cols="12" sm="6">
                <h4>{{ survey.title }}</h4>
              </v-col>
              <v-col cols="12" sm="6">
                <h5>
                  {{
                    survey.start_date.substring(0, 10) +
                      ' ' +
                      survey.start_date.substring(11, 16)
                  }}
                </h5>
                <h5>
                  ~
                  {{
                    survey.end_date.substring(0, 10) +
                      ' ' +
                      survey.end_date.substring(11, 16)
                  }}
                </h5>
              </v-col>
            </v-row>
          </div>
        </v-col>
        <v-col
          style="min-height: 300px"
          cols="12"
          sm="6"
          class="container-item"
        >
          <div style="border-bottom: 2px solid">
            <h2>
              완료된 설문 <i @click="gotoCompleted" class="fas fa-plus"></i>
            </h2>
          </div>
          <div
            class="d-flex justify-space-between"
            style="margin: 3px 5px"
            v-if="completedSurvey.length <= 0"
          >
            완료된 설문이 없습니다.
          </div>
          <div
            class="d-flex justify-space-between"
            v-for="(survey, index) in completedSurvey"
            :key="index"
            style="
            padding: 5px; 
            margin: 3px 5px; 
            align-items: center; 
            border: 1px solid; 
            border-radius: 10px;
            "
          >
            <v-btn fab small class="mx-2" style="font-weight: bold">완료</v-btn>
            <v-row class="d-flex" style="align-items: center">
              <v-col cols="12" sm="6">
                <h4>{{ survey.title }}</h4>
              </v-col>
              <v-col cols="12" sm="6">
                <h5>
                  {{ survey.start_date }} ~
                  {{ survey.end_date }}
                </h5>
              </v-col>
            </v-row>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import main from '@/api/main.js'
import UserApi from '@/api/UserApi'
import axios from 'axios'
export default {
  data() {
    return {
      images: [
        { url: require('@/assets/carousel1.jpg'), alt: 'carousel1' },
        { url: require('@/assets/carousel1.jpg'), alt: 'carousel1' },
      ],
      colors: [
        'indigo',
        'warning',
        'pink darken-2',
        'red lighten-1',
        'deep-purple accent-4',
      ],
      slides: ['First', 'Second', 'Third', 'Fourth', 'Fifth'],
      expectedSurvey: [],
      proceedingSurvey: [
        // {
        //   title: '첫번째 설문',
        //   start_date: '2021.11.01 18:00',
        //   end_date: '2021.11.02.09:00',
        // },
        // {
        //   title: '첫번째 설문',
        //   start_date: '2021.11.01 18:00',
        //   end_date: '2021.11.02.09:00',
        // },
        // {
        //   title: '첫번째 설문',
        //   start_date: '2021.11.01 18:00',
        //   end_date: '2021.11.02.09:00',
        // },
      ],
      completedSurvey: [],
    }
  },
  async created() {
    UserApi.userInfo(
      {
        uid: this.$store.state.uid,
      },
      res => {
        this.$store.commit('setUser', res.data.user)
      },
      err => {
        console.log(err)
        alert('올바르지 않은 UID입니다.')
      },
    )
    axios
      .get(
        'https://meeting.ssafy.com/api/v4/users/' +
          this.$store.state.uid +
          '/image',
        {
          headers: {
            Authorization: 'Bearer uieydcqsspf87n6d7xo3kugp7r',
            'Access-Control-Allow-Origin': 'http://k5c105.p.ssafy.io',
          },
        },
      )
      .then(res => {
        console.log(res)
      })
    main.getSurveysByStatus(
      {
        id: this.$store.state.uid,
      },
      res => {
        this.expectedSurvey = res.data.EXPECTED
        this.proceedingSurvey = res.data.PROCEEDING
        this.completedSurvey = res.data.COMPLETED
      },
      err => {
        console.log(err)
      },
    )
  },
  methods: {
    gotoExpected() {
      this.$router.push('/survey/state/expected')
    },
    gotoProceeding() {
      this.$router.push('/survey/state/proceeding')
    },
    gotoCompleted() {
      this.$router.push('/survey/state/completed')
    },
  },
  // created() {
  //   UserApi.userInfo(
  //     {
  //       uid: this.$store.state.uid,
  //     },
  //     res => {
  //       this.$store.commit('setUser', res.data.user)
  //     },
  //     err => {
  //       console.log(err)
  //       alert('올바르지 않은 UID입니다.')
  //     },
  //   )
  //   axios
  //     .get(
  //       'https://meeting.ssafy.com/api/v4/users/' +
  //         this.$store.state.uid +
  //         '/image',
  //       {
  //         headers: {
  //           Authorization: 'Bearer uieydcqsspf87n6d7xo3kugp7r',
  //           'Access-Control-Allow-Origin': 'http://k5c105.p.ssafy.io',
  //         },
  //       },
  //     )
  //     .then(res => {
  //       console.log(res)
  //     })
  // },
}
</script>

<style scoped src="./../css/view/main.css"></style>
<style scoped>
/* .circle {
  border-radius: 50%;
  border: 3px solid blue;
  overflow: hidden;
  padding: 3px;
} */
</style>
