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
        <div class="sub-explain">
          <button @click="gotoCompare(survey.sid)">
            <span class="center">
              <p>지난 설문과 비교</p>
              <i class="fas fa-angle-double-right"></i>
            </span>
          </button>
        </div>
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
            <button class="sub-item">
              <p class="subtitle-item">상세보기</p>
              <span class="center">
                <i class="fas fa-angle-double-right"></i>
              </span>
            </button>
          </div>
          <div class="item-one" v-if="element.q_type != 'SHORT'">
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
            v-if="element.q_type != 'SHORT' && isQueOfShort[index]"
          >
            <p>워드클라우드 넣을거임</p>
          </div>
          <div v-if="element.q_type == 'SHORT'">
            <p>주관식일 때 워드클라우드</p>
          </div>
        </div>
        <!-- 주관식일 때 워드클라우드-->
      </section>
    </div>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import ExportToExcel from '@/components/SurveyResult/exportToExcel'
export default {
  components: {
    apexchart: VueApexCharts,
    ExportToExcel,
  },
  props: {
    survey: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      isQueOfShort: [],
      surveyLabelSerie: null,
      // surveyLabelSerie: [[['유튜브', '싸피', '블로그'],[30, 20, 10]],[[],[]]]
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
    }
  },
  methods: {
    gotoCompare(sid) {
      this.$router.push('/compare/' + sid)
    },
    // 해당 문항에 기타가 포함되어 있는지 확인.
    findShortOfQue() {
      console.log('findShortOfQue')
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
      console.log(this.isQueOfShort)
    },
    initLS() {
      console.log('initLS')
      // 배열 만들기
      this.surveyLabelSerie = Array.from(
        { length: this.survey.question.length },
        () => [[]],
      )
      console.log('1')
      // labels.length 만큼 1번째 index에 0 push 하기
      this.survey.question.forEach((q, index) => {
        this.surveyLabelSerie[index].push(
          Array.from({ length: q.q_option.length }, () => 0),
        )
      })
      console.log('2')
      // label 데이터 넣기
      this.survey.question.forEach((q, qIndex) => {
        q.q_option.forEach(o => {
          this.surveyLabelSerie[qIndex][0].push(o.o_explanation)
        })
      })
      console.log('3')
      // label에 해당하는 숫자 sum
      this.survey.answers.forEach(a => {
        a.answers.forEach(b => {
          b.answer.forEach(c => {
            console.log(c)
            this.surveyLabelSerie[parseInt(a.q_number) - 1][1][
              parseInt(c) - 1
            ]++
          })
        })
      })
      console.log('4')
      //chartOptions 세팅
      for (let i = 0; i < this.surveyLabelSerie.length; i++) {
        let temp = this.tempChartOption
        temp.labels = this.surveyLabelSerie[i][0]
        this.chartOptions.push(temp)
      }
      console.log(this.surveyLabelSerie)
    },
  },
  created() {
    console.log('???', this.survey)
    this.findShortOfQue()
    this.initLS()
  },
}
</script>
<style scoped src="./../../css/survey/survey/survey-analysis.css"></style>
