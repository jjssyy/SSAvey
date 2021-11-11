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
  mounted() {
    if (this.$store.getters.getSurveySet.isWriting) {
      this.$swal({
        title: '작성중인 설문지가 존재합니다.',
        text: '이어서 작성하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '계속',
        cancelButtonText: '취소',
      }).then(result => {
        if (result.isConfirmed) {
          this.$router.push('/surveyset')
        } else {
          let payload = {
            survey: {},
            isWriting: false,
          }
          this.$store.commit('setSurveySet', payload)
        }
      })
    }
  },
}
</script>

<style scoped src="./../css/survey/survey/survey-page.css"></style>
