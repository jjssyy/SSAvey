<template>
  <div class="carousel-container">
    <div class="carousel-slides">
      <div class="item">
        <SurveySetOne @nextSet="nextSet"></SurveySetOne>
      </div>
      <!-- carousel로 구현할 예정 -->
      <div class="item">
        <SurveySetTwo @nextSet="nextSet" @prevSet="prevSet"></SurveySetTwo>
      </div>
      <div class="item">
        <SurveySetThr @prevSet="prevSet"></SurveySetThr>
      </div>
    </div>
  </div>
</template>

<script>
import SurveySetOne from '@/components/SurveySet/SurveySetOne.vue'
import SurveySetTwo from '@/components/SurveySet/SurveySetTwo.vue'
import SurveySetThr from '@/components/SurveySet/SurveySetThr.vue'
export default {
  components: {
    SurveySetOne,
    SurveySetTwo,
    SurveySetThr,
  },
  data() {
    return {
      counter: 0,
      size: null,
      carouselSlide: null,
      carouselTexts: null,
    }
  },
  methods: {
    nextSet() {
      this.carouselSlide.style.transition = 'transform 0.6s ease-in-out'
      this.counter++
      this.carouselSlide.style.transform =
        'translateX(' + -this.size * this.counter + 'px)'
    },
    prevSet() {
      this.carouselSlide.style.transition = 'transform 0.6s ease-in-out'
      this.counter--
      if (this.counter === 1) {
        this.carouselSlide.style.transform =
          'translateX(' + -this.size * this.counter + 'px)'
      } else {
        this.carouselSlide.style.transform =
          'translateX(' + this.size * this.counter + 'px)'
      }
    },
  },
  mounted() {
    this.carouselSlide = document.querySelector('.carousel-slides')
    this.carouselTexts = document.querySelectorAll('.item')
    this.size = this.carouselTexts[0].clientWidth
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set.css"></style>
