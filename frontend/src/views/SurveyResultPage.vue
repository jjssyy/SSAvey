<template>
  <div class="page-component">
    <div class="component-1">
      <survey-analysis></survey-analysis>
      <!-- <survey-analysis :survey="survey"></survey-analysis> -->
    </div>
    <div class="component-2">
      <survey-user></survey-user>
    </div>
  </div>
</template>

<script>
import SurveyAnalysis from '@/components/SurveyResult/SurveyAnalysis.vue'
import SurveyUser from '@/components/SurveyResult/SurveyUser.vue'
// import SurveyApi from '@/api/SurveyApi'
export default {
  components: {
    SurveyAnalysis,
    SurveyUser,
  },
  data() {
    return {
      survey: {},
    }
  },
  methods: {
    // loadData() {
    //   let payload = this.$route.params.surveyId
    //   SurveyApi.loadSurveyResult(
    //     payload,
    //     res => {
    //       console.log(res)
    //       this.survey = res.data
    //     },
    //     err => {
    //       console.log(err)
    //     },
    //   )
    // },
  },
  created() {
    // this.loadData()
  },
}
</script>

<style scoped src="./../css/survey/survey/survey-result-page.css"></style>
