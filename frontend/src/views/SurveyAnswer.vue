<template>
  <div class=".component-2">
    <v-app class="notosanskr">
      <v-card width="1000" class="mx-auto">
        <v-toolbar color="#4E7AF5" dark>
          <v-toolbar-title>{{ this.items.data.title }}</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <v-list>
          <template v-for="(item, index) in items">
            <v-subheader v-if="item.title" :key="index"
              >{{ item.explain }} <br />기간:
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
            </v-subheader>

            <v-list-item
              v-for="(ques, index) in item.question"
              :key="index + 1"
            >
              <v-list-item-content>
                <v-list-item-title>
                  {{ ques.q_number }}. {{ ques.q_explanation }}
                </v-list-item-title>
                <v-radio-group v-if="ques.q_type == 'SINGLE'" class="mx-5">
                  <div
                    v-for="(answer, r_index) in ques.q_option"
                    :key="r_index + 2"
                    :label="`${answer.o_explanation}`"
                    :value="`${answer.o_number}`"
                  >
                    <v-radio
                      :label="`${answer.o_explanation}`"
                      :value="`${answer.o_number}`"
                      @click="
                        checkSingle(index + 1, `${answer.o_number}`),
                          (select = `${answer.o_number}`)
                      "
                    ></v-radio>
                    <v-textarea
                      v-if="answer.short_answer"
                      auto-grow
                      counter
                      outlined
                      :rules="shortrules"
                      rows="2"
                      clearable
                      :disabled="select != `${answer.o_number}`"
                      clear-icon="mdi-close-circle"
                      @focusout="checkSingle(index + 1, `${answer.o_number}`)"
                    >
                    </v-textarea>
                  </div>
                </v-radio-group>
                <v-container class="mx-2" v-if="ques.q_type == 'MULTIPLE'">
                  <v-checkbox
                    v-for="(answer, c_index) in ques.q_option"
                    :key="c_index + 3"
                    class="mt-0 pt-0"
                    :label="`${answer.o_explanation}`"
                    :value="`${answer.o_number}`"
                    @click="checkMultiple(index + 1, `${answer.o_number}`)"
                  >
                  </v-checkbox>
                </v-container>
                <v-container v-if="ques.q_type == 'SHORT'">
                  <v-textarea
                    auto-grow
                    counter
                    :rules="shortrules"
                    :value="shortvalue"
                    v-model="short[index + 1 + '번']"
                    background-color="grey lighten-4"
                    color="cyan"
                    label="답변을 입력해주세요."
                    @focusout="checkShort(index + 1)"
                  ></v-textarea>
                </v-container>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list>
        <v-card-actions style="float: right;">
          <v-btn @click="saveresult" text color="primary accent-4">
            SUBMIT
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-app>
  </div>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'
import AnswerApi from '@/api/AnswerApi'

export default {
  data: () => ({
    shortrules: [
      v =>
        v ? (v.length <= 20 ? true : '최대 20자까지만 입력해주세요.') : false,
    ],
    short: {},
    sid: '',
    shortvalue: '',
    result: {},
    items: [],
    select: '',
    isDisabled: true,
  }),
  created() {
    console.log(this.$route.params.sid)
    SurveyApi.getSurvey(
      this.$route.params.sid,
      res => {
        console.log(res)
        this.items = res.data
        this.result = res.data.data
      },
      err => {
        console.log(err)
      },
    )
  },
  computed: {
    resultCompute() {
      return this.result
    },
  },
  methods: {
    saveresult() {
      if (this.isDisabled) {
        this.isDisabled = false
        console.log(this.result)
        this.result.answer_question = this.result.question
        let tmp = {
          id: this.$store.state.uid,
          result: this.result,
        }
        AnswerApi.saveSurveyResponse(
          tmp,
          res => {
            console.log(res)
            if (res.data.data == 'success') {
              this.$swal({
                icon: 'success',
                title: '설문응답을 완료하였습니다.',
                target: '.component-2',
                showConfirmButton: false,
                timer: 1500,
              })
              this.isDisabled = true
              setTimeout(() => {
                this.$router.push({ name: 'SurveyCompleted' })
              }, 1000)
            }
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    checkMultiple(index, key) {
      if (this.result.question[index - 1].hasOwnProperty('answer')) {
        if (this.result.question[index - 1].answer.includes(key)) {
          this.result.question[index - 1].answer.splice(
            this.result.question[index - 1].answer.indexOf(key),
            1,
          )
        } else {
          this.result.question[index - 1].answer.push(key)
        }
      } else {
        this.result.question[index - 1].answer = []
        this.result.question[index - 1].answer.push(key)
      }
      console.log(this.result)
    },
    checkSingle(index, key) {
      if (this.result.question[index - 1].hasOwnProperty('answer')) {
        if (this.result.question[index - 1].answer.length != 0) {
          this.result.question[index - 1].answer.pop()
          this.result.question[index - 1].answer.push(key)
        } else {
          this.result.question[index - 1].answer.push(key)
        }
      } else {
        this.result.question[index - 1].answer = []
        this.result.question[index - 1].answer.push(key)
      }
      console.log(this.result)
    },
    checkShort(index) {
      if (this.result.question[index - 1].hasOwnProperty('answer')) {
        this.result.question[index - 1].answer.pop()
        this.result.question[index - 1].answer.push(this.short[`${index}번`])
      } else {
        this.result.question[index - 1].answer = []
        this.result.question[index - 1].answer.push(this.short[`${index}번`])
      }
      console.log(this.result)
    },
  },
}
</script>
<style scoped>
.component-2 {
  position: relative;
}
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
