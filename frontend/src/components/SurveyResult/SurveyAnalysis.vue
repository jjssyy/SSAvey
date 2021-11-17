<template>
  <div class="analysis-container">
    <section class="analysis-item">
      <div class="analysis-title">
        <p class="main-title">
          {{ survey.title }}
        </p>
        <export-to-excel v-if="survey" :survey="survey"></export-to-excel>
      </div>
      <div class="analysis-explain">
        <p class="main-explain">
          {{ survey.explain }}
        </p>
        <button class="sub-explain" @click="gotoCompare(survey.sid)">
          <p>지난 설문과 비교</p>
          <span class="center">
            <i class="fas fa-angle-double-right"></i>
          </span>
        </button>
      </div>
    </section>
    <div class="temp ">
      <!-- v-for 돌리기 -->
      <section
        class="analysis-item-two"
        v-for="(element, index) in survey.question"
        :key="index"
      >
        <!-- 단일, 복수일 때 도넛차트-->
        <div class="item-container">
          <div class="question-title">
            <p class="title-item">
              {{ index + 1 }}. {{ element.q_explanation }}
            </p>
            <button class="sub-item" @click="openModal(index)">
              <p class="subtitle-item">상세보기</p>
              <span class="center">
                <i class="fas fa-angle-double-right"></i>
              </span>
            </button>
          </div>
          <div
            class="item-one"
            v-if="element.q_type != 'SHORT' && isValueInQue[index]"
          >
            <apexchart
              type="pie"
              width="100%"
              :options="chartOptions[index]"
              :series="surveyLabelSerie[index][1]"
            ></apexchart>
          </div>
          <!-- 단일, 복수에서 기타가 있을 경우 워드클라우드-->
          <div
            class="item-two"
            v-if="
              element.q_type != 'SHORT' &&
                isQueOfShort[index] &&
                isValueInQue[index]
            "
          >
            <vue-word-cloud
              style="width: 100%; cursor: pointer; height: 250px"
              :words="wordsOne[index]"
              :color="color"
              font-family="Roboto"
              font-weight="Bold"
              :font-size-ratio="Ratio"
              :rotation="rotation"
            >
            </vue-word-cloud>
          </div>
          <div v-if="element.q_type != 'SHORT' && !isValueInQue[index]">
            <p>(객관식)설문 대상자가 응답하지 않았습니다.</p>
          </div>
          <div
            class="item-one"
            v-if="element.q_type == 'SHORT' && isValueInQue[index]"
          >
            <vue-word-cloud
              style="width: 100%; cursor: pointer; height: 250px"
              :words="wordsTwo[index]"
              :color="color"
              font-family="Roboto"
              font-weight="Bold"
              :font-size-ratio="Ratio"
              :rotation="rotation"
            >
            </vue-word-cloud>
          </div>
          <div v-if="element.q_type == 'SHORT' && !isValueInQue[index]">
            <p>(주관식)설문 대상자가 응답하지 않았습니다.</p>
          </div>
        </div>
        <!-- 주관식일 때 워드클라우드-->
      </section>
    </div>
    <!-- 상세보기 modal -->
    <div v-if="isOpenModal" class="survey-modal">
      <div class="survey-container">
        <div class="survey-container-title">
          <p class="modal-sub-title">상세응답</p>
          <button class="modal-center" @click="closeModal">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <hr class="hr-line" />
        <div
          class="detail-container"
          v-if="this.survey.question[rememberIndex].q_type != 'SHORT'"
        >
          <!-- 반복문 -->
          <div
            class="detail-percent"
            v-for="(element, index) in resultOne"
            :key="index"
          >
            <p class="percent-item">{{ element[0] }}</p>
            <p class="percent-item">{{ element[1] }}%</p>
            <!-- 기타일 경우는 안보이게 한다. -->
            <select
              class="detail-select-box"
              v-if="element[2].length > 1 && !survey.anony"
            >
              <option>{{ element[2][0] }} 외 {{ element[2].length }}명</option>
              <option
                disabled
                v-for="(element2, index2) in element[2]"
                :key="index2"
                >{{ element2 }}</option
              >
            </select>
            <select
              class="detail-select-box"
              v-if="element[2].length == 1 && !survey.anony"
            >
              <option>{{ element[2][0] }}</option>
            </select>
            <select
              class="detail-select-box"
              v-if="element[2].length == 0 && !survey.anony"
            >
              <option>없음</option>
            </select>
          </div>
        </div>
        <!-- 주관식일 경우 -->
        <div class="detail-container-two" v-else>
          <!-- 반복문 -->
          <div
            class="detail-short"
            v-for="(element, index) in resultOne"
            :key="index"
          >
            <p class="short-percent-item">{{ element[0] }}</p>
            <p class="short-text-box">{{ element[1] }}</p>
          </div>
        </div>
        <!-- 기타 있을 경우에만 -->
        <div
          class="survey-container-title"
          v-if="this.survey.question[rememberIndex].q_type != 'SHORT'"
        >
          <p class="modal-sub-title">기타 상세 응답</p>
        </div>
        <hr
          class="hr-line"
          v-if="this.survey.question[rememberIndex].q_type != 'SHORT'"
        />
        <div
          class="detail-container"
          v-if="
            isQueOfShort[rememberIndex] &&
              this.survey.question[rememberIndex].q_type != 'SHORT'
          "
        >
          <!-- 반복문 -->
          <div
            class="detail-percent"
            v-for="(element, index) in resultTwo"
            :key="index"
          >
            <p class="percent-item">{{ index }}</p>
            <p class="percent-item"><span> </span></p>
            <select
              class="detail-select-box"
              v-if="element.length > 1 && !survey.anony"
            >
              <option>{{ element[0] }} 외 {{ element.length }}명</option>
              <option
                disabled
                v-for="(element2, index2) in element"
                :key="index2"
                >{{ element2 }}</option
              >
            </select>
            <select
              class="detail-select-box"
              v-if="element.length == 1 && !survey.anony"
            >
              <option>{{ element2[0] }}</option>
            </select>
            <select
              class="detail-select-box"
              v-if="element.length == 0 && !survey.anony"
            >
              <option>없음</option>
            </select>
          </div>
        </div>
        <div
          class="detail-container"
          v-if="
            !isQueOfShort[rememberIndex] &&
              this.survey.question[rememberIndex].q_type != 'SHORT'
          "
        >
          <p>기타가 없습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
