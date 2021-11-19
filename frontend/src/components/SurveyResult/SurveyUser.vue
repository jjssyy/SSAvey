<template>
  <div class="user-container">
    <section class="user-item">
      <p class="item-title">설문 완료 비율</p>
    </section>
    <section class="user-item">
      <p class="percent-item">{{ percentComplete }}%</p>
      <!-- 설문 완료자 비율 -->
      <apexchart
        type="donut"
        width="100%"
        height="350px"
        :options="chartOptions"
        :series="[series[0].show, series[1].show]"
      ></apexchart>
    </section>
    <section class="user-item">
      <div class="item-title-two">
        <p>설문 미완료자</p>
        <button class="center" @click="reset()">
          <i class="fas fa-sync-alt"></i>
        </button>
      </div>
      <div class="item-alarm">
        <p>현재 {{ countInComplete }}명</p>
        <div>
          <button
            v-if="countInComplete != 0 && !survey.anony"
            class="item-alarm-button"
            @click="sendPeopleAlarm()"
          >
            <p>Mattermost로 전체 알림</p>
            <i class="fas fa-bell"></i>
          </button>
        </div>
      </div>
      <div class="item-list" v-if="!survey.anony">
        <!-- 반복문 -->
        <div
          class="item-list-user"
          v-for="(user, index) in survey.incomplete"
          :key="index"
        >
          <div class="user-info">
            <p class="user-info-name">{{ user.name }}</p>
            <p v-if="user.generation">
              {{ user.generation }}기/{{ user.area }}/{{ user.group }}
            </p>
            <p v-else>{{ user.position }}</p>
          </div>
          <div class="user-alarm">
            <i class="fas fa-bell" @click="sendPersonAlarm(user)"></i>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import SurveyApi from '@/api/SurveyApi'
export default {
  components: {
    apexchart: VueApexCharts,
  },
  props: {
    survey: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      series: [{ show: 0 }, { show: 0 }],
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
  computed: {
    countInComplete() {
      return this.survey.incomplete.length
    },
    percentComplete() {
      let temp = Math.round(
        (this.survey.complete.length /
          (this.survey.incomplete.length + this.survey.complete.length)) *
          100,
      )
      return temp
    },
  },
  watch: {
    survey: function(newVal) {
      this.series[0].show = newVal.complete.length
      this.series[1].show = newVal.incomplete.length
    },
  },
  methods: {
    // 완료자, 미완료자 count
    initCount() {
      this.series[0].show = this.survey.complete.length
      this.series[1].show = this.survey.incomplete.length
    },
    sendPersonAlarm(user) {
      // 알림 보내고,
      let payload = {
        data: [user.uid],
        sid: this.$route.params.sid,
        uid: this.$store.state.uid,
      }
      SurveyApi.alarmSurveyResult(
        payload,
        res => {
          console.log(res)
          this.$swal({
            icon: 'success',
            text: `${user.name}님에게 MM 보냈습니다.`,
            target: '.component-2',
            showConfirmButton: false,
            timer: 1500,
          })
        },
        err => {
          console.log(err)
        },
      )
    },
    sendPeopleAlarm() {
      let temp = []
      this.survey.incomplete.forEach(user => {
        temp.push(user.uid)
      })
      let payload = {
        data: temp,
        sid: this.$route.params.sid,
        uid: this.$store.state.uid,
      }
      SurveyApi.alarmSurveyResult(
        payload,
        res => {
          console.log(res)
          this.$swal({
            icon: 'success',
            text: '전체에게 MM 보냈습니다.',
            target: '.component-2',
            showConfirmButton: false,
            timer: 1500,
          })
        },
        err => {
          console.log(err)
        },
      )
    },
    reset() {
      this.$emit('reset')
    },
  },
  created() {
    console.log('??')
    this.initCount()
  },
}
</script>

<style scoped src="./../../css/survey/survey/survey-user.css"></style>
