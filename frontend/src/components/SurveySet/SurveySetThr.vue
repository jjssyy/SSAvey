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
            <input type="time" v-model="startFullTime" />
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
            <input type="time" v-model="endFullTime" />
          </div>
        </div>
      </div>
    </section>
    <div class="bottom-btn">
      <button @click="prevSet" class="prev-btn">
        <i class="fas fa-chevron-left fa-lg"></i>
      </button>
      <button class="update-btn" @click="submit">배포하기</button>
    </div>
  </div>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'
export default {
  data() {
    return {
      startFullTime: '',
      endFullTime: '',
      test: null,
      test_list: [1, 2, 3],
      dates: [],
      dateNext: null,
      datePrev: null,
      startTime: ['00', '00'],
      endTime: ['00', '00'],
      isDisabled: true,
    }
  },
  watch: {
    dates: function(newVal) {
      // 달력에서 한번만 클릭을 하면 배열의 길이는 1이 된다.
      // 두번 클릭시에는 길이가 2가 된다.
      // 똑같은 날짜를 두번클릭하면 배열의 길이는 2가 되고, 0번째와 1번째 index의 value는 같다.
      // console.log(this.dates)
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
    this.startFullTime = `${this.startTime[0]}:${this.startTime[1]}`
    this.endFullTime = `${this.endTime[0]}:${this.endTime[1]}`
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
    document.querySelector(
      '.container-set .v-application--wrap',
    ).style.minHeight = 0
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
    submit() {
      if (this.isDisabled) {
        this.isDisabled = false
        let temp1 = null
        let temp2 = null
        if (this.dates.length == 1) {
          temp1 = 0
          temp2 = 0
        } else {
          temp1 = 0
          temp2 = 1
        }
        let tempDateOne = new Date()
        let tempDateTwo = new Date()
        //년, 월, 일 설정(start)
        tempDateOne.setFullYear(
          Number(this.dates[temp1].substring(0, 4)),
          Number(this.dates[temp1].substring(5, 7)),
          Number(this.dates[temp1].substring(8, 10)),
        )
        // 시간과 분 설정
        tempDateOne.setHours(Number(this.startFullTime.substring(0, 2)))
        tempDateOne.setMinutes(Number(this.startFullTime.substring(3, 5)))
        //년, 월, 일 설정(end)
        tempDateTwo.setFullYear(
          Number(this.dates[temp2].substring(0, 4)),
          Number(this.dates[temp2].substring(5, 7)),
          Number(this.dates[temp2].substring(8, 10)),
        )
        // 시간과 분 설정
        tempDateTwo.setHours(Number(this.endFullTime.substring(0, 2)))
        tempDateTwo.setMinutes(Number(this.endFullTime.substring(3, 5)))
        console.log(tempDateOne)
        console.log(tempDateTwo)

        let startResult = ''
        let endResult = ''
        // 년
        startResult += `${tempDateOne.getFullYear()}` + '-'
        endResult += `${tempDateTwo.getFullYear()}` + '-'
        //월
        startResult +=
          `${
            String(tempDateOne.getMonth()).length == 1
              ? '0' + String(tempDateOne.getMonth())
              : tempDateOne.getMonth()
          }` + '-'
        endResult +=
          `${
            String(tempDateTwo.getMonth()).length == 1
              ? '0' + String(tempDateTwo.getMonth())
              : tempDateTwo.getMonth()
          }` + '-'
        // 일
        startResult += `${
          String(tempDateOne.getDate()).length == 1
            ? '0' + String(tempDateOne.getDate())
            : tempDateOne.getDate()
        }T`
        endResult += `${
          String(tempDateTwo.getDate()).length == 1
            ? '0' + String(tempDateTwo.getDate())
            : tempDateTwo.getDate()
        }T`
        // 시간
        startResult +=
          `${
            String(tempDateOne.getHours()).length == 1
              ? '0' + String(tempDateOne.getHours())
              : tempDateOne.getHours()
          }` + ':'
        endResult +=
          `${
            String(tempDateTwo.getHours()).length == 1
              ? '0' + String(tempDateTwo.getHours())
              : tempDateTwo.getHours()
          }` + ':'
        // 분
        startResult +=
          `${
            String(tempDateOne.getMinutes()).length == 1
              ? '0' + String(tempDateOne.getMinutes())
              : tempDateOne.getMinutes()
          }` + ':00'
        endResult +=
          `${
            String(tempDateTwo.getMinutes()).length == 1
              ? '0' + String(tempDateTwo.getMinutes())
              : tempDateTwo.getMinutes()
          }` + ':00'
        this.$store.state.surveySet.survey.start_date = startResult
        this.$store.state.surveySet.survey.end_date = endResult
        // 최종 target과 incomplete 수정
        let tempTarget = new Set()
        for (let targetItem of this.$store.state.surveySet.survey.target) {
          targetItem.forEach(function(element) {
            tempTarget.add(element)
          })
        }
        // 최종 share 수정
        let tempShare = new Set()
        for (let shareItem of this.$store.state.surveySet.survey.share) {
          shareItem.forEach(function(element) {
            tempShare.add(element)
          })
        }
        this.$store.state.surveySet.survey.target = Array.from(tempTarget)
        this.$store.state.surveySet.survey.incomplete = Array.from(tempTarget)
        this.$store.state.surveySet.survey.share = Array.from(tempShare)
        this.$store.state.surveySet.survey.share.push(this.$store.state.uid)
        SurveyApi.makeSurvey(
          this.$store.state.surveySet.survey,
          res => {
            console.log(res)
            let payload = {
              isWriting: false,
              isClkUpdate: false,
              survey: {
                use_template: null,
              },
            }
            this.$store.commit('resetSurveyDragThing', [])
            this.$store.commit('setSurveySet', payload)
            if (res.data.state == 'EXPECTED') {
              this.$swal({
                icon: 'success',
                title: '설문 생성을 완료하였습니다.',
                // target: '.container-set',
                position: 'center-center',
                showConfirmButton: false,
                timer: 1500,
              })
              this.isDisabled = true
              setTimeout(() => {
                this.$router.push({ name: 'MySurveyExpected' })
              }, 1000)
            }
            if (res.data.state == 'PROCEEDING') {
              this.$swal({
                icon: 'success',
                title: '설문 생성을 완료하였습니다.',
                // target: '.container-set',
                position: 'center-center',
                showConfirmButton: false,
                timer: 1500,
              })
              this.isDisabled = true
              setTimeout(() => {
                this.$router.push({ name: 'MySurveyProceeding' })
              }, 1000)
            }
            if (res.data.state == 'COMPLETED') {
              this.$swal({
                icon: 'success',
                title: '설문 생성을 완료하였습니다.',
                // target: '.container-set',
                position: 'center-center',
                showConfirmButton: false,
                timer: 1500,
              })
              this.isDisabled = true
              setTimeout(() => {
                this.$router.push({ name: 'MySurveyCompleted' })
              }, 1000)
            }
          },
          err => {
            console.log(err)
          },
        )
      }
    },
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey-set-thr.css">
.container-set {
  position: relative;
}
</style>
