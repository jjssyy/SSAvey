<template>
  <v-app>
    <v-container>
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
                <template>
                  <v-subheader
                    >설정기간 내 설문목록{{ selectedSurvey }}</v-subheader
                  >
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle>시작</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                      {{ sid }}
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
      <v-btn color="warning" style="width: 50%;" @click="compare"
        >비교하기</v-btn
      >
    </div>
  </v-app>
</template>
<script>
// import SurveyApi from '@/api/SurveyApi'
export default {
  data() {
    return {
      totaldata: [],
      originsurvey: [],
      dates: [],
      sid: this.$route.params.sid,
      selectedSurvey: '',
      rows: '',
      page: 1,
    }
  },
  methods: {
    compare() {
      console.log('비교하기')
    },
  },
  created() {
    // SurveyApi.loadSurveyResult(sid)
    // SurveyApi.getCertainStateSurveys(
    //   'COMPLETED',
    //   this.$store.state.uid,
    //   this.page - 1,
    //   res => {
    //     console.log(res)
    //     this.totaldata = res.data.data
    //     this.rows = res.data.Pagecount
    //     for (const item of res.data.data) {
    //       if (item.title !== null) {
    //         this.surveys.push(
    //           // item.title + '[' + item.start_date + item.end_date + ']',
    //           item.title,
    //         )
    //       }
    //     }
    //   },
    //   () => {},
    // )
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ')
    },
  },
}
</script>
