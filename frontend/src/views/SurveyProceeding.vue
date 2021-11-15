<template>
  <v-app>
    <v-card width="600" class="mx-auto">
      <v-toolbar color="black" dark>
        <v-toolbar-title>진행중인 설문리스트</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>

      <v-list three-line v-if="surveys.length >= 2">
        <template v-for="(item, index) in surveys">
          <v-subheader
            v-if="item.header"
            :key="index"
            v-text="item.header"
          ></v-subheader>

          <v-divider
            v-else-if="item.divider"
            :key="index + 1"
            :inset="item.inset"
          ></v-divider>
          <v-list-item v-else :key="index + 2">
            <v-list-item-content>
              <v-list-item-title v-html="item.title"></v-list-item-title>
              <v-list-item-subtitle
                style="
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                "
                v-html="item.explain"
              ></v-list-item-subtitle>
              <v-list-item-subtitle>
                기간: {{ item.start_date }} ~ {{ item.end_date }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              설문 종류
              <v-btn
                v-if="answersurvey != null && answersurvey.includes(item.sid)"
                @click="gotothissurvey(item.sid)"
                icon
              >
                <v-icon>mdi-check</v-icon>
              </v-btn>
              <v-btn v-else @click="gotothissurvey(item.sid)" icon>
                <v-icon>mdi-arrow-right</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>
        </template>
      </v-list>
      <v-list v-else three-line>
        <template>
          <v-subheader v-text="'진행중인 설문이 없습니다.'"></v-subheader>
        </template>
      </v-list>
      <v-pagination v-model="page" :length="rows"></v-pagination>
    </v-card>
  </v-app>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'

export default {
  data: () => ({
    surveys: [],
    answersurvey: [],
    rows: 2,
    page: 1,
  }),
  methods: {
    gotothissurvey(sid) {
      console.log(sid)
      this.$router.push(`/answer/survey/${sid}`)
    },
  },
  watch: {
    page() {
      SurveyApi.getCertainStateSurveys(
        'PROCEEDING',
        this.$store.state.uid,
        this.page - 1,
        res => {
          this.rows = res.data.Pagecount
          this.surveys = res.data.data
          this.surveys.push({ divider: true, inset: true })
        },
        () => {},
      )
    },
  },
  created() {
    // this.answersurvey = this.$store.state.user.answer_survey
    SurveyApi.getCertainStateSurveys(
      'PROCEEDING',
      this.$store.state.uid,
      this.page - 1,
      res => {
        console.log(res.data.data)
        console.log(res.data.Pagecount)
        this.rows = res.data.Pagecount
        this.surveys = res.data.data
        this.surveys.push({ divider: true, inset: true })
      },
      () => {},
    )
  },
}
</script>

<style scoped></style>
