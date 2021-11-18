<template>
  <div
    class=".
  component-2"
  >
    <v-app class="notosanskr">
      <v-card width="1000" class="mx-auto">
        <v-toolbar color="#4E7AF5" dark>
          <v-toolbar-title>{{ items.data.title }}</v-toolbar-title>
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
                  <span
                    v-if="requiredCheck[index].show"
                    class="notice-question"
                  >
                    필수응답 문항
                  </span>
                </v-list-item-title>
                <v-radio-group v-if="ques.q_type == 'SINGLE'" class="mx-5">
                  <div
                    v-for="(answer, r_index) in ques.q_option"
                    :key="r_index + 2"
                  >
                    <div v-if="!answer.short_answer" class="item-select">
                      <div class="input-center">
                        <input
                          type="radio"
                          :name="`SINGLE${index}`"
                          @click="checkSingle(index + 1, `${answer.o_number}`)"
                        />
                      </div>
                      <p class="center">{{ answer.o_explanation }}</p>
                    </div>
                    <div v-if="answer.short_answer" class="item-select">
                      <div class="input-center">
                        <input
                          type="radio"
                          :name="`SINGLE${index}`"
                          @click="checkSingle(index + 1, `${answer.o_number}`)"
                        />
                      </div>
                      <p class="center">
                        {{ answer.o_explanation }}
                      </p>
                    </div>
                    <div v-if="answer.short_answer" class="item-select">
                      <textarea
                        :ref="index"
                        cols="30"
                        rows="5"
                        class="short-text"
                        :class="{ selectText: !checkShortData[index].show }"
                        type="text"
                        :disabled="checkShortData[index].show"
                        v-model="shortData[index].show"
                      ></textarea>
                    </div>
                  </div>
                </v-radio-group>
                <v-container class="mx-2" v-if="ques.q_type == 'MULTIPLE'">
                  <div
                    v-for="(answer, c_index) in ques.q_option"
                    :key="c_index + 2"
                  >
                    <div v-if="!answer.short_answer" class="item-select">
                      <div class="input-center">
                        <input
                          type="checkbox"
                          :name="`MULTIPLE${index}`"
                          @click="
                            checkMultiple(index + 1, `${answer.o_number}`)
                          "
                        />
                      </div>
                      <p class="center">{{ answer.o_explanation }}</p>
                    </div>
                    <div v-if="answer.short_answer" class="item-select">
                      <div class="input-center">
                        <input
                          type="checkbox"
                          :name="`MULTIPLE${index}`"
                          @click="
                            checkMultiple(index + 1, `${answer.o_number}`)
                          "
                        />
                      </div>
                      <p class="center">{{ answer.o_explanation }}</p>
                    </div>
                    <div v-if="answer.short_answer" class="item-select">
                      <textarea
                        :ref="index"
                        cols="30"
                        rows="5"
                        class="short-text"
                        :class="{ selectText: !checkShortData[index].show }"
                        type="text"
                        :disabled="checkShortData[index].show"
                        v-model="shortData[index].show"
                      ></textarea>
                    </div>
                  </div>
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
          <v-btn
            @click="saveresult"
            text
            color="primary accent-4"
            :disabled="isDisabled"
          >
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
import headerInfo from '@/api/header.js'
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
    isDisabled: false,
    shortData: [],
    checkShortData: [],
    findShortIndex: [],
    requiredCheck: [],
  }),
  created() {
    console.log(this.$route.params.sid)
    SurveyApi.getSurvey(
      this.$route.params.sid,
      res => {
        this.items = res.data
        this.result = res.data.data
        console.log('hi')
        console.log(this.result)
        this.initInput()
        for (let i = 0; i < this.result.question.length; i++) {
          this.requiredCheck.push({ show: null })
        }
        this.result.question.forEach((element, index) => {
          if (element.required) {
            this.requiredCheck[index].show = true
          } else {
            this.requiredCheck[index].show = false
          }
        })
      },
      err => {
        console.log(err)
      },
    )
  },
  computed: {
    returnShortData() {
      return this.checkShortData
    },
  },
  methods: {
    initInput() {
      // 기타의 입력값이 들어온다.
      for (let i = 0; i < this.result.question.length; i++) {
        this.shortData.push({ show: '' })
      }
      // 기타의 disabled true, false 여부를 저장한다.(기타가 1개 있다고 가정)
      for (let i = 0; i < this.result.question.length; i++) {
        this.checkShortData.push({ show: true })
      }
      this.findShortIndex = Array.from(
        { length: this.result.question.length },
        () => -1,
      )
      // 기타의 q_option의 몇번째 index에 위치한지 검사한다. 없으면 -1
      this.result.question.forEach((element, index) => {
        if (element.q_type != 'SHORT') {
          element.q_option.forEach((element2, index2) => {
            if (element2.short_answer) {
              this.findShortIndex[index] = index2
            }
          })
        }
      })
      console.log(this.shortData)
      console.log(this.checkShortData)
      console.log(this.findShortIndex)
    },
    saveresult() {
      this.isDisabled = true
      if (this.isDisabled) {
        // this.isDisabled = false
        console.log(this.result)
        // 기타 포함된 question 조작들어감
        // question만큼 반복문 돌아
        for (let i = 0; i < this.result.question.length; i++) {
          // 그 question이 기타가 있냐?
          if (this.findShortIndex[i] != -1) {
            // 있으면 그 question 기타 index가 answer에 있는지 찾아
            if (
              this.result.question[i].answer.includes(
                String(this.findShortIndex[i] + 1),
              )
            ) {
              // 기타가 있네? 그러면 해당 index에 this.shortData[i] 에 있는거 넣어
              this.result.question[i].answer[
                this.result.question[i].answer.indexOf(
                  String(this.findShortIndex[i] + 1),
                )
              ] = this.shortData[i].show
            }
          }
        }
        console.log('최종!')
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
              headerInfo.usercount(
                this.$store.state.uid,
                res => {
                  this.$store.commit('setCount', res.data.data)
                  this.count = this.$store.state.count
                  setTimeout(() => {
                    this.$router.push({ name: 'SurveyCompleted' })
                  }, 1000)
                },
                err => {
                  console.log(err)
                },
              )
            }
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    checkMultiple(index, key) {
      console.log('mult', index, key) // index는 문항번호, key는 선택지번호
      // 해당 문항이 기타가 있냐?
      console.log(this.findShortIndex[index - 1])
      if (this.findShortIndex[index - 1] != -1) {
        console.log('ㅎ2')
        // key는 기타의 선택지 번호와 같냐?
        if (parseInt(key) - 1 == this.findShortIndex[index - 1]) {
          console.log('ㅎ3')
          // 지금 해당 문항의 input 비활성화되어있냐?
          if (this.checkShortData[index - 1].show) {
            console.log('ㅎ4')
            this.checkShortData[index - 1].show = false
            console.log(this.checkShortData.show)
          } else {
            // 비활성화 시켜주기
            console.log('ㅎ5')
            this.checkShortData[index - 1].show = true
            // 데이터 날리기
            this.shortData[index - 1].show = ''
          }
        }
      }
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
      console.log('여기', this.result)
    },
    checkSingle(index, key) {
      console.log('sing', index, key) // index는 문항번호, key는 선택지번호
      // 해당 문항이 기타가 있냐?
      console.log(this.findShortIndex[index - 1])
      if (this.findShortIndex[index - 1] != -1) {
        console.log('ㅎ2')
        // key는 기타의 선택지 번호와 같냐?
        if (parseInt(key) - 1 == this.findShortIndex[index - 1]) {
          console.log('ㅎ3')
          // 지금 해당 문항의 input 비활성화되어있냐?
          if (this.checkShortData[index - 1].show) {
            console.log('ㅎ4')
            this.checkShortData[index - 1].show = false
            console.log(this.$refs[index - 1])
            // this.$refs[index - 1][0].click()
            console.log(this.checkShortData.show)
          }
        } else {
          // 비활성화 시켜주기
          console.log('ㅎ5')
          this.checkShortData[index - 1].show = true
          // 데이터 날리기
          this.shortData[index - 1].show = ''
        }
      }
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
      console.log('여기', this.result)
    },
    checkShort(index) {
      if (this.result.question[index - 1].hasOwnProperty('answer')) {
        this.result.question[index - 1].answer.pop()
        this.result.question[index - 1].answer.push(this.short[`${index}번`])
      } else {
        this.result.question[index - 1].answer = []
        this.result.question[index - 1].answer.push(this.short[`${index}번`])
      }
      console.log(this.result.question)
    },
  },
}
</script>
<style scoped src="./../css/survey/survey/survey-answer.css"></style>
