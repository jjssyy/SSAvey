<template>
  <v-app>
    <v-card width="1000" class="mx-auto">
      <v-toolbar color="#4E7AF5" dark>
        <v-toolbar-title>{{ this.items.data.title }}</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
      <v-list>
        <template v-for="(item, index) in items">
          <v-subheader v-if="item.title" :key="index"
            >{{ item.explain }} <br />시작 {{ item.start_date }} ~ 종료
            {{ item.start_date }}</v-subheader
          >

          <v-list-item v-for="(ques, index) in item.question" :key="index + 1">
            <v-list-item-content>
              <v-list-item-title>
                {{ ques.q_number }}. {{ ques.q_explanation }}
              </v-list-item-title>
              <v-radio-group v-if="ques.q_type == 'SINGLE'" class="mx-5">
                <v-radio
                  v-for="(answer, r_index) in ques.q_option"
                  :key="r_index + 2"
                  :label="`${answer.o_explanation}`"
                  :value="`${answer.o_number}`"
                  @click="checkSingle(index + 1, `${answer.o_number}`)"
                >
                </v-radio>
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
    {{ this.result }}
  </v-app>
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
    // shortrules: null,
    short: {},
    sid: '',
    shortvalue: '',
    result: {},
    items: [],
  }),
  // mounted() {
  //   this.shortrules = [v => v.length <= 20 || '최대 20자까지만 입력해주세요.']
  // },
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
      console.log(this.result)
      let tmp = {
        id: this.$store.state.uid,
        result: this.result,
      }
      AnswerApi.saveSurveyResponse(
        tmp,
        res => {
          console.log(res)
        },
        err => {
          console.log(err)
        },
      )
    },
    checkMultiple(index, key) {
      if (this.result.hasOwnProperty(`${index}번`)) {
        if (this.result[`${index}번`].includes(key)) {
          this.result[`${index}번`].splice(
            this.result[`${index}번`].indexOf(key),
            1,
          )
        } else {
          this.result[`${index}번`].push(key)
        }
      } else {
        this.result[`${index}번`] = []
        this.result[`${index}번`].push(key)
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
      console.log(this.result.question[index - 1])
    },
    checkShort(index) {
      if (this.result.hasOwnProperty(`${index}번`)) {
        this.result[`${index}번`].pop()
        this.result[`${index}번`].push(this.short[`${index}번`])
      } else {
        this.result[`${index}번`] = []
        this.result[`${index}번`].push(this.short[`${index}번`])
      }
      console.log(this.result)
    },
  },
}
</script>
