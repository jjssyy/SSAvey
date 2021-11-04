<template>
  <v-app style="background-color: #EEF2F7;">
    <v-carousel
      cycle
      height="400"
      hide-delimiter-background
      show-arrows-on-hover
    >
      <v-carousel-item v-for="(slide, i) in slides" :key="i">
        <v-sheet :color="colors[i]" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <div class="text-h2">{{ slide }} Slide</div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
    <v-container class="lighten-5" style="min-width: 100%;">
      <v-row>
        <v-col
          @click="gotoProceeding"
          style="min-height: 300px;"
          cols="12"
          sm="6"
        >
          <div style="border-bottom: 2px solid;">
            <h2>진행중인 설문</h2>
          </div>
          <div
            class="d-flex justify-space-between"
            v-if="proceedingSurvey.length > 0"
            style="margin: 3px 5px;"
          >
            <h4>{{ proceedingSurvey[0].title }}</h4>
            <h5>
              {{ proceedingSurvey[0].start_date }} ~
              {{ proceedingSurvey[0].end_date }}
            </h5>
          </div>
        </v-col>
        <v-col
          @click="gotoExpected"
          style="min-height: 300px;"
          cols="12"
          sm="6"
        >
          <div style="border-bottom: 2px solid;">
            <h2>진행예정 설문</h2>
          </div>
          <div
            class="d-flex justify-space-between"
            v-if="expectedSurvey.length > 0"
            style="margin: 3px 5px;"
          >
            <h4>{{ expectedSurvey[0].title }}</h4>
            <h5>
              {{ expectedSurvey[0].start_date }} ~
              {{ expectedSurvey[0].end_date }}
            </h5>
          </div>
        </v-col>
        <v-col
          @click="gotoCompleted"
          style="min-height: 300px;"
          cols="12"
          sm="6"
        >
          <div style="border-bottom: 2px solid;">
            <h2>완료된 설문</h2>
          </div>
          <div
            class="d-flex justify-space-between"
            v-if="completedSurvey.length > 0"
            style="margin: 3px 5px;"
          >
            <h4>{{ completedSurvey[0].title }}</h4>
            <h5>
              {{ completedSurvey[0].start_date }} ~
              {{ completedSurvey[0].end_date }}
            </h5>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import main from '@/api/main.js'
export default {
  data() {
    return {
      colors: [
        'indigo',
        'warning',
        'pink darken-2',
        'red lighten-1',
        'deep-purple accent-4',
      ],
      slides: ['First', 'Second', 'Third', 'Fourth', 'Fifth'],
      expectedSurvey: [],
      proceedingSurvey: [],
      completedSurvey: [],
    }
  },
  async created() {
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
  // computed: {
  //   // eslint-disable-next-line no-undef
  //   ...mapState(['uid']),
  // },
}
</script>

<style></style>
