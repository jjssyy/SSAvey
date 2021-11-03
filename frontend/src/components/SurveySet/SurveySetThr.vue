<template>
  <div class="container-set">
    <p class="container-title">설문 공유 설정</p>
    <!-- 검색 -->
    <section class="container-items">
      <div class="item">
        <div class="container-subtitle">
          <p>설문이 진행될 기간을 설정해주세요.</p>
        </div>
      </div>
      <!-- 달력 -->
      <v-app>
        <v-date-picker v-model="dates" range></v-date-picker>
      </v-app>
    </section>
    <section class="container-items-date">
      <div class="date-box">
        <p class="date-item">{{ dates[0] }}</p>
        <span><i class="fas fa-caret-right"></i></span>
        <p class="date-item" v-if="dates.length === 2">{{ dates[1] }}</p>
        <p class="date-item" v-if="dates.length === 1">{{ dates[0] }}</p>
      </div>
    </section>
    <!-- <div class="temp"></div>
    <div class="bottom-btn">
      <button @click="prevSet" class="prev-btn">
        <i class="fas fa-chevron-left fa-lg"></i>
      </button>
      <button @click="nextSet" class="next-btn">
        <i class="fas fa-chevron-right fa-lg"></i>
      </button>
    </div> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      dates: ['2021-11-02'],
      dateNext: null,
      datePrev: null,
    }
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ')
    },
  },
  watch: {
    dates: function(newVal) {
      if (newVal.length === 2) {
        let start = Number(newVal[0].replace(/-/g, ''))
        let end = Number(newVal[1].replace(/-/g, ''))
        if (start > end) {
          ;[this.dates[0], this.dates[1]] = [this.dates[1], this.dates[0]]
        }
      }
    },
  },
  created() {
    // let tempDate = new Date()
    // this.dates[0] =
    //   String(tempDate.getFullYear()) +
    //   '-' +
    //   String(tempDate.getMonth()) +
    //   '-' +
    //   String(tempDate.getDate())
  },
  mounted() {
    document.querySelector('.v-application').style.height = '350px'
    // document.querySelector('.v-application--wrap').style.display = 'block'
    // document.querySelector('.v-application--wrap').style.height = '100%'
    // count header 지우기
    document.querySelector('.v-picker__title').style.display = 'none'
    // header 100%
    document.querySelector('.v-date-picker-header').style.width = '100%'
    // table width 100%
    document.querySelector('.v-date-picker-table').style.width = '100%'
    document.querySelector('.v-date-picker-table').style.height = '300px'
    // table 높이 세팅
    document.querySelector('.v-date-picker-table>table').style.height = '100%'
    // 양쪽 margin없애기 v-date-picker-table  v-date-picker-table
    document.querySelector('.v-picker__body').style.margin = '0'
    // body width 100%
    document.querySelector('.v-picker__body').style.width = '100%'
    this.dateNext = document.querySelector('button[aria-label="Next month"]')
    this.datePrev = document.querySelector(
      'button[aria-label="Previous month"]',
    )
    this.dateNext.addEventListener('click', () => {
      this.reset()
    })
    this.datePrev.addEventListener('click', () => {
      this.reset()
    })
  },
  methods: {
    reset() {
      document
        .querySelectorAll('.v-date-picker-table>table')
        .forEach(element => {
          element.style.height = '100%'
        })
    },
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set-thr.css"></style>
