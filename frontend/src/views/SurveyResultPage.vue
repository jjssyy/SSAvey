<template>
  <div class="page-component">
    <div class="component-1">
      <survey-analysis v-if="survey" :survey="survey"></survey-analysis>
    </div>
    <div class="component-2">
      <survey-user
        v-if="survey"
        :survey="survey"
        @reset="loadData"
      ></survey-user>
    </div>
    <input type="text" :value="items" />
    <div>{{ items }}</div>
    <button @click="test">change</button>
  </div>
</template>

<script>
import SurveyAnalysis from '@/components/SurveyResult/SurveyAnalysis.vue'
import SurveyUser from '@/components/SurveyResult/SurveyUser.vue'
import SurveyApi from '@/api/SurveyApi'
export default {
  data() {
    return {
      survey: null,
      items: { '1': 2 },
    }
  },
  components: {
    SurveyAnalysis,
    SurveyUser,
  },
  methods: {
    test() {
      this.items['1'] = 4
      console.log(this.items)
    },
    loadData() {
      let payload = this.$route.params.sid
      this.$swal({
        title: '분석중',
        html: '잠시만 기다려 주세요.',
        timerProgressBar: true,
        target: '.page-component',
        didOpen: () => {
          this.$swal.showLoading()
        },
      })
      SurveyApi.loadSurveyResult(
        payload,
        res => {
          this.survey = res.data.data
          console.log(this.survey)
          this.$swal.close()
        },
        err => {
          console.log(err)
        },
      )
    },
  },
  created() {
    this.loadData()
  },
}
</script>

<style scoped src="./../css/survey/survey/survey-result-page.css"></style>
