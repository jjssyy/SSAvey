<template>
  <v-app class="notosanskr">
    <v-container>
      <v-toolbar dark class="mb-5" color="primary">
        결과비교: {{ this.originsurvey.title }}
      </v-toolbar>
      <v-row class="">
        <v-col class="d-flex justify-center" cols="12" sm="6">
          <v-card class="">
            <v-date-picker v-model="dates" width="75%" range></v-date-picker>
            <v-text-field
              v-model="dateRangeText"
              label="Date range"
              prepend-icon="mdi-calendar"
              readonly
            ></v-text-field>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6">
          <v-card>
            <v-list dense class="overflow-y-auto" style="height: 472px;">
              <v-list-item-group v-model="selectedSurvey">
                <v-subheader
                  >설정기간 내 설문목록{{ selectedSurvey }}</v-subheader
                >
                <v-divider></v-divider>
                <template v-for="(target, index) in targetlist">
                  <v-list-item v-if="target.sid != sid" :key="index">
                    <v-list-item-content @click="addtargetresult(target.sid)">
                      <v-list-item-title>{{ target.title }}</v-list-item-title>
                      <!-- <v-list-item-subtitle>
                        {{ target.explain }}
                      </v-list-item-subtitle> -->
                      <v-list-item-subtitle
                        >{{ target.start_date }} ~
                        {{ target.end_date }}</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
              </v-list-item-group>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <div class="d-flex justify-center">
      <v-btn color="warning" style="width: 50%;" @click="makeChartData"
        >비교하기</v-btn
      >
    </div>
    <div v-if="this.chartDatas.length != 0">
      <div
        class="mx-5 mt-5"
        v-for="(item, index) in originsurvey.question"
        :key="index"
      >
        <div v-if="item.q_type != 'SHORT'">
          <h2>{{ item.q_number }}. {{ item.q_explanation }}</h2>
          <div v-if="item.q_type != 'SHORT'" id="chart" :key="index">
            <apexchart
              type="bar"
              height="430"
              :options="chartDatas[index].chartOptions"
              :series="chartDatas[index].series"
            ></apexchart>
          </div>
        </div>
      </div>
    </div>
    <!-- 조회기간: {{ this.dates }}<br /><br />
    템플릿아이디: {{ this.tid }}<br /><br />
    원본결과: {{ originsurvey }}<br /><br />
    타겟결과: {{ targetresult }}<br /><br /> -->
  </v-app>
</template>
<script>
import SurveyApi from '@/api/SurveyApi'
import VueApexCharts from 'vue-apexcharts'

export default {
  components: {
    apexchart: VueApexCharts,
  },
  data() {
    return {
      totaldata: [],
      originsurvey: [],
      originresult: [],
      targetlist: [],
      targetresult: [],
      dates: [],
      chartDatas: [],
      targetsit: '',
      sid: this.$route.params.sid,
      tid: '',
      selectedSurvey: '',
      rows: '',
      page: 1,
    }
  },
  methods: {
    makeChartData() {
      const chartDatas = []
      const questions = this.originsurvey.question
      const originAnswers = this.originresult
      const targetAnswers = this.targetresult
      for (let i = 0; i < questions.length; i++) {
        if (questions[i].q_type == 'SHORT') continue

        const chartData = {
          series: [],
          chartOptions: {
            chart: {
              type: 'bar',
              height: 430,
            },
            plotOptions: {
              bar: {
                horizontal: false,
                dataLabels: {
                  position: 'top',
                },
              },
            },
            dataLabels: {
              enabled: true,
              offsetX: -6,
              style: {
                fontSize: '12px',
                colors: ['#fff'],
              },
            },
            stroke: {
              show: true,
              width: 1,
              colors: ['#fff'],
            },
            tooltip: {
              shared: true,
              intersect: false,
            },
            xaxis: {
              categories: [],
            },
          },
        }

        const options = questions[i].q_option
        const originDataMap = new Map()
        const targetDataMap = new Map()

        for (let j = 0; j < options.length; j++) {
          if (!options[j].short_answer) {
            chartData.chartOptions.xaxis.categories.push(
              options[j].o_explanation,
            )

            originDataMap.set(options[j].o_number, 0)
            targetDataMap.set(options[j].o_number, 0)
          } else {
            chartData.chartOptions.xaxis.categories.push('기타')

            originDataMap.set(-1, 0)
            targetDataMap.set(-1, 0)
          }
        }

        const originDetailAnswers = originAnswers[i].answers

        console.log(originDetailAnswers)

        originDetailAnswers.forEach(answerList => {
          answerList.answer.forEach(answer => {
            if (isNaN(answer)) {
              originDataMap.set(-1, originDataMap.get(-1) + 1)
            } else {
              originDataMap.set(
                Number(answer),
                originDataMap.get(Number(answer)) + 1,
              )
            }
          })
        })

        const targetDetailAnswers = targetAnswers[i].answers

        targetDetailAnswers.forEach(answerList => {
          answerList.answer.forEach(answer => {
            if (isNaN(answer)) {
              targetDataMap.set(-1, targetDataMap.get(-1) + 1)
            } else {
              targetDataMap.set(
                Number(answer),
                targetDataMap.get(Number(answer)) + 1,
              )
            }
          })
        })

        let originArrangedAnswers = {
          data: [],
          name: '원본',
        }
        let targetArrangedAnswers = {
          data: [],
          name: '비교',
        }

        for (let value of originDataMap.values()) {
          originArrangedAnswers.data.push(value)
        }
        for (let value of targetDataMap.values()) {
          targetArrangedAnswers.data.push(value)
        }

        chartData.series.push(originArrangedAnswers)
        chartData.series.push(targetArrangedAnswers)

        chartDatas.push(chartData)
      }
      this.chartDatas = chartDatas
    },
    compare() {
      console.log('비교하기')
    },
    addtargetresult(targetsid) {
      console.log('targetresult에 추가')
      SurveyApi.loadSurveyResult(
        targetsid,
        res => {
          console.log('타겟답안배열만들기')
          this.targetresult = res.data.data.answers
        },
        err => {
          console.log(err)
        },
      )
    },
  },
  watch: {
    dates() {
      if (this.dates.length == 2) {
        SurveyApi.getTemplateSurvey(
          this.tid,
          this.dates[0],
          this.dates[1],
          res => {
            console.log('기간내 데이터 불러오기 성공')
            this.targetlist = res.data.data
          },
          err => {
            console.log(err)
          },
        )
      }
    },
  },
  created() {
    console.log(this.sid)
    SurveyApi.loadSurveyResult(
      this.sid,
      res => {
        console.log(res)
        this.originsurvey = res.data.data
        this.originresult = res.data.data.answers
        this.tid = res.data.data.template
      },
      err => {
        console.log(err)
      },
    )
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ')
    },
  },
}
</script>
<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);
.notosanskr * {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
