<template>
  <div class="user-container">
    <section class="user-item">
      <p class="item-title">설문 완료 비율</p>
    </section>
    <section class="user-item">
      <p class="percent-item">20%</p>
      <!-- 설문 완료자 비율 -->
      <apexchart
        type="donut"
        width="100%"
        height="350px"
        :options="chartOptions"
        :series="series"
      ></apexchart>
    </section>
    <section class="user-item">
      <div class="item-title-two">
        <p>설문 미완료자</p>
        <button class="center"><i class="fas fa-sync-alt"></i></button>
      </div>
      <div class="item-alarm">
        <p>현재 2명</p>
        <div>
          <button class="item-alarm-button">
            <p>Mattermost로 알림</p>
            <i class="fas fa-bell"></i>
          </button>
        </div>
      </div>
      <div class="item-list">
        <!-- 반복문 -->
        <div class="item-list-user">
          <div class="user-info">
            <p class="user-info-name">김대호</p>
            <p>5기/광주/1반</p>
          </div>
          <div class="user-alarm">
            <i class="fas fa-bell"></i>
          </div>
        </div>
        <div class="item-list-user">
          <div class="user-info">
            <p class="user-info-name">민찬우</p>
            <p>5기/광주/1반</p>
          </div>
          <div class="user-alarm">
            <i class="fas fa-bell"></i>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
export default {
  components: {
    apexchart: VueApexCharts,
  },
  data() {
    return {
      series: [44, 55],
      chartOptions: {
        chart: {
          width: 380,
          type: 'donut',
        },
        dataLabels: {
          enabled: false,
        },
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: 200,
              },
              legend: {
                show: false,
              },
            },
          },
        ],
        legend: {
          position: 'bottom',
          offsetY: 0,
          height: 30,
        },
        labels: ['완료자', '미완료자'],
        colors: ['#393e41', '#a1adb3'],
      },
    }
  },
  mounted() {},
}
</script>

<style scoped src="./../../css/survey/survey/survey-user.css"></style>
