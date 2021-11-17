<template>
  <v-app class="notosanskr">
    <v-card width="600" class="mx-auto" elevation="1">
      <v-toolbar color="#4E7AF5" dark elevation="1">
        <v-toolbar-title>진행완료 설문리스트</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>

      <v-list three-line v-if="surveys.length >= 2">
        <template v-for="(item, index) in surveys">
          <v-subheader
            v-if="item.header"
            :key="index"
            v-text="item.header"
          ></v-subheader>

          <v-divider v-else-if="item.divider" :key="index + 1"></v-divider>

          <v-list-item v-else :key="index">
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
                기간 :
                {{
                  item.start_date.substring(0, 10) +
                    ' ' +
                    item.start_date.substring(11, 16)
                }}
                ~
                {{
                  item.end_date.substring(0, 10) +
                    ' ' +
                    item.end_date.substring(11, 16)
                }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              <v-dialog width="500">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    v-if="answer_surveys.includes(item.sid)"
                    @click="loadmyresult(item.sid)"
                    v-bind="attrs"
                    v-on="on"
                    dark
                    color="primary"
                    elevation="0"
                  >
                    <v-icon>mdi-text-box-search</v-icon>
                  </v-btn>
                  <v-btn v-else @click="alertinfo" color="error" elevation="0">
                    <v-icon>mdi-alarm</v-icon>
                  </v-btn>
                </template>
                <v-card width="1000" class="mx-auto">
                  <v-toolbar color="#4E7AF5" dark>
                    <v-toolbar-title>내 응답결과 보기</v-toolbar-title>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                  <v-list>
                    <template>
                      <v-subheader>
                        <strong
                          >설문제목: {{ myresult.title }}</strong
                        ></v-subheader
                      >
                      <v-subheader
                        >{{ myresult.explain }} <br />시작
                        {{ myresult.start_date }} ~ 종료
                        {{ myresult.start_date }}</v-subheader
                      >

                      <v-list-item
                        v-for="(ques, index) in myresult.answer_question"
                        :key="index"
                      >
                        <v-list-item-content>
                          <v-list-item-title>
                            {{ ques.q_number }}. {{ ques.q_explanation }}
                          </v-list-item-title>
                          <v-radio-group
                            v-model="ques.answer[0]"
                            v-if="ques.q_type == 'SINGLE'"
                            class="mx-5"
                            disabled
                          >
                            <v-radio
                              v-for="(answer, r_index) in ques.q_option"
                              :key="r_index + 2"
                              :label="`${answer.o_explanation}`"
                              :value="`${answer.o_number}`"
                            >
                            </v-radio>
                          </v-radio-group>
                          <v-container
                            class="mx-2"
                            v-if="ques.q_type == 'MULTIPLE'"
                          >
                            <v-checkbox
                              v-for="(answer, c_index) in ques.q_option"
                              :key="c_index + 3"
                              class="mt-0 pt-0"
                              :label="`${answer.o_explanation}`"
                              :value="`${answer.o_number}`"
                              v-model="ques.answer"
                              disabled
                            >
                            </v-checkbox>
                          </v-container>
                          <v-container v-if="ques.q_type == 'SHORT'">
                            <v-textarea
                              auto-grow
                              counter
                              background-color="grey lighten-4"
                              color="cyan"
                              label="답변을 입력해주세요."
                              v-model="ques.answer"
                              disabled
                            ></v-textarea>
                          </v-container>
                        </v-list-item-content>
                      </v-list-item>
                    </template>
                  </v-list>
                </v-card>
              </v-dialog>
            </v-list-item-action>
          </v-list-item>
          <v-divider :key="index + 1"></v-divider>
        </template>
      </v-list>
      <v-list v-else three-line>
        <template>
          <v-subheader v-text="'완료된 설문이 없습니다.'"></v-subheader>
        </template>
      </v-list>
      <v-pagination v-model="page" :length="rows"></v-pagination>
    </v-card>
  </v-app>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'
import AnswerApi from '@/api/AnswerApi'

export default {
  components: {},
  data: () => ({
    surveys: [],
    myresult: [],
    rows: 2,
    page: 1,
    answer_surveys: [],
  }),
  methods: {
    alertinfo() {
      console.log('경고창 만들거임')
      alert('응답하지 못한 설문입니다.')
    },
    loadmyresult(sid) {
      let temp = {
        id: this.$store.state.uid,
        sid: sid,
      }
      AnswerApi.loadSurveyResponse(
        temp,
        res => {
          this.myresult = res.data.data
        },
        err => {
          console.log(err)
        },
      )
    },
  },
  watch: {
    page() {
      SurveyApi.getCertainStateSurveys(
        'COMPLETED',
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
    this.answer_surveys = this.$store.state.user.answer_survey
    SurveyApi.getCertainStateSurveys(
      'COMPLETED',
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
}
</script>

<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
