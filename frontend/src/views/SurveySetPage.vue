<template>
  <div class="page-component">
    <!-- 설문 문항 -->
    <div class="component-1">
      <survey :survey="survey"></survey>
    </div>
    <!-- 오른쪽 사이드바 -->
    <div class="component-2">
      <surveySet></surveySet>
    </div>
  </div>
</template>

<script>
import Survey from '@/components/SurveySet/Survey.vue'
import SurveySet from '@/components/SurveySet/SurveySet.vue'
export default {
  data() {
    return {
      survey: {},
    }
  },
  components: {
    Survey,
    SurveySet,
  },
  mounted() {
    if (this.$store.getters.getSurveySet.isWriting) {
      this.survey = this.$store.getters.getSurveySet.survey
    } else {
      this.$router.push('/survey')
    }
  },
}
</script>

<style scoped src="./../css/survey/surveyset/survey-set-page.css"></style>
