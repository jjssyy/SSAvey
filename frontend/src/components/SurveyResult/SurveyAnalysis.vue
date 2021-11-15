<template>
  <div class="analysis-container">
    <section class="analysis-item">
      <div class="analysis-title">
        <p class="main-title">
          {{ survey.title }}
        </p>
        <div class="sub-title">
          <button>
            <p>Excel로 반환</p>
            <i class="fas fa-reply"></i>
          </button>
        </div>
      </div>
      <div class="analysis-explain">
        <p class="main-explain">
          {{ survey.explain }}
        </p>
        <div class="sub-explain">
          <p>지난 설문과 비교</p>
          <span class="center">
            <i class="fas fa-angle-double-right"></i>
          </span>
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
              :series="serie"
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
export default {
  components: {
    apexchart: VueApexCharts,
  },
  // props: {
  //   survey: {
  //     type: Object,
  //     required: true,
  //   },
  // },
  data() {
    return {
      // surveyLabelSerie: [[['유튜브', '싸피', '블로그'],[30, 20, 10]],[[],[]]]
      serie: [44, 55, 13, 43, 22],
      chartOptions: {
        chart: {
          width: '100%',
          type: 'pie',
        },
        legend: {
          position: 'bottom',
        },
        labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
      },
    }
  },
  methods: {},
  created() {},
}
</script>
<style scoped src="./../../css/survey/survey/survey-analysis.css"></style>