// import Chance from 'chance'
import VueWordCloud from 'vuewordcloud'
import ExportToExcel from '@/components/SurveyResult/exportToExcel'
let Chance = require('chance')
let chance = new Chance()
export default {
  components: {
    apexchart: VueApexCharts,
    ExportToExcel,
    VueWordCloud,
  },
  props: {
    survey: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      // surveyLabelSerie: [[['유튜브', '싸피', '블로그'],[30, 20, 10]],[[],[]]]
      rememberIndex: null,
      resultOne: null,
      resultTwo: null,
      isOpenModal: false,
      isValueInQue: [],
      isQueOfShort: [],
      isShortQue: [],
      surveyLabelSerie: null,
      chartOptions: [],
      tempChartOption: {
        chart: {
          width: '100%',
          type: 'pie',
        },
        legend: {
          position: 'bottom',
        },
        labels: null,
      },
      // wordCloud
      Ratio: 3,
      wordsOne: [],
      wordsTwo: [],
      colorItems: [
        [
          '#FEDE00',
          '#B4F8C8',
          '#6AB8EE',
          '#ff4e69',
          '#BCECE0',
          '#4C5270',
          '#F652A0',
          '#36EEE0',
          '#461e47',
          '#DB1F48',
        ],
      ],
      rotationItemIndex: null,
      rotationItems: [
        {
          value: 0,
        },
        {
          value: function() {
            let chance = new Chance()
            return chance.pickone([0, 3 / 4])
          },
        },
        {
          value: function() {
            let chance = new Chance()
            return chance.pickone([0, 1 / 8, 3 / 4, 7 / 8])
          },
        },
      ],
    }
  },
  methods: {
    gotoCompare(sid) {
      this.$router.push('/compare/' + sid)
    },
    // 해당 문항에 기타가 포함되어 있는지 확인.
    findShortOfQue() {
      this.survey.question.forEach(element => {
        let check = 0
        for (let i = 0; i < element.q_option.length; i++) {
          if (element.q_option[i].short_answer) {
            this.isQueOfShort.push(true)
            check = 1
            break
          }
        }
        if (check == 0) {
          this.isQueOfShort.push(false)
        }
      })
      console.log('ㄴ', this.isQueOfShort)
    },
    initLS() {
      // 배열 만들기
      this.surveyLabelSerie = Array.from(
        { length: this.survey.question.length },
        () => [[]],
      )
      // labels.length 만큼 1번째 index에 0 push 하기
      this.survey.question.forEach((q, index) => {
        this.surveyLabelSerie[index].push(
          Array.from({ length: q.q_option.length }, () => 0),
        )
      })
      // label 데이터 넣기
      this.survey.question.forEach((q, qIndex) => {
        q.q_option.forEach(o => {
          this.surveyLabelSerie[qIndex][0].push(o.o_explanation)
        })
      })
      // label에 해당하는 숫자 sum
      this.survey.answers.forEach((a, index) => {
        a.answers.forEach(b => {
          b.answer.forEach(c => {
            if (this.survey.question[index].q_type != 'SHORT') {
              this.surveyLabelSerie[parseInt(a.q_number) - 1][1][
                parseInt(c) - 1
              ]++
            } else {
              this.surveyLabelSerie[parseInt(a.q_number) - 1][1].push(c)
            }
          })
        })
      })
      console.log('s', this.surveyLabelSerie)
      // surveyLabelSerie에서 해당 index에서 [1]인 serie의 모든 값이 0인지 아닌지. 모두 0이면 false
      this.surveyLabelSerie.forEach(element1 => {
        let check = false
        for (let i = 0; i < element1[1].length; i++) {
          if (element1[1][i] != 0 && element1[1][i] != '') {
            check = true
          }
        }
        this.isValueInQue.push(check)
      })
      console.log('ㅇ', this.isValueInQue)
      //chartOptions 세팅
      for (let i = 0; i < this.surveyLabelSerie.length; i++) {
        let temp = JSON.parse(JSON.stringify(this.tempChartOption))
        temp.labels = this.surveyLabelSerie[i][0]
        this.chartOptions.push(temp)
      }
      console.log(this.surveyLabelSerie)

      // 주관식 문항이면 true
      this.isShortQue = Array.from(
        { length: this.survey.question.length },
        () => false,
      )
      this.survey.question.forEach((element, index) => {
        if (element.q_type == 'SHORT') {
          this.isShortQue[index] = true
        }
      })
      console.log('여기야', this.isShortQue)
    },
    initAll() {
      this.isValueInQue = []
      this.isQueOfShort = []
      this.surveyLabelSerie = null
      this.chartOptions = []
      this.isShortQue = []
    },
    // 상세보기 클릭시 데이터 정리
    openModal(index) {
      this.rememberIndex = index
      if (this.survey.question[index].q_type != 'SHORT') {
        let labelLength = this.surveyLabelSerie[index][0].length
        // 상세응답
        this.resultOne = Array.from({ length: labelLength }, () => ['', 0, []])
        for (let i = 0; i < labelLength; i++) {
          // 선택항목 넣기
          this.resultOne[i][0] = this.surveyLabelSerie[index][0][i]
          // % 넣기
          this.resultOne[i][1] = parseInt(
            (this.surveyLabelSerie[index][1][i] / this.survey.complete.length) *
              100,
          )
          // position과 name 합친거 push
          for (let j = 0; j < this.survey.answers.length; j++) {
            if (this.survey.answers[j].q_number == `${parseInt(index) + 1}`) {
              for (let k = 0; k < this.survey.answers[j].answers.length; k++) {
                if (
                  this.survey.answers[j].answers[k].answer.includes(`${i + 1}`)
                ) {
                  this.resultOne[i][2].push(
                    `${this.survey.answers[j].answers[k].position} ${this.survey.answers[j].answers[k].name}`,
                  )
                }
              }
              break
            }
          }
        }
        // this.resultTwo = {
        //   JavaScript: ['교육생 민찬우', '교육생 모진혁'],
        //   Java: ['', ''],
        // }
        // 기타응답
        this.resultTwo = {}
        // 해당 index+1문항이 기타가 있냐?
        if (this.isQueOfShort[index]) {
          // 있으면 일단 해당 문항으로 가자 -> 기타가 몇번 선택지야?
          let shortIndex = this.surveyLabelSerie[index][0].indexOf('기타')
          this.survey.answers[index].answers.forEach(element => {
            if (element.answer[0] == `${shortIndex + 1}`) {
              // 이것을 key로
              if (this.resultTwo.hasOwnProperty(element.answer[1])) {
                this.resultTwo[`${element.answer[1]}`].push(
                  `${element.position} ${element.name}`,
                )
              } else {
                this.resultTwo[`${element.answer[1]}`] = [
                  `${element.position} ${element.name}`,
                ]
              }
            }
          })
        }
      } else {
        // 주관식일 경우. [['유저이름', '주관식'],...]
        this.resultOne = Array.from(
          { length: this.survey.answers[index].answers.length },
          () => ['', ''],
        )
        this.survey.answers[index].answers.forEach((element, i) => {
          this.resultOne[i][0] = `${element.position} ${element.name}`
          this.resultOne[i][1] = element.answer[0]
        })
      }
      console.log(this.resultOne)
      console.log(this.resultTwo)
      this.isOpenModal = !this.isOpenModal
    },
    closeModal() {
      this.isOpenModal = !this.isOpenModal
    },
    wordForm() {
      this.rotationItemIndex = chance.integer({
        min: 0,
        max: this.rotationItems.length - 1,
      })
    },
    wordsCreate() {
      // 초기화
      // [[['aa', 2],['bb', 2],['cc', 1],['dd', 4]],[[],[],..],...]
      this.wordsOne = Array.from({ length: this.isQueOfShort.length }, () => [])
      // 기타 워드클라우드
      this.isQueOfShort.forEach((element1, index1) => {
        let temp = {}
        // 기타가 있으면 temp를 이용해서 개수파악
        if (element1) {
          this.survey.answers[index1].slicedWords.forEach(element2 => {
            if (temp.hasOwnProperty(element2)) {
              temp[element2]++
            } else {
              temp[element2] = 1
            }
          })
          // temp={'aa': 3, 'b': 4}wordsOne에 넣기
          for (let i in temp) {
            this.wordsOne[index1].push([`${i}`, temp[i]])
          }
        }
      })
      // 이제 해당 객관식 문항이 기타가 포함하는지 안하는지 조사하고, 그 해당 문항에 word클라우드를 보여준다.
      // 주관식 워드클라우드 this.isShortQue
      this.wordsTwo = Array.from({ length: this.isQueOfShort.length }, () => [])
      this.isShortQue.forEach((element1, index1) => {
        let temp = {}
        if (element1) {
          this.survey.answers[index1].slicedWords.forEach(element2 => {
            if (temp.hasOwnProperty(element2)) {
              temp[element2]++
            } else {
              temp[element2] = 1
            }
          })
          // temp={'aa': 3, 'b': 4}wordsOne에 넣기
          for (let i in temp) {
            this.wordsTwo[index1].push([`${i}`, temp[i]])
          }
        }
      })
      console.log('hi')
      console.log(this.wordsOne)
      console.log(this.wordsTwo)
    },
  },
  computed: {
    color() {
      const colors = this.colorItems[0]
      return function() {
        return chance.pickone(colors)
      }
    },
    rotation() {
      const item = this.rotationItems[this.rotationItemIndex]
      return item.value
    },
  },
  created() {
    console.log(this.survey)
    this.findShortOfQue()
    this.initLS()
    this.wordForm()
    this.wordsCreate()
  },
  watch: {
    survey: function() {
      console.log('Watch!!')
      this.initAll()
      this.findShortOfQue()
      this.initLS()
      this.wordsCreate()
      this.wordForm()
    },
  },
}
</script>
<style scoped src="./../../css/survey/survey/survey-analysis.css"></style>
