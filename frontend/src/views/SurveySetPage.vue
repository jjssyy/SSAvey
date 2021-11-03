<template>
  <div class="page-component">
    <!-- 설문 문항 -->
    <div class="component-1">
      <survey></survey>
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
  components: {
    Survey,
    SurveySet,
  },
}
</script>

<style scoped src="./../css/survey/surveyset/survey-set-page.css"></style>
