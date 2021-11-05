<template>
  <div class="page-component">
    <div class="component-1">
      <surveyList></surveyList>
    </div>

    <!-- 오른쪽 사이드바 -->
    <!-- <div class="component-2">
      <surveySet></surveySet>
    </div> -->
  </div>
</template>

<script>
import SurveyList from '@/components/Survey/SurveyList.vue'
// import SurveySet from '@/components/Survey/SurveySet.vue'
export default {
  components: {
    SurveyList,
    // SurveySet,
  },
}
</script>

<style scoped src="./../css/survey/survey/survey-page.css"></style>
