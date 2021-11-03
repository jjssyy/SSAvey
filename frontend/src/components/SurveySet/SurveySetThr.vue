<template>
  <div class="container-set">
    <p class="container-title">설문기간 설정</p>
    <section class="container-items">
      <div class="item">
        <div class="container-subtitle">
          <p>설문이 진행될 기간을 설정해주세요.</p>
        </div>
      </div>
      <v-app>
        <v-date-picker v-model="dates" range></v-date-picker>
      </v-app>
    </section>
    <section class="container-items-date">
      <div class="item">
        <p class="container-subtitle-two">설문 시작 시간</p>
        <div class="date-box">
          <div class="date-item">
            <p>{{ dates[0] }}</p>
          </div>
          <div class="date-item item-left">
            <input v-model="startTime[0]" type="text" />
            <p class="time-margin">시</p>
            <input v-model="startTime[1]" type="text" />
            <p class="time-margin">분</p>
          </div>
        </div>
      </div>
      <div class="item">
        <p class="container-subtitle-two">설문 종료 시간</p>
        <div class="date-box">
          <div class="date-item">
            <p v-if="dates.length === 2">{{ dates[1] }}</p>
            <p v-if="dates.length === 1">{{ dates[0] }}</p>
          </div>
          <div class="date-item item-left">
            <input v-model="endTime[0]" type="text" />
            <p class="time-margin">시</p>
            <input v-model="endTime[1]" type="text" />
            <p class="time-margin">분</p>
          </div>
        </div>
      </div>
    </section>
    <div class="bottom-btn">
      <button @click="prevSet" class="prev-btn">
        <i class="fas fa-chevron-left fa-lg"></i>
      </button>
      <button class="update-btn">배포하기</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dates: [],
      dateNext: null,
      datePrev: null,
      startTime: ['00', '00'],
      endTime: ['00', '00'],
    }
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ')
    },
  },
  watch: {
    dates: function(newVal) {
      // 달력에서 한번만 클릭을 하면 배열의 길이는 1이 된다.
      // 두번 클릭시에는 길이가 2가 된다.
      // 똑같은 날짜를 두번클릭하면 배열의 길이는 2가 되고, 0번째와 1번째 index의 value는 같다.
      console.log(this.dates)
      if (newVal.length === 2) {
        let start = newVal[0].replace(/-/g, '')
        let end = newVal[1].replace(/-/g, '')
        if (Number(start) > Number(end)) {
          ;[this.dates[0], this.dates[1]] = [this.dates[1], this.dates[0]]
        }
      }
    },
  },
  created() {
    let tempDate = new Date()
    this.startTime[0] = String(tempDate.getHours())
    this.startTime[1] = String(tempDate.getMinutes())
    this.endTime[0] = String(tempDate.getHours())
    if (tempDate.getMinutes() + 30 >= 60) {
      if (tempDate.getHours() === 24) {
        this.endTime[0] = '00'
      }
      this.endTime[0] = String(tempDate.getHours() + 1)
      this.endTime[1] = String(tempDate.getMinutes() + 30 - 60)
    } else {
      this.endTime[1] = String(tempDate.getMinutes() + 30)
    }
    let tempMonth = ''
    let tempDay = ''
    if (String(tempDate.getMonth()).length === 1) {
      tempMonth += '0'
    }
    if (String(tempDate.getDate()).length === 1) {
      tempDay += '0'
    }

    this.dates.push(
      String(tempDate.getFullYear()) +
        '-' +
        tempMonth +
        String(tempDate.getMonth() + 1) +
        '-' +
        tempDay +
        String(tempDate.getDate()),
    )
  },
  mounted() {
    document.querySelector('.v-application').style.height = '350px'
    // v-application--wrap
    // document.querySelector('.v-application--wrap').style.display = 'block'
    document.querySelector('.v-application--wrap').style.minHeight = 0
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
    prevSet() {
      this.$emit('prevSet')
    },
    submit() {},
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set-thr.css"></style>
