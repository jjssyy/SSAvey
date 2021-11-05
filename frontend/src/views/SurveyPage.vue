<template>
  <div class="page-component">
    <div class="component-1">
      <surveyList></surveyList>
    </div>
  </div>
</template>

<script>
import SurveyList from '@/components/Survey/SurveyList.vue'
export default {
  components: {
    SurveyList,
  },
}
</script>

<style scoped src="./../css/survey/survey/survey-page.css"></style>
